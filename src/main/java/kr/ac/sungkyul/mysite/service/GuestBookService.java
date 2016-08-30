package kr.ac.sungkyul.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.sungkyul.mysite.dao.GuestBookDao;
import kr.ac.sungkyul.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao guestBookDao;
	
	public List<GuestBookVo> list(){
		return guestBookDao.getList();
	}
	
	public void delete(GuestBookVo vo){
		guestBookDao.delete(vo);
	}
	
	public void insert(GuestBookVo vo){
		guestBookDao.insert(vo);
	}
}
