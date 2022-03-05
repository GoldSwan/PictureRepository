package com.swan.picturerepository.config;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/dao-context.xml"
		     ,"file:src/main/webapp/WEB-INF/spring/appServlet/security-context.xml"
		     ,"file:src/main/webapp/WEB-INF/spring/appServlet/service-context.xml"
		     ,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@TestPropertySource
(locations = {"classpath:jdbc.properties"})
public class Configure {

	@Autowired
	public ApplicationContext ctx;
}
