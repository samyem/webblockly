package com.samyem.webblocks.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.samyem.webblocks.shared.Application;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ApplicationServiceAsync {
	void saveApplication(Application application, AsyncCallback<Void> callback);

	void loadApplication(String name, AsyncCallback<Application> callback);
}
