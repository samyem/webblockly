package com.samyem.webblocks.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.HasAllDragAndDropHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
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
	ScrollPanel documentCanvas, testTab;

	@UiField
	ComponentPallet pallet;

	@UiField
	FlexTable propertyGrid;

	@UiField
	CodeEditor codeEditor;

	@UiField
	TestWindow testWindow;

	@UiField
	TabLayoutPanel tabs;

	@UiField
	MenuItem mnuRun;

	@UiField
	PreElement console;

	private Element lastSelectedElement;

	private static DesignerPage me;

	/**
	 * Items in the document by name
	 */
	private Map<String, ComponentPalletItem> documentItemsMap = new HashMap<>();

	private Map<String, Integer> countByItemType = new HashMap<>();

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
		testWindow.setCodeEditor(codeEditor);

		registerJNSICalls();
	}

	private void setupMenus() {
		// run command
		mnuRun.setScheduledCommand(this::runProject);

		Event.addNativePreviewHandler(event -> {
			NativeEvent ne = event.getNativeEvent();
			if (ne.getType().equals("keyup") && ne.getKeyCode() == KeyCodes.KEY_F2) {
				runProject();
				event.cancel();
			}
		});
	}

	public void runProject() {
		testWindow.start(docPanel.getElement());

		tabs.selectTab(testTab);
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
		docPanel.add(widget, x, y);

		String elementId = createUniqueId(palletItem.getKey());
		documentItemsMap.put(elementId, palletItem);
		setupWidget(widget, elementId, docTop, docLeft);

		List<Property<?, ? extends Widget>> props = palletItem.getProperties();
		applyProperties(props, widgetObj);

		widget.addDomHandler((e) -> applyProperties(props, widgetObj), ClickEvent.getType());

		GWT.log(x + "," + y);
	}

	private void setupWidget(Widget widget, String elementId, int docTop, int docLeft) {
		widget.setStyleName("docElement");
		Element element = widget.getElement();
		element.setId(elementId);

		if (widget instanceof HasClickHandlers) {
			((HasClickHandlers) widget).addClickHandler(e -> e.stopPropagation());
		}

		widget.getElement().setDraggable(Element.DRAGGABLE_TRUE);

		if (widget instanceof HasAllDragAndDropHandlers) {
			HasAllDragAndDropHandlers dragHandler = (HasAllDragAndDropHandlers) widget;
			dragHandler.addDragStartHandler(e -> e.setData("action", "move"));
			dragHandler.addDragEndHandler(new DragEndHandler() {
				@Override
				public void onDragEnd(DragEndEvent event) {
					NativeEvent nativeEvent = event.getNativeEvent();
					int x = nativeEvent.getClientX() - docLeft;
					int y = nativeEvent.getClientY() - docTop;

					Style style = widget.getElement().getStyle();
					style.setTop(y, Unit.PX);
					style.setLeft(x, Unit.PX);
				}
			});
		}
	}

	private String createUniqueId(String key) {
		Integer count = countByItemType.get(key);
		count = count == null ? 1 : count + 1;
		countByItemType.put(key, count);
		return key + count;
	}

	@SuppressWarnings("unchecked")
	public void applyProperties(List<Property<?, ? extends Widget>> props, WidgetAppObject<? extends Widget> widget) {
		propertyGrid.clear();
		initializePropertyEditor();

		int row = 1;
		for (@SuppressWarnings("rawtypes")
		Property prop : props) {
			propertyGrid.setText(row, 0, prop.getKey());
			propertyGrid.setWidget(row, 1, prop.createValueEditor(widget));
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

	/**
	 * Register JNSI callable GWT code as parsed eval because Direct calls are not
	 * possible in eval strings.
	 */
	private native void registerJNSICalls() /*-{
		$wnd.generatePropertySetter = 
			@com.samyem.webblocks.client.DesignerPage::generatePropertySetter(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;);
		
		$wnd.gwt_getItemNames = @com.samyem.webblocks.client.DesignerPage::getItemNames();
		$wnd.gwt_getPropertiesForId = @com.samyem.webblocks.client.DesignerPage::getPropertiesForId(Ljava/lang/String;);
	}-*/;

	public static JsArray<JsArrayString> getItemNames() {
		Set<String> ids = me.documentItemsMap.keySet();
		JsArray<JsArrayString> items = (JsArray<JsArrayString>) JsArrayString.createArray();

		for (String id : ids) {
			ComponentPalletItem componentPalletItem = me.documentItemsMap.get(id);
			JsArrayString item = (JsArrayString) JsArrayString.createArray();
			items.push(item);

			// id - type
			item.push(id + " - " + componentPalletItem.getKey());
			item.push(id);
		}

		GWT.log("ids:" + items.toString());
		return items;
	}

	/**
	 * Get all applicable properties for the item with given id
	 * 
	 * @param id
	 * @return
	 */
	public static JsArray<JsArrayString> getPropertiesForId(String id) {
		JsArray<JsArrayString> items = (JsArray<JsArrayString>) JsArrayString.createArray();
		ComponentPalletItem componentPalletItem = me.documentItemsMap.get(id);
		List<Property> props = componentPalletItem.getProperties();

		for (Property p : props) {
			String key = p.getKey();

			// don't let user supplied blockly code change ID of fields to prevent
			// unexpected behaviour
			if (key.equals("Name")) {
				continue;
			}
			JsArrayString item = (JsArrayString) JsArrayString.createArray();
			items.push(item);

			// id - type
			item.push(key + (p.getDescription() == null ? "" : " - " + p.getDescription()));
			item.push(key);
		}

		GWT.log("props for id:" + items.toString());
		return items;
	}

	/**
	 * Generate javascript code necessary to set the property of the given item
	 * 
	 * @param id
	 * @param property
	 * @param value
	 * @return
	 */
	public static String generatePropertySetter(String id, String property, String value) {
		GWT.log("generate setter for id : " + id);
		ComponentPalletItem item = me.documentItemsMap.get(id);
		if (item == null) {
			GWT.log(me.documentItemsMap.toString());
			throw new RuntimeException("No object with name of " + id + " found");
		}
		String propSetterCode;
		try {
			propSetterCode = me.pallet.generatePropertySetter(item.getKey(), property, value);
		} catch (Exception e) {
			throw new RuntimeException("Unable to set property of " + id + ". " + e.getMessage());
		}

		String code = "$('.app #" + id + "')." + propSetterCode;
		GWT.log("generatePropertySetter: " + code);
		return code;
	}

}
