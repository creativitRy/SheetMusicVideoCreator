package com.gahwonlee.smvc;

import io.humble.video.Rational;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Smvc
 *
 * @author creativitRy
 */
public class Smvc extends Application {
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		System.out.println("hi");
		System.out.println(System.getProperty("java.class.path"));
		logger.error("hello world");
		logger.debug("hello world");
		test();
		System.out.println(System.getProperty("log4j.configurationFile"));
		
		primaryStage.show();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\creativitRy\\Documents\\minecraft\\maps\\_mymaps\\water\\processed"));
		File file = fileChooser.showOpenDialog(null);
		File file2 = fileChooser.showOpenDialog(null);
		File file3 = fileChooser.showOpenDialog(null);
		
		TreeMap<Rational, BufferedImage> map = new TreeMap<>(new RationalComparator());
		try {
			map.put(Rational.make(0, 1), ImageIO.read(file));
			map.put(Rational.make(1, 1), ImageIO.read(file2));
			map.put(Rational.make(3, 1), ImageIO.read(file3));
			
			VideoEncoder videoEncoder = new VideoEncoder(
					fileChooser.showSaveDialog(null),
					map,
					new VideoProperties(Rational.make(1, 24), 960, 540, Rational.make(6, 1)));
			videoEncoder.run();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void test() {
		logger.error("lol");
	}
}
