package adb.actions;

import adb.Logger;
import adb.proccess.ProcessHelper;
import structure.Config;
import structure.ModelSPFolders;

import java.io.IOException;

public class ActionRenameFile extends DataReceiverAction {

    protected String newValue, oldValue, processResult;

    protected ModelSPFolders model;

    public ActionRenameFile(String oldValue, String newValue, ModelSPFolders model)
    {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.model = model;
    }

    @Override
    protected void beforeAction() {
        String moveString = "mv '" + oldValue + "' " + "'" + newValue + "'";
        initCommand(moveString);
    }

    @Override
    protected void afterAction() {
    }

    @Override
    public Object doEvent() {
        processResult = ProcessHelper.executeCommand(args);
        if (processResult.toLowerCase().indexOf("failed on") == 0) {
            return false;
        }
        return true;
    }
}
