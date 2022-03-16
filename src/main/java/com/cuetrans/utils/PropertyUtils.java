package com.cuetrans.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyUtils {
	

	private Properties properties;
	
	public PropertyUtils()
	{}

	/**
	 * Loads the property file from the project directory
	 *
	 * @throws FileNotFoundException
	 *             Signals that an attempt to open the file denoted by a
	 *             specified pathname has failed.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public PropertyUtils(String fileName)
	{
		properties = new Properties();
		try {
			properties.load(new FileInputStream(System.getProperty("user.dir")
					+ "/properties/file.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the value of the specified key in the property file
	 *
	 * @param key
	 *            the name of the key in the property file
	 * @return the value of the key in the property file
	 */
	public String getValue(final String key)
	{
		return properties.getProperty(key);
	}

	/**
	 * Returns the value of the key in the specified file
	 *
	 * @param fileName
	 *            the name of the property file
	 * @param key
	 *            the name of the key in the property file
	 * @return the value of the key in the property file
	 * @throws FileNotFoundException
	 *             Signals that an attempt to open the file denoted by a
	 *             specified pathname has failed.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public String getValue(final String fileName, final String key) throws FileNotFoundException,
			IOException
	{

		properties.load(new FileInputStream(System.getProperty("user.dir") + "/properties/"
				+ fileName));
		return properties.getProperty(key);
	}

	/**
	 * Returns the property object after loading the specified property file.
	 *
	 * @param fileName
	 *            the name of the property file
	 * @return the property object of the specified property file
	 * @throws FileNotFoundException
	 *             Signals that an attempt to open the file denoted by a
	 *             specified pathname has failed.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public Properties getProperty(final String fileName) throws FileNotFoundException, IOException
	{
		properties.load(new FileInputStream(System.getProperty("user.dir") + "/properties/"
				+ fileName));
		return properties;
	}
	
	public HashMap<String,String> getMap(String fileName)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			properties = new Properties();
			properties.load(new FileInputStream(fileName));
			for (String key : properties.stringPropertyNames()) {
			    String value = properties.getProperty(key);
			    map.put(key, value);
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return map;
	}



}
