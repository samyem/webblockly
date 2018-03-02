package com.samyem.webblocks.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class WebBlocks implements EntryPoint {
	public static final ApplicationServiceAsync appService = GWT.create(ApplicationService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DesignerPage mainPanel = new DesignerPage();
		RootLayoutPanel.get().add(mainPanel);
	}
}
