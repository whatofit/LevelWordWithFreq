package com.myblog.level.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.myblog.model.Word;

//Create/New/Insert
//Modify/Append/Update
public class NewOrUpdateWordDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField aTextField;
    private JTextField bTextField;
    private JTextField cTextField;
    private DefaultTableModel wordsTableModel; // 表格模型对象
    private JTable wordsTable;
    String num;

    // 1.当word不为空，查询数据库，并展示，
    // 2.当word为空，不查询数据库
    // 3.当用户输入或修改word并确认，(实时)重新查询数据库，并展示。
    public NewOrUpdateWordDialog(String dialogTitle, String wordSpelling) {
        setTitle(dialogTitle);
        setBounds(20, 60, 700, 400);

        final JPanel panelNorth = new JPanel();
        getContentPane().add(panelNorth, BorderLayout.NORTH);

        panelNorth.add(new JLabel("Frequency: "));
        aTextField = new JTextField("", 6);
        panelNorth.add(aTextField);

        panelNorth.add(new JLabel("Spelling: "));
        bTextField = new JTextField("", 20);
        bTextField.setText(wordSpelling);
        panelNorth.add(bTextField);

        panelNorth.add(new JLabel("Level: "));
        cTextField = new JTextField("", 4);
        panelNorth.add(cTextField);
        cTextField.addActionListener(actionListener);

        Vector<String> titleVector = new Vector<String>(); // headVector/column
        titleVector.addElement("id");
        titleVector.addElement(Word.FIELD_NAME_FREQUENCY);
        titleVector.addElement(Word.FIELD_NAME_SPELLING);
        titleVector.addElement(Word.FIELD_NAME_DJ);
        titleVector.addElement(Word.FIELD_NAME_KK);
        titleVector.addElement(Word.FIELD_NAME_LEVEL);
        titleVector.addElement(Word.FIELD_NAME_POS);
        titleVector.addElement(Word.FIELD_NAME_MEANINGS);
        titleVector.addElement(Word.FIELD_NAME_SENTENCES);
        Vector<Vector<Object>> tableData = LevelWord.wordDao
                .findBySpelling(wordSpelling);
        if (tableData.size() > 0) {
            aTextField.setText((String) tableData.get(0).get(1));
            cTextField.setText((String) tableData.get(0).get(5));
        }
        wordsTableModel = new DefaultTableModel(tableData, titleVector) {
            public boolean isCellEditable(int row, int column) {
                if (column <= 5) {
                    return false;
                }
                return true;// 默认是true
            }
        };
        wordsTable = new JTable(wordsTableModel);
        // 在触发其他操作之前，取消表格正在编辑的状态。(当其他控件获取焦点后，表格会停止编辑，但据说点击最小化时没有效果。)
        wordsTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        wordsTable.setFillsViewportHeight(true);
        wordsTable.setRowHeight(30); // 设置行高

        int[] tableColumnWidths = new int[] { 0, 0, 0, 0, 0, 0, 100, 300 };
        for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
            int columnWidth = tableColumnWidths[columnIndex];
            hiddenCell(wordsTable, columnIndex, columnWidth);
        }

        // JComboBox comboBox = new JComboBox();
        // comboBox.addItem("分可数名词countable noun(c.)");
        // comboBox.addItem("不可数名词uncountable noun(u.)");
        // comboBox.addItem("及物动词verb transitive(vt)");
        // comboBox.addItem("不及物动词verb intransitive(vi)");
        // comboBox.addItem("助动词auxiliary verb(aux.v)");
        // comboBox.addItem("情态动词modal verb");
        // comboBox.addItem("短语动词phr v(phrasal verb)");
        // comboBox.addItem("形容词 Adjectives(a./adj.)");
        // comboBox.addItem("副词 Adverbs(ad./adv.)");
        // comboBox.addItem("数词 Numeral(num.)");
        // comboBox.addItem("感叹词 Interjection(interj.) ");
        // comboBox.addItem("代（名）词 Pronouns(pron.)");
        // comboBox.addItem("介词 Prepositions(prep.)");
        // comboBox.addItem("冠词 Article(art.)");
        // comboBox.addItem("连词 Conjunction（conj.）");
        // comboBox.addItem("疑问词 Interrogative (int.)");
        // comboBox.addItem("量词 Quantifier(quant.)");
        // comboBox.addItem("复数plural(pl.)");

        String[] items = new String[] { "n.", "v.", "vt.", "vi.", "adj.",
                "adv.", "num.", "int.", "pron.", "prep.", "art.", "conj.",
                "abbr.", "aux.", "det.", "link-v." };
        // abbr.(abbreviation) 缩写;
        // aux.(auxiliary) 助动词
        // det.(deterninate) 限定词
        // link-v. (Link Verb) 系动词&连系动词

        JComboBox<String> comboBox = new JComboBox<String>(items);
        // Dimension d = comboBox.getPreferredSize();
        // comboBox.setPopupWidth(d.width);

        // 利用TableColumn类中的setCellEditor()方法来设置单元格的编辑器
        // DefaultCellEditor类可以将表格中的单元格设置成下拉框
        // tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
        // TableColumnModel tcm = wordsTable.getColumnModel();
        // TableColumn tableColumn = tcm.getColumn(0);
        TableColumn tableColumn = wordsTable.getColumn("partsOfSpeech");
        tableColumn.setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(wordsTable); // 支持滚动
        scrollPane.setViewportView(wordsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        final JPanel panelSouth = new JPanel();
        getContentPane().add(panelSouth, BorderLayout.SOUTH);

        final JButton insertButton = new JButton("New Word");
        insertButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        wordsTableModel.addRow(new Object[] { "", "", "" });
                    }
                });
        panelSouth.add(insertButton);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 在需要取消表格编辑状态的按钮等控件的事件响应中根据具体情况来取消表格的编辑状态。
                // if (wordsTable.isEditing()) {
                // wordsTable.getCellEditor().stopCellEditing();
                // }
                String aTxtFreq = aTextField.getText();
                String bTxtSpelling = bTextField.getText();
                String cTxtLevel = cTextField.getText();
                if (wordsTableModel.getRowCount() == 0 ) {
                    if (bTxtSpelling.trim().length() == 0) {
                        //提示无添加或无更新
                        return;
                    }else if (bTxtSpelling.trim().length() > 0) {
                        Word dbWord = new Word();
                        dbWord.setFrequency(Integer.parseInt(aTxtFreq));
                        dbWord.setSpelling(bTxtSpelling);
                        dbWord.setLevel(cTxtLevel);
                        try {
                            // LevelWord.wordDao.update(dbWord);
                            CreateOrUpdateStatus cuStatus = LevelWord.wordDao
                                    .createOrUpdate(dbWord);
                            System.out.println("cuStatus:" + cuStatus.isCreated()
                                    + "" + cuStatus.isUpdated());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }    
                    }
                    return;
                }
                // 只取第一行数据
                String freq = (String) wordsTableModel.getValueAt(0, 1);
                String spelling = (String) wordsTableModel.getValueAt(0, 2);
                String DJ = (String) wordsTableModel.getValueAt(0, 3);
                String KK = (String) wordsTableModel.getValueAt(0, 4);
                String level = (String) wordsTableModel.getValueAt(0, 5);

                if (spelling == null || spelling.trim().length() == 0) {
                    spelling = bTxtSpelling;
                }
                if (freq == null || freq.trim().length() == 0) {
                    freq = aTxtFreq;
                }

                if (level == null || level.trim().length() == 0) {
                    level = cTxtLevel;
                }

                for (int row = 0; row < wordsTableModel.getRowCount(); row++) {
                    Word dbWord = new Word();
                    Object column1 = wordsTableModel.getValueAt(row, 0);
                    if (column1 != null) {
                        if (column1 instanceof Integer) {
                            dbWord.setId((Integer) column1);
                        } else if (column1 instanceof String
                                && !column1.equals("")) {
                            dbWord.setId(Integer.parseInt((String) column1));
                        }
                    }

                    // 取第二行以后的数据
                    String pos = (String) wordsTableModel.getValueAt(row, 6);
                    String meanings = (String) wordsTableModel.getValueAt(row,
                            7);
                    String sentences = (String) wordsTableModel.getValueAt(row,
                            8);
                    if (pos != null && pos.trim().length() > 0
                            || meanings != null && meanings.trim().length() > 0
                            || sentences != null
                            && sentences.trim().length() > 0) {
                        dbWord.setFrequency(Integer.parseInt(freq));
                        dbWord.setSpelling(spelling);
                        dbWord.setPhoneticDJ(DJ);
                        dbWord.setPhoneticKK(KK);
                        dbWord.setLevel(level);
                        dbWord.setPartsOfSpeech(pos);
                        dbWord.setMeanings(meanings);
                        dbWord.setSentences(sentences);
                        try {
                            // LevelWord.wordDao.update(dbWord);
                            CreateOrUpdateStatus cuStatus = LevelWord.wordDao
                                    .createOrUpdate(dbWord);
                            System.out.println("cuStatus:"
                                    + cuStatus.isCreated() + ""
                                    + cuStatus.isUpdated());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
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
                    num = aTextField.getText().trim();
                    // con=DriverManager.getConnection("jdbc:odbc:hello","","");
                    // con = DriverManager
                    // .getConnection("jdbc:sqlite:Students.db3");
                    // sql = con.createStatement();
                    // rs =
                    // sql.executeQuery("SELECT * FROM message WHERE number='"
                    // + num + "'");
                    // boolean boo = rs.next();
                    // if (boo == false) {
                    // JOptionPane.showMessageDialog(NewOrUpdateDialog.this,
                    // "学号不存在", "提示", JOptionPane.WARNING_MESSAGE);
                    // } else {
                    // }
                    // con.close();
                } catch (Exception ee) {
                    System.out.println(ee);
                }
            if (e.getSource() == bTextField) {
                try {
                    // con=DriverManager.getConnection("jdbc:odbc:hello","","");
                    // con = DriverManager
                    // .getConnection("jdbc:sqlite:Students.db3");
                    // sql = con.createStatement();
                    // sql.executeUpdate("UPDATE message SET name='"
                    // + tableData[0][0] + "',birthday='"
                    // + tableData[0][1] + "',height='" + tableData[0][2]
                    // + "'WHERE number='" + num + "'");
                    // JOptionPane.showMessageDialog(NewOrUpdateDialog.this,
                    // "更新成功",
                    // "成功", JOptionPane.PLAIN_MESSAGE);
                    // con.close();
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(NewOrUpdateWordDialog.this,
                            "更新失败" + ee, "失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };
}