package structure;

public class ModelPC extends BasicModel {

    public static final String[] COLUMNS = {"", "Filename", "Ext", "Size", "Date"};
    int i, sz;

    public ModelPC(int rowCount) {
        this.columnNames = COLUMNS;
        //this.data = new Object[rowCount][5];
        for (int u = 0; u < rowCount; u++) {
            data.add(new Object[COLUMNS.length]);
        }
        sz = rowCount;
        i = 0;
    }

    public void setRow(TableRowElementPC tre) {
        if (i < sz) {
            data.get(i)[0] = tre.icon;
            data.get(i)[1] = tre.f;
            data.get(i)[2] = tre.e;
            data.get(i)[3] = tre.s;
            data.get(i)[4] = tre.d;
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
