package com.yinye.yyedt.checkDeps;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.yinye.yyedt.checkDeps.bs.CDepsModInfoHelper;
import java.io.IOException;

public class CDepsMgr {

    private static CDepsMgr instance = null;
    public static CDepsMgr newInstance(Project project){
        instance = new CDepsMgr();
        instance.project = project;
        return instance;
    }
    public static CDepsMgr getInstance(){
        return instance;
    }
    private Project project;

    public void showMsgBox(String title, String msg){
        Messages.showInfoMessage(project,msg,title);
    }

    public CDepsResult doCheckDeps(String oriDirPath, String destDirPath) throws IOException {
        return CDepsModInfoHelper.getInstance().checkDeps(oriDirPath,destDirPath);
    }


}


