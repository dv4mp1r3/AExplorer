package adb.actions;

import adb.Logger;
import adb.proccess.ProcessHelper;

import java.io.IOException;

public class ActionRenameFile extends DataReceiverAction {

    protected String newValue, oldValue, processResult;

    public ActionRenameFile(String oldValue, String newValue)
    {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    protected void beforeAction() {
        String moveString = "mv '" + oldValue + "' " + "'" + newValue + "'";
        args = new String[] {adbPath, "-s", selectedDevice, "shell", moveString};
    }

    @Override
    protected void afterAction() {
        //todo: refresh form tablesp
    }

    @Override
    public Object doEvent() {
        try {
            processResult = ProcessHelper.executeCommand(args);
            if (processResult.toLowerCase().indexOf("failed on") == 0) {
                return false;
            }
        } catch (IOException e) {
            Logger.writeToLog(e);
            return false;
        } catch (InterruptedException e) {
            Logger.writeToLog(e);
            return false;
        }
        return true;
    }
}
