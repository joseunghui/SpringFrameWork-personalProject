package com.seung.mb.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seung.mb.dto.Board;
import com.seung.mb.dto.Comment;
import com.seung.mb.dto.Member;
import com.seung.mb.dto.Page;

@Repository
public class BDAO {

	@Autowired
	private SqlSessionTemplate sql;

	// 게시글 작성하기
	public int bWrite(Board board) {
		return sql.insert("Board.bWrite", board);
	}

	// 게시글 갯수 세기
	public int mListCount() {
		return sql.selectOne("Board.bListCount");
	}	
	
	// 게시글 목록 불러오기
	public List<Board> mList(Page paging) {
		return sql.selectList("Board.bList", paging);
	}
	
	// 게시글 상세보기
	public Board bView(int Bnum, int page) {
	return sql.selectOne("Board.bView", Bnum);
	}
	
	// 게시글 수정하기
	public int bModi(Board board, int page) {
		return sql.update("Board.bModi", board);
	}
	
	// 게시글 삭제하기
	public int bDel(int Bnum, int page) {
		return sql.delete("Board.bDel", Bnum);
	}
	
	// 기능추가
	// 게시글 목록 옆에 댓글 목록 불러오기
	public List<Comment> cList(int CBno) {
		return sql.selectList("Board.cList", CBno);
	}
	// 댓글 입력(작성)
	public int cWrite(Comment comment) {
		return sql.insert("Board.cWrite", comment);
	}
	// 댓글 삭제
	public int cDel(Comment comment) {
		return sql.delete("Board.cDel", comment);
	}

	

	

	



	




	
	
}
