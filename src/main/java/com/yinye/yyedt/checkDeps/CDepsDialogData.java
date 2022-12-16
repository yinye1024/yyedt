package com.yinye.yyedt.checkDeps;

public class CDepsDialogData {

    //源目录
    private String oriDirPath;
    //目标目录
    private String destDirPath;

    // 1 检查源目录引用了目标目录的哪些模块 check deps
    // 2 检查源目录被目标目录引用了哪些模块 check refs


    public CDepsDialogData(String oriDirPathP, String destDirPathP) {
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
