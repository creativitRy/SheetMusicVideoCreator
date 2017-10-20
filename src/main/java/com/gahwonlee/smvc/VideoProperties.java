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
