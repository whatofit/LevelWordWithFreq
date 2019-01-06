package com.level.demo;

//import java.util.logging.LogManager;
//import java.util.logging.Logger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4j2Test {
	public static void testLog4j() {
        try {  
            //Logger logger = LogManager.getLogger(Log4j2Test.class.getName());
        	Logger logger = LogManager.getLogger(Log4j2Test.class.getName());
//            String thing = "world";
//            logger.info("Hello, {}!", thing);
//            logger.debug("Got calculated value only if debug enabled: {}", () -> doSomeCalculation());
            
            logger.trace("this is trace");         
            logger.debug("this is debug");         
            logger.info("this is info");       
            logger.warn("this is warn");       
            logger.error("this is error"); 
            logger.fatal("this is fatal");
        } catch (Exception e) {  
            e.printStackTrace();  
        }      
    }
	
    private static Object doSomeCalculation() {
        // do some complicated calculation
		return null;
    }
    
	public static void main(String[] args) {
		 testLog4j();
	}

}
