package com.samyem.webblocks.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Window to test the application
 * 
 * @author samye
 *
 */
public class TestWindow extends VerticalPanel {
	private CodeEditor codeEditor;

	public void start(Element rootElement) {
		// render the UI
		clear();

		String appContent = rootElement.getInnerHTML();
		GWT.log(appContent);

		HTMLPanel html = new HTMLPanel(appContent);
		add(html);

		// and then run the script
		codeEditor.run();
	}

	public void setCodeEditor(CodeEditor codeEditor) {
		this.codeEditor = codeEditor;
	}
}
