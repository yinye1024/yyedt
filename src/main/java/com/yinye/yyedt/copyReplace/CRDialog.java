package com.yinye.yyedt.copyReplace;

import com.yinye.yyedt.utils.FileUtils;

import javax.swing.*;
import java.awt.event.*;

public class CRDialog extends JDialog {

    private JPanel contentPanel;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JLabel textReplaceLabel;
    private JTextArea textReplace;

    private JLabel fnReplaceLabel;
    private JTextArea fnReplace;

    private JLabel dnReplaceLabel;
    private JTextArea dnReplace;

    private DialogCallBack mCallBack;

    public CRDialog(DialogCallBack  callBack, String filePath) {
        String fileName = FileUtils.getFileName(filePath);
        String initValue = fileName+" - toText\n";

        this.mCallBack = callBack;
        setTitle("模块代码生成:根据已有代码结构生成相同结构的新模块代码。");
        setSize(450,500); // 设置大小

        //目录名替换
        dnReplaceLabel.setText("目录名替换，格式: 替换源-替换目标。可多行，不能相互包含。");
        dnReplace.setText(fileName+" - newDirName\n");
        //文件名替换
        fnReplaceLabel.setText("文件名替换，格式: 替换源-替换目标。可多行，不能相互包含。");
        fnReplace.setText(fileName+" - newFileName\n");
        //文件替换
        textReplaceLabel.setText("文件内容替换，格式: 替换源-替换目标。可多行，不能相互包含。");
        textReplace.setText(FileUtils.upFirstChar(fileName)+" - newText\n");

        setLocationRelativeTo(null); // 居中
        setContentPane(contentPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (null != mCallBack){
            CRDialogData dialogData = new CRDialogData(textReplace.getText().trim(),
                    fnReplace.getText().trim(),dnReplace.getText().trim());
            mCallBack.onOkClick(dialogData);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        ReplaceForm dialog = new ReplaceForm();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    public interface DialogCallBack{
        void onOkClick(CRDialogData dialogData);
    }

}
