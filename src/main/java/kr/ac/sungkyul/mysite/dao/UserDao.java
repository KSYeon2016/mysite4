package kr.ac.sungkyul.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.mysite.exception.UserInfoUpdateException;
import kr.ac.sungkyul.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource dataSource;
	
	public UserVo get(String email){
		UserVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "select no, name, email from users where email = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				vo = new UserVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setEmail(rs.getString(3));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
				if(conn != null){
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	public void update(UserVo vo) throws UserInfoUpdateException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = dataSource.getConnection();
			
			Long no = vo.getNo();
			String name = vo.getName();
			String password = vo.getPassword();
			String gender = vo.getGender();
			boolean isPasswordEmpty = "".equals(password);
			
			String sql = null;
			if(isPasswordEmpty == true){
				sql = "update users set name=?, gender=? where no=?";
			} else {
				sql = "update users set name=?, password=?, gender=? where no=?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			if(isPasswordEmpty == true){
				pstmt.setString(1, name);
				pstmt.setString(2, gender);
				pstmt.setLong(3, no);
			} else {
				pstmt.setString(1, name);
				pstmt.setString(2, password);
				pstmt.setString(3, gender);
				pstmt.setLong(4, no);
			}
			
			pstmt.executeUpdate();
		} catch(SQLException e){
			//e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try{
				if(pstmt != null){
					pstmt.close();
				}
				
				if(conn != null){
					conn.close();
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public UserVo get(Long userNo){
		UserVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "select no, name, gender from users where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, userNo);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				
				vo = new UserVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setGender(gender);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if(pstmt != null){
					pstmt.close();
				}
				
				if(conn != null){
					conn.close();
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	public UserVo get(String email, String password){
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		
		UserVo vo = sqlSession.selectOne("user.getByEmailAndPassword", userVo);
		
		return vo;
	}
	
	public void insert(UserVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "insert into users "
					+ "		values(seq_users.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			pstmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				if(pstmt != null){
					pstmt.close();
				}
				
				if(conn != null){
					conn.close();
				}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
