package com.seung.mb.dto;

import lombok.Data;

@Data
public class Comment {
	private int Cno;   // ��� ��ȣ(PK)
	private int CBno;  // �Խñ� ��ȣ(FK)
	private String Cwriter;  // �ۼ���
	private String Ccontent; // ��� ����
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
