package com.swan.picturerepository.dao;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swan.picturerepository.model.User;

@Repository
public class UserDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean insertUser(User user) {
		
	    String authority = "ROLE_USER";
	    String email = user.getEmail();
	    int enabled = 1;
	    String password = user.getPassword();
	    String username = user.getUsername();
	    
		String sqlStatement = "insert into user (authority, email, enabled, password, username) values(?,?,?,?,?)";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { authority, email, enabled, password, username }) == 1);
	}
}
