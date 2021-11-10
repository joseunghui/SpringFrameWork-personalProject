package com.seung.mb.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.seung.mb.dto.Member;
import com.seung.mb.dao.MDAO;
import com.seung.mb.dto.Page;

@Service
public class MService {
	
	@Autowired
	private MDAO dao;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BCryptPasswordEncoder pwEnc;
	
	private ModelAndView mav = new ModelAndView();

	// ȸ������
	public ModelAndView mJoin(Member member) throws IllegalStateException, IOException {
		//System.out.println("[2]Service : member ->" + member);
		// ���� �̸� ��������(Mprofile�� ���� ��ü, MprofileName�� ���� �̸���!)
		// Mprofile > MprofileName
		MultipartFile Mprofile = member.getMprofile();
		String MprofileName = Mprofile.getOriginalFilename();
		
		// �ּ� addr1, addr2, addr3 ��ġ��
		member.setMaddr("(" + member.getAddr1() + ")" + member.getAddr2() + ", " + member.getAddr3());
		
		// ���� ���� ��ġ ����
		String savePath="C:/Users/ooo16/Downloads/sts-4.11.1.RELEASE/springFile/MEMBOARD/src/main/webapp/resources/memProfile/" + MprofileName;
		
		// ���� ���ε� ������
		if(!Mprofile.isEmpty()) {
			member.setMprofileName(MprofileName);
			Mprofile.transferTo(new File(savePath));
		}
		
		// �Է¹��� �н����� -> ��ȣȭ -> DB�� ����
		member.setMpw(pwEnc.encode(member.getMpw()));
		
		int result = dao.mJoin(member);
		//System.out.println("[4] Service : result ->" + result);
		
		if(result>0) {
			mav.setViewName("M_Login");
		} else {
			mav.setViewName("index");
		}
		
		return mav;
	}

	// �α���
	public ModelAndView mLogin(Member member) {
		System.out.println("[6]Service : member ->" + member);
		// ��й�ȣ�� ��ȣȭ�ƴµ� ��ȣȭ�ϱ� �� ��й�ȣ�� �α����ϴ� ���
		// [1] �Է��� ���̵�� ��ȣȭ�� PW�� �˻�
		Member encPw = dao.mView(member.getMid());
		System.out.println("[14]Service : encPw ->" + encPw);
		
		// [2] DB�� ����� PW�� �Է��� PW�� match�ؼ� ��ġ���θ� Ȯ���ϱ�
		if(pwEnc.matches(member.getMpw(), encPw.getMpw())) {
			session.setAttribute("loginId", encPw.getMid());
			session.setAttribute("profile", encPw.getMprofileName());
			mav.setViewName("index");
		} else {
			mav.setViewName("M_Login");
		}
		//System.out.println("[8] Service : member ->" + member);
		return mav;
	}

	private static final int PAGE_LIMIT = 5;
	private static final int BLOCK_LIMIT = 5;
	// ȸ�� ���
	public ModelAndView mList(int page) {
		//System.out.println("[12-1] Service : page ->" + page);
		
		Page paging = new Page();
		
		int listCount = dao.mListCount();
		//System.out.println("[12-3] Service : page ->" + page);
		
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
		
		List<Member> memberList = dao.mList(paging);
		// System.out.println("[4] Service : memberList ->" + memberList);
		
		mav.addObject("memberList", memberList);
		mav.addObject("paging", paging);
		mav.setViewName("M_List");
		
		return mav;
	}

	// �� ���� ����
	public ModelAndView mView(String mid) {
		Member member = dao.mView(mid);
		
		mav.addObject("view", member);
		mav.setViewName("M_View");
		return mav;
	}

	// ȸ�� ���� �������� �̵�
	public ModelAndView mModiForm(String Mid) {
		Member member = dao.mView(Mid);
		
		mav.addObject("modi", member);
		mav.setViewName("M_Modi");
		return mav;
	}

	// ȸ�� ���� ����
	public ModelAndView mModify(int page, Member member) throws IllegalStateException, IOException {
		MultipartFile Mprofile = member.getMprofile();
		String MprofileName = Mprofile.getOriginalFilename();
		
		String savePath="C:/Users/ooo16/Downloads/sts-4.11.1.RELEASE/springFile/MEMBOARD/src/main/webapp/resources/memProfile/" + MprofileName;
				
		// ���ε��� ������ ���� ���
		if(!Mprofile.isEmpty()) {
			member.setMprofileName(MprofileName);
			Mprofile.transferTo(new File(savePath));
		} 
		// �Է¹��� �н����� -> ��ȣȭ -> DB�� ����				
		member.setMpw(pwEnc.encode(member.getMpw()));
		
		int result = dao.mModify(member);
		if(result>0) {
			mav.setViewName("redirect:/M_view?Mid=" + member.getMid());
		} else {
			mav.setViewName("index");
		}
		return mav;
	}

	// ȸ�� ����
	public ModelAndView mDelete(String Mid) {
		int result = dao.mDelete(Mid);
		if(result>0) {
			mav.setViewName("redirect:/M_list");
		} else {
			mav.setViewName("index");
		}
		return mav;
	}	

	// ���̵� �ߺ� �˻�
	public String idoverlap(String Mid) {
		String idCheck = dao.idoverlap(Mid);
		String result = null;
		if(idCheck == null) {
			result = "OK";
		} else {
			result = "NO";
		}
		return result;
	}
	
	
	
	
}
