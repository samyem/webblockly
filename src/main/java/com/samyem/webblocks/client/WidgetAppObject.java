package com.samyem.webblocks.client;

import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.shared.AppObject;

public class WidgetAppObject<W extends Widget> extends AppObject {
	private static final long serialVersionUID = -5564375845991778490L;

	private W widget;

	public WidgetAppObject(W widget) {
		this.widget = widget;
	}

	public W getWidget() {
		return widget;
	}

	public void setWidget(W widget) {
		this.widget = widget;
	}

}
