package com.gahwonlee.smvc;

import io.humble.video.Rational;

import java.util.Comparator;

/**
 * RationalComparator
 *
 * @author creativitRy
 */
public class RationalComparator implements Comparator<Rational> {
	@Override
	public int compare(Rational o1, Rational o2) {
		return o1.compareTo(o2);
	}
}
