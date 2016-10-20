package com.celloud.listener;

import org.springframework.context.ApplicationEvent;

public class FileDownloadedEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileDownloadedEvent(Object source) {
		super(source);
	}

}
