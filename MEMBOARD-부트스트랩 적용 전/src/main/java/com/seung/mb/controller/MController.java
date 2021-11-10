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
	
	// 0. �ʱ� ���� ȭ��(���⼭ ��������� BController������ ����� �ȵ�!!!) 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	
	// 1. ȸ������
	// M_joinForm : ȸ������ �������� �̵�
	@RequestMapping(value = "/M_joinForm", method = RequestMethod.GET)
	public String mJoinForm() {	
		return "M_Join";
	}
	// M_join : ȸ������ ����
	@RequestMapping(value="/M_join", method = RequestMethod.POST)
	public ModelAndView mJoin(@ModelAttribute Member member) throws IllegalStateException, IOException {
		//System.out.println("[1]Controller : member ->" + member);
		mav = msvc.mJoin(member);
		//System.out.println("[5]Controller : mav -> " + mav);
		return mav;
	}
	
	
	// 2. �α���
	// M_loginForm : �α��� �������� �̵�
	@RequestMapping(value = "/M_loginForm", method = RequestMethod.GET)
	public String mLoginForm() {	
		return "M_Login";
	}
	// M_login : �α��� �ϱ�
	@RequestMapping(value="/M_login", method = RequestMethod.POST)
	public ModelAndView mLogin(@ModelAttribute Member member) {
		//System.out.println("[6]Controller : member ->" + member);
		mav = msvc.mLogin(member);
		System.out.println("[10]Controller : mav -> " + mav);
		return mav;
	}
	
	
	// 3. �α׾ƿ�
	// M_logout : �α׾ƿ��ϱ�
	@RequestMapping(value = "/M_logout", method = RequestMethod.GET)
	public String mLogout() {	
		session.invalidate();
		return "index";
	}
	
	
	// 4. ȸ�� ���
	// M_list : ����¡ ó���� ȸ�� ���
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
	
	
	// 5. �� ���� ����
	// M_view : �� ���� �󼼺���
	@RequestMapping(value = "/M_view", method = RequestMethod.GET)
	public ModelAndView mView(@RequestParam("Mid") String Mid) {
		mav = msvc.mView(Mid);
		return mav;
	}
	
	
	// 6. ȸ�� ���� ����
	// M_modiForm : ȸ�� ���� ���� �������� �̵�
	@RequestMapping(value = "/M_modiForm", method = RequestMethod.GET)
	public ModelAndView mModiForm(@RequestParam("Mid") String Mid) {
		mav = msvc.mModiForm(Mid);
		return mav;
	}
	// M_modify : ȸ�� ���� ����
	@RequestMapping(value = "/M_modify", method = RequestMethod.POST)
	public ModelAndView mModify(@RequestParam(value="page", required=false, defaultValue="1") int page, @ModelAttribute Member member) throws IllegalStateException, IOException {
		mav = msvc.mModify(page, member);
		return mav;
	}

	// 7. ȸ�� ����
	// M_delete : ȸ�� ������ ����
	@RequestMapping(value = "/M_delete", method = RequestMethod.GET)
	public ModelAndView mDelete(@RequestParam("Mid") String Mid) {
		mav = msvc.mDelete(Mid);
		return mav;
	}
	
	// 8. ���̵� �ߺ� �˻�
	// A_idoverlap : ���̵� �ߺ� �˻��� �� ȸ�������ϱ�
	@RequestMapping(value="/A_idoverlap", method = RequestMethod.POST)
	public @ResponseBody String idoverlap(@RequestParam("Mid") String Mid) {
		String result = msvc.idoverlap(Mid);
		return result;
	}
	
	// 9. �̸�������
	// A_emailConfirm : �̸��Ϸ� ������ȣ ������
	@RequestMapping(value="/A_emailConfirm", method = RequestMethod.GET)
	public @ResponseBody String emailConfirm(@RequestParam("Memail") String Memail) {
		String emailKey = emailKey(false, 7);
		
		// ������ �̸��� Ű�� ������ ������
		MimeMessage mail = mailsender.createMimeMessage();
		
		String message = "<h2>�ȳ��ϼ���. seungȨ������ �Դϴ�</h2>"
						+ "<br/><p> ������ȣ�� " + emailKey + "�Դϴ�.</p>"
						+ "<p>������ȣ�� ������ȣ �Է¶��� ��Ȯ�� �������ּ���.</p>";
		try {
			mail.setSubject("[��������] seung Ȩ������ �̸��� ���� �����Դϴ�.", "utf-8");
			
			mail.setText(message, "utf-8", "html");
			mail.addRecipient(RecipientType.TO, new InternetAddress(Memail));
			mailsender.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return emailKey;
	}
	// ������ �̿��� Ű ����
	private boolean lowerCheck;
	private int size;
	// ���� �־��ֱ�
	public String emailKey(boolean lowerCheck, int size) {
		this.lowerCheck = lowerCheck;
		this.size = size;
		return init();
	}
	// ������ ����� �޼ҵ�
	private String init() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		
		int num = 0;
		do {
			num = ran.nextInt(75) + 48;
			// �ƽ�Ű �ڵ尡 48�̻��̸� ����, 65�̻��̸� �빮�� A~Z, 97�̻��̸� �ҹ��� a~z �� �̿�
			if((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char)num); // ������ num�� 1������ char������ ��ȯ�Ͽ� ���������� ���� sb�� ���
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