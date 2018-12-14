package gui;

import adb.DataReciever;

import javax.swing.*;
import java.io.File;
import structure.Explorer;

public class DataReceiverSwingWorker extends SwingWorker<File, String> {

    protected DataReciever adb;

    protected String from;
    protected String to;

    protected CustomTable table;

    protected Explorer e;

    public void setDataReceiver(DataReciever adb)
    {
        this.adb = adb;
    }

    public void setFromTo(String from, String to)
    {
        this.from = from;
        this.to = to;
    }

    @Override
    protected File doInBackground() throws Exception {
        File f = adb.pullFile(from, to);
        if (f != null)
        {
            table.setModel(e.setPath(), null);
            return f;
        }
        throw new Exception();
    }

    public void setExplorer(Explorer e)
    {
        this.e = e;
    }

    /**
     * Передача таблицы для перерисовки
     * @param table
     */
    public void setTable(CustomTable table)
    {
        this.table = table;
    }

}
