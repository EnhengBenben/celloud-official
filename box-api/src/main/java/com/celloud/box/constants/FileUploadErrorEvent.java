package com.celloud.box.constants;

import org.springframework.context.ApplicationEvent;

public class FileUploadErrorEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public FileUploadErrorEvent(String source) {
		super(source);
	}

}
