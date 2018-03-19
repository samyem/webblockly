package com.samyem.webblocks.client.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;

public abstract class DefaultRequestCallback implements RequestCallback {
	public void onError(Request request, Throwable e) {
		GWT.log("Error " + e.getMessage());
		e.printStackTrace();
	}
}
