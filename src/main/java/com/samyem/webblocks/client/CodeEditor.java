package com.samyem.webblocks.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
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

	private JavaScriptObject workspace;

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

		GWT.log("change test");

		Timer timer = new Timer() {

			@Override
			public void run() {
				Element element = getElement();

				GWT.log("H:" + element.getClientHeight() + ", " + element.getClientWidth());

				workspace = createBlockly(element);
				clear();
			}
		};
		timer.schedule(100);

		initialized = true;
	}

	public void run() {
		DesignerPage.clearConsole();
		String code = getCode(workspace);
		GWT.log(code);
		eval(code);
	}

	public static native void eval(String js)/*-{
		$wnd.eval(js);
	}-*/;

	private native String getCode(JavaScriptObject workspace) /*-{
		var code = $wnd.Blockly.JavaScript.workspaceToCode(workspace);
		return code;
	}-*/;

	private native JavaScriptObject createBlockly(Element element) /*-{
		var toolBox = $wnd.document.getElementById('toolbox');

		var workspace = $wnd.Blockly.inject("codeEditor", {
			toolbox : toolBox,
			zoom : {
				controls : true,
				wheel : true,
				startScale : 1.0,
				maxScale : 3,
				minScale : 0.3,
				scaleSpeed : 1.2
			},
			trashcan : true
		});

		return workspace;
	}-*/;
}
