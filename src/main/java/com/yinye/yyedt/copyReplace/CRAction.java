package com.yinye.yyedt.copyReplace;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class CRAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if(vFile==null){
            Messages.showInfoMessage(project,"先选择模板目录或文件","");
        }else{
            String selectedFilePath = vFile.getPath();
            showDialog(project,selectedFilePath);
        }
    }


    /**
     *
     * @param filePath
     */
    private void showDialog(Project project, String filePath){
        try {
            String workPath = project.getBasePath(); // 获取工作空间路径
            //初始化单例
            CRMgr.newInstance(project);
            //准备创建工作目录
            CRMgr.getInstance().initCRDir(workPath);


            CRDialog myDialog = new CRDialog(new CRDialog.DialogCallBack() {
                @Override
                public void onOkClick(CRDialogData dialogData) {

                    try {
                        CRMgr.getInstance().copy2InputDir(filePath);
                        doCR(filePath,dialogData);
                    } catch (Exception e) {
                        e.printStackTrace();
                        CRMgr.getInstance().showMsgBox("错误信息","生成过程有异常，请查看console的错误日志" );
                    }
                }
            },filePath);
            myDialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCR(String filePath, CRDialogData dialogData){
        try {
            CRMgr.getInstance().doCR(filePath,dialogData);
        } catch (IOException e) {
            CRMgr.getInstance().showMsgBox("io 异常","生成过程有异常，请查看console的错误日志");
            throw new RuntimeException(e);
        }
    }



}
