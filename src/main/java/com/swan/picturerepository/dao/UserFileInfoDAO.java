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
		String sqlStatement = "SELECT fileId, likeFlag FROM userFileInfo WHERE fileName LIKE CONCAT('%', (?),'%')";
		 return jdbcTemplate.query(sqlStatement, new Object[] {strSearch}, 
				new RowMapper<UserFileInfo>() {
					@Override
					public UserFileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub						
						UserFileInfo userFileInfo = new UserFileInfo();
						userFileInfo.setFileId(rs.getString("fileId"));
						userFileInfo.setLikeFlag(rs.getString("likeFlag"));
						return userFileInfo;
					}		
		});
	}
	
	public boolean insertUserFileInfo(String username, String fileId, String fileName) {
	    
		String sqlStatement = "insert into userfileinfo (username, fileId, fileName, isrtDt) values(?,?,?,SYSDATE())";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { username, fileId, fileName }) == 1);
	}
	
	public List<String> selectLikeFlag(String username, String fileId) {
		String sqlStatement = "select likeFlag from userFileInfo where username = ? and fileId = ?";
		 
		return jdbcTemplate.query(sqlStatement, new Object[] {username, fileId}, 
					new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("likeFlag");
						}				
			});
	}
	
	public boolean updateLikeFlag(String username, String fileId) {
		String sqlStatement = "update userfileinfo set likeFlag = case when likeFlag='Y' THEN 'N' ELSE 'Y' END where username = ? and fileId = ?";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { username, fileId }) == 1);
	}
}
