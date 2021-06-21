package com.swan.picturerepository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.swan.picturerepository.model.UserFileInfo;

@Repository
public class UserFileInfoDAO {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall procReadAllSearchFile;
	private SimpleJdbcCall procReadAllSearchFileCnt;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		procReadAllSearchFile = new SimpleJdbcCall(dataSource)
				.withProcedureName("USP_SEARCH_FILE")
				.returningResultSet("UserFileInfo",BeanPropertyRowMapper.newInstance(UserFileInfo.class));
		procReadAllSearchFileCnt = new SimpleJdbcCall(dataSource)
				.withProcedureName("USP_SEARCH_FILE_CNT");	
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
	public List<UserFileInfo> selectFileNameByProcedure(String strSearch, int page, int maxImageCnt) {
		Map<String, Object> param = new HashMap<>();
		param.put("search_flg", "T");
		param.put("search", strSearch);
		param.put("pagesize", maxImageCnt);
		param.put("start", (page-1)*maxImageCnt);
		Map<String, Object> m = procReadAllSearchFile.execute(param);
		return (List<UserFileInfo>)m.get("UserFileInfo");
	}
	
	public int selectFileCntByProcedure(String strSearch) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", strSearch);
		Map<String, Object> m = procReadAllSearchFileCnt.execute(param);
		return Integer.parseInt(m.get("out_cnt").toString());
	}
	
	public List<UserFileInfo> selectFileId(String strFileId) {
		String sqlStatement = "SELECT fileId, fileName, isrtDt, likeFlag, likeCnt, content, tag, title FROM userFileInfo WHERE fileId = (?)";
		 return jdbcTemplate.query(sqlStatement, new Object[] {strFileId}, 
				new RowMapper<UserFileInfo>() {
					@Override
					public UserFileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

						UserFileInfo userFileInfo = new UserFileInfo();
						Timestamp dtIsrtDt = Timestamp.valueOf(rs.getString("isrtDt"));
						userFileInfo.setFileId(rs.getString("fileId"));
						userFileInfo.setFileName(rs.getString("fileName"));
						userFileInfo.setIsrtDt(dtIsrtDt);
						userFileInfo.setLikeFlag(rs.getString("likeFlag"));
						userFileInfo.setLikeCnt(rs.getLong("likeCnt"));
						userFileInfo.setContent(rs.getString("content"));
						userFileInfo.setTag(rs.getString("tag"));
						userFileInfo.setTitle(rs.getString("title"));
						
						return userFileInfo;											
					}		
		});
	}
	
	public boolean insertUserFileInfo(final ArrayList<String> userInfoList) {	
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String sqlStatement = "insert into bulletinboard (content, isrtDt, likeFlag, publicRange, title, username) values(?,SYSDATE(),?,?,?,?)";
				/*
				System.out.println(userInfoList.get(0));
				System.out.println(userInfoList.get(1));
				System.out.println(userInfoList.get(2));
				System.out.println(userInfoList.get(3));
				System.out.println(userInfoList.get(4));
				*/
				PreparedStatement pstmt = con.prepareStatement(sqlStatement, new String[] {"bulletinId"});
				pstmt.setString(1, userInfoList.get(0));
				pstmt.setString(2, userInfoList.get(1));
				pstmt.setString(3, userInfoList.get(2));
				pstmt.setString(4, userInfoList.get(3));
				pstmt.setString(5, userInfoList.get(4));
				return pstmt;
			}}, keyHolder);
		Number keyValue = keyHolder.getKey();
		System.out.println("bulletinId:"+keyValue.longValue());
		/*
		return (jdbcTemplate.update(sqlStatement,
				new Object[] { userInfoList.get(0), userInfoList.get(1), userInfoList.get(2), userInfoList.get(3), 
							   userInfoList.get(4), userInfoList.get(5), userInfoList.get(6)}) == 1);
		*/
	    return true;
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
