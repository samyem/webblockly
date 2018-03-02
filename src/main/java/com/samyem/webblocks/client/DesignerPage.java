package com.samyem.webblocks.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

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

		Widget widget = pallet.createWidgetForItem(item);
		widget.setStyleName("docElement");
		docPanel.add(widget, x, y);

		GWT.log(x + "," + y);
	}
}
