package com.celloud.box.event;

import org.springframework.context.ApplicationEvent;

public class FileUploadErrorEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public FileUploadErrorEvent(String source) {
		super(source);
	}

}
