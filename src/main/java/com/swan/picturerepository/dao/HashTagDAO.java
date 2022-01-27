package com.swan.picturerepository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.swan.picturerepository.model.BulletinBoard;

@Repository
public class HashTagDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<String> createHashTagInfo(List<String> arrTags) {
		List<String> listTagIds = new ArrayList<>();
		for(String tagName : arrTags) {
			listTagIds.add(createHashTagInfoInsert(tagName));
		}
		return listTagIds;
	}
	public String createHashTagInfoInsert(final String tagName) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String strTagId = "";
		
		if(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String sqlStatement = "insert into hashtag (tagName, isrtDt) values(?, SYSDATE())";

				PreparedStatement pstmt = con.prepareStatement(sqlStatement, new String[] {"tagId"});

				pstmt.setString(1, tagName);
				return pstmt;
			}}, keyHolder)!=1)
			return "";
		
		strTagId = keyHolder.getKey().toString();
		
		return strTagId;
	}
}
