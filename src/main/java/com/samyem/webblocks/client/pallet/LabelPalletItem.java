package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Label;
import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.pallet.Property.PropertyApplier;
import com.samyem.webblocks.client.pallet.Property.SetterGenerator;

/**
 * Item that creates new labels
 * 
 * @author samyem
 *
 */
public class LabelPalletItem extends ComponentPalletItem<Label> {
	private Label currentLabel;

	public LabelPalletItem(Consumer<ComponentPalletItem<Label>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);

		addText();

		addStyleProp("Color", Style::getColor, Style::setColor, "color");
		addStyleProp("Background Color", Style::getBackgroundColor, Style::setBackgroundColor, "background-color");
		addStyleProp("Font Size", Style::getFontSize, (s, v) -> s.setFontSize(Double.parseDouble(v), Unit.PX),
				"font-size");
		addStyleProp("Font Style", Style::getFontStyle, (s, v) -> s.setFontStyle(FontStyle.valueOf(v)), "font-style");
	}

	private void addText() {
		PropertyApplier<String, Label> propApplier = (w, value) -> w.getWidget().setText(value);
		Function<WidgetAppObject<Label>, String> propInitializer = t -> t.getWidget().getText();
		SetterGenerator setterProps = value -> "text(" + value + ")";
		TextProperty<Label> captionProp = new TextProperty<>("Text", propApplier, propInitializer, setterProps);
		addProperty(captionProp);
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

		label.addClickHandler(e -> {
			e.stopPropagation();
		});

		label.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		label.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				GWT.log("drag has started");
				event.setData("action", "move");
			}
		});

		label.addDragEndHandler(new DragEndHandler() {
			@Override
			public void onDragEnd(DragEndEvent event) {
				NativeEvent nativeEvent = event.getNativeEvent();
				int x = nativeEvent.getClientX() - docLeft.get();
				int y = nativeEvent.getClientY() - docTop.get();

				Style style = label.getElement().getStyle();
				style.setTop(y, Unit.PX);
				style.setLeft(x, Unit.PX);
			}
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
		return "label";
	}

}