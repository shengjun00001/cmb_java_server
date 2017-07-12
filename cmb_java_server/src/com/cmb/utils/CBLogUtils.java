package com.cmb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CBLogUtils {

	static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static void Log(Object message) {
		System.out.println("["+format.format(new Date())+"] "+message);
	}
}
