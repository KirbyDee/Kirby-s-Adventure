package com.kirbydee.utils;

public class Utils {

	public static final String FILE_DELIMS = "\\s+";
	
	private Utils() {
		// insurance
		throw new AssertionError();
	}
	
	public static void debug() {
		System.out.println("------------------------------");
	}
	
	public static void debug(String name, Object o) {
		System.out.println(name + " = " + o.toString());
	}
}
