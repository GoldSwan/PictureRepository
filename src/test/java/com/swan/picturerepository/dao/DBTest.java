package com.swan.picturerepository.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import org.junit.Test;
import com.swan.picturerepository.config.Configure;

//@WebAppConfiguration
public class DBTest extends Configure {
	
	@Test
	public void dbConnect() throws Exception {
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		Connection conn = (Connection) dataSource.getConnection();
		System.out.println("성공 : " + conn);
	}
}