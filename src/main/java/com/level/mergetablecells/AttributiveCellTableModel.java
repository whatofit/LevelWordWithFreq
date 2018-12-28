package com.level.mergetablecells;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * @version 1.0 11/22/98
 */
// 带属性单元格的TableModel
public class AttributiveCellTableModel extends DefaultTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2256041704810279587L;

    protected ICellAttribute cellAtt;

    // public AttributiveCellTableModel() {
    // this((Vector) null, 0);
    // }

    public AttributiveCellTableModel(int numRows, int numColumns) {
        Vector<Object> names = new Vector<Object>(numColumns);
        // System.out.println("AttributiveCellTableModel,names.size()=" +
        // names.size());
        names.setSize(numColumns);
        setColumnIdentifiers(names);
        // dataVector = new Vector();
        setNumRows(numRows);
        cellAtt = new DefaultCellAttribute(numRows, numColumns);
        // changeAllCellAttribute();
    }

    // public AttributiveCellTableModel(Vector columnNames, int numRows) {
    // setColumnIdentifiers(columnNames);
    // dataVector = new Vector();
    // setNumRows(numRows);
    // cellAtt = new DefaultCellAttribute(numRows, columnNames.size());
    // }
    //
    // public AttributiveCellTableModel(Object[] columnNames, int numRows) {
    // this(convertToVector(columnNames), numRows);
    // }
    //
    // public AttributiveCellTableModel(Vector data, Vector columnNames) {
    // setDataVector(data, columnNames);
    // }
    //
    public AttributiveCellTableModel(Object[][] data, Object[] columnNames) {
        setDataVector(data, columnNames);
        cellAtt = new DefaultCellAttribute(data.length, columnNames.length);
        // changeAllCellAttribute();
    }

    //
    // public void setDataVector(Vector newData, Vector columnNames) {
    // if (newData == null)
    // throw new IllegalArgumentException(
    // "setDataVector() - Null parameter");
    // dataVector = new Vector(0);
    // setColumnIdentifiers(columnNames);
    // dataVector = newData;
    //
    // //
    // cellAtt = new DefaultCellAttribute(dataVector.size(),
    // columnIdentifiers.size());
    //
    // newRowsAdded(new TableModelEvent(this, 0, getRowCount() - 1,
    // TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    // }

    // public void addColumn(Object columnName, Vector columnData) {
    // if (columnName == null)
    // throw new IllegalArgumentException("addColumn() - null parameter");
    // columnIdentifiers.addElement(columnName);
    // int index = 0;
    // Enumeration enumeration = dataVector.elements();
    // while (enumeration.hasMoreElements()) {
    // Object value;
    // if ((columnData != null) && (index < columnData.size()))
    // value = columnData.elementAt(index);
    // else
    // value = null;
    // ((Vector) enumeration.nextElement()).addElement(value);
    // index++;
    // }
    //
    // //
    // cellAtt.addColumn();
    //
    // fireTableStructureChanged();
    // }

    // public void addRow(Vector rowData) {
    // Vector newData = null;
    // if (rowData == null) {
    // newData = new Vector(getColumnCount());
    // } else {
    // rowData.setSize(getColumnCount());
    // }
    // dataVector.addElement(newData);
    //
    // //
    // cellAtt.addRow();
    //
    // newRowsAdded(new TableModelEvent(this, getRowCount() - 1,
    // getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
    // TableModelEvent.INSERT));
    // }

    // public void insertRow(int row, Vector rowData) {
    // if (rowData == null) {
    // rowData = new Vector(getColumnCount());
    // } else {
    // rowData.setSize(getColumnCount());
    // }
    //
    // dataVector.insertElementAt(rowData, row);
    //
    // //
    // cellAtt.insertRow(row);
    //
    // newRowsAdded(new TableModelEvent(this, row, row,
    // TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    // }

    public AttributiveCellTableModel(Vector<Vector<Object>> selectAll2Vector,
            Vector<String> tableTitle) {
        setDataVector(selectAll2Vector, tableTitle);
        cellAtt = new DefaultCellAttribute(selectAll2Vector == null ? 0
                : selectAll2Vector.size(), tableTitle.size());
    }

    //增加或减少行数，若不改变cellAtt的size值，重绘时会引起下标越界异常
    public void updateData(Vector<Vector<Object>> selectAll2Vector,Vector<String> tableTitle) {
        setDataVector(selectAll2Vector, tableTitle);
        //cellAtt.reSize();
        cellAtt = new DefaultCellAttribute(selectAll2Vector == null ? 0: selectAll2Vector.size(), tableTitle.size());
    }
    
    public ICellAttribute getCellAttribute() {
        return cellAtt;
    }

    // public void setCellAttribute(CellAttribute newCellAtt) {
    // int numColumns = getColumnCount();
    // int numRows = getRowCount();
    // if ((newCellAtt.getSize().width != numColumns)
    // || (newCellAtt.getSize().height != numRows)) {
    // newCellAtt.setSize(new Dimension(numRows, numColumns));
    // }
    // cellAtt = newCellAtt;
    // fireTableDataChanged();
    // }

    /*
     * public void changeCellAttribute(int row, int column, Object command) {
     * cellAtt.changeAttribute(row, column, command); }
     * 
     * public void changeCellAttribute(int[] rows, int[] columns, Object
     * command) { cellAtt.changeAttribute(rows, columns, command); }
     */

    // public void changeAllCellAttribute() {
    // dataVector = cellAtt.getAllCellValue();
    // }
    public boolean isCellEditable(int row, int column) {
        // JTextField tf = new JTextField();
        // tf.addKeyListener(new KeyAdapter() {
        // public void keyReleased(KeyEvent e) {
        // event(e);
        // };
        // });
        // tf.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // tf.setSelectionStart(0);
        // tf.setSelectionEnd(tf.getText().length());
        // table.getColumnModel().getColumn(column)
        // .setCellEditor(new DefaultCellEditor(tf));

        // if (column<=2) {
        // return false;// 默认是true
        // }else{
        // return true;
        // }
        return false;// 默认是true
    }
}
