package com.samyem.webblocks.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.samyem.webblocks.shared.Application;

public interface WebBlocksServiceAsync {

	void getWebBlock(int id, AsyncCallback<Application> callback);

	void getWebBlocks(AsyncCallback<List<Application>> callback);

	void saveApp(Application application, AsyncCallback<Application> callback);

}
