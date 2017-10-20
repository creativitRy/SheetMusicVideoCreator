package com.gahwonlee.smvc;

import io.humble.video.PixelFormat;
import io.humble.video.Rational;

/**
 * VideoProperties
 *
 * @author creativitRy
 */
public class VideoProperties {
	private Rational framerate;
	private int width;
	private int height;
	private PixelFormat.Type pixelformat;
	private Rational duration;
	
	public VideoProperties(Rational framerate, int width, int height, Rational duration) {
		this.framerate = framerate;
		this.width = width;
		this.height = height;
		pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
		this.duration = duration;
	}
	
	public Rational getFramerate() {
		return framerate;
	}
	
	public void setFramerate(Rational framerate) {
		this.framerate = framerate;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public PixelFormat.Type getPixelformat() {
		return pixelformat;
	}
	
	public void setPixelformat(PixelFormat.Type pixelformat) {
		this.pixelformat = pixelformat;
	}
	
	public Rational getDuration() {
		return duration;
	}
	
	public void setDuration(Rational duration) {
		this.duration = duration;
	}
}
