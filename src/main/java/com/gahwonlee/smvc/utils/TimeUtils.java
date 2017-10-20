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
