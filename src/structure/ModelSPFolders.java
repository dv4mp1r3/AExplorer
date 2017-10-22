/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import javax.swing.ImageIcon;
/**
 *
 * @author divan
 */
public class ModelSPFolders extends BasicModel {

    private int i, sz;
    public static final String[] COLUMNS = {"", "Filename", "Ext", "Owner", "Group", "Size", "Date", "Attr"};

    public ModelSPFolders(int rowCount) {
        sz = rowCount;
        this.columnNames = COLUMNS;
        for (int u = 0; u < rowCount; u++) {
            data.add(new Object[COLUMNS.length]);
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        cellOldCol = col;
        cellOldRow = row;
        Object val = this.getValueAt(row, col);       
        cellOldValue = val != null ? val.toString() : null;

        return col == 1 && this.getValueAt(row, 5) != null;
    }

    public void setRow(TableRowElementSP tre) {
        if (i < sz) {
            data.get(i)[0] = (ImageIcon) tre.Icon;
            data.get(i)[1] = tre.filename;
            data.get(i)[2] = tre.ext;
            data.get(i)[3] = tre.owner;
            data.get(i)[4] = tre.group;
            data.get(i)[5] = tre.size;
            data.get(i)[6] = tre.date; // date
            data.get(i)[7] = tre.attr;

            i++;
        }

    }

    public int getColumnNumberByName(String columnName) {
        int i = 0;
        for (String colName : COLUMNS) {
            if (colName.equals(columnName)) {
                return i;
            }
            i++;
        }

        return -1;
    }

}
