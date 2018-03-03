package com.samyem.webblocks.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.pallet.ComponentPalletItem;
import com.samyem.webblocks.client.pallet.Property;

public class DesignerPage extends Composite {
	private static DesignerPageUiBinder uiBinder = GWT.create(DesignerPageUiBinder.class);

	interface DesignerPageUiBinder extends UiBinder<Widget, DesignerPage> {
	}

	@UiField
	FlowPanel palletPanel;

	@UiField
	AbsolutePanel docPanel;

	@UiField
	ScrollPanel documentCanvas;

	@UiField
	ComponentPallet pallet;

	@UiField
	FlexTable propertyGrid;

	private Element lastSelectedElement;

	public DesignerPage() {
		initWidget(uiBinder.createAndBindUi(this));

		documentCanvas.addDomHandler(event -> {
		}, DragOverEvent.getType());

		documentCanvas.addDomHandler(event -> {
			event.preventDefault();
			handleDocDrop(event);
		}, DropEvent.getType());

		documentCanvas.addDomHandler(event -> {
			event.preventDefault();

			handleClick(event);

		}, ClickEvent.getType());

		propertyGrid.setText(0, 0, "Property");
		propertyGrid.setText(0, 1, "Value");
	}

	private void handleClick(ClickEvent event) {
		pallet.handleDocumentCanvasClick(event);
	}

	private void handleDocDrop(DropEvent event) {
		String item = event.getData("item");
		String dropAction = event.getData("action");

		// only relevant if dropping a new item as an add
		if (!dropAction.equals("add")) {
			return;
		}

		int docLeft = docPanel.getAbsoluteLeft();
		int docTop = docPanel.getAbsoluteTop();
		// GWT.log(docLeft + " " + docTop);

		pallet.setDocLeft(docLeft);
		pallet.setDocTop(docTop);

		GWT.log("Drop " + item);
		GWT.log(event.getAssociatedType().toString());

		NativeEvent nativeEvent = event.getNativeEvent();
		int x = nativeEvent.getClientX() - docLeft;
		int y = nativeEvent.getClientY() - docTop;

		ComponentPalletItem palletItem = pallet.createPalletItem(item);
		Widget widget = palletItem.createWidget();
		widget.setStyleName("docElement");
		docPanel.add(widget, x, y);

		List<Property<?>> props = palletItem.getProperties();
		applyProperties(props, widget);

		widget.addDomHandler((e) -> {
			applyProperties(props, widget);

		}, ClickEvent.getType());

		GWT.log(x + "," + y);
	}

	public void applyProperties(List<Property<?>> props, Widget widget) {
		int row = 1;
		for (Property<?> prop : props) {
			propertyGrid.setText(row, 0, prop.getKey());
			propertyGrid.setWidget(row, 1, prop.getValueEditor());
			row++;
		}

		Element selectedElement = widget.getElement();
		selectedElement.addClassName("selected");

		if (lastSelectedElement != null && lastSelectedElement != selectedElement) {
			lastSelectedElement.removeClassName("selected");
		}
		lastSelectedElement = selectedElement;

	}
}
