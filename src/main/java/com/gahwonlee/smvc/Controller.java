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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 * Controller
 *
 * @author creativitRy
 */
public class Controller {
	@FXML
	public FlowPane imagePane;
	@FXML
	public ScrollPane scrollPane;
	
	@FXML
	public void onAddImage(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Add image(s)");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.bmp"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp")
		);
		List<File> files = fileChooser.showOpenMultipleDialog(Smvc.getInstance().getPrimaryStage());
		
		if (files == null || files.isEmpty())
			return;
		
//		scrollPane.viewportBoundsProperty().addListener((arg0, arg1, arg2) -> {
//			Node content = scrollPane.getContent();
//			scrollPane.setFitToWidth(content.prefWidth(-1) < arg2.getWidth());
//			scrollPane.setFitToHeight(content.prefHeight(-1) < arg2.getHeight());
//		});
		ImageDisplayManager.getInstance().setImagePane(imagePane);
		
		for (File file : files) {
			ImageDisplayManager.getInstance().add(new Image(file.toURI().toString()));
		}
		
	}
}
