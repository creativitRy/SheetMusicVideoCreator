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

package com.gahwonlee.smvc.utils;

import io.humble.video.Rational;

/**
 * TimeUtils
 *
 * @author creativitRy
 */
public class TimeUtils {
	private TimeUtils() {
	}
	
	/**
	 * Converts a time duration to the number of frames
	 * @param time duration of time to convert
	 * @param framerate duration of each frame (1 / fps)
	 * @return number of frames
	 */
	public static Rational timeToFrames(Rational time, Rational framerate){
		return time.divide(framerate);
	}
	
	/**
	 * Converts the number of frames to a time duration
	 * @param numFrames number of frames to convert
	 * @param framerate duration of each frame (1 / fps)
	 * @return duration of time
	 */
	public static Rational framesToTime(Rational numFrames, Rational framerate) {
		return framerate.multiply(numFrames);
	}
}
