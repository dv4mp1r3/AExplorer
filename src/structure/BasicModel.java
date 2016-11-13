/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author divan
 */
public class BasicModel extends AbstractTableModel
{
    protected String[] columnNames;
    protected /*Object[][]*/ Vector<Object[]> data = new Vector<Object[]>();
    protected String cellOldValue = null;
    protected int cellOldRow = -1, cellOldCol = -1;
    //private Vector<String[]> data = new Vector<String[]>();
    public BasicModel()
    {
    }
     
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    public int getRowCount() 
    {
        return data.size();
    }

    public String getColumnName(int col) 
    {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) 
    {
        return data.get(row)[col];
    }

    public boolean isCellEditable(int row, int col)
    { 
        return false; 
    }
    
    public void setValueAt(Object value, int row, int col) 
    {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public Class getColumnClass(int column) 
    {
        Class clazz = String.class;
        switch (column) 
        {
            case 0:
                clazz = ImageIcon.class;
                break;
        }
        return clazz;
    }  
    
    public String getCellOldValue()
    {
        return cellOldValue;
    }
    
    public int getCellOldCol()
    {
        return cellOldCol;
    }
    
    public int getCellOldRow()
    {
        return cellOldRow;
    }
    
    public void removeRow(int row) 
    {
        data.removeElementAt(row);
    }
}