package com.samyem.webblocks.server;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.samyem.webblocks.client.WebBlocksService;
import com.samyem.webblocks.shared.Application;

public class WebBlocksServletImpl extends RemoteServiceServlet implements WebBlocksService {
	private static final long serialVersionUID = -6779975088058402888L;

	final static Logger logger = LoggerFactory.getLogger(WebBlocksServletImpl.class);

	@Autowired
	private JdbcOperations jdbc;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Application getWebBlock(int id) {
		try {
			List<Map<String, Object>> rows = jdbc.queryForList("select id, program from application where id=?", id);

			for (Map<String, Object> row : rows) {
				String program = (String) row.get("program");
				Application app = mapper.readValue(program, Application.class);
				app.setId((Integer) row.get("id"));
				return app;
			}
			return null;
		} catch (Exception ex) {
			logger.error("Error", ex);
			throw new RuntimeException(ex.getMessage());
		}
	}

	@Override
	public List<Application> getWebBlocks() {
		List<Map<String, Object>> rows = jdbc.queryForList("select id, name from application");

		List<Application> apps = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			String name = (String) row.get("name");
			Integer id = (Integer) row.get("id");
			Application app = new Application();
			app.setId(id);
			app.setName(name);
			apps.add(app);
		}
		return apps;
	}

	@Override
	public Application saveApp(Application application) {
		try {
			String appJson = mapper.writeValueAsString(application);

			if (application.getId() == null) {
				GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
				jdbc.update(con -> {
					PreparedStatement st = con.prepareStatement("insert into application (program) values (?)",
							Statement.RETURN_GENERATED_KEYS);
					st.setString(1, appJson);
					return st;
				}, keyHolder);
				Number key = keyHolder.getKey();
				application.setId(key.intValue());
			} else {
				jdbc.update("update application set program=? where id=? ", appJson, application.getId());
			}
			return application;
		} catch (Exception ex) {
			logger.error("Error", ex);
			throw new RuntimeException(ex.getMessage());
		}

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
}
