package com.swan.picturerepository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.swan.picturerepository.model.UserFileInfo;

@Repository
public class UserFileInfoDAO {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall procReadAllSearchFile;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		procReadAllSearchFile = new SimpleJdbcCall(dataSource)
				.withProcedureName("SEARCH_FILE")
				.returningResultSet("UserFileInfo",BeanPropertyRowMapper.newInstance(UserFileInfo.class));
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
	
	@SuppressWarnings("unchecked")
	public List<UserFileInfo> selectFileNameByProcedure(String strSearch, int page, int maxPage) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", strSearch);
		param.put("pagesize", maxPage);
		param.put("start", (page-1)*maxPage);
		Map<String, Object> m = procReadAllSearchFile.execute(param);
		return (List<UserFileInfo>)m.get("UserFileInfo");
	}
	
	public List<UserFileInfo> selectFileId(String strFileId) {
		String sqlStatement = "SELECT fileId, fileName, isrtDt, likeFlag, likeCnt, content, tag, title FROM userFileInfo WHERE fileId = (?)";
		 return jdbcTemplate.query(sqlStatement, new Object[] {strFileId}, 
				new RowMapper<UserFileInfo>() {
					@Override
					public UserFileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub						
						UserFileInfo userFileInfo = new UserFileInfo();
						userFileInfo.setFileId(rs.getString("fileId"));
						userFileInfo.setFileName(rs.getString("fileName"));
						userFileInfo.setIsrtDt(LocalDate.parse(rs.getString("isrtDt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")));
						userFileInfo.setLikeFlag(rs.getString("likeFlag"));
						userFileInfo.setLikeCnt(rs.getLong("likeCnt"));
						userFileInfo.setContent(rs.getString("content"));
						userFileInfo.setTag(rs.getString("tag"));
						userFileInfo.setTitle(rs.getString("title"));
						return userFileInfo;
					}		
		});
	}
	
	public boolean insertUserFileInfo(ArrayList<String> userInfoList) {
	    
		String sqlStatement = "insert into userfileinfo (username, fileId, fileName, isrtDt, title, content, tag, publicRange) values(?,?,?,SYSDATE(),?,?,?,?)";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { userInfoList.get(0), userInfoList.get(1), userInfoList.get(2), userInfoList.get(3), 
							   userInfoList.get(4), userInfoList.get(5), userInfoList.get(6)}) == 1);
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
