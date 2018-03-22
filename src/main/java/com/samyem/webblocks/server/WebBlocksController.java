// package com.samyem.webblocks.server;
//
// import java.io.IOException;
// import java.sql.PreparedStatement;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.jdbc.core.JdbcOperations;
// import org.springframework.jdbc.support.GeneratedKeyHolder;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.fasterxml.jackson.core.JsonParseException;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonMappingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.samyem.webblocks.shared.Application;
//
// @RestController
// @RequestMapping("/api/webblocks")
// public class WebBlocksController {
// final static Logger logger =
// LoggerFactory.getLogger(WebBlocksController.class);
//
// @Autowired
// private JdbcOperations jdbc;
//
// private ObjectMapper mapper = new ObjectMapper();
//
// @RequestMapping(path = "/{id}", method = RequestMethod.GET)
// @ResponseStatus(HttpStatus.OK)
// public Application getWebBlock(@PathVariable("id") int id)
// throws JsonParseException, JsonMappingException, IOException {
// List<Map<String, Object>> rows = jdbc.queryForList("select id, program from
// application where id=?", id);
//
// for (Map<String, Object> row : rows) {
// String program = (String) row.get("program");
// Application app = mapper.readValue(program, Application.class);
// app.setId((Integer) row.get("id"));
// return app;
// }
// return null;
// }
//
// @RequestMapping(method = RequestMethod.GET)
// @ResponseStatus(HttpStatus.OK)
// public List<Application> getWebBlocks() throws JsonParseException,
// JsonMappingException, IOException {
// List<Map<String, Object>> rows = jdbc.queryForList("select id, name from
// application");
//
// List<Application> apps = new ArrayList<>();
//
// for (Map<String, Object> row : rows) {
// String name = (String) row.get("name");
// Integer id = (Integer) row.get("id");
// Application app = new Application();
// app.setId(id);
// app.setName(name);
// apps.add(app);
// }
// return apps;
// }
//
// @RequestMapping(method = RequestMethod.POST)
// @ResponseStatus(HttpStatus.ACCEPTED)
// public Application saveApp(@RequestBody Application application) throws
// JsonProcessingException {
// String appJson = mapper.writeValueAsString(application);
//
// if (application.getId() == null) {
// GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
// jdbc.update(con -> {
// PreparedStatement st = con.prepareStatement("insert into application
// (program) values (?)",
// Statement.RETURN_GENERATED_KEYS);
// st.setString(1, appJson);
// return st;
// }, keyHolder);
// Number key = keyHolder.getKey();
// application.setId(key.intValue());
// } else {
// jdbc.update("update application set program=? where id=? ", appJson,
// application.getId());
// }
// return application;
// }
//
// }
