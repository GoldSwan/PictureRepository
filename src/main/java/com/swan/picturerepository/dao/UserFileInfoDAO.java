package com.swan.picturerepository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.swan.picturerepository.model.UserFileInfo;

@Repository
public class UserFileInfoDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<UserFileInfo> selectFileName(String strSearch) {
		String sqlStatement = "SELECT fileId FROM userFileInfo WHERE fileName LIKE CONCAT('%', (?),'%')";
		 return jdbcTemplate.query(sqlStatement, new Object[] {strSearch}, 
				new RowMapper<UserFileInfo>() {

					@Override
					public UserFileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub						
						UserFileInfo userFileInfo = new UserFileInfo();
						userFileInfo.setFileId(rs.getString("fileId"));
						return userFileInfo;
					}
			
		});
	}
}
