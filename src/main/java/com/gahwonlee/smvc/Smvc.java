package com.gahwonlee.smvc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Smvc
 * @author creativitRy
 */
public class Smvc {
    private static Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) {
        System.out.println("hi");
        System.out.println(System.getProperty("java.class.path"));
        logger.error("hello world");
        logger.debug("hello world");
        test();
        System.out.println(System.getProperty("log4j.configurationFile"));
    }
    
    private static void test() {
        logger.error("lol");
    }
}
