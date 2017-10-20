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

import java.awt.image.BufferedImage;

/**
 * ImageUtil
 *
 * @author creativitRy
 */
public class ImageUtil {
	private ImageUtil() {
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
	public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		// if the source image is already the target type, return the source image
		if (sourceImage.getType() == targetType)
			return sourceImage;
		
		// otherwise create a new image of the target type and draw the new image
		BufferedImage image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		return image;
	}
}
