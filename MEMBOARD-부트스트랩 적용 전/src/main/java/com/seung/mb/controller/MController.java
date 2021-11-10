package com.seung.mb.controller;


import java.io.IOException;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seung.mb.dto.Member;
import com.seung.mb.service.MService;

@Controller
public class MController {

	@Autowired
	private MService msvc;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	JavaMailSender mailsender;
	
	private ModelAndView mav = new ModelAndView();
	
	// 0. 초기 실행 화면(여기서 만들었으면 BController에서는 만들면 안됨!!!) 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	
	// 1. 회원가입
	// M_joinForm : 회원가입 페이지로 이동
	@RequestMapping(value = "/M_joinForm", method = RequestMethod.GET)
	public String mJoinForm() {	
		return "M_Join";
	}
	// M_join : 회원가입 실행
	@RequestMapping(value="/M_join", method = RequestMethod.POST)
	public ModelAndView mJoin(@ModelAttribute Member member) throws IllegalStateException, IOException {
		//System.out.println("[1]Controller : member ->" + member);
		mav = msvc.mJoin(member);
		//System.out.println("[5]Controller : mav -> " + mav);
		return mav;
	}
	
	
	// 2. 로그인
	// M_loginForm : 로그인 페이지로 이동
	@RequestMapping(value = "/M_loginForm", method = RequestMethod.GET)
	public String mLoginForm() {	
		return "M_Login";
	}
	// M_login : 로그인 하기
	@RequestMapping(value="/M_login", method = RequestMethod.POST)
	public ModelAndView mLogin(@ModelAttribute Member member) {
		//System.out.println("[6]Controller : member ->" + member);
		mav = msvc.mLogin(member);
		System.out.println("[10]Controller : mav -> " + mav);
		return mav;
	}
	
	
	// 3. 로그아웃
	// M_logout : 로그아웃하기
	@RequestMapping(value = "/M_logout", method = RequestMethod.GET)
	public String mLogout() {	
		session.invalidate();
		return "index";
	}
	
	
	// 4. 회원 목록
	// M_list : 페이징 처리된 회원 목록
	@RequestMapping(value = "/M_list", method = RequestMethod.GET)
	public ModelAndView mList(@RequestParam(value="page", required=false, defaultValue="1") int page) {
		if(page<=0) {
			page = 1;
		}
		//System.out.println("[10] Controller : page" + page);
		mav = msvc.mList(page);
		//System.out.println("[15] Controller : mav" + mav);
		return mav;
	}
	
	
	// 5. 내 정보 보기
	// M_view : 내 정보 상세보기
	@RequestMapping(value = "/M_view", method = RequestMethod.GET)
	public ModelAndView mView(@RequestParam("Mid") String Mid) {
		mav = msvc.mView(Mid);
		return mav;
	}
	
	
	// 6. 회원 정보 수정
	// M_modiForm : 회원 정보 수정 페이지로 이동
	@RequestMapping(value = "/M_modiForm", method = RequestMethod.GET)
	public ModelAndView mModiForm(@RequestParam("Mid") String Mid) {
		mav = msvc.mModiForm(Mid);
		return mav;
	}
	// M_modify : 회원 정보 수정
	@RequestMapping(value = "/M_modify", method = RequestMethod.POST)
	public ModelAndView mModify(@RequestParam(value="page", required=false, defaultValue="1") int page, @ModelAttribute Member member) throws IllegalStateException, IOException {
		mav = msvc.mModify(page, member);
		return mav;
	}

	// 7. 회원 삭제
	// M_delete : 회원 정보를 삭제
	@RequestMapping(value = "/M_delete", method = RequestMethod.GET)
	public ModelAndView mDelete(@RequestParam("Mid") String Mid) {
		mav = msvc.mDelete(Mid);
		return mav;
	}
	
	// 8. 아이디 중복 검사
	// A_idoverlap : 아이디 중복 검사한 후 회원가입하기
	@RequestMapping(value="/A_idoverlap", method = RequestMethod.POST)
	public @ResponseBody String idoverlap(@RequestParam("Mid") String Mid) {
		String result = msvc.idoverlap(Mid);
		return result;
	}
	
	// 9. 이메일인증
	// A_emailConfirm : 이메일로 인증번호 보내기
	@RequestMapping(value="/A_emailConfirm", method = RequestMethod.GET)
	public @ResponseBody String emailConfirm(@RequestParam("Memail") String Memail) {
		String emailKey = emailKey(false, 7);
		
		// 생성된 이메일 키로 메일을 보내자
		MimeMessage mail = mailsender.createMimeMessage();
		
		String message = "<h2>안녕하세요. seung홈페이지 입니다</h2>"
						+ "<br/><p> 인증번호는 " + emailKey + "입니다.</p>"
						+ "<p>인증번호를 인증번호 입력란에 정확히 기입해주세요.</p>";
		try {
			mail.setSubject("[본인인증] seung 홈페이지 이메일 인증 메일입니다.", "utf-8");
			
			mail.setText(message, "utf-8", "html");
			mail.addRecipient(RecipientType.TO, new InternetAddress(Memail));
			mailsender.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return emailKey;
	}
	// 난수를 이용한 키 생성
	private boolean lowerCheck;
	private int size;
	// 값을 넣어주기
	public String emailKey(boolean lowerCheck, int size) {
		this.lowerCheck = lowerCheck;
		this.size = size;
		return init();
	}
	// 난수를 만드는 메소드
	private String init() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		
		int num = 0;
		do {
			num = ran.nextInt(75) + 48;
			// 아스키 코드가 48이상이면 숫자, 65이상이면 대문자 A~Z, 97이상이면 소문자 a~z 를 이용
			if((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char)num); // 생성된 num를 1음절인 char단위로 변환하여 랜덤생성된 값을 sb에 담기
			} else {
				continue;
			}
		} while(sb.length() < size);
		
		if(lowerCheck) {
			return sb.toString().toLowerCase();
		}
		return sb.toString();
	}
	
	
	
	
}