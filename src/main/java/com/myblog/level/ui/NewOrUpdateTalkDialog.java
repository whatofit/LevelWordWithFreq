package com.myblog.level.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.myblog.model.Talk;
import com.myblog.model.TalkSentence;
import com.myblog.util.FastJsonUtil;

//Create/New/Insert
//Modify/Append/Update
public class NewOrUpdateTalkDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField aTextField;
    private JTextField bTextField;
    private JTextField cTextField;
    private JTextField dTextField;
    private JTextField eTextField;
    private DefaultTableModel talksTableModel; // 表格模型对象
    private JTable talksTable;
    private Talk mTalk;

    // 1.当talk不为空，查询数据库，并展示，
    // 2.当talk为空，不查询数据库
    // 3.当用户输入或修改talk并确认，(实时)重新查询数据库，并展示。
    public NewOrUpdateTalkDialog(String dialogTitle, Talk dbTalk) {
        setTitle(dialogTitle);
        setBounds(20, 60, 700, 400);
        this.mTalk = dbTalk;

        final JPanel panelNorth = new JPanel();
        getContentPane().add(panelNorth, BorderLayout.NORTH);

        panelNorth.add(new JLabel("English Title: "));
        aTextField = new JTextField("", 6);
        aTextField.setText(dbTalk.getEnglishTitle());
        panelNorth.add(aTextField);

        panelNorth.add(new JLabel("Chinese Title: "));
        bTextField = new JTextField("", 6);
        bTextField.setText(dbTalk.getChineseTitle());
        panelNorth.add(bTextField);

        panelNorth.add(new JLabel("category: "));
        cTextField = new JTextField("", 4);
        cTextField.setText(dbTalk.getCategory());
        panelNorth.add(cTextField);
        cTextField.addActionListener(actionListener);

        panelNorth.add(new JLabel("occasion: "));
        dTextField = new JTextField("", 4);
        dTextField.setText(dbTalk.getOccasion());
        panelNorth.add(dTextField);

        panelNorth.add(new JLabel("level: "));
        eTextField = new JTextField("", 4);
        eTextField.setText(dbTalk.getOccasion());
        panelNorth.add(eTextField);

        Vector<String> titleVector = new Vector<String>(); // headVector/column
        titleVector.addElement("person");
        titleVector.addElement("English");
        titleVector.addElement("Chinese");

        Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
        if (dbTalk.getTalkText() != null && dbTalk.getTalkText() != "") {
            List<TalkSentence> sentenceList = FastJsonUtil.json2list(
                    dbTalk.getTalkText(), TalkSentence.class);
            for (TalkSentence sentence : sentenceList) {
                Vector<Object> curRow = new Vector<Object>();
                curRow.addElement(sentence.getPerson());
                curRow.addElement(sentence.getEs());
                curRow.addElement(sentence.getCs());
                tableData.addElement(curRow); // rows.add(curRow);
            }
        }

        talksTableModel = new DefaultTableModel(tableData, titleVector) {
            public boolean isCellEditable(int row, int column) {
                if (column <= 5) {
                    //return false;
                }
                return true;// 默认是true
            }
        };
        talksTable = new JTable(talksTableModel);
        // 在触发其他操作之前，取消表格正在编辑的状态。(当其他控件获取焦点后，表格会停止编辑，但据说点击最小化时没有效果。)
        talksTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        talksTable.setFillsViewportHeight(true);
        talksTable.setRowHeight(30); // 设置行高

        JScrollPane scrollPane = new JScrollPane(talksTable); // 支持滚动
        scrollPane.setViewportView(talksTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        final JPanel panelSouth = new JPanel();
        getContentPane().add(panelSouth, BorderLayout.SOUTH);

        final JButton insertButton = new JButton("New talk");
        insertButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        talksTableModel.addRow(new Object[] { "", "", "" });
                    }
                });
        panelSouth.add(insertButton);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {//
            public void actionPerformed(ActionEvent e) {
                String aTxt = aTextField.getText();
                String bTxt = bTextField.getText();
                String cTxt = cTextField.getText();
                String dTxt = dTextField.getText();
                String eTxt = eTextField.getText();
                if (aTxt.trim().length() == 0 && bTxt.trim().length() == 0
                        && talksTableModel.getRowCount() == 0) {
                    // 提示无添加或无更新
                    return;
                } else {
                    Talk dbTalk = new Talk();
                    dbTalk.setId(mTalk.getId());
                    dbTalk.setEnglishTitle(aTxt);
                    dbTalk.setChineseTitle(bTxt);
                    dbTalk.setCategory(cTxt);
                    dbTalk.setOccasion(dTxt);
                    dbTalk.setLevel(eTxt);
                    List<TalkSentence> sList = new ArrayList<>();
                    for (int row = 0; row < talksTableModel.getRowCount(); row++) {
                        String person = (String) talksTableModel.getValueAt(
                                row, 0);
                        String english = (String) talksTableModel.getValueAt(
                                row, 1);
                        String chinese = (String) talksTableModel.getValueAt(
                                row, 2);
                        TalkSentence sentence = new TalkSentence(person,
                                english, chinese);
                        sList.add(sentence);
                    }
                    String talkText = FastJsonUtil.obj2json(sList);
                    dbTalk.setTalkText(talkText);
                    try {
                        CreateOrUpdateStatus cuStatus = LevelTalk.talkDao
                                .createOrUpdate(dbTalk);
                        System.out.println("cuStatus:" + cuStatus.isCreated()
                                + "" + cuStatus.isUpdated());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        panelSouth.add(saveBtn);

        setVisible(true);
    }

    // 设置列宽，当列宽为0时，可以隐藏列
    public void hiddenCell(JTable table, int column, int width) {
        TableColumn tc = table.getTableHeader().getColumnModel()
                .getColumn(column);
        tc.setMaxWidth(width);
        tc.setPreferredWidth(width);
        tc.setWidth(width);
        tc.setMinWidth(width);
        table.getTableHeader().getColumnModel().getColumn(column)
                .setMaxWidth(width);
        table.getTableHeader().getColumnModel().getColumn(column)
                .setMinWidth(width);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == aTextField)
                try {
                } catch (Exception ee) {
                    System.out.println(ee);
                }
            if (e.getSource() == bTextField) {
                try {
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(NewOrUpdateTalkDialog.this,
                            "更新失败" + ee, "失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };
}