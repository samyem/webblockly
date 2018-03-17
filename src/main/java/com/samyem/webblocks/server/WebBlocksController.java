package com.samyem.webblocks.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samyem.webblocks.shared.Application;

@RestController
@RequestMapping("/api/webblocks")
public class WebBlocksController {
	final static Logger logger = LoggerFactory.getLogger(WebBlocksController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Application getWebBlocks() {
		Application app = new Application();
		app.setName("app xx");

		return app;
	}

}
