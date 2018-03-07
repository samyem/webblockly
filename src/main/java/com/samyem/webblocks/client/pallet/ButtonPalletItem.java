package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.user.client.ui.Button;
import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.pallet.Property.PropertyApplier;

/**
 * Item that creates new labels
 * 
 * @author samyem
 *
 */
public class ButtonPalletItem extends ComponentPalletItem<Button> {
	private Button currentButton;

	public ButtonPalletItem(Consumer<ComponentPalletItem<Button>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);

		// properties
		addText();

		addStyleProp("Color", Style::getColor, Style::setColor);
		addStyleProp("Background Color", Style::getBackgroundColor, Style::setBackgroundColor);
		addStyleProp("Font Size", Style::getFontSize, (s, v) -> s.setFontSize(Double.parseDouble(v), Unit.PX));
		addStyleProp("Font Style", Style::getFontStyle, (s, v) -> s.setFontStyle(FontStyle.valueOf(v)));
	}

	private void addText() {
		PropertyApplier<String, Button> propApplier = (w, value) -> w.getWidget().setText(value);
		Function<WidgetAppObject<Button>, String> propInitializer = t -> t.getWidget().getText();
		TextProperty<Button> captionProp = new TextProperty<>("Text", propApplier, propInitializer);
		properties.add(captionProp);
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

		button.addClickHandler(e -> {
			e.stopPropagation();
		});

		button.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		button.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("action", "move");
			}
		});

		button.addDragEndHandler(new DragEndHandler() {
			@Override
			public void onDragEnd(DragEndEvent event) {
				NativeEvent nativeEvent = event.getNativeEvent();
				int x = nativeEvent.getClientX() - docLeft.get();
				int y = nativeEvent.getClientY() - docTop.get();

				Style style = button.getElement().getStyle();
				style.setTop(y, Unit.PX);
				style.setLeft(x, Unit.PX);
			}
		});

		WidgetAppObject<Button> wAppObj = new WidgetAppObject<>(button);
		return wAppObj;
	}

	@Override
	public String getKey() {
		return "button";
	}

}