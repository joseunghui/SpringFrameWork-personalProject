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

	// 회원가입
	public ModelAndView mJoin(Member member) throws IllegalStateException, IOException {
		//System.out.println("[2]Service : member ->" + member);
		// 파일 이름 가져오기(Mprofile은 파일 자체, MprofileName이 파일 이름임!)
		// Mprofile > MprofileName
		MultipartFile Mprofile = member.getMprofile();
		String MprofileName = Mprofile.getOriginalFilename();
		
		// 주소 addr1, addr2, addr3 합치기
		member.setMaddr("(" + member.getAddr1() + ")" + member.getAddr2() + ", " + member.getAddr3());
		
		// 파일 저장 위치 설정
		String savePath="C:/Users/ooo16/Downloads/sts-4.11.1.RELEASE/springFile/MEMBOARD/src/main/webapp/resources/memProfile/" + MprofileName;
		
		// 파일 업로드 성공시
		if(!Mprofile.isEmpty()) {
			member.setMprofileName(MprofileName);
			Mprofile.transferTo(new File(savePath));
		}
		
		// 입력받은 패스워드 -> 암호화 -> DB로 전달
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

	// 로그인
	public ModelAndView mLogin(Member member) {
		System.out.println("[6]Service : member ->" + member);
		// 비밀번호가 암호화됐는데 암호화하기 전 비밀번호로 로그인하는 방법
		// [1] 입력한 아이디로 암호화된 PW를 검색
		Member encPw = dao.mView(member.getMid());
		System.out.println("[14]Service : encPw ->" + encPw);
		
		// [2] DB에 저장된 PW와 입력한 PW를 match해서 일치여부를 확인하기
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
	// 회원 목록
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

	// 내 정보 보기
	public ModelAndView mView(String mid) {
		Member member = dao.mView(mid);
		
		mav.addObject("view", member);
		mav.setViewName("M_View");
		return mav;
	}

	// 회원 수정 페이지로 이동
	public ModelAndView mModiForm(String Mid) {
		Member member = dao.mView(Mid);
		
		mav.addObject("modi", member);
		mav.setViewName("M_Modi");
		return mav;
	}

	// 회원 정보 수정
	public ModelAndView mModify(int page, Member member) throws IllegalStateException, IOException {
		MultipartFile Mprofile = member.getMprofile();
		String MprofileName = Mprofile.getOriginalFilename();
		
		String savePath="C:/Users/ooo16/Downloads/sts-4.11.1.RELEASE/springFile/MEMBOARD/src/main/webapp/resources/memProfile/" + MprofileName;
				
		// 업로드한 파일이 있을 경우
		if(!Mprofile.isEmpty()) {
			member.setMprofileName(MprofileName);
			Mprofile.transferTo(new File(savePath));
		} 
		// 입력받은 패스워드 -> 암호화 -> DB로 전달				
		member.setMpw(pwEnc.encode(member.getMpw()));
		
		int result = dao.mModify(member);
		if(result>0) {
			mav.setViewName("redirect:/M_view?Mid=" + member.getMid());
		} else {
			mav.setViewName("index");
		}
		return mav;
	}

	// 회원 삭제
	public ModelAndView mDelete(String Mid) {
		int result = dao.mDelete(Mid);
		if(result>0) {
			mav.setViewName("redirect:/M_list");
		} else {
			mav.setViewName("index");
		}
		return mav;
	}	

	// 아이디 중복 검사
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
