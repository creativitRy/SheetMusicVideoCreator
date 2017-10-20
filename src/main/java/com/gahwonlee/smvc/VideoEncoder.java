package com.gahwonlee.smvc;

import io.humble.video.*;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;

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
 */
public class VideoEncoder {
	private final File file;
	private final VideoProperties properties;
	/**
	 * key = what time should this frame start showing?
	 * value = what image?
	 */
	private TreeMap<Rational, BufferedImage> frames;
	
	public VideoEncoder(File file, TreeMap<Rational, BufferedImage> frames, VideoProperties properties) {
		this.file = file;
		this.frames = frames;
		this.properties = properties;
	}
	
	public void run() throws IOException, InterruptedException {
		Muxer muxer = Muxer.make(file.getName(), null, null);
		
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
		muxer.open(null, null);
		
		/* Next, we need to make sure we have the right MediaPicture format objects
		  to encode data with. Java (and most on-screen graphics programs) use some
		  variant of Red-Green-Blue image encoding (a.k.a. RGB or BGR). Most video
		  codecs use some variant of YCrCb formatting. So we're going to have to
		  convert. To do that, we'll introduce a MediaPictureConverter object later. object.
		 */
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
		long currentFrame = 0;
		while (iterator.hasNext()) {
			Map.Entry<Rational, BufferedImage> entry = iterator.next();
			
			long amount = (long) Math.ceil(entry.getKey().divide(properties.getFramerate()).getDouble()
					- prev.getKey().divide(properties.getFramerate()).getDouble())
					+ currentFrame;
			
			// This is LIKELY not in YUV420P format, so we're going to convert it using some handy utilities.
			BufferedImage image = convertToType(prev.getValue(), BufferedImage.TYPE_3BYTE_BGR);
			if (converter == null)
				converter = MediaPictureConverterFactory.createConverter(image, picture);
			
			for (; currentFrame < amount; currentFrame++) {
				converter.toPicture(picture, image, currentFrame);
				do {
					encoder.encode(packet, picture);
					if (packet.isComplete())
						muxer.write(packet, false);
				} while (packet.isComplete());
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
	}
	
	/**
	 * Convert a {@link BufferedImage} of any type, to {@link BufferedImage} of a
	 * specified type. If the source image is the same type as the target type,
	 * then original image is returned, otherwise new image of the correct type is
	 * created and the content of the source image is copied into the new image.
	 *
	 * @param sourceImage the image to be converted
	 * @param targetType  the desired BufferedImage type
	 * @return a BufferedImage of the specifed target type.
	 * @see BufferedImage
	 */
	
	public static BufferedImage convertToType(BufferedImage sourceImage,
	                                          int targetType) {
		BufferedImage image;
		
		// if the source image is already the target type, return the source image
		
		if (sourceImage.getType() == targetType)
			image = sourceImage;
			
			// otherwise create a new image of the target type and draw the new
			// image
		
		else {
			image = new BufferedImage(sourceImage.getWidth(),
					sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}
		
		return image;
	}
}
