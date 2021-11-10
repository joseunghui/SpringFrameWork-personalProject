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
		
	// 1. �Խñ� �ۼ��ϱ�
	// B_writeForm : �Խñ� �ۼ� �������� �̵��ϱ�
	@RequestMapping(value="B_writeForm", method = RequestMethod.GET)
	public String B_writeForm(@RequestParam("Mid") String Mid, Board board) {
		session.setAttribute("loginId", Mid);
		return "B_Write";
	}
	// B_write : �Խñ� �ۼ��ϱ�
	@RequestMapping(value="/B_write", method = RequestMethod.POST)
	public ModelAndView bWrite(@ModelAttribute Board board) throws IllegalStateException, IOException {
		mav = bsvc.bWrite(board);
		return mav;
	}
	
	// 2. �Խñ� ��� ����
	// B_list : �Խñ� �ۼ� ���� ��� ���� ����
	@RequestMapping(value="/B_list", method = RequestMethod.GET)
	public ModelAndView bList(@RequestParam(value="page", required=false, defaultValue="1") int page) {
		if(page<=0) {
			page = 1;
		}
		mav = bsvc.bList(page);
		return mav;
	}
	
	// 3. �Խñ� �󼼺���
	// B_view : �Խñ� ������ ������ �� �󼼺��� �������� �̵�
	@RequestMapping(value="/B_view", method = RequestMethod.GET)
	public ModelAndView bView(@RequestParam("Bnum") int Bnum, @RequestParam(value="paging", required=false, defaultValue="1") int page) {
		Board board = new Board();
		mav = bsvc.bView(Bnum, page);
		return mav;
	}
	
	// 4. �Խñ� �����ϱ�
	// B_modiForm : �Խñ� ���� jsp�� �̵�
	@RequestMapping(value="/B_modiForm", method = RequestMethod.GET)
	public ModelAndView bModiForm(@RequestParam("Bnum") int Bnum, @RequestParam(value="paging", required=false, defaultValue="1") int page) throws IllegalStateException, IOException {
		mav = bsvc.bModiForm(Bnum, page);
		return mav;
	}
	// B_modi : �Խñ� ����
	@RequestMapping(value="/B_modi", method = RequestMethod.POST)
	public ModelAndView bModi(@ModelAttribute Board board, @RequestParam(value="paging", required=false, defaultValue="1") int page) throws IllegalStateException, IOException {
		mav = bsvc.bModi(board, page);
		return mav;
	}
	
	// 5. �Խñ� ����
	// B_delete : �Խñ� ��ȣ(Bnum)�� �����ͼ� �ش� ���� ����
	@RequestMapping(value="/B_delete", method = RequestMethod.GET)
	public ModelAndView bDel(@RequestParam("Bnum") int Bnum,  @RequestParam(value="paging", required=false, defaultValue="1") int page) {
		mav = bsvc.bDel(Bnum, page);
		return mav;
	}
	
	
	
	// ��� �߰�
	List<Comment> commentList = new ArrayList<Comment>();	
	
	// C_list : �Խñ� ��� ���� ��� ����Ʈ �ҷ�����
	@RequestMapping(value="/C_list", method = RequestMethod.POST)
	public @ResponseBody List<Comment> cList(@RequestParam("CBno") int CBno){
		commentList = bsvc.cList(CBno);
		return commentList;
	}
	
	// C_write : �ش� �Խñۿ� ��� �Է��ϱ�
	@RequestMapping(value="/C_write", method = RequestMethod.POST)
	public @ResponseBody List<Comment> cWrite(@ModelAttribute Comment comment){
		commentList = bsvc.cWrite(comment);
		
		session.setAttribute("loginId", comment.getCwriter());
		return commentList;
	}

	// C_delete : ��� �����ϱ�
	@RequestMapping(value="/C_delete", method = RequestMethod.GET)
	public @ResponseBody List<Comment> cDel(@ModelAttribute Comment comment){
		commentList = bsvc.cDel(comment);
		return commentList;
	}
		
		

}
