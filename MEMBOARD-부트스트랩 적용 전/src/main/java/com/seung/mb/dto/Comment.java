package com.seung.mb.dto;

import lombok.Data;

@Data
public class Comment {
	private int Cno;   // 댓글 번호(PK)
	private int CBno;  // 게시글 번호(FK)
	private String Cwriter;  // 작성자
	private String Ccontent; // 댓글 내용
	private String Cdate;
	
	/*
	 	CREATE TABLE COMMENTDTO(
    CNO         NUMBER PRIMARY KEY,
    CBNO        NUMBER,
    CWRITER     NVARCHAR2(20),
    CCONTENT    NVARCHAR2(500),
    CDATE       DATE,
    CONSTRAINT CBNO_FK FOREIGN KEY(CBNO) REFERENCES BOARDDTO(BNUM) 
);
	 */
}
