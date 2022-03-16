package com.cuetrans.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class OsUtils {

	public static String getOsName() {
		return System.getProperty("os.name");
	}

	public static String getCurrentDir() {
		return System.getProperty("user.dir");
	}

	public static String getMacAddress() {
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			// System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			// System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			// System.out.println(sb.toString());

			return (sb.toString());

		} catch (UnknownHostException e) {

			return null;

		} catch (SocketException e) {

			return null;
		}

	}

	public static String getRandomNumber() {
		return String.valueOf(System.currentTimeMillis());
	}

}
