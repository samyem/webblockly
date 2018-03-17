package com.samyem.webblocks.client.controller;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.samyem.webblocks.shared.Application;

public class WebBlocksClient {
	public static interface AppMapper extends ObjectMapper<Application> {
	}

	private static AppMapper mapper = GWT.create(AppMapper.class);

	public static WebBlocksClient client = new WebBlocksClient();

	public void getWebBlocks(AsyncCallback<Application> callback) {
		String pageBaseUrl = GWT.getHostPageBaseURL();
		// String baseUrl = GWT.getModuleBaseURL();
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, pageBaseUrl + "/api/webblocks");
		rb.setCallback(new RequestCallback() {

			public void onError(Request request, Throwable e) {
				Window.alert("error = " + e.getMessage());
				GWT.log("Error " + e.getMessage());
				e.printStackTrace();
			}

			public void onResponseReceived(Request request, Response response) {
				if (200 == response.getStatusCode()) {
					String text = response.getText();
					System.out.println("text = " + text);

					Application app = mapper.read(text);
					callback.onSuccess(app);
				}
			}
		});

		GWT.log("Sending request...");
		try {
			rb.send();
		} catch (RequestException e) {
			e.printStackTrace();
			Window.alert("error = " + e.getMessage());
		}
	}
}
