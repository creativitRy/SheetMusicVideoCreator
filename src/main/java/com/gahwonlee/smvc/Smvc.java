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

import io.humble.video.Rational;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.TreeMap;

/**
 * Smvc
 *
 * @author creativitRy
 */
public class Smvc extends Application {
	private static Logger logger = LogManager.getLogger();
	
	private static Smvc INSTANCE;
	private Controller controller;
	
	public static Smvc getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private StackPane mainGui;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		INSTANCE = this;
		this.primaryStage = primaryStage;
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Main.fxml"));
			mainGui = loader.load();
			controller = loader.getController();
			controller.init();
			
			// Show the scene containing the root layout.
			Scene scene = new Scene(mainGui);
			primaryStage.setTitle("SMVC");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//todo https://github.com/artclarke/humble-video/blob/master/humble-video-demos/src/main/java/io/humble/video/demos/DecodeAndPlayAudio.java
		
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setInitialDirectory(new File("C:\\Users\\creativitRy\\Documents\\minecraft\\maps\\_mymaps\\water\\processed"));
//		//todo: https://stackoverflow.com/questions/36462710/bufferedimage-extract-subimage-with-same-data (last option of rendering)
//		TreeMap<Rational, BufferedImage> map = new TreeMap<>(new RationalComparator());
//		try {
//			File file = fileChooser.showOpenDialog(primaryStage);
//			if (file == null)
//				return;
//			map.put(Rational.make(0, 1), ImageIO.read(file));
//			File file2 = fileChooser.showOpenDialog(primaryStage);
//			if (file2 == null)
//				return;
//			map.put(Rational.make(1, 1), ImageIO.read(file2));
//			File file3 = fileChooser.showOpenDialog(primaryStage);
//			if (file3 == null)
//				return;
//			map.put(Rational.make(3, 1), ImageIO.read(file3));
//
//			VideoEncoder videoEncoder = new VideoEncoder(
//					fileChooser.showSaveDialog(primaryStage),
//					map,
//					new VideoProperties(Rational.make(1, 24), 960, 540, Rational.make(6, 1)));
//			videoEncoder.run();
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
