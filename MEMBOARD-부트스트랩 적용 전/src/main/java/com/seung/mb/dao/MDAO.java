package com.seung.mb.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seung.mb.dto.Member;
import com.seung.mb.dto.Page;

@Repository
public class MDAO {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	// 회원가입
	public int mJoin(Member member) {
		//System.out.println("[3]dao : member ->" + member);
		return sql.insert("Member.mJoin", member);
	}

	// 로그인
	public Member mLogin(Member member) {
		//System.out.println("[7]dao : member ->" + member);
		return sql.selectOne("Member.mLogin", member);
	}

	// 회원 목록 갯수 세기
	public int mListCount() {
		//System.out.println("[12-2]dao");
		return sql.selectOne("Member.mListCount");
	}

	// 회원 목록
	public List<Member> mList(Page paging) {
		//System.out.println("[13]dao : paging ->" + paging);
		return sql.selectList("Member.mList", paging);
	}

	// 내 정보 보기
	public Member mView(String Mid) {
		return sql.selectOne("Member.mView", Mid);
	}

	// 회원 정보 수정
	public int mModify(Member member) {
		return sql.update("Member.mModify", member);
	}

	// 회원 삭제
	public int mDelete(String Mid) {
		return sql.delete("Member.mDelete", Mid);
	}
	
	// 아이디 중복 검사
	public String idoverlap(String Mid) {
		return sql.selectOne("Member.idoverlap", Mid);
	}
	public Member mEncPw(String Mid) {
		return sql.selectOne("Member.mEncPw", Mid);
	}




	
	
}
