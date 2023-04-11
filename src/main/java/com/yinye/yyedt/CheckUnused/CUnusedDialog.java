package com.yinye.yyedt.CheckUnused;


import com.yinye.yyedt.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class CUnusedDialog extends JDialog {
    private JPanel contentPane;

    private JFormattedTextField destDir;
    private JButton selectDestBtn;
    private JFormattedTextField oriDir;
    private JButton selectOriBtn;
    private JButton findDepsBtn;
    private JButton cancelBtn;

    private JButton copyBtn;
    private JButton filterBtn;

    private JButton loadFilterBtn;
    private JButton saveFilterBtn;
    private JTextArea filterText;
    private JTextArea includeText;

    private JTextArea resultText;

    private DialogCallBack mCallBack;
    private CUnusedResult result;

    public CUnusedDialog(DialogCallBack mCallBack, File selectDir) {
        this.mCallBack = mCallBack;
        setSize(800,400); // 设置大小
        setContentPane(contentPane);
        setLocationRelativeTo(null); // 居中

        String destPath = selectDir.getAbsolutePath();
        oriDir.setValue(destPath);
        destDir.setValue(destPath);

        setModal(true);
        getRootPane().setDefaultButton(findDepsBtn);

        selectDestBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectDestBtn(selectDir);
            }
        });
        selectOriBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectOriBtn(selectDir);
            }
        });
        findDepsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCheckDeps();
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFilter();
            }
        });
        loadFilterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFilterText();
            }
        });
        saveFilterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFilterText();
            }
        });
        copyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCopy();
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCheckDeps() {
        if (null != mCallBack){
            CUnusedDialogData dialogData = new CUnusedDialogData(this.oriDir.getText(),this.destDir.getText());
            this.result = this.mCallBack.checkDeps(dialogData);
            this.showResult();

        }
    }

    private void showResult(){
        if(this.result != null){
            List<String> filterList = getTextList(this.filterText);
            List<String> includeList = getTextList(this.includeText);
            this.resultText.setText(result.toText(filterList,includeList));
        }
    }

    @NotNull
    private List<String> getTextList(JTextArea filterText) {
        String filterTextTmp = filterText.getText();
        String[] splits = filterTextTmp.split("\\r?\\n");
        List<String> filterList = new ArrayList<>();
        for (String splitTmp : splits) {
            if(!StringUtils.isEmpty(splitTmp)){
                filterList.add(splitTmp.trim());
            }
        }
        return filterList;
    }

    private void onCancel() {
        dispose();
    }
    private void onFilter() {
        showResult();
    }
    private void loadFilterText() {
        List<String> list = CUnusedMgr.getInstance().loadFilter();
        if(list != null){
            this.filterText.setText(list.get(0));
            this.includeText.setText(list.get(1));
        }
    }

    private void saveFilterText(){
        try {
            CUnusedMgr.getInstance().saveFilter(this.filterText.getText(),this.includeText.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void onCopy() {
        String text = this.resultText.getText();
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void onSelectOriBtn(File curFile) {
        final JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(curFile);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue=chooser.showOpenDialog(CUnusedDialog.this);
        if (returnValue==JFileChooser.APPROVE_OPTION) {
            File file=chooser.getSelectedFile();
            oriDir.setText(file.getAbsolutePath());
        }
    }
    private void onSelectDestBtn(File curFile) {
        final JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(curFile);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue=chooser.showOpenDialog(CUnusedDialog.this);
        if (returnValue==JFileChooser.APPROVE_OPTION) {
            File file=chooser.getSelectedFile();
            destDir.setText(file.getAbsolutePath());
        }
    }

    public interface DialogCallBack{
        CUnusedResult checkDeps(CUnusedDialogData dialogData);
    }

}
