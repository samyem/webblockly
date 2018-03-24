package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;
import com.samyem.webblocks.client.WidgetAppObject;

/**
 * Item that creates new labels
 * 
 * @author samyem
 *
 */
public class LabelPalletItem extends TextualPalletItem<Label> {
	private Label currentLabel;

	public LabelPalletItem(Consumer<ComponentPalletItem<Label>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);
	}

	@Override
	public WidgetAppObject<Label> createAppObject() {
		Label label = new Label("Text");

		label.addDoubleClickHandler(e -> {
			e.stopPropagation();

			Element element = label.getElement();
			element.setAttribute("contenteditable", "true");
			element.focus();
			consumerOfThis.accept(this);
			currentLabel = label;
		});

		WidgetAppObject<Label> appObj = new WidgetAppObject<>(label);
		return appObj;
	}

	@Override
	public void handleDocumentCanvasClick(ClickEvent event) {
		if (currentLabel != null) {
			currentLabel.getElement().setAttribute("contenteditable", "false");
			currentLabel = null;
		}
	}

	@Override
	public String getKey() {
		return "lbl";
	}

	@Override
	public String getFriendlyName() {
		return "Text";
	}

}