/**    
 * @Title: RdpUtil.java  
 * @Package com.nova.utils   
 * @author summer    
 * @date 2012-6-26 上午10:59:50  
 * @version V1.0    
 */
package com.nova.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nova.sdo.Software;

/**
 * @ClassName: RdpUtil
 * @Description: (生成RDP配置文件)
 * @author summer
 * @date 2012-6-26 上午10:59:50
 * 
 */
public class RdpUtil {
	
	public static String getRdpInfo(Software software, String username,
			String password){
		//此处约定顺利：用户名;密码;软件入口;软件名称
		//String str = "host:"+software.getHost()+";username:"+username+";password:"+password+";softwareName:"+software.getSoftwareEntry();
		String str = username+";"+password+";"+software.getSoftwareEntry()+";"+software.getProcessName();
		return str;
	}

	/**  
	* @Title: getRdp  
	* @Description: (生产前端调用的rdp文件,密码经过加密处理)  
	* @param software
	* @param username
	* @param password
	* @return 
	* @param String  
	* @throws  
	*/
	public static String getRdp(Software software, String username,
			String password) {
		password = getRdpPassword(password);
		String str = "redirectclipboard:i:0" + "\r\n"
				+ "redirectposdevices:i:0" + "\r\n" + "redirectprinters:i:0"
				+ "\r\n" + "redirectcomports:i:0" + "\r\n"
				+ "redirectsmartcards:i:0" + "\r\n" + "redirectdrives:i:0"
				+ "\r\n" + "devicestoredirect:s:no" + "\r\n"
				+ "drivestoredirect:s:*" + "\r\n" + "redirectdrives:i:1"
				+ "\r\n" + "session bpp:i:32" + "\r\n" + "span monitors:i:1"
				+ "\r\n" + "prompt for credentials on client:i:0" + "\r\n"
				+ "remoteapplicationmode:i:1" + "\r\n" + "server port:i:3389"
				+ "\r\n" + "allow font smoothing:i:1" + "\r\n"
				+ "promptcredentialonce:i:0" + "\r\n"
				+ "authentication level:i:3" + "\r\n"
				+ "gatewayusagemethod:i:2" + "\r\n"
				+ "gatewayprofileusagemethod:i:0" + "\r\n"
				+ "gatewaycredentialssource:i:0" + "\r\n"
				+ "gatewayhostname:s:" + "\r\n" + "remoteapplicationcmdline:s:"
				+ "\r\n" + "full address:s:" + software.getHost() + "\r\n"
				+ "alternate shell:s:||"+ software.getSoftwareEntry() + "\r\n"
				+ "remoteapplicationprogram:s:||"+ software.getSoftwareEntry() + "\r\n"
				+ "remoteapplicationname:s:" + software.getSoftwareName()
				+ "\r\n" + "username:s:" + username + "\r\n" + "password 51:b:"
				+ password;
		str = escape(str);
		return str;
	}

	/**  
	* @Title: getRdp  
	* @Description: (生产rdp文件的例子)  
	* @return 
	* @param String  
	* @throws  
	*/  
	public static String getRdp() {
		String str = "redirectclipboard:i:0"
				+ "\r\n"
				+ "redirectposdevices:i:0"
				+ "\r\n"
				+ "redirectprinters:i:0"
				+ "\r\n"
				+ "redirectcomports:i:0"
				+ "\r\n"
				+ "redirectsmartcards:i:0"
				+ "\r\n"
				+ "redirectdrives:i:0"
				+ "\r\n"
				+ "devicestoredirect:s:no"
				+ "\r\n"
				+ "drivestoredirect:s:*"
				+ "\r\n"
				+ "redirectdrives:i:1"
				+ "\r\n"
				+ "session bpp:i:32"
				+ "\r\n"
				+ "span monitors:i:1"
				+ "\r\n"
				+ "prompt for credentials on client:i:0"
				+ "\r\n"
				+ "remoteapplicationmode:i:1"
				+ "\r\n"
				+ "server port:i:3389"
				+ "\r\n"
				+ "allow font smoothing:i:1"
				+ "\r\n"
				+ "promptcredentialonce:i:0"
				+ "\r\n"
				+ "authentication level:i:3"
				+ "\r\n"
				+ "gatewayusagemethod:i:2"
				+ "\r\n"
				+ "gatewayprofileusagemethod:i:0"
				+ "\r\n"
				+ "gatewaycredentialssource:i:0"
				+ "\r\n"
				+ "gatewayhostname:s:"
				+ "\r\n"
				+ "remoteapplicationcmdline:s:"
				+ "\r\n"
				+ "full address:s:192.168.10.129:3389"
				+ "\r\n"
				+ "alternate shell:s:||M5b3"
				+ "\r\n"
				+ "remoteapplicationprogram:s:||M5b3"
				+ "\r\n"
				+ "remoteapplicationname:s:计算器"
				+ "\r\n"
				+ "username:s:test2"
				+ "\r\n"
				+ "password 51:b:01000000D08C9DDF0115D1118C7A00C04FC297EB01000000DAFD50810652F54DA7401830F9D5D95504000000080000007000730077000000106600000001000020000000ACF1B5B4F24587A4BD667560859B6ECE88EC9E24CDB78FA4A8C6C03EED9261A0000000000E8000000002000020000000248D71BEC721AC3055EA8E29475013E1C22688CDC218AF212D62AAA8CB48CEAD10000000794A1998729E5176EAEA2F6F62359EEA4000000040AA5C67C3643FCCC1208111BF4B74975BFC74BBEA33DBC2074EDE4DBD123A2EF03E7C150A71F73D05277A9D97A97978A1D63F06108217A87777D7F3FEEB67E5";
		return str;
	}

