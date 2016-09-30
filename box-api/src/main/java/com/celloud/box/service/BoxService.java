package com.celloud.box.service;

import java.io.File;

public interface BoxService {

	public void finish(Integer userId, String name, Integer tagId, String batch, Integer needSplit, File f);
}
