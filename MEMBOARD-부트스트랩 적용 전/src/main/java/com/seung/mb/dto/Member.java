package com.seung.mb.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Member {
	
	private String Mid;
	private String Mpw;
	private String Mname;
	private String Mphone;
	private String Mbirth;
	private String Memail;
	private String Maddr;
	private MultipartFile Mprofile;
	private String MprofileName;
	
	private String addr1;
	private String addr2;
	private String addr3;
}
