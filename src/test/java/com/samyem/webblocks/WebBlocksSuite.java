package com.samyem.webblocks;

import com.samyem.webblocks.client.WebBlocksTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class WebBlocksSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for WebBlocks");
		suite.addTestSuite(WebBlocksTest.class);
		return suite;
	}
}
