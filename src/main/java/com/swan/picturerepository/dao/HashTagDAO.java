package com.swan.picturerepository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@Repository
public class HashTagDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<String> createHashTagInfo(List<String> arrTags) {
		List<String> listTagIds = new ArrayList<>();
		String strTagId = "";
		
		for(String tagName : arrTags) {
			strTagId = createHashTagInfoInsert(tagName);
			
			if(strTagId == "")
				continue;
			
			listTagIds.add(strTagId);
		}
		return listTagIds;
	}
	
	public List<String> selectTagIdByName(String tagName) {
		String sqlStatement = "SELECT tagId FROM hashtag WHERE tagName = ?";
		
		return jdbcTemplate.query(sqlStatement, new Object[] { tagName }, 
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("tagId");
					}				
		});
	}
	
	public List<String> selectTagList(String strBulletinId) {
		String sqlStatement = " SELECT b.tagName"
							+ " FROM boardtagrelation a"
							+ " INNER JOIN hashtag b on b.tagId = a.tagId"
							+ " WHERE a.bulletinId = ?";
		
		
		return jdbcTemplate.query(sqlStatement, new Object[] { strBulletinId }, 
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("tagName");
					}				
		});
	}
	
	public String createHashTagInfoInsert(final String tagName) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String strTagId = "";
		List<String> listTags = null;
		
		listTags = selectTagIdByName(tagName);
				
		if(listTags!=null && listTags.size() > 0) {
			strTagId = listTags.get(0);
			return strTagId;
		}
		
		if(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				String sqlStatement = "insert into hashtag (tagName, isrtDt) values(?, SYSDATE())";

				PreparedStatement pstmt = con.prepareStatement(sqlStatement, new String[] {"tagId"});

				pstmt.setString(1, tagName);
				return pstmt;
			}}, keyHolder)!=1)
			return "";
		
		strTagId = keyHolder.getKey().toString();
		
		return strTagId;
	}
	
	public boolean createBoardTagRelationInfo(String strBulletinId, List<String> listHashTagId) {
		
		String sqlStatement = "insert into BoardTagRelation (bulletinId, tagId, isrtDt) values(?, ?, SYSDATE())";
		
		for(String strTagId : listHashTagId) {
			if(!(jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId, strTagId }) == 1)) {
				return false;
			}
		}
		return true;
	}
}
