package com.myblog.mergetablecells;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/*
 * (swing1.1beta3)
 */

/**
 * @version 1.0 11/26/98
 */

public class MultiSpanCellTable extends JTable {
    private static final long serialVersionUID = 8279957701540683008L;

    public MultiSpanCellTable(TableModel model) {
        super(model);
        setUI(new MultiSpanCellTableUI());
        getTableHeader().setReorderingAllowed(false);
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);// 允许选择相邻的一系列行。
    }

    // 返回位于 row 和 column 相交位置的单元格矩形。
    // 如果 includeSpacing 为 true，则返回的值具有指定行和列的完整高度和宽度。
    // 如果为 false，则返回的矩形为单元格空间减去单元格间的间隙，以便在呈现期间设置该属性后，返回呈现和编辑的组件的真实边界。
    // 如果列索引有效但是行索引小于 0，则此方法返回一个矩形，此矩形的 y 和 height 设置为合适的值，其x 和width 值都设置为
    // 0。通常，行索引或列索引指示适当区域外的单元格时，此方法都返回一个矩形，它描绘了表范围内最近单元格的最近边。当行索引和列索引都超出范围时，返回的矩形覆盖了最近单元格的最近点。
    // 在所有的情形中，使用此方法沿一个轴的计算结果不会因为沿另一个轴的计算出现异常而失败。当单元格无效时，忽略 includeSpacing 参数。
    // 参数：
    // row - 所需单元格所在的行索引
    // column - 所需单元格所在的列索引；不一定与表中数据模型的列索引相同；convertColumnIndexToView(int)
    // 方法可以用来将数据模型的列索引转换为显示的列索引
    // includeSpacing - 如果为 false，则返回实际的单元格边界，计算方法是从列模型和行模型的高度和宽度中减去单元格间距
    // 返回：
    // 包含 row、column 处单元格的矩形
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        Rectangle sRect = super.getCellRect(row, column, includeSpacing);
        if ((row < 0) || (column < 0) || (getRowCount() <= row)
                || (getColumnCount() <= column)) {
            return sRect;
        }
        ICellSpan cellAtt = (ICellSpan) ((AttributiveCellTableModel) getModel())
                .getCellAttribute();
        if (!cellAtt.isVisible(row, column)) {
            int temp_row = row;
            int temp_column = column;
            row += cellAtt.getSpan(temp_row, temp_column)[ICellSpan.ROW];
            column += cellAtt.getSpan(temp_row, temp_column)[ICellSpan.COLUMN];
        }
        int[] n = cellAtt.getSpan(row, column);

        int index = 0;
        int columnMargin = getColumnModel().getColumnMargin();
        Rectangle cellFrame = new Rectangle();
        int aCellHeight = rowHeight + rowMargin;
        cellFrame.y = row * aCellHeight;
        cellFrame.height = n[ICellSpan.ROW] * aCellHeight;

        Enumeration<TableColumn> enumeration = getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            cellFrame.width = aColumn.getWidth() + columnMargin;
            if (index == column)
                break;
            cellFrame.x += cellFrame.width;
            index++;
        }
        for (int i = 0; i < n[ICellSpan.COLUMN] - 1; i++) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            cellFrame.width += aColumn.getWidth() + columnMargin;
        }

        if (!includeSpacing) {
            Dimension spacing = getIntercellSpacing();
            cellFrame.setBounds(cellFrame.x + spacing.width / 2, cellFrame.y
                    + spacing.height / 2, cellFrame.width - spacing.width,
                    cellFrame.height - spacing.height);
        }
        return cellFrame;
    }

    private int[] rowColumnAtPoint(Point point) {
        int[] retValue = { -1, -1 };
        int row = point.y / (rowHeight + rowMargin);
        if ((row < 0) || (getRowCount() <= row))
            return retValue;
        int column = getColumnModel().getColumnIndexAtX(point.x);

        ICellSpan cellAtt = (ICellSpan) ((AttributiveCellTableModel) getModel())
                .getCellAttribute();

        if (cellAtt.isVisible(row, column)) {
            retValue[ICellSpan.COLUMN] = column;
            retValue[ICellSpan.ROW] = row;
            return retValue;
        }
        retValue[ICellSpan.COLUMN] = column
                + cellAtt.getSpan(row, column)[ICellSpan.COLUMN];
        retValue[ICellSpan.ROW] = row
                + cellAtt.getSpan(row, column)[ICellSpan.ROW];
        return retValue;
    }

    public int rowAtPoint(Point point) {
        return rowColumnAtPoint(point)[ICellSpan.ROW];
    }

    // public int columnAtPoint(Point point) {
    // return rowColumnAtPoint(point)[CellSpan.COLUMN];
    // }

    // public void columnSelectionChanged(ListSelectionEvent e) {
    // repaint();
    // }

    public void valueChanged(ListSelectionEvent e) {
        int firstIndex = e.getFirstIndex();
        int lastIndex = e.getLastIndex();
        if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
            repaint();
        }
        Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
        int numCoumns = getColumnCount();
        int index = firstIndex;
        for (int i = 0; i < numCoumns; i++) {
            dirtyRegion.add(getCellRect(index, i, false));
        }
        index = lastIndex;
        for (int i = 0; i < numCoumns; i++) {
            dirtyRegion.add(getCellRect(index, i, false));
        }
        repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width,
                dirtyRegion.height);
    }

}