package com.yinye.yyedt.checkDeps;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.yinye.yyedt.CheckUnused.bs.CUnusedDao;
import com.yinye.yyedt.checkDeps.bs.CDepsModInfoHelper;
import com.yinye.yyedt.checkDeps.bs.CDepsdDao;

import java.io.IOException;
import java.util.List;

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


    public List<String> loadFilter(){
        String workPath = this.project.getBasePath();
        return CDepsdDao.getInstance().loadFilter(workPath);
    }
    public void saveFilter(String filterText,String includeText) throws IOException {
        String workPath = this.project.getBasePath();
        CDepsdDao.getInstance().saveFilter(workPath,filterText,includeText);
    }

}


