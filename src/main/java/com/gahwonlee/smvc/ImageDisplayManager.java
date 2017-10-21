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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

/**
 * ImageFactory
 *
 * @author creativitRy
 */
public class ImageDisplayManager {
	private static final ImageDisplayManager INSTANCE = new ImageDisplayManager();
	
	public static ImageDisplayManager getInstance() {
		return INSTANCE;
	}
	
	private ArrayList<Image> images;
	private FlowPane imagePane;
	
	private ImageDisplayManager() {
		images = new ArrayList<>();
	}
	
	public void add(Image image)
	{
		add(images.size(), image);
	}
	
	public void add(int index, Image image)
	{
		images.add(index, image);
		imagePane.getChildren().add(index, new ImageView(image));
	}
	
	public int size()
	{
		return images.size();
	}
	
	protected void setImagePane(FlowPane imagePane) {
		this.imagePane = imagePane;
	}
}
