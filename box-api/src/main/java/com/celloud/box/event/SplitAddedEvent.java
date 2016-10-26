package com.celloud.box.event;

import org.springframework.context.ApplicationEvent;

public class SplitAddedEvent extends ApplicationEvent {

	public SplitAddedEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
