package kr.ac.sungkyul.mysite.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(String email){
		UserVo vo = sqlSession.selectOne("user.getByEmail", email);
		
		return vo;
	}
	
	public boolean update(UserVo vo){
		return sqlSession.update("user.update", vo) != 0;
	}
	
	public UserVo get(Long userNo){
		UserVo vo = sqlSession.selectOne("user.getByNo", userNo);
		//List<UserVo> list = sqlSession.selectList("user.getByNo", userNo);
		
		return vo;
	}
	
	public UserVo get(String email, String password){
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		
		// 만약에 파라미터로 넘겨야할 매핑 클래스가 없는 경우
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("email", email);
//		map.put("password", password);
		// 넘기는 것도 map (alias 되있음)
		
		UserVo vo = sqlSession.selectOne("user.getByEmailAndPassword", userVo);
		
		return vo;
	}
	
	public void insert(UserVo vo){
		int count = sqlSession.insert("user.insert", vo);
		System.out.println(count);
	}
}
