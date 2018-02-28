package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base pallet item
 * 
 * @author samye
 *
 */
public abstract class ComponentPalletItem {
	protected Consumer<ComponentPalletItem> consumerOfThis;

	protected Supplier<Integer> docLeft, docTop;

	public ComponentPalletItem(Consumer<ComponentPalletItem> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		this.consumerOfThis = consumerOfThis;
		this.docTop = docTop;
		this.docLeft = docLeft;
	}

	public abstract Widget createWidget();

	public abstract void handleDocumentCanvasClick(ClickEvent event);
}