package com.yinye.yyedt.CheckUnused;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

public class CUnusedAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if(vFile!=null){
            String filePath = vFile.getPath();
            File selectFile = new File(filePath);
            showDialog(project,selectFile);
        }
    }


    private void showDialog(Project project, File selectFile){
        try {
            String workPath = project.getBasePath(); // 获取工作空间路径
            //初始化单例
            CUnusedMgr.newInstance(project);

            CUnusedDialog depsDialog = new CUnusedDialog(new CUnusedDialog.DialogCallBack() {
                @Override
                public CUnusedResult checkDeps(CUnusedDialogData dialogData) {
                    try {
                        return doCheckUnused(dialogData);
                    } catch (Exception e) {
                        e.printStackTrace();
                        CUnusedMgr.getInstance().showMsgBox("错误信息","生成过程有异常，请查看console的错误日志" );
                        return null;
                    }
                }

            },selectFile);
            depsDialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1 检查源目录有哪些模块方法是没有被目标目录调用过的，主要用来检查无用方法
    private CUnusedResult doCheckUnused(CUnusedDialogData dialogData) throws IOException {
        CUnusedResult result = CUnusedMgr.getInstance().checkUnused(dialogData.getOriDirPath(),dialogData.getDestDirPath());
        return result;
    }




}
