package com.yinye.yyedt.checkRepeatMod;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class CRModMgr {

    private static CRModMgr instance = null;
    public static CRModMgr newInstance(Project project){
        instance = new CRModMgr();
        instance.project = project;
        return instance;
    }
    public static CRModMgr getInstance(){
        return instance;
    }
    private Project project;

    public void showMsgBox(String title, String msg){
        Messages.showInfoMessage(project,msg,title);
    }


}


