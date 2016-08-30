package kr.ac.sungkyul.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.sungkyul.mysite.service.GuestBookService;
import kr.ac.sungkyul.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping("/list")
	public String list(Model model){
		List<GuestBookVo> list = guestBookService.list();
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	@RequestMapping("/deleteform/{no}")
	public String deleteForm(@PathVariable("no") Long no){
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestBookVo vo){
		guestBookService.insert(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @ModelAttribute GuestBookVo vo){
		guestBookService.delete(vo);
		return "redirect:/guestbook/list";
	}
}
