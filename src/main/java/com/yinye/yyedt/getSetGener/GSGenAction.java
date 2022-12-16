package com.yinye.yyedt.getSetGener;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class GSGenAction extends AnAction {

    private Project project;
    private Editor editor;
    private Caret caret;
    private String packageName = "";//
    private String text1; //
    private String text2; //
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
//        DataContext dataContext = e.getDataContext();
//        this.caret = e.getData(PlatformDataKeys.CARET);
//        String str = "";
//        if(caret != null){
//            str = caret.getSelectedText();
//        }

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        String selectedText = "";

        if(editor!=null){
            selectedText = editor.getSelectionModel().getSelectedText();
        }
        System.out.print("++++++++++ "+selectedText);
        if(selectedText == null){
            try {
                selectedText = (String) Toolkit.getDefaultToolkit()
                        .getSystemClipboard().getData(DataFlavor.stringFlavor);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.print("++++++++++ "+selectedText);
        init(selectedText);
    }

    private void init(String str){
        try {

            GSGenForm myDialog = new GSGenForm(new GSGenForm.DialogCallBack() {
                @Override
                public String ok(String intext1,int flag, boolean isGet, boolean pri) {

                    GSHelper getAndSet = new GSHelper();

                    String reMessage = getAndSet.getMessage(intext1, flag, isGet, pri);

                    return reMessage;
//                    Messages.showInfoMessage(project,Test1 + text2,filePath);
                }
            }, str);
            myDialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Messages.showInfoMessage(project, "Error", "出错." + e.getMessage());
        }

    }

    public static void main(String[] args) {
        GSGenAction moduleSetAction = new GSGenAction();
        moduleSetAction.init("");
    }
}
