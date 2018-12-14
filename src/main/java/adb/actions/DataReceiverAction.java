package adb.actions;

import gui.formExplorer;

public abstract class DataReceiverAction extends AbstractAction {

    protected String adbPath;

    protected String selectedDevice;

    protected String[] args;

    protected formExplorer form;

    public void setSelectedDevice(String device)
    {
        this.selectedDevice = device;
    }

    public void setAdbPath(String adbPath)
    {
        this.adbPath = adbPath;
    }

    public void setForm(formExplorer form)
    {
        this.form = form;
    }

}
