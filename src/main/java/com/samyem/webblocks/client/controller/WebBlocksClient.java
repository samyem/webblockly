package com.samyem.webblocks.client.controller;

import com.google.gwt.core.client.GWT;
import com.samyem.webblocks.client.WebBlocksService;
import com.samyem.webblocks.client.WebBlocksServiceAsync;

public class WebBlocksClient {
	public static final WebBlocksServiceAsync webBlocksService = GWT.create(WebBlocksService.class);

}
