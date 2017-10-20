package com.gahwonlee.smvc;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Smvc
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
    }
    
    
    private static void test() {
        logger.error("lol");
    }
}
