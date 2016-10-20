package com.celloud.listener;

import org.springframework.context.ApplicationEvent;

public class FileDownloadErrorEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileDownloadErrorEvent(Object source) {
		super(source);
	}

}
