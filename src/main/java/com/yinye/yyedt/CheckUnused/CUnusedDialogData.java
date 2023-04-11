package com.yinye.yyedt.CheckUnused;

public class CUnusedDialogData {

    //源目录
    private String oriDirPath;
    //目标目录
    private String destDirPath;


    public CUnusedDialogData(String oriDirPathP, String destDirPathP) {
        this.oriDirPath = oriDirPathP;
        this.destDirPath = destDirPathP;
    }

    public String getOriDirPath() {
        return oriDirPath;
    }

    public String getDestDirPath() {
        return destDirPath;
    }
}
