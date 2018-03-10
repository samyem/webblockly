package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.user.client.ui.Widget;

/**
 * Pallet item that can be styled using common styling
 * 
 * @author samye
 *
 */
public abstract class StyledPalletItem<W extends Widget> extends ComponentPalletItem<W> {

	public StyledPalletItem(Consumer<ComponentPalletItem<W>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);

		addStyleProp("Color", Style::getColor, Style::setColor, "color");
		addStyleProp("Background Color", Style::getBackgroundColor, Style::setBackgroundColor, "background-color");
		addStyleProp("Font Size", s -> s.getProperty("fontSize"), (s, v) -> s.setProperty("fontSize", v), "font-size");
		addStyleProp("Font", s -> s.getProperty("fontFamily"), (s, v) -> s.setProperty("fontFamily", v), "font-family");
		addStyleProp("Font Style", Style::getFontStyle, (s, v) -> s.setFontStyle(FontStyle.valueOf(v)), "font-style");
	}

}
