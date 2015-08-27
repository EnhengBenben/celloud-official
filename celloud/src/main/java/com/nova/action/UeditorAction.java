package com.nova.action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("celloud-default")
@Action("ueditorueditor")
@Results({})
public class UeditorAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private File ueditorUpfile;

    /**
     * ueditor上传图片
     * 
     * @return
     */
    public String uploadImage() {
	return SUCCESS;
    }

    public File getUeditorUpfile() {
	return ueditorUpfile;
    }

    public void setUeditorUpfile(File ueditorUpfile) {
	this.ueditorUpfile = ueditorUpfile;
    }
}