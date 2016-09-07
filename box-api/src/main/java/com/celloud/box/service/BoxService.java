package com.celloud.box.service;

import java.io.File;

public interface BoxService {
	public void upload2oss(Integer userId,String dataKey, String ext, File f);
}
