package com.samyem.webblocks.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Main application entry
 * 
 * @author samye
 *
 */
public class WebBlocks implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DesignerPage mainPanel = new DesignerPage();
		RootLayoutPanel.get().add(mainPanel);
	}
}
