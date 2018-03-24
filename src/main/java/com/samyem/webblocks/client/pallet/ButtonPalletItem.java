package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.samyem.webblocks.client.WidgetAppObject;

/**
 * Item that creates new labels
 * 
 * @author samyem
 *
 */
public class ButtonPalletItem extends TextualPalletItem<Button> {
	private Button currentButton;

	public ButtonPalletItem(Consumer<ComponentPalletItem<Button>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);

		// properties
	}

	@Override
	public void handleDocumentCanvasClick(ClickEvent event) {
		if (currentButton != null) {
			currentButton.getElement().setAttribute("contenteditable", "false");
			currentButton = null;
		}
	}

	@Override
	public WidgetAppObject<Button> createAppObject() {
		Button button = new Button("Button");
		button.addKeyUpHandler(event -> event.preventDefault());

		button.addDoubleClickHandler(e -> {
			e.stopPropagation();

			Element element = button.getElement();
			element.setAttribute("contenteditable", "true");
			element.focus();
			consumerOfThis.accept(this);
			currentButton = button;
		});

		WidgetAppObject<Button> wAppObj = new WidgetAppObject<>(button);
		return wAppObj;
	}

	@Override
	public String getKey() {
		return "btn";
	}

	@Override
	public String getFriendlyName() {
		return "Button";
	}

}