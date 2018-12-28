package com.level.mergetablecells;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/*   ----------------------------------------------
 *  |         SNo.        |
 *   ----------------------------------------------
 *  |          |     1    |
 *  |   Name   |-----------------------------------
 *  |          |     2    |
 *   ----------------------------------------------
 *  |          |     1    |
 *  |          |-----------------------------------
 *  | Language |     2    |
 *  |          |-----------------------------------
 *  |          |     3    |
 *   ----------------------------------------------
 */

/**
 * @version 1.0 03/06/99
 */
public class MultipleRowHeaderExample extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1312081284532959920L;

    Object[][] data;
    Object[] column;
    // JTable table;
    MultiSpanCellTable fixedTable;

    public MultipleRowHeaderExample() {
        super("Multiple Row Header Example");
        setSize(400, 150);

        data =  new Object[][]{
                {"SNo."    ,"" },
                {"Name"    ,"1"},
                {""        ,"2"},
                {"Language","1"},
                {""        ,"2"},
                {""        ,"3"}};
        column = new Object[] { "a column", "b column" };

        AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(
                data, column) {
            private static final long serialVersionUID = 2988121793616670164L;

            public boolean CellEditable(int row, int col) {
                return false;
            }
        };

        ICellSpan cellAtt = (ICellSpan) fixedModel.getCellAttribute();
        cellAtt.combine(new int[] { 0 }, new int[] { 0, 1 });
        cellAtt.combine(new int[] { 1, 2 }, new int[] { 0 });
        cellAtt.combine(new int[] { 3, 4, 5 }, new int[] { 0 });

        // DefaultTableModel model = new DefaultTableModel(data.length, 3);

        fixedTable = new MultiSpanCellTable(fixedModel);
        // table = new JTable( model );
        // fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//         fixedTable.setDefaultRenderer(Object.class, new RowHeaderRenderer(fixedTable));
//         fixedTable.setGridColor(fixedTable.getTableHeader().getBackground());

        // JScrollPane scroll = new JScrollPane( table );
        JScrollPane scroll = new JScrollPane(fixedTable);
        JViewport viewport = new JViewport();
        viewport.setView(fixedTable);
        viewport.setPreferredSize(fixedTable.getPreferredSize());
        scroll.setRowHeaderView(viewport);

        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MultipleRowHeaderExample frame = new MultipleRowHeaderExample();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }

//    class RowHeaderRenderer extends JLabel implements TableCellRenderer {
//        private static final long serialVersionUID = 7367929138900145414L;
//
//        RowHeaderRenderer(JTable table) {
//            JTableHeader header = table.getTableHeader();
//            setOpaque(true);
//            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
//            setHorizontalAlignment(CENTER);
//            setForeground(header.getForeground());
//            setBackground(header.getBackground());
//            setFont(header.getFont());
//        }
//
//        public Component getTableCellRendererComponent(JTable table,
//                Object value, boolean isSelected, boolean hasFocus, int row,
//                int column) {
//            setText((value == null) ? "" : value.toString());
//            return this;
//        }
//    }
}