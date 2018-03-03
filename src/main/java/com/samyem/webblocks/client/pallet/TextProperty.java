package com.samyem.webblocks.client.pallet;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextProperty extends Property<String> {

	@Override
	public Widget getValueEditor() {
		TextBox input = new TextBox();
		return input;
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStringValue(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub

	}

}
