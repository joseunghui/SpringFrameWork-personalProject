package com.seung.mb.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.seung.mb.dao.BDAO;
import com.seung.mb.dto.Board;
import com.seung.mb.dto.Comment;
import com.seung.mb.dto.Member;
import com.seung.mb.dto.Page;

@Service
public class BService {
	
	@Autowired
	private BDAO Bdao;
	
	@Autowired
	private HttpSession session;
	
	private ModelAndView mav = new ModelAndView();

	// 게시글 작성하기
	public ModelAndView bWrite(Board board) throws IllegalStateException, IOException {
		MultipartFile Bfile = board.getBfile();
		String BfileName = Bfile.getOriginalFilename();
		
		String savePath="C:/Users/ooo16/Downloads/sts-4.11.1.RELEASE/springFile/MEMBOARD/src/main/webapp/resources/boardFile/" + BfileName;
		if(!Bfile.isEmpty()) {
			board.setBfileName(BfileName);
			Bfile.transferTo(new File(savePath));
		}
		int result = Bdao.bWrite(board);
		if(result>0) {
			mav.setViewName("redirect:/B_list");
		} else {
			mav.setViewName("index");
		}
		return mav;
	}

	private static final int PAGE_LIMIT = 5;
	private static final int BLOCK_LIMIT = 5;
	// 게시글 목록 보기
	public ModelAndView bList(int page) {
		Page paging = new Page();
		
		int listCount = Bdao.mListCount();
		
		int startRow = (page-1)* PAGE_LIMIT + 1;
		int endRow = page*PAGE_LIMIT;
		
		int maxPage = (int)(Math.ceil((double)listCount / PAGE_LIMIT));
		int startPage = (((int)(Math.ceil((double)page / BLOCK_LIMIT))) -1 ) * BLOCK_LIMIT + 1;
		int endPage = startPage + BLOCK_LIMIT - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		paging.setPage(page);
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);
		paging.setMaxPage(maxPage);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		
		List<Board> boardList = Bdao.mList(paging);
		
		mav.addObject("boardList", boardList);
		mav.addObject("paging", paging);
		mav.setViewName("B_List");
		return mav;
	}
	
	// 게시글 상세보기
	public ModelAndView bView(int Bnum, int page) {
		Page paging = new Page();
		Board board = Bdao.bView(Bnum, page);
		
		// 상세보기 누를때마다 조회수 올리기 -> 수정 필요
		board.setBhit(board.getBhit());
		
		mav.addObject("view", board);
		mav.addObject("paging", paging);
		mav.setViewName("B_View");
		
		return mav;
	}
	
	// 게시글 수정 페이지로 이동
	public ModelAndView bModiForm(int Bnum, int page) {
		Board board = Bdao.bView(Bnum, page);
		
		mav.addObject("modi", board);
		mav.setViewName("B_Modi");
		return mav;
	}
	
	// 게시글 수정하기
	public ModelAndView bModi(Board board, int page) throws IllegalStateException, IOException {
		MultipartFile Bfile = board.getBfile();
		String BfileName = Bfile.getOriginalFilename();
		
		String savePath="C:/Users/ooo16/Downloads/sts-4.11.1.RELEASE/springFile/MEMBOARD/src/main/webapp/resources/boardFile/" + BfileName;
		
		if(!Bfile.isEmpty()) {
			board.setBfileName(BfileName);
			Bfile.transferTo(new File(savePath));
		}
		int result = Bdao.bModi(board, page);
		if(result>0) {
			mav.setViewName("redirect:/B_list?page=" + page );
		} else {
			mav.setViewName("index");
		}
		return mav;
	}

	// 게시글 삭제
	public ModelAndView bDel(int Bnum, int page) {
		int result = Bdao.bDel(Bnum, page);
		if(result>0) {
			mav.setViewName("redirect:/B_list?page=" + page);
		} else {
			mav.setViewName("index");
		}
		return mav;
	}
	
	
	
	
	// 기능 추가
	List<Comment> commentList = new ArrayList<Comment>();
	
	// 게시글 목록 옆에 댓글 목록 불러오기
	public List<Comment> cList(int CBno) {
		commentList = Bdao.cList(CBno);
		return commentList;
	}

	// 댓글 입력(작성)
	public List<Comment> cWrite(Comment comment) {
		commentList = null;
		int result = Bdao.cWrite(comment);
		
		if(result>0) {
			commentList = Bdao.cList(comment.getCBno());
		} else {
			commentList = null;
		}
		return commentList;
	}

	// 댓글 삭제
	public List<Comment> cDel(Comment comment) {
		commentList = null;
		int result = Bdao.cDel(comment);
		
		if(result>0) {
			commentList = Bdao.cList(comment.getCBno());
		} else {
			commentList = null;
		}
		return commentList;
	}







}
