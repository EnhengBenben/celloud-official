package com.celloud.box.service;

import java.io.File;

import com.celloud.box.model.DataFile;

public interface BoxService {

	public void finish(Integer userId, String name, Integer tagId, String batch, Integer needSplit, File f);

	public DataFile save(Integer userId, String name, Integer tagId, String batch, Integer needSplit, File f);

	public DataFile newfile(DataFile file);

	public DataFile updatefile(DataFile file);

	public boolean uploaded(DataFile file);
}
