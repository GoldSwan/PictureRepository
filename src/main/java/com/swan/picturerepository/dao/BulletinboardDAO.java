package com.swan.picturerepository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.swan.picturerepository.dto.BulletinBoardDTO;
import com.swan.picturerepository.dto.UserFileInfoDTO;

@Repository
public class BulletinboardDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<BulletinBoardDTO> selectBoardInfo(String strSearchType, String strSearch, int page, int maxImageCnt){
		String sqlStatement = "";
		int start = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append(" (")
		  .append("   SELECT bulletinId, representativeFileId, likeFlag")
		  .append("   FROM bulletinboard")
		  .append("   WHERE 'title' = ?")
		  .append("   AND title LIKE CONCAT('%', ?, '%')")
		  .append("   AND publicRange = 'A'")
		  .append("   ORDER BY isrtDt DESC")
		  .append("   LIMIT ? OFFSET ?")
		  .append("  )")
		  .append("  UNION ALL")
		  .append(" (")
		  .append("   SELECT bulletinId, representativeFileId, likeFlag")
		  .append("   FROM bulletinboard")
		  .append("   WHERE 'content' = ?")
		  .append("   AND content LIKE CONCAT('%', ?, '%')")
		  .append("   AND publicRange = 'A'")
		  .append("   ORDER BY isrtDt DESC")
		  .append("   LIMIT ? OFFSET ?")
		  .append("  )")
		  .append("  UNION ALL")
		  .append(" (")
		  .append("   SELECT a.bulletinId, a.representativeFileId, a.likeFlag")
		  .append("   FROM bulletinboard a")
		  .append("   WHERE 'tag' = ?")
		  .append("   AND publicRange = 'A'")
		  .append("   AND EXISTS (")
		  .append("       SELECT 1")
		  .append("       FROM boardtagrelation b")
		  .append("       INNER JOIN hashtag c on c.tagId = b.tagId")
		  .append("       WHERE b.bulletinId = a.bulletinId")
		  .append("       AND c.tagName = ?")
		  .append("       )")
		  .append("   ORDER BY isrtDt DESC")
		  .append("   LIMIT ? OFFSET ?")
		  .append("  )");	
		
		sqlStatement = sb.toString();
		start = (page-1)*maxImageCnt;
		return jdbcTemplate.query(sqlStatement, new Object[] {strSearchType, strSearch, maxImageCnt, start
				                                            , strSearchType, strSearch, maxImageCnt, start
				                                            , strSearchType, strSearch, maxImageCnt, start }, 
					new RowMapper<BulletinBoardDTO>() {
						@Override
						public BulletinBoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {					
							BulletinBoardDTO bulletinBoard = new BulletinBoardDTO.Builder(rs.getString("bulletinId"), rs.getString("representativeFileId"), rs.getString("likeFlag")).build();				
							return bulletinBoard;											
						}		
			   });
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
						return userFileInfo;											
					}		
		});
	}
	
	public int selectBulletinboardCnt(String strSearchType, String strSearch) {
		String sqlStatement = "";
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT SUM(cnt) cnt ")
		  .append(" FROM")		
		  .append(" (")
		  .append(" 	(")
		  .append(" 		SELECT COUNT(1) cnt")
		  .append(" 		FROM bulletinboard")
		  .append(" 		WHERE 'title' = ?")
		  .append(" 		AND title LIKE CONCAT('%', ?,'%')")
		  .append(" 	)")
		  .append(" 	UNION ALL")
		  .append(" 	(")
		  .append(" 		SELECT COUNT(1) cnt")
		  .append(" 		FROM bulletinboard")
		  .append(" 		WHERE 'content' = ?")
		  .append(" 		AND content LIKE CONCAT('%', ?,'%')")
		  .append(" 	)")
		  .append(" 	UNION ALL")
		  .append(" 	(")
		  .append(" 		SELECT COUNT(1) cnt")
		  .append(" 		FROM bulletinboard a")
		  .append(" 		WHERE 'tag' = ?")
		  .append(" 		AND EXISTS (")
		  .append(" 			SELECT 1")
		  .append(" 			FROM boardtagrelation b")
		  .append(" 			INNER JOIN hashtag c on c.tagId = b.tagId")
		  .append(" 			WHERE b.bulletinId = a.bulletinId")
		  .append(" 			AND c.tagName = ?")
		  .append(" 			)")
		  .append(" 	)")
		  .append(" )result");
		
		sqlStatement = sb.toString();
		return jdbcTemplate.queryForObject(sqlStatement, new Object[] {strSearchType, strSearch
				                                                     , strSearchType, strSearch
				                                                     , strSearchType, strSearch}, Integer.class);
	}
	
	public String insertBulletinboardInfo(final ArrayList<String> bulletinBoardInfoList, final ArrayList<String> userFileInfoList) {	
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String strBulletinId = "";
		
		if(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
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
			return "BulletinboardError";
		
		strBulletinId = keyHolder.getKey().toString();
		
		String sqlStatement = "insert into UserFileInfo (bulletinId, fileId, isrtDt) values(?, ?, SYSDATE())";

		for(String strFileId : userFileInfoList) {
			if(!(jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId, strFileId }) == 1)) {
				return "";
			}
		}
	
		return strBulletinId;
	}
	
	public boolean updateBulletinboardInfo(final ArrayList<String> bulletinBoardInfoList, final ArrayList<String> userFileInfoList,  String strBulletinId) {	
		
		String sqlStatement = " update bulletinboard"
				+ " set content = ?"
				+ " ,isrtDt = SYSDATE()"
				+ " ,likeFlag = ?"
				+ " ,publicRange = ?"
				+ " ,title = ?"
				+ " ,representativeFileId = case when 'NEWFILE' = ? then ? else representativeFileId end"
				+ " where bulletinId = ?";		
	
		if(!(jdbcTemplate.update(sqlStatement,
				new Object[] { bulletinBoardInfoList.get(0)
						, bulletinBoardInfoList.get(1)
						, bulletinBoardInfoList.get(2)
						, bulletinBoardInfoList.get(3)
						, userFileInfoList.size() > 0 ? "NEWFILE" : "NONEWFILE"
						, userFileInfoList.size() > 0 ? userFileInfoList.get(0) : "NOFILEID"
						, strBulletinId}) == 1)) return false;
							
		sqlStatement = "insert into UserFileInfo (bulletinId, fileId, isrtDt) values(?, ?, SYSDATE())";

		for(String strFileId : userFileInfoList) {
			if(!(jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId, strFileId }) == 1)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean deleteBulletinboardInfo(String strBulletinId) {		
		String sqlStatement = "delete from bulletinboard where bulletinId = ?";
		return ((jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId }) == 1));
	}
	
	public boolean deleteUserFileInfo(String strBulletinId) {				
		String sqlStatement = "delete from userfileinfo where bulletinId = ?";
		return ((jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId }) == 1));
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
