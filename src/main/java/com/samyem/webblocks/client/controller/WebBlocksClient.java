package com.samyem.webblocks.client.controller;

import java.util.List;
import java.util.function.Consumer;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.samyem.webblocks.shared.Application;

public class WebBlocksClient {
	public static interface AppMapper extends ObjectMapper<Application> {
	}

	public static interface AppListMapper extends ObjectMapper<List<Application>> {
	}

	private static AppMapper mapper = GWT.create(AppMapper.class);
	private static AppListMapper appListMapper = GWT.create(AppListMapper.class);

	public static WebBlocksClient client = new WebBlocksClient();

	public static String pageBaseUrl = GWT.getHostPageBaseURL();

	public void getWebBlocks(Consumer<List<Application>> callback) {
		getRequest(appListMapper, "/api/webblocks", callback);
	}

	public void getWebBlock(Consumer<Application> callback, int id) {
		getRequest(mapper, "/api/webblocks/" + id, callback);
	}

	public void saveWebBlocks(Application app, Consumer<Application> callback) {
		postRequest(mapper, mapper, "/api/webblocks", app, callback);
	}

	public <T> void getRequest(ObjectMapper<T> mapper, String path, Consumer<T> consumer) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, pageBaseUrl + path);
		rb.setCallback(new DefaultRequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				if (200 == response.getStatusCode()) {
					String text = response.getText();
					T app = mapper.read(text);
					consumer.accept(app);
				}
			}
		});
		send(rb);
	}

	public <REQ, RES> void postRequest(ObjectMapper<REQ> mapper, ObjectMapper<RES> responseMapper, String path,
			REQ body, Consumer<RES> consumer) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, pageBaseUrl + path);
		rb.setHeader("Content-Type", "application/json");
		String json = mapper.write(body);
		rb.setRequestData(json);
		rb.setCallback(new DefaultRequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
					if (responseMapper != null) {
						String text = response.getText();
						RES app = responseMapper.read(text);
						consumer.accept(app);
					} else {
						consumer.accept(null);
					}
				}
			}
		});
		send(rb);
	}

	private void send(RequestBuilder rb) {
		try {
			rb.send();
		} catch (RequestException e) {
			e.printStackTrace();
			GWT.log("Cannot send request", e);
			Window.alert("error = " + e.getMessage());
		}
	}
}
