package com.samyem.webblocks.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.samyem.webblocks.shared.Application;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("appService")
public interface ApplicationService extends RemoteService {
	void saveApplication(Application application) throws Exception;

	Application loadApplication(String name) throws Exception;
}
