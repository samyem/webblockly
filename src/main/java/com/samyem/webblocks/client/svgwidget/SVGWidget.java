package com.samyem.webblocks.client.svgwidget;

import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.user.client.ui.Widget;

public abstract class SVGWidget extends Widget {
	public static final OMSVGDocument doc = OMSVGParser.currentDocument();

	public abstract void addToSvg(OMSVGSVGElement svg, int x, int y);

}
