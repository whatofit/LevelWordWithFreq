package com.myblog.mergetablecells;

//http://www.apihome.cn/api/java/JTable.html

//重写TableUI，来达到实现表格的合并和分解。

//真正实现表格多行多列合并的是在MultiSpanCellTableUI这个类里实现，这个类完全重新绘制表格从而达到合并的目的。
//代码很精练，不到100行，就搞定了表格的绘制。
//
//MultiSpanCellTable 继承于JTable，主要是重载了和表格定位相关的方法，
//比如rowColumnAtPoint，getCellRect，rowAtPoint这几个方法，
//根据Model里的数据来重新计算，道理也很简单,这样才能正确的判断用户选择的单元格，
//因为被合并的单元格是无法选择的和无法显示的，所以必须要在这里进行。代码还是百来行。
//
//AttributiveCellTableModel继续于DefaultTableModel，
//最关键的东西在这里，CellAttribute，他的实现是交给DefaultCellAttribute这个对象的，这个是存储单元格合并信息的地方，
//而且也是MultiSpanCellTable在计算的重要数据，和MultiSpanCellTableUI绘制的核心数据之一。
//DefaultCellAttribute有三个接口分别对应表格的功能。
//
//CellAttribute保存的是真正的视野上的表格信息，包括表格有多少行多少列（这是视觉上的不是逻辑上），
//JTable逻辑数据任然是在传统的model，逻辑数据每一次改变都将会映射到这里，会刷新视觉数据。
//
//CellSpan 保存的是单元格的合并属性，可视行，行跨度，列跨度。这些是UI绘制必需的东西。
//还有就是合并和拆分的功能也在这里，非常值得注意的是，在拆分合并表格的时候，他没有处理任何TableModel和Table的东西，就是在这里处理数据，
//处理完毕JTable重刷，这种M和V隔离的是相当的清晰。

//单元格的合并和拆分.
//http://www.blogjava.net/zeyuphoenix/archive/2010/04/12/318097.html
//JTable的单元格可编辑时可以把它看做一个JTextField,不可操作时可以看做一个JLabel,对于单元格的合并和拆分操作来说就是把JLabel或JTextField进行合并和拆分的过程.
//JTable单元格的合并简单来说就是把你选定的要合并的单元格的边线擦掉,然后调整宽度和高度,再在这几个合并的单元格外围画一个新的边线,然后设置JTable的UI,刷新就可以了.

//Example from http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
/* (swing1.1) (swing#1356,#1454) */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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
 * @version 1.0 11/26/98
 */
public class MultiSpanCellTableExample extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -2140084363480237258L;

    MultiSpanCellTableExample() {
        super("Multi-Span Cell Example");
        Object[][] data =  new Object[][]{
                {"SNo."    ,""  ,"" ,"" ,"" ,""},
                {"Name"    ,"1" ,"" ,"" ,"" ,""},
                {""        ,"2" ,"" ,"" ,"" ,""},
                {"Language","1" ,"" ,"" ,"" ,""},
                {""        ,"2" ,"" ,"" ,"" ,""},
                {""        ,"3" ,"" ,"" ,"" ,""},
                {""        ,"4" ,"" ,"" ,"" ,""},
                {""        ,"5" ,"" ,"" ,"" ,""},
                {""        ,"6" ,"" ,"" ,"" ,""},
                {""        ,"7" ,"" ,"" ,"" ,""},
                {""        ,"8" ,"" ,"" ,"" ,""}};
        Object[] column = new Object[] { "A col" ,"B col" ,"C col" ,"D col" ,"E col" ,"F col"};

        final AttributiveCellTableModel tableModel = new AttributiveCellTableModel(
                data, column);// 10行,6列
        /*
         * AttributiveCellTableModel ml = new AttributiveCellTableModel(10,6) {
         * public Object getValueAt(int row, int col) { return "" + row + ","+
         * col; } };
         */
        final MultiSpanCellTable table = new MultiSpanCellTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        final ICellSpan cellAtt = (ICellSpan) tableModel.getCellAttribute();
        cellAtt.combine(new int[] { 0 }, new int[] { 0, 1 });
        cellAtt.combine(new int[] { 1, 2 }, new int[] { 0 });
        cellAtt.combine(new int[] { 3, 4, 5 }, new int[] { 0 });
        
        JButton b_one = new JButton("Combine");
        b_one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] columns = table.getSelectedColumns();
                int[] rows = table.getSelectedRows();
                System.out.println("getSelectedColumns="
                        + Arrays.toString(columns));
                System.out.println("getSelectedRows=" + Arrays.toString(rows));
                cellAtt.combine(rows, columns);
                //tableModel.changeAllCellAttribute();
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
        JButton b_split = new JButton("Split");
        b_split.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int column = table.getSelectedColumn();
                int row = table.getSelectedRow();
                cellAtt.split(row, column);
                table.clearSelection();
                // revalidate()是把布局管理器对应的容器内的子组件重新计算大小,布局并绘制。
                // revalidate()是Jcompnent的方法。它并不是立即改变组件的大小，而是标记该组件需要改变大小。
                // 这样就可以避免了多个组件都要改变大小时带来的重复计算。
                // 但是，如果想重新计算一个Jframe中的所有组件，就需要调用repaint()方法
                // validate方法是告诉父容器，“我更新了，你要重绘！”。
                // 容器自身的重绘，轻量级的方法一般调用repain()。
                table.revalidate();
                table.repaint();
            }
        });
        JPanel p_buttons = new JPanel();
        p_buttons.setLayout(new GridLayout(2, 1));
        p_buttons.add(b_one);
        p_buttons.add(b_split);

        Box box = new Box(BoxLayout.X_AXIS);
        box.add(scroll);
        box.add(new JSeparator(SwingConstants.HORIZONTAL));
        box.add(p_buttons);
        getContentPane().add(box);
        setSize(600, 300);
    }

    public static void main(String[] args) {
        MultiSpanCellTableExample frame = new MultiSpanCellTableExample();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
