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

		registerJNSICalls();
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

		if (workspace == null) {
			GWT.log("No code defined");
			return;
		}
		String code = getJS(workspace);
		GWT.log("code is: " + code);
		eval(code);
	}

	public static native void eval(String js)/*-{
		$wnd.eval(js);
	}-*/;

	private native String getJS(JavaScriptObject workspace) /*-{
		console.log(workspace);
		var code = $wnd.Blockly.JavaScript.workspaceToCode(workspace);
		return code;
	}-*/;

	/**
	 * Get the blocks as XML text
	 * 
	 * @return
	 */
	public String getCode() {
		return getNativeCode(workspace);
	}

	public native String getNativeCode(JavaScriptObject workspace) /*-{
		var xml = $wnd.Blockly.Xml.workspaceToDom(workspace);
		var xmlText = $wnd.Blockly.Xml.domToText(xml);
		return xmlText;
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

	/**
	 * Register JNSI callable GWT code as parsed eval because Direct calls are not
	 * possible in eval strings.
	 */
	private native void registerJNSICalls() /*-{
		$wnd.fromJava = function(arg) {
			$entry(@com.samyem.webblocks.client.DesignerPage::fromJava(Ljava/lang/String;)(arg))
		}
	}-*/;

}