	/**  
	* @Title: getRdpPassword  
	* @Description: (获取RDP文件密码)  
	* @param password
	* @return 
	* @param String  
	* @throws  
	*/  
	public static String getRdpPassword(String password) {
		Runtime r = Runtime.getRuntime();
		String command = "d:/cryptRDP5.exe " + password;
		String str = "";
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			Process p = r.exec(command);
			isr = new InputStreamReader(p.getInputStream());
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				str += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	private final static String[] hex = { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
			"1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
			"27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
			"32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
			"3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
			"48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
			"5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
			"69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
			"74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
			"7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
			"8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
			"95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
			"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
			"AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
			"B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
			"CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
			"D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
			"E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
			"ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
			"F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };
	private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
			0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	/**  
	* @Title: escape  
	* @Description: (密码加密算法)  
	* @param s
	* @return 
	* @param String  
	* @throws  
	*/  
	public static String escape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			if (ch == ' ') {
				// space : map to '+'  or map to '20%'
				sbuf.append('%');
				sbuf.append('2');
				sbuf.append('0');
			} else if ('A' <= ch && ch <= 'Z') {// 'A'..'Z' : as it was
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') {// 'a'..'z' : as it was
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') {// '0'..'9' : as it was
				sbuf.append((char) ch);
			} else if (ch == '-'
					|| ch == '_' // unreserved : as it was
					|| ch == '.' || ch == '!' || ch == '~' || ch == '*'
					|| ch == '\'' || ch == '(' || ch == ')') {
				sbuf.append((char) ch);
			} else if (ch <= 0x007F) {// other ASCII : map to %XX
				sbuf.append('%');
				sbuf.append(hex[ch]);
			} else {// unicode : map to %uXXXX
				sbuf.append('%');
				sbuf.append('u');
				sbuf.append(hex[(ch >>> 8)]);
				sbuf.append(hex[(0x00FF & ch)]);
			}
		}
		return sbuf.toString();
	}

	/**  
	* @Title: unescape  
	* @Description: (密码解密算法)  
	* @param s
	* @return 
	* @param String  
	* @throws  
	*/  
	public static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int i = 0;
		int len = s.length();
		while (i < len) {
			int ch = s.charAt(i);
			if (ch == '+') {// + : map to ' '
				sbuf.append(' ');
			} else if ('A' <= ch && ch <= 'Z') {// 'A'..'Z' : as it was
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') {// 'a'..'z' : as it was
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') {// '0'..'9' : as it was
				sbuf.append((char) ch);
			} else if (ch == '-'
					|| ch == '_' // unreserved : as it was
					|| ch == '.' || ch == '!' || ch == '~' || ch == '*'
					|| ch == '\'' || ch == '(' || ch == ')') {
				sbuf.append((char) ch);
			} else if (ch == '%') {
				int cint = 0;
				if ('u' != s.charAt(i + 1)) { // %XX : map to ascii(XX)
					cint = (cint << 4) | val[s.charAt(i + 1)];
					cint = (cint << 4) | val[s.charAt(i + 2)];
					i += 2;
				} else {
					cint = (cint << 4) | val[s.charAt(i + 2)];
					cint = (cint << 4) | val[s.charAt(i + 3)];
					cint = (cint << 4) | val[s.charAt(i + 4)];
					cint = (cint << 4) | val[s.charAt(i + 5)];
					i += 5;
				}
				sbuf.append((char) cint);
			}
			i++;
		}
		return sbuf.toString();
	}

	public static void main(String[] args) {
		System.out.println(RdpUtil.getRdpPassword("test"));
	}

}
