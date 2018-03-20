package com.samyem.webblocks.client.controller;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Callback<T> implements AsyncCallback<T> {
	private Consumer<T> consumer;

	public static <A> Callback<A> callback(Consumer<A> consumer) {
		return new Callback<>(consumer);
	}

	public Callback(Consumer<T> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void onFailure(Throwable e) {
		GWT.log("Error " + e.getMessage());
		e.printStackTrace();
	}

	@Override
	public void onSuccess(T result) {
		consumer.accept(result);
	}
}
