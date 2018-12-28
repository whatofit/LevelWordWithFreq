package com.level.mergetablecells;

import java.awt.Dimension;
import java.util.Vector;

/**
 * @version 1.0 11/22/98
 */
// 默认单元格属性，继承与CellAttribute与CellSpan
public class DefaultCellAttribute implements ICellAttribute, ICellSpan {
    // implements ICellAttribute, ICellSpan, IColoredCell, ICellFont {

    //
    // !!!! CAUTION !!!!!
    // these values must be synchronized to Table data
    //
    protected int rowSize; // 表格总行数

    protected int columnSize; // 表格总列数

    // 某个单元格的列与行的跨度值,1代表正常的单元格,行正整数代表合并了多少个行单元格，行负数代表被合并(即：小于1的单元格，都是不可见单元格),无0跨度
    protected int[][][] span; // CellSpan

    // protected Color[][] foreground; // ColoredCell
    //
    // protected Color[][] background; //
    //
    // protected Font[][] font; // CellFont

    // public DefaultCellAttribute() {
    // this(1, 1);
    // }

    public DefaultCellAttribute(int numRows, int numColumns) {
        setSize(new Dimension(numColumns, numRows));
        initValue();
    }

    // 设置单元格行列方向上，跨度都为1，即单元格无跨度，原始状态
    protected void initValue() {
        for (int i = 0; i < span.length; i++) {
            for (int j = 0; j < span[i].length; j++) {
                span[i][j][ICellSpan.COLUMN] = 1;
                span[i][j][ICellSpan.ROW] = 1;
            }
        }
    }

    // // 设置单元格的值，起始x/y 宽/width高/height
    // public Vector<Object> getAllCellValue() {
    // Vector<Object> cellsVector = new Vector<Object>();
    // for (int i = 0; i < span.length; i++) {
    // Vector<Object> v = new Vector<Object>();
    // for (int j = 0; j < span[i].length; j++) {
    // v.add(span[i][j][ICellSpan.ROW] + " X "
    // + span[i][j][ICellSpan.COLUMN]);
    // }
    // cellsVector.add(v);
    // }
    // return cellsVector;
    // }

    //
    // CellSpan
    //
    public int[] getSpan(int row, int column) {
        if (isOutOfBounds(row, column)) {
            int[] ret_code = { 1, 1 };
            return ret_code;
        }
        return span[row][column];
    }

    // public void setSpan(int[] span, int row, int column) {
    // if (isOutOfBounds(row, column))
    // return;
    // this.span[row][column] = span;
    // }

    public boolean isVisible(int row, int column) {
        if (isOutOfBounds(row, column))
            return false;
        if ((span[row][column][ICellSpan.COLUMN] < 1)
                || (span[row][column][ICellSpan.ROW] < 1))
            return false;
        return true;
    }

