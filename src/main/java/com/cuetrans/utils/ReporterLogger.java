package com.cuetrans.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


public class ReporterLogger {

	 static{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss");
	        System.setProperty("current.date", dateFormat.format(new Date()));
	    }
	 
	public static Logger log4j = Logger.getLogger("devpinoyLogger");

	public static void loggerInfo(String message) {
		log4j.info(message);
	}

	public static void loggerWarn(String message) {
		log4j.warn(message);
	}

	public static void loggerDebug(String message) {
		log4j.debug(message);
	}

	public static void loggerFatal(String message) {
		log4j.fatal(message);
	}

	public static void loggerError(String message) {
		log4j.error(message);
	}

}
