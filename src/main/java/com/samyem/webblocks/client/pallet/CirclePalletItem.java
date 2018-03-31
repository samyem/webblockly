package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.svgwidget.Circle;

/**
 * Item that creates new labels
 * 
 * @author samyem
 *
 */
public class CirclePalletItem extends ComponentPalletItem<Circle> {

	public CirclePalletItem(Consumer<ComponentPalletItem<Circle>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);
	}

	@Override
	public WidgetAppObject<Circle> createAppObject() {
		Circle label = new Circle();

		WidgetAppObject<Circle> appObj = new WidgetAppObject<>(label);
		return appObj;
	}

	@Override
	public String getKey() {
		return "circle";
	}

	@Override
	public String getFriendlyName() {
		return "Circle";
	}

}