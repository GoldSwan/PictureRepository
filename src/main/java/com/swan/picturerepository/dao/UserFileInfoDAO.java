package com.swan.picturerepository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.swan.picturerepository.dto.UserFileInfoDTO;

@Repository
public class UserFileInfoDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
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
	
	public boolean deleteUserFileInfo(List<UserFileInfoDTO> fileList) {		
		String sqlStatement = "delete from userfileinfo where fileId = ?";
		
		for(UserFileInfoDTO userFileInfoDTO : fileList) {
			if(!(jdbcTemplate.update(sqlStatement, new Object[] { userFileInfoDTO.getFileId() }) == 1)) {
				return false;
			}
		}
		return true;
	}
}
