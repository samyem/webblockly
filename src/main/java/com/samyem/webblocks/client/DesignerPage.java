package com.samyem.webblocks.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
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

	@UiField
	CodeEditor codeEditor;

	@UiField
	TabLayoutPanel tabs;

	@UiField
	MenuItem mnuRun;

	@UiField
	PreElement console;

	private Element lastSelectedElement;

	private static DesignerPage me;

	public DesignerPage() {
		initWidget(uiBinder.createAndBindUi(this));
		me = this;

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

		initializeCodeEditor();
		initializePropertyEditor();

		// menus
		setupMenus();

		console.setId("console");
	}

	private void setupMenus() {
		// run command
		mnuRun.setScheduledCommand(() -> codeEditor.run());

		Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				NativeEvent ne = event.getNativeEvent();

				if (ne.getKeyCode() == KeyCodes.KEY_F5) {
					codeEditor.run();
					event.cancel();
				}
			}
		});
	}

	private void initializePropertyEditor() {
		propertyGrid.setText(0, 0, "Properties");
		propertyGrid.setText(0, 1, "Values");
	}

	private void initializeCodeEditor() {
		SelectionHandler<Integer> handler = event -> {
			if (event.getSelectedItem() == 1) {
				codeEditor.initializeUI();
			}
		};
		tabs.addSelectionHandler(handler);

		tabs.addBeforeSelectionHandler(s -> {
			GWT.log("before handler " + s.getItem());
		});

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
		WidgetAppObject<? extends Widget> widgetObj = palletItem.createAppObject();
		Widget widget = widgetObj.getWidget();
		widget.setStyleName("docElement");
		docPanel.add(widget, x, y);

		List<Property<?, ? extends Widget>> props = palletItem.getProperties();
		applyProperties(props, widgetObj);

		widget.addDomHandler((e) -> applyProperties(props, widgetObj), ClickEvent.getType());

		GWT.log(x + "," + y);
	}

	@SuppressWarnings("unchecked")
	public void applyProperties(List<Property<?, ? extends Widget>> props, WidgetAppObject<? extends Widget> widget) {
		propertyGrid.clear();
		initializePropertyEditor();

		int row = 1;
		for (@SuppressWarnings("rawtypes")
		Property prop : props) {
			propertyGrid.setText(row, 0, prop.getKey());
			propertyGrid.setWidget(row, 1, prop.getValueEditor(widget));
			row++;
		}

		// mark element as selected
		Element selectedElement = widget.getWidget().getElement();
		selectedElement.addClassName("selected");

		if (lastSelectedElement != null && lastSelectedElement != selectedElement) {
			lastSelectedElement.removeClassName("selected");
		}
		lastSelectedElement = selectedElement;
	}

	public static void printToConsole(String object) {
		String str = object == null ? "null" : object;
		str = str + "\n";
		PreElement console = me.console;
		console.setInnerText(console.getInnerText() + str);
	}

	public static void fromJava(String arg) {
		GWT.log("From java " + arg);
	}

	public static void clearConsole() {
		me.console.setInnerText("");
	}
}
