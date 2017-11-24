/*
 * SMVC
 * Copyright (C) 2017 creativitRy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gahwonlee.smvc;

import com.gahwonlee.smvc.utils.ImageUtil;
import com.gahwonlee.smvc.utils.TimeUtils;
import io.humble.video.*;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;
import org.apache.logging.log4j.LogManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * VideoEncoder
 *
 * @author creativitRy
 * @see #run()
 */
public class VideoEncoder implements Runnable {
	
	private final File file;
	private final VideoProperties properties;
	/**
	 * key = what time should this frame start showing?
	 * value = what image?
	 */
	private TreeMap<Rational, BufferedImage> frames;
	private long currentFrame;
	
	public VideoEncoder(File file, TreeMap<Rational, BufferedImage> frames, VideoProperties properties) {
		this.file = file;
		this.frames = frames;
		this.properties = properties;
		currentFrame = 0;
	}
	
	@Override
	public void run() {
		Muxer muxer = Muxer.make(file.getAbsolutePath(), null, null);
		
		//decide what type of codec to use to encode video. Muxers have limited sets of codecs they can use.
		//We're going to pick the first one that works
		final MuxerFormat format = muxer.getFormat();
		final Codec codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());
		//Now that we know what codec, we need to create an encoder
		Encoder encoder = Encoder.make(codec);
		
		// set specs
		encoder.setWidth(properties.getWidth());
		encoder.setHeight(properties.getHeight());
		encoder.setPixelFormat(properties.getPixelformat());
		encoder.setTimeBase(properties.getFramerate());
		if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
			encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);
		
		// Open the encoder.
		encoder.open(null, null);
		
		
		// Add this stream to the muxer.
		muxer.addNewStream(encoder);
		
		// And open the muxer for business.
		try {
			muxer.open(null, null);
		} catch (InterruptedException | IOException e) {
			throw new IllegalStateException(e);
		}
		
		// Next, we need to make sure we have the right MediaPicture format objects
		//to encode data with. Java (and most on-screen graphics programs) use some
		//variant of Red-Green-Blue image encoding (a.k.a. RGB or BGR). Most video
		//codecs use some variant of YCrCb formatting. So we're going to have to
		//convert. To do that, we'll introduce a MediaPictureConverter object later
		MediaPictureConverter converter = null;
		final MediaPicture picture = MediaPicture.make(
				encoder.getWidth(),
				encoder.getHeight(),
				properties.getPixelformat());
		picture.setTimeBase(properties.getFramerate());
		
		// ending
		frames.put(properties.getDuration(), frames.lastEntry().getValue());
		
		// We're going to encode and then write out any resulting packets.
		final MediaPacket packet = MediaPacket.make();
		Iterator<Map.Entry<Rational, BufferedImage>> iterator = frames.entrySet().iterator();
		Map.Entry<Rational, BufferedImage> prev = iterator.next();
		currentFrame = 0;
		while (iterator.hasNext()) {
			Map.Entry<Rational, BufferedImage> entry = iterator.next();
			System.out.println(entry.getKey());
			
			long amount = (long) Math.ceil(TimeUtils.timeToFrames(entry.getKey(), properties.getFramerate()).getDouble()
					- TimeUtils.timeToFrames(prev.getKey(), properties.getFramerate()).getDouble())
					+ currentFrame;
			
			// This is LIKELY not in YUV420P format, so we're going to convert it using some handy utilities.
			BufferedImage image = ImageUtil.convertToType(prev.getValue(), BufferedImage.TYPE_3BYTE_BGR);
			if (converter == null)
				converter = MediaPictureConverterFactory.createConverter(image, picture);
			
			// write frames
			while (currentFrame < amount) {
				converter.toPicture(picture, image, currentFrame);
				do {
					encoder.encode(packet, picture);
					if (packet.isComplete())
						muxer.write(packet, false);
				} while (packet.isComplete());
				currentFrame++;
			}
			
			prev = entry;
		}
		
		// Encoders, like decoders, sometimes cache pictures so it can do the right key-frame optimizations.
		//So, they need to be flushed as well. As with the decoders, the convention is to pass in a null
		//input until the output is not complete.
		do {
			encoder.encode(packet, null);
			if (packet.isComplete())
				muxer.write(packet, false);
		} while (packet.isComplete());
		
		//Finally, let's clean up after ourselves.
		muxer.close();
		
		LogManager.getLogger().debug("Done");
	}
	
	/**
	 * Get progress
	 *
	 * @return current frame being processed
	 * @see #getTotalFrames()
	 */
	public long getFramesLeft() {
		return currentFrame;
	}
	
	/**
	 * Get total number of frames to process
	 *
	 * @return total number of frames to process
	 * @see #getFramesLeft()
	 */
	public long getTotalFrames() {
		return (long) Math.ceil(TimeUtils.timeToFrames(properties.getDuration(), properties.getFramerate()).getDouble());
	}
}
