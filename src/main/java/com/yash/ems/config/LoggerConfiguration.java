package com.yash.ems.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerConfiguration {

	public static Logger getLogger(Class<?> classname) {
		
		return LoggerFactory.getLogger(classname);
	}
}

