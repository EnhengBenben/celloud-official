package com.celloud.box.constants;

import org.springframework.context.ApplicationEvent;

public class FileUploadedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	public FileUploadedEvent(String source) {
		super(source);
	}

}
