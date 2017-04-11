package com.celloud.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 获取本地ip地址的工具类
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年3月9日下午3:26:19
 * @version Revision: 1.0
 */
public class LocalIpAddressUtil {

	/**
	 * 获取本地ip地址，有可能会有多个地址, 若有多个网卡则会搜集多个网卡的ip地址
	 */
	public static Set<InetAddress> getLocalAddresses() {
		return resolveLocalNetworks().keySet();
	}

	/**
	 * 根据网卡名获取ip地址
	 * 
	 * @param name
	 * @return
	 */
	public static String getLocalArress(String name) {
		Enumeration<NetworkInterface> ns = null;
		try {
			ns = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// ignored...
		}
		while (ns != null && ns.hasMoreElements()) {
			NetworkInterface n = ns.nextElement();
			if (n.getName().equals(name)) {
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

	/**
	 * 获取所有的网卡接口
	 * 
	 * @return
	 */
	public static Map<InetAddress, NetworkInterface> resolveLocalNetworks() {
		Map<InetAddress, NetworkInterface> networks = new HashMap<>();
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
					networks.put(i, n);
			}
		}
		return networks;
	}

	/**
	 * 获取所有的ip地址
	 * 
	 * @return
	 */
	public static Set<String> getLocalIps() {
		Set<InetAddress> addrs = getLocalAddresses();
		Set<String> ret = new HashSet<String>();
		for (InetAddress addr : addrs)
			ret.add(addr.getHostAddress());
		return ret;
	}

	/**
	 * 判断给定的ip是不是一个特殊的ip地址
	 * 
	 * @param ip
	 * @return
	 */
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
