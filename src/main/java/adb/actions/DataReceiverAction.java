package adb.actions;

import gui.formExplorer;
import structure.Config;

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

    public DataReceiverAction initFromAction(DataReceiverAction action)
    {
        selectedDevice = action.selectedDevice;
        form = action.form;
        adbPath = action.adbPath;

        return this;
    }

    public void initCommand(String command)
    {
        if (Config.startAsRoot())
        {
            command = "su -c \"" + command + "\"";
            args = new String[]{adbPath, "-s", selectedDevice, "shell", command};
        }
        else
        {
            args = new String[]{adbPath, "-s", selectedDevice, "shell", command};
        }
    }
}
