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

	// �Խñ� �ۼ��ϱ�
	public int bWrite(Board board) {
		return sql.insert("Board.bWrite", board);
	}

	// �Խñ� ���� ����
	public int mListCount() {
		return sql.selectOne("Board.bListCount");
	}	
	
	// �Խñ� ��� �ҷ�����
	public List<Board> mList(Page paging) {
		return sql.selectList("Board.bList", paging);
	}
	
	// �Խñ� �󼼺���
	public Board bView(int Bnum, int page) {
	return sql.selectOne("Board.bView", Bnum);
	}
	
	// �Խñ� �����ϱ�
	public int bModi(Board board, int page) {
		return sql.update("Board.bModi", board);
	}
	
	// �Խñ� �����ϱ�
	public int bDel(int Bnum, int page) {
		return sql.delete("Board.bDel", Bnum);
	}
	
	// ����߰�
	// �Խñ� ��� ���� ��� ��� �ҷ�����
	public List<Comment> cList(int CBno) {
		return sql.selectList("Board.cList", CBno);
	}
	// ��� �Է�(�ۼ�)
	public int cWrite(Comment comment) {
		return sql.insert("Board.cWrite", comment);
	}
	// ��� ����
	public int cDel(Comment comment) {
		return sql.delete("Board.cDel", comment);
	}

	

	

	



	




	
	
}
