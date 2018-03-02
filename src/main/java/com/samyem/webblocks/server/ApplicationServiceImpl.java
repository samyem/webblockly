package com.samyem.webblocks.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.samyem.webblocks.client.ApplicationService;
import com.samyem.webblocks.shared.Application;

@SuppressWarnings("serial")
public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService {
	private static final Logger logger = Logger.getLogger(ApplicationServiceImpl.class.getName());

	private static final String DATA = "data";
	private File dataDir;

	public ApplicationServiceImpl() {
		dataDir = new File(DATA);
		dataDir.mkdirs();
	}

	@Override
	public void saveApplication(Application application) throws Exception {
		File appFile = new File(dataDir, application.getName() + ".app");
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(appFile);
			try (ObjectOutputStream oos = new ObjectOutputStream(fout)) {
				oos.writeObject(application);
			}
		} catch (IOException e) {
			logger.severe("Unable to save application " + application.getName());
			throw new Exception("Unable to save application " + application.getName() + ". " + e.getMessage());
		}
	}

	@Override
	public Application loadApplication(String name) throws Exception {
		File appFile = new File(dataDir, name + ".app");
		FileInputStream fin;
		try {
			fin = new FileInputStream(appFile);
			try (ObjectInputStream oos = new ObjectInputStream(fin)) {
				Object rawObject = oos.readObject();
				return (Application) rawObject;
			}
		} catch (IOException | ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Unable to read application " + name, e);
			throw new Exception("Unable to read application " + name + ". " + e.getMessage());
		}
	}
}
