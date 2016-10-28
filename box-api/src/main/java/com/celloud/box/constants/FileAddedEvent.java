package com.celloud.box.constants;

import org.springframework.context.ApplicationEvent;

public class FileAddedEvent extends ApplicationEvent {

	public FileAddedEvent(String source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
