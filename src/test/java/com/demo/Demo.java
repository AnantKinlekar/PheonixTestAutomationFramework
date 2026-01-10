package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Demo {
    private static final Logger logger = LogManager.getLogger(Demo.class);
    public static void main(String[] args) {
        logger.info("Inside main method");
        int a = 10;
        logger.info("Value of a {} ", a);
        int b =20;
        logger.info("Value of b {} ", b);
        int result = a+b;
        logger.info("Value of result {} ", result);

    }
}
