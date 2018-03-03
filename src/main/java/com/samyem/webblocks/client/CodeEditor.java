package com.samyem.webblocks.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;

/**
 * Component Pallet
 * 
 * @author samyem
 *
 */
public class CodeEditor extends ResizeLayoutPanel {
	private boolean initialized = false;

	public CodeEditor() {

		initialize();
	}

	private void initialize() {
		getElement().setId("codeEditor");
		addAttachHandler(event -> {
			if (event.isAttached() && this.isVisible()) {
				initializeUI();
			}
		});

		addResizeHandler(event -> {
			GWT.log("resized");
			if (isVisible()) {
				initializeUI();
			}
		});
	}

	public void initializeUI() {
		if (initialized) {
			return;
		}

		// setWidth("100%");
		// setHeight("500px");

		Timer timer = new Timer() {

			@Override
			public void run() {
				Element element = getElement();

				GWT.log("H:" + element.getClientHeight() + ", " + element.getClientWidth());

				createBlockly(element);
				clear();
			}
		};
		timer.schedule(100);

		initialized = true;
	}

	private native void createBlockly(Element element) /*-{
														$wnd.console.log(element);
														
														var toolBox = $wnd.document.getElementById('toolbox');
														$wnd.console.log();
														
														var workspacePlayground = $wnd.Blockly.inject("codeEditor", {
														toolbox : toolBox
														});
														}-*/;
}