    public void combine(int[] rows, int[] columns) {
        if (isOutOfBounds(rows, columns))
            return;
        int rowSpan = rows.length;
        int columnSpan = columns.length;
        // if (rowSpan == 0 || columnSpan == 0) {
        // return;
        // }
        int startRow = rows[0];
        int startColumn = columns[0];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                // 合并单元格前，所有单元格都是原始单元格，没有合并其他单元格也没有被其他单元格合并
                if ((span[startRow + i][startColumn + j][ICellSpan.COLUMN] != 1)
                        || (span[startRow + i][startColumn + j][ICellSpan.ROW] != 1)) {
                    // System.out.println("can't combine");
                    return;
                }
            }
        }
        for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
            for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
                // 设置已被合并后的单元格离合并后的主单元格的列跨度/行跨度(负数),暂设置自身离自己是0跨度
                span[startRow + i][startColumn + j][ICellSpan.COLUMN] = jj;
                span[startRow + i][startColumn + j][ICellSpan.ROW] = ii;
                // System.out.println("r " +ii +" c " +jj);
            }
        }
        // 设置已合并后的单元格的列跨度/行跨度(整数)
        span[startRow][startColumn][ICellSpan.COLUMN] = columnSpan;
        span[startRow][startColumn][ICellSpan.ROW] = rowSpan;
    }

    public void split(int row, int column) {
        if (isOutOfBounds(row, column))
            return;
        int columnSpan = span[row][column][ICellSpan.COLUMN];
        int rowSpan = span[row][column][ICellSpan.ROW];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                // 重置单元格跨度
                span[row + i][column + j][ICellSpan.COLUMN] = 1;
                span[row + i][column + j][ICellSpan.ROW] = 1;
            }
        }
    }

    // //
    // // ColoredCell
    // //
    // public Color getForeground(int row, int column) {
    // if (isOutOfBounds(row, column))
    // return null;
    // return foreground[row][column];
    // }
    //
    // public void setForeground(Color color, int row, int column) {
    // if (isOutOfBounds(row, column))
    // return;
    // foreground[row][column] = color;
    // }
    //
    // public void setForeground(Color color, int[] rows, int[] columns) {
    // if (isOutOfBounds(rows, columns))
    // return;
    // setValues(foreground, color, rows, columns);
    // }
    //
    // public Color getBackground(int row, int column) {
    // if (isOutOfBounds(row, column))
    // return null;
    // return background[row][column];
    // }
    //
    // public void setBackground(Color color, int row, int column) {
    // if (isOutOfBounds(row, column))
    // return;
    // background[row][column] = color;
    // }
    //
    // public void setBackground(Color color, int[] rows, int[] columns) {
    // if (isOutOfBounds(rows, columns))
    // return;
    // setValues(background, color, rows, columns);
    // }

    // //
    //
    // //
    // // CellFont
    // //
    // public Font getFont(int row, int column) {
    // if (isOutOfBounds(row, column))
    // return null;
    // return font[row][column];
    // }
    //
    // public void setFont(Font font, int row, int column) {
    // if (isOutOfBounds(row, column))
    // return;
    // this.font[row][column] = font;
    // }
    //
    // public void setFont(Font font, int[] rows, int[] columns) {
    // if (isOutOfBounds(rows, columns))
    // return;
    // setValues(this.font, font, rows, columns);
    // }

    //

    //
    // CellAttribute
    //
    // public void addColumn() {
    // int[][][] oldSpan = span;
    // int numRows = oldSpan.length;
    // int numColumns = oldSpan[0].length;
    // span = new int[numRows][numColumns + 1][2]; //增加一列
    // System.arraycopy(oldSpan, 0, span, 0, numRows);
    // for (int i = 0; i < numRows; i++) {
    // span[i][numColumns][CellSpan.COLUMN] = 1;
    // span[i][numColumns][CellSpan.ROW] = 1;
    // }
    // }

    // public void addRow() {
    // int[][][] oldSpan = span;
    // int numRows = oldSpan.length;
    // int numColumns = oldSpan[0].length;
    // span = new int[numRows + 1][numColumns][2]; //增加一行
    // System.arraycopy(oldSpan, 0, span, 0, numRows);
    // for (int i = 0; i < numColumns; i++) {
    // span[numRows][i][CellSpan.COLUMN] = 1;
    // span[numRows][i][CellSpan.ROW] = 1;
    // }
    // }

    // public void insertRow(int row) {
    // int[][][] oldSpan = span;
    // int numRows = oldSpan.length;
    // int numColumns = oldSpan[0].length;
    // span = new int[numRows + 1][numColumns][2];//增加一样
    // if (0 < row) {
    // System.arraycopy(oldSpan, 0, span, 0, row - 1); //拷贝row行之前的数据，
    // }
    // System.arraycopy(oldSpan, 0, span, row, numRows - row);//拷贝row行之后的数据
    // for (int i = 0; i < numColumns; i++) { //初始化插入行位置数据
    // span[row][i][CellSpan.COLUMN] = 1;
    // span[row][i][CellSpan.ROW] = 1;
    // }
    // }

    public Dimension getSize() {
        return new Dimension(rowSize, columnSize);
    }

    public void setSize(Dimension size) {
        columnSize = size.width;
        rowSize = size.height;
        span = new int[rowSize][columnSize][2]; // 2: COLUMN,ROW
        // foreground = new Color[rowSize][columnSize];
        // background = new Color[rowSize][columnSize];
        // font = new Font[rowSize][columnSize];
    }

    // public void reSize(Dimension size) {
    // columnSize = size.width;
    // rowSize = size.height;
    // span = new int[rowSize][columnSize][2]; // 2: COLUMN,ROW
    // // foreground = new Color[rowSize][columnSize];
    // // background = new Color[rowSize][columnSize];
    // // font = new Font[rowSize][columnSize];
    // }

    /*
     * public void changeAttribute(int row, int column, Object command) { }
     * 
     * public void changeAttribute(int[] rows, int[] columns, Object command) {
     * }
     */

    // 用于拆分单元格
    protected boolean isOutOfBounds(int row, int column) {
        if ((row < 0) || (rowSize <= row) || (column < 0)
                || (columnSize <= column)) {
            return true;
        }
        return false;
    }

    // 用于合并单元格
    protected boolean isOutOfBounds(int[] rows, int[] columns) {
        if (rows.length == 0 || columns.length == 0) {
            return true;
        }
        for (int i = 0; i < rows.length; i++) {
            if ((rows[i] < 0) || (rowSize <= rows[i]))
                return true;
        }
        for (int i = 0; i < columns.length; i++) {
            if ((columns[i] < 0) || (columnSize <= columns[i]))
                return true;
        }
        return false;
    }

    // protected void setValues(Object[][] target, Object value, int[] rows,
    // int[] columns) {
    // for (int i = 0; i < rows.length; i++) {
    // int row = rows[i];
    // for (int j = 0; j < columns.length; j++) {
    // int column = columns[j];
    // target[row][column] = value;
    // }
    // }
    // }

}