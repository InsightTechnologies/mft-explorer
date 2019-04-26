package com.mqmft.rest.api.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class LoadGlobalProperties {

	private static Properties globalProperties = null;

	@SuppressWarnings("rawtypes")
	public static void refreshGlobalProperties() {
		if (globalProperties == null) {
			globalProperties = new Properties();
		}

		try {
			InputStream inputStream = new FileInputStream(System.getProperty("spring.global.filepath"));
			globalProperties.clear();

			globalProperties.load(inputStream);
			Enumeration e = globalProperties.propertyNames();
			while (e.hasMoreElements()) {
				String property = (String) e.nextElement();
				System.setProperty(property, globalProperties.getProperty(property));

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
