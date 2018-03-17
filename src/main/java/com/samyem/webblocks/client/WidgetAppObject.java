package com.samyem.webblocks.client;

import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.shared.AppObject;

public class WidgetAppObject<W extends Widget> {
	private AppObject appObject;

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

	public AppObject getAppObject() {
		return appObject;
	}

	public void setAppObject(AppObject appObject) {
		this.appObject = appObject;
	}

}
