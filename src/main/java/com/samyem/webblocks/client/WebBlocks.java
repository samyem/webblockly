package com.samyem.webblocks.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class WebBlocks implements EntryPoint {
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DesignerPage mainPanel = new DesignerPage("Send");
		RootLayoutPanel.get().add(mainPanel);
	}
}
