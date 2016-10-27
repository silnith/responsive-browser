package org.silnith.browser.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


public class PathSegmentTableModel extends AbstractTableModel implements TableModel {
    
    private final List<String> pathSegments;
    
    public PathSegmentTableModel() {
        super();
        this.pathSegments = new ArrayList<>();
        this.pathSegments.add("TR");
        this.pathSegments.add("html4");
        this.pathSegments.add("charset.html");
    }

    @Override
    public int getRowCount() {
        return pathSegments.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Path Segment";
        } else {
            return "Matrix Variable";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return pathSegments.get(rowIndex);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            pathSegments.set(rowIndex, String.valueOf(aValue));
            fireTableCellUpdated(rowIndex, columnIndex);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
