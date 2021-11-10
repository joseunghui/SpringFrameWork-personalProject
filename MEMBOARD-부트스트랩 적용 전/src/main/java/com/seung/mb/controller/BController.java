package com.seung.mb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seung.mb.dto.Board;
import com.seung.mb.dto.Comment;
import com.seung.mb.service.BService;


@Controller
public class BController {
	
	@Autowired
	private BService bsvc;
	
	@Autowired
	private HttpSession session;
	
	private ModelAndView mav = new ModelAndView();
		
	// 1. 게시글 작성하기
	// B_writeForm : 게시글 작성 페이지로 이동하기
	@RequestMapping(value="B_writeForm", method = RequestMethod.GET)
	public String B_writeForm(@RequestParam("Mid") String Mid, Board board) {
		session.setAttribute("loginId", Mid);
		return "B_Write";
	}
	// B_write : 게시글 작성하기
	@RequestMapping(value="/B_write", method = RequestMethod.POST)
	public ModelAndView bWrite(@ModelAttribute Board board) throws IllegalStateException, IOException {
		mav = bsvc.bWrite(board);
		return mav;
	}
	
	// 2. 게시글 목록 보기
	// B_list : 게시글 작성 이후 목록 보기 오픈
	@RequestMapping(value="/B_list", method = RequestMethod.GET)
	public ModelAndView bList(@RequestParam(value="page", required=false, defaultValue="1") int page) {
		if(page<=0) {
			page = 1;
		}
		mav = bsvc.bList(page);
		return mav;
	}
	
	// 3. 게시글 상세보기
	// B_view : 게시글 제목을 누르면 글 상세보기 페이지로 이동
	@RequestMapping(value="/B_view", method = RequestMethod.GET)
	public ModelAndView bView(@RequestParam("Bnum") int Bnum, @RequestParam(value="paging", required=false, defaultValue="1") int page) {
		Board board = new Board();
		mav = bsvc.bView(Bnum, page);
		return mav;
	}
	
	// 4. 게시글 수정하기
	// B_modiForm : 게시글 수정 jsp로 이동
	@RequestMapping(value="/B_modiForm", method = RequestMethod.GET)
	public ModelAndView bModiForm(@RequestParam("Bnum") int Bnum, @RequestParam(value="paging", required=false, defaultValue="1") int page) throws IllegalStateException, IOException {
		mav = bsvc.bModiForm(Bnum, page);
		return mav;
	}
	// B_modi : 게시글 수정
	@RequestMapping(value="/B_modi", method = RequestMethod.POST)
	public ModelAndView bModi(@ModelAttribute Board board, @RequestParam(value="paging", required=false, defaultValue="1") int page) throws IllegalStateException, IOException {
		mav = bsvc.bModi(board, page);
		return mav;
	}
	
	// 5. 게시글 삭제
	// B_delete : 게시글 번호(Bnum)를 가져와서 해당 글을 삭제
	@RequestMapping(value="/B_delete", method = RequestMethod.GET)
	public ModelAndView bDel(@RequestParam("Bnum") int Bnum,  @RequestParam(value="paging", required=false, defaultValue="1") int page) {
		mav = bsvc.bDel(Bnum, page);
		return mav;
	}
	
	
	
	// 기능 추가
	List<Comment> commentList = new ArrayList<Comment>();	
	
	// C_list : 게시글 목록 옆에 댓글 리스트 불러오기
	@RequestMapping(value="/C_list", method = RequestMethod.POST)
	public @ResponseBody List<Comment> cList(@RequestParam("CBno") int CBno){
		commentList = bsvc.cList(CBno);
		return commentList;
	}
	
	// C_write : 해당 게시글에 댓글 입력하기
	@RequestMapping(value="/C_write", method = RequestMethod.POST)
	public @ResponseBody List<Comment> cWrite(@ModelAttribute Comment comment){
		commentList = bsvc.cWrite(comment);
		
		session.setAttribute("loginId", comment.getCwriter());
		return commentList;
	}

	// C_delete : 댓글 삭제하기
	@RequestMapping(value="/C_delete", method = RequestMethod.GET)
	public @ResponseBody List<Comment> cDel(@ModelAttribute Comment comment){
		commentList = bsvc.cDel(comment);
		return commentList;
	}
		
		

}
