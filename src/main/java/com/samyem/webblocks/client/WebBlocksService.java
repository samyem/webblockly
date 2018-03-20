package com.samyem.webblocks.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.samyem.webblocks.shared.Application;

@RemoteServiceRelativePath("webBlocksService")
public interface WebBlocksService extends RemoteService {

	Application getWebBlock(int id);

	List<Application> getWebBlocks();

	Application saveApp(Application application);

}
