package com.level.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.level.model.Word;


//import com.genericsdao.bean.Word;
//import com.genericsdao.daoimp.WordDaoImpl;

public class WordTableCellEditor extends JPanel implements TableCellEditor {
    private static final long serialVersionUID = 5860619160549087886L;
    // EventListenerList:保存EventListener 列表的类。
    private EventListenerList listenerList = new EventListenerList();
    // ChangeEvent用于通知感兴趣的参与者事件源中的状态已发生更改。
    private ChangeEvent changeEvent = new ChangeEvent(this);

    JButton edit_btn;
    JButton del_btn;
    JTextField edit_txf;

    // JTableTest jTableTest;

    public WordTableCellEditor() {
        super();
        setLayout(new BorderLayout());

        edit_btn = new JButton("edit");
        del_btn = new JButton("del");
        edit_txf = new JTextField();

        add(edit_btn, BorderLayout.WEST);
        add(del_btn, BorderLayout.EAST);
        add(edit_txf);

        edit_btn.setBackground(Color.white);
        del_btn.setBackground(Color.white);

        edit_btn.setPreferredSize(new Dimension(60, getHeight()));
        del_btn.setPreferredSize(new Dimension(60, getHeight()));

        edit_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Point p = edit_btn.getLocation();
                // new JTableTestDialog(100, 180, jTableTest).setVisible(true);
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(
                        JTable.class, (Component) e.getSource());
                int row = table.getEditingRow();
                int column = table.getEditingColumn();
                System.out.println("edit_btn,row,column=" + row + "," + column);
                DefaultTableModel tableModel = (DefaultTableModel) table
                        .getModel();
                Word word = new Word();
                switch (column) {
                case 0:
                    int id = (int) tableModel.getValueAt(row, column);
                    word.setId(id);
                    break;
                case 1:
                	String frequency = (String) tableModel.getValueAt(row,
                            column);
                    word.setFrequency(frequency);
                    break;
                case 2:
                    String spelling = (String) tableModel.getValueAt(row,
                            column);
                    word.setSpelling(spelling);
                    break;
                case 3:
                    String phoneticDJ = (String) tableModel.getValueAt(row,
                            column);
                    word.setPhoneticDJ(phoneticDJ);
                    break;
                case 4:
                    String phoneticKK = (String) tableModel.getValueAt(row,
                            column);
                    word.setPhoneticKK(phoneticKK);
                case 5:
                    String level = (String) tableModel.getValueAt(row, column);
                    word.setLevel(level);
                case 6:
                    String partsOfSpeech = (String) tableModel.getValueAt(row,
                            column);
                    word.setPartsOfSpeech(partsOfSpeech);
                case 7:
                    String meanings = (String) tableModel.getValueAt(row,
                            column);
                    word.setMeanings(meanings);
                case 8:
                    String sentences = (String) tableModel.getValueAt(row,
                            column);
                    word.setSentences(sentences);
                default:
                    break;
                }
                System.out.println("del_btn,id=" + word);

                // table.getCellEditor().stopCellEditing();
                // ((DefaultTableModel) table.getModel()).removeRow(row);
                if (e.getSource() == edit_btn) {
                    try {
                        LevelWord.wordDao.update(word);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        del_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(
                        JTable.class, (Component) e.getSource());
                int row = table.getEditingRow();
                int column = table.getEditingColumn();
                // table.getCellEditor().stopCellEditing();
                // ((DefaultTableModel) table.getModel()).removeRow(row);
                if (e.getSource() == del_btn) {
                    System.out.println("del_btn,row,column=" + row + ","
                            + column);
                    DefaultTableModel tableModel = (DefaultTableModel) table
                            .getModel();
                    int id = (int) tableModel.getValueAt(row, 0);
                    System.out.println("del_btn,id=" + id);
                    try {
                        LevelWord.wordDao.deleteById(String.valueOf(id));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    tableModel.removeRow(row); // 删除行
                }
            }
        });
    }

    // JTableTestCellEdit(JTableTest jTableTest) {
    // this();
    // this.jTableTest = jTableTest;
    // }

    public void addCellEditorListener(CellEditorListener l) {
        // System.out.println("addCellEditorListener=");
        listenerList.add(CellEditorListener.class, l);
    }

    public void removeCellEditorListener(CellEditorListener l) {
        // System.out.println("removeCellEditorListener=");
        listenerList.remove(CellEditorListener.class, l);
    }

    private void fireEditingStopped() {
        // System.out.println("fireEditingStopped=");
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                // 之所以是i+1，是因为一个为CellEditorListener.class（Class对象），
                // 接着的是一个CellEditorListener的实例
                listener = (CellEditorListener) listeners[i + 1];
                // 让changeEvent去通知编辑器已经结束编辑
                // //在editingStopped方法中，JTable调用getCellEditorValue()取回单元格的值，
                // 并且把这个值传递给TableValues(TableModel)的setValueAt()
                listener.editingStopped(changeEvent);
            }
        }
    }

    public void cancelCellEditing() {
        // System.out.println("cancelCellEditing=");
    }

    public boolean stopCellEditing() {
        // 可以注释掉下面的fireEditingStopped();，然后在GenderEditor的构造函数中把
        // addActionListener()的注释去掉（这时请求终止编辑操作从JComboBox获得），
        System.out.println("stopCellEditing=编辑其中一个单元格，再点击另一个单元格时，调用。");
        fireEditingStopped();// 请求终止编辑操作从JTable获得
        return true;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        // System.out.println("getTableCellEditorComponent=");
        if (value != null)
            edit_txf.setText(value.toString());
        return this;
    }

    public boolean isCellEditable(EventObject anEvent) {
        // System.out.println("isCellEditable=");
        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        // System.out.println("shouldSelectCell=");
        return true;
    }

    public Object getCellEditorValue() {
        // System.out.println("getCellEditorValue=");
        return edit_txf.getText();
    }

}