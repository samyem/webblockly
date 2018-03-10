package com.samyem.webblocks.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.samyem.webblocks.client.pallet.ButtonPalletItem;
import com.samyem.webblocks.client.pallet.ComponentPalletItem;
import com.samyem.webblocks.client.pallet.LabelPalletItem;

/**
 * Component Pallet
 * 
 * @author samyem
 *
 */
public class ComponentPallet extends VerticalPanel {
	private Map<String, ComponentPalletItem> palletItemsByName = new HashMap<>();

	private ComponentPalletItem currentEditFocus;

	private int docLeft;
	private int docTop;

	public ComponentPallet() {
		addItem(new LabelPalletItem(w -> currentEditFocus = w, () -> docLeft, () -> docTop));
		addItem(new ButtonPalletItem(w -> currentEditFocus = w, () -> docLeft, () -> docTop));
	}

	public ComponentPalletItem createPalletItem(String item) {
		return palletItemsByName.get(item);
	}

	public String generatePropertySetter(String item, String property, String value) throws Exception {
		try {
			return palletItemsByName.get(item).generatePropertySetter(property, value);
		} catch (Exception e) {
			throw new Exception("Unable to set property for type " + item + ". " + e.getMessage());
		}
	}

	public void handleDocumentCanvasClick(ClickEvent event) {
		if (currentEditFocus != null) {
			GWT.log("canvas got click");

			currentEditFocus.handleDocumentCanvasClick(event);
		}
	}

	private void addItem(ComponentPalletItem componentItem) {
		String key = componentItem.getKey();
		String icon = "icons/pallet-" + key + ".png";
		palletItemsByName.put(key, componentItem);

		Image image = new Image(icon);
		image.setTitle(key);
		add(image);

		image.addDragStartHandler(event -> {
			event.setData("item", key);
			event.setData("action", "add");
		});
	}

	public void setDocLeft(int docLeft) {
		this.docLeft = docLeft;
	}

	public void setDocTop(int docTop) {
		this.docTop = docTop;
	}

}
