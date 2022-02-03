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
	
	public List<String> createHashTagInfo(List<String> tagList) {
		List<String> listTagIds = new ArrayList<>();
		String strTagId = "";
		
		for(String tagName : tagList) {
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
		
		String sqlStatement = "insert into boardtagrelation (bulletinId, tagId, isrtDt) values(?, ?, SYSDATE())";
		
		for(String strTagId : listHashTagId) {
			if(!(jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId, strTagId }) == 1)) {
				return false;
			}
		}
		return true;
	}

	public List<String> selectCanDeleteTagId(String strTagName, String strBulletinId) {
		//현재 게시판이 아닌 다른 게시판에 태그 참조가 없는 경우 삭제 가능. 즉 아래 sql 실행 결과로 반환되는 tagId는 삭제 가능
		String sqlStatement = " select tagId"
				            + " from hashtag a"
				            + " where a.tagName = ?"
				            + " and not exists"
				            + " (select 1 from boardtagrelation where tagId = a.tagId and bulletinId <> ?)"
				            + " limit 1";
		
		return jdbcTemplate.query(sqlStatement, new Object[] { strTagName, strBulletinId }, 
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("tagId");
					}				
		});
	}
	
	public boolean deleteHashTag(List<String> tagList) {
		for(String tagId : tagList) {
			if(!deleteHashTagInfo(tagId))
				return false;
		}
		return true;
	}
	
	public boolean deleteHashTagInfo(String strTagId) {
		String sqlStatement = " delete a from hashtag a where a.tagId = ?";
		return ((jdbcTemplate.update(sqlStatement, new Object[] { strTagId }) == 1));
	}
	
	public boolean deleteBoardTagRelation(String strBulletinId, List<String> tagList) {
		
		for(String tagName : tagList) {
			if(!deleteBoardTagRelationInfo(strBulletinId, tagName))
				return false;
		}
		return true;
	}
	
	public boolean deleteBoardTagRelationInfo(String strBulletinId, String strTagName) {		
		String sqlStatement = " delete a"
				            + " from boardtagrelation a"
				            + " inner join hashtag b on b.tagId = a.tagId"
				            + " where a.bulletinId = ? and b.tagName = ?";
		return ((jdbcTemplate.update(sqlStatement, new Object[] { strBulletinId, strTagName }) == 1));
	}
}
