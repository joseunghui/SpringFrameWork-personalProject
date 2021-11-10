package com.seung.mb.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Board {

    private String Bnum;
    private String Bwri;
    private String Btitle;
    private String Bcontent; 
    private String Bdate;
    private int Bhit;
    private MultipartFile Bfile;
    private String BfileName;
}
