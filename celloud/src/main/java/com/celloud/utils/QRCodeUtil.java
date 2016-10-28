package com.celloud.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 * 创建二维码
 * 
 * @author lin
 * @date 2016年10月20日 下午2:57:42
 */
public class QRCodeUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	public static void createQRCode(String context, String path) {
		ByteArrayOutputStream out = QRCode.from(context).to(ImageType.PNG).stream();
		try {
			FileOutputStream fout = new FileOutputStream(new File(path));
			fout.write(out.toByteArray());
			fout.flush();
			fout.close();
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			logger.error("创建二维码失败！" + path + "文件不存在：" + e);
		} catch (IOException e) {
			logger.error("创建二维码失败！" + e);
		}
	}

	public static void main(String[] args) {
		createQRCode("https://www.celloud.cn/", "/Users/lin/aa.png");
	}
}
