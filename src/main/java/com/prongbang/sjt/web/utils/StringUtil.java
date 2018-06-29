package com.prongbang.sjt.web.utils;

public class StringUtil {
	
	public static <T> String beanName(Class<T> c) {
		String className = c.getSimpleName();
		return ((className.charAt(0)+"").toLowerCase()) + className.substring(1);
	}
	
}
