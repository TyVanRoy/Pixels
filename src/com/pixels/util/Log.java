package com.pixels.util;

import java.io.PrintStream;
import java.util.Date;

public abstract class Log {
	private static final PrintStream out = System.out;
	private static String log = "";

	public static void log(String input){
		Date date = new Date();
		out.println(date + ": " + input);
		log += date + ": " + input;
	}
	
	public static void log(int input){
		Date date = new Date();
		out.println(date + ": " + input);
		log += date + ": " + input;
	}

	public static String getLog(){
		return log;
	}
}
