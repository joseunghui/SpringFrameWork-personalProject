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
	
	// ȸ������
	public int mJoin(Member member) {
		//System.out.println("[3]dao : member ->" + member);
		return sql.insert("Member.mJoin", member);
	}

	// �α���
	public Member mLogin(Member member) {
		//System.out.println("[7]dao : member ->" + member);
		return sql.selectOne("Member.mLogin", member);
	}

	// ȸ�� ��� ���� ����
	public int mListCount() {
		//System.out.println("[12-2]dao");
		return sql.selectOne("Member.mListCount");
	}

	// ȸ�� ���
	public List<Member> mList(Page paging) {
		//System.out.println("[13]dao : paging ->" + paging);
		return sql.selectList("Member.mList", paging);
	}

	// �� ���� ����
	public Member mView(String Mid) {
		return sql.selectOne("Member.mView", Mid);
	}

	// ȸ�� ���� ����
	public int mModify(Member member) {
		return sql.update("Member.mModify", member);
	}

	// ȸ�� ����
	public int mDelete(String Mid) {
		return sql.delete("Member.mDelete", Mid);
	}
	
	// ���̵� �ߺ� �˻�
	public String idoverlap(String Mid) {
		return sql.selectOne("Member.idoverlap", Mid);
	}
	public Member mEncPw(String Mid) {
		return sql.selectOne("Member.mEncPw", Mid);
	}




	
	
}
