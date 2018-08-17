package com.efive.services;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GetIpmac {

	public static void main(String args[]) {
		new GetIpmac().getipmacaddress();
	}

	public String getipmacaddress() {

		InetAddress ip;
		StringBuilder sb = new StringBuilder("");
		try {
			ip = InetAddress.getLocalHost();
			// System.out.println("Current IP address : " +
			// ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();

			// System.out.print("Current MAC address : ");
			if (null != mac) {
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i],
							(i < mac.length - 1) ? "-" : ""));
				}
			}
			// System.out.println("Current Mac Address : "+sb.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		if (sb.toString().trim().length() == 0) {
			sb.append("...");
		}

		return sb.toString();
	}
}
