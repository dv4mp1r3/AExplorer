/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

/**
 *
 * @author Dm
 */
public class ModelPackage extends BasicModel 
{
    public static final String[] COLUMNS = {"","Package Name"};
    int i, sz;
    public ModelPackage(int rowCount)
    {
        this.columnNames = COLUMNS;
        for (int u = 0; u < rowCount; u++)
            data.add(new Object[COLUMNS.length]);
        sz = rowCount;
        i = 0;
    }
    
    public void setRow(TableRowElementPackage trp)
    {
        if (i < sz)
        {
            data.get(i)[0] = null;
            data.get(i)[1] = trp.p;
            //data[i][2] = trp.k;
            
            i++;
        }       
    }
    
    public int getColumnNumberByName(String columnName)
    {
        int i = 0;
        for (String colName : COLUMNS)
        {
            if (colName.equals(columnName))
                return i;
            i++;
        }
        
        return -1;
    }
    
    
}
