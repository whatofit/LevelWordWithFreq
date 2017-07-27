package com.myblog.mergetablecells;

import java.awt.Dimension;
import java.util.Vector;

/*
 * (swing1.1beta3)
 */
/**
 * @version 1.0 11/22/98
 */

public interface ICellAttribute {

    // public void addColumn();

    // public void addRow();

    // public void insertRow(int row);

    public Dimension getSize();

    public void setSize(Dimension size);

    //public Vector<Object> getAllCellValue();
}