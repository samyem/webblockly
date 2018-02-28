package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Item that creates new labels
 * 
 * @author samyem
 *
 */
public class LabelPalletItem extends ComponentPalletItem {
	private Label currentLabel;

	public LabelPalletItem(Consumer<ComponentPalletItem> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);
	}

	public Widget createWidget() {
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
		return label;
	}

	@Override
	public void handleDocumentCanvasClick(ClickEvent event) {
		if (currentLabel != null) {
			currentLabel.getElement().setAttribute("contenteditable", "false");
			currentLabel = null;
		}
	}
}