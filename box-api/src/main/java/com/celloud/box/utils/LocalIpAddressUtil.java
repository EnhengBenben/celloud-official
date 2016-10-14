package com.celloud.box.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LocalIpAddressUtil {

	/**
	 * 获取本地ip地址，有可能会有多个地址, 若有多个网卡则会搜集多个网卡的ip地址
	 */
	public static Set<InetAddress> getLocalAddresses() {
		return resolveLocalNetworks().keySet();
	}

	public static String getLocalArress(String displayName) {
		Enumeration<NetworkInterface> ns = null;
		try {
			ns = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// ignored...
		}
		while (ns != null && ns.hasMoreElements()) {
			NetworkInterface n = ns.nextElement();
			if (n.getDisplayName().equals(displayName)) {
				Enumeration<InetAddress> is = n.getInetAddresses();
				while (is.hasMoreElements()) {
					InetAddress i = is.nextElement();
					if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
							&& !isSpecialIp(i.getHostAddress())) {
						return i.getHostAddress();
					}
				}
			}
		}
		return null;
	}

	public static Map<InetAddress, String> resolveLocalNetworks() {
		Map<InetAddress, String> networks = new HashMap<>();
		Enumeration<NetworkInterface> ns = null;
		try {
			ns = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// ignored...
		}
		while (ns != null && ns.hasMoreElements()) {
			NetworkInterface n = ns.nextElement();
			Enumeration<InetAddress> is = n.getInetAddresses();
			while (is.hasMoreElements()) {
				InetAddress i = is.nextElement();
				if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
						&& !isSpecialIp(i.getHostAddress()))
					networks.put(i, n.getDisplayName());
			}
		}
		return networks;
	}

	public static Set<String> getLocalIps() {
		Set<InetAddress> addrs = getLocalAddresses();
		Set<String> ret = new HashSet<String>();
		for (InetAddress addr : addrs)
			ret.add(addr.getHostAddress());
		return ret;
	}

	private static boolean isSpecialIp(String ip) {
		if (ip.contains(":"))
			return true;
		if (ip.startsWith("127."))
			return true;
		if (ip.startsWith("169.254."))
			return true;
		if (ip.equals("255.255.255.255"))
			return true;
		return false;
	}
}
