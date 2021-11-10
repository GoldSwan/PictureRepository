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

import com.swan.picturerepository.dto.UserFileInfoDTO;
import com.swan.picturerepository.model.BulletinBoard;
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
				.returningResultSet("BulletinBoard",BeanPropertyRowMapper.newInstance(BulletinBoard.class));
		procReadAllSearchFileCnt = new SimpleJdbcCall(dataSource)
				.withProcedureName("USP_SEARCH_FILE_CNT");	
	}
	
	/*
	public List<UserFileInfo> selectFileName(String strSearch) {
		String sqlStatement = "SELECT fileId, likeFlag FROM userFileInfo WHERE fileName LIKE CONCAT('%', (?),'%')";
		 return jdbcTemplate.query(sqlStatement, new Object[] {strSearch}, 
				new RowMapper<UserFileInfo>() {
					@Override
					public UserFileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub						
						UserFileInfo userFileInfo = new UserFileInfo();
						userFileInfo.setFileId(rs.getString("fileId"));
						//userFileInfo.setLikeFlag(rs.getString("likeFlag"));
						return userFileInfo;
					}		
		});
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<BulletinBoard> selectBoardByProcedure(String strSearch, int page, int maxImageCnt) {
		Map<String, Object> param = new HashMap<>();
		param.put("search_flg", "T");
		param.put("search", strSearch);
		param.put("pagesize", maxImageCnt);
		param.put("start", (page-1)*maxImageCnt);
		Map<String, Object> m = procReadAllSearchFile.execute(param);
		return (List<BulletinBoard>)m.get("BulletinBoard");
	}
	
	public int selectFileCntByProcedure(String strSearch) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", strSearch);
		Map<String, Object> m = procReadAllSearchFileCnt.execute(param);
		return Integer.parseInt(m.get("out_cnt").toString());
	}
	
	public List<UserFileInfoDTO> selectFileId(String strbulletinId) {
		String sqlStatement = "SELECT b.fileId, a.title, a.isrtDt, a.username, a.content, a.likeCnt, a.likeFlag"
				+ " FROM bulletinboard a"
				+ " inner join userfileinfo b on a.bulletinId = b.bulletinId"
				+ " WHERE a.bulletinId = (?)";
		 return jdbcTemplate.query(sqlStatement, new Object[] {strbulletinId}, 
				new RowMapper<UserFileInfoDTO>() {
					@Override
					public UserFileInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						
						Timestamp dtIsrtDt = Timestamp.valueOf(rs.getString("isrtDt"));
						
						UserFileInfoDTO userFileInfo = new UserFileInfoDTO.Builder(rs.getString("title")
								        , dtIsrtDt.toString()
								        , rs.getString("username")
										, rs.getString("content")
										, rs.getString("fileId")
										, rs.getString("likeCnt")
										, rs.getString("likeFlag")).build();
						//Timestamp dtIsrtDt = Timestamp.valueOf(rs.getString("isrtDt"));
						//userFileInfo.setFileId(rs.getString("fileId"));
						//userFileInfo.set(rs.getString("fileName"));
						//userFileInfo.setIsrtDt(dtIsrtDt);
						//userFileInfo.setLikeFlag(rs.getString("likeFlag"));
						//userFileInfo.setLikeCnt(rs.getLong("likeCnt"));
						//userFileInfo.setContent(rs.getString("content"));
						//userFileInfo.setTag(rs.getString("tag"));
						//userFileInfo.setTitle(rs.getString("title"));
						
						return userFileInfo;											
					}		
		});
	}
	
	public String insertUserFileInfo(final ArrayList<String> bulletinBoardInfoList, final ArrayList<String> userFileInfoList) {	
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String strBulletinId = "";
		
		if(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String sqlStatement = "insert into bulletinboard (content, isrtDt, likeFlag, publicRange, title, username, representativeFileId) values(?,SYSDATE(),?,?,?,?,?)";

				PreparedStatement pstmt = con.prepareStatement(sqlStatement, new String[] {"bulletinId"});

				pstmt.setString(1, bulletinBoardInfoList.get(0));
				pstmt.setString(2, bulletinBoardInfoList.get(1));
				pstmt.setString(3, bulletinBoardInfoList.get(2));
				pstmt.setString(4, bulletinBoardInfoList.get(3));
				pstmt.setString(5, bulletinBoardInfoList.get(4));
				pstmt.setString(6, userFileInfoList.get(0));
				return pstmt;
			}}, keyHolder)!=1)
			return "";
		
		strBulletinId = keyHolder.getKey().toString();
		
		String sqlStatement = "insert into UserFileInfo (bulletinId, fileId, isrtDt) values(?, ?, SYSDATE())";

		for(String strFileId : userFileInfoList) {
			if(!(jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId, strFileId }) == 1)) {
				return "";
			}
		}
	
		return strBulletinId;
	}
	
	public List<String> selectLikeFlag(String fileId) {
		String sqlStatement = "select likeFlag from bulletinboard where representativeFileId = ?";
		 
		return jdbcTemplate.query(sqlStatement, new Object[] { fileId }, 
					new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("likeFlag");
						}				
			});
	}
	
	public boolean updateLikeFlag(String fileId) {
		String sqlStatement = "update bulletinboard set likeFlag = case when likeFlag='Y' THEN 'N' ELSE 'Y' END where representativeFileId = ?";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { fileId }) == 1);
	}
}
