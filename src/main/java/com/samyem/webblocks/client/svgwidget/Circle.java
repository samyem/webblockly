package com.samyem.webblocks.client.svgwidget;

import org.vectomatic.dom.svg.OMSVGEllipseElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.OMSVGStyle;
import org.vectomatic.dom.svg.utils.SVGConstants;

public class Circle extends SVGWidget {
	protected com.google.gwt.dom.client.Element svgElement;
	private OMSVGSVGElement svg;
	private OMSVGEllipseElement ellipse;

	public Circle() {
		super();
	}

	@Override
	public void addToSvg(OMSVGSVGElement svg, int x, int y) {
		ellipse = doc.createSVGEllipseElement(x, y, 30f, 30f);

		OMSVGStyle style = ellipse.getStyle();
		style.setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, SVGConstants.CSS_WHITE_VALUE);
		style.setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, SVGConstants.CSS_BLACK_VALUE);
		// style.setSVGProperty(SVGConstants.CSS_STROKE_DASHARRAY_PROPERTY, "5,2,2,2");

		svg.appendChild(ellipse);
	}

}
