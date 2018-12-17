package adb.actions;

import adb.Logger;
import adb.proccess.ProcessHelper;
import gui.formPackages;
import structure.ModelPackage;
import structure.TableRowElementPackage;

import java.io.IOException;

public class ActionGetPackages extends DataReceiverAction {
    @Override
    protected void beforeAction() {

    }

    @Override
    protected void afterAction() {
        new formPackages((ModelPackage)result).setVisible(true);
    }

    @Override
    public Object doEvent() {
        initCommand("pm list packages");
        String res = null;
        res = ProcessHelper.executeCommand(args);

        String[] packages = res.split("\r\npackage:");
        packages[0] = packages[0].substring(packages[0].indexOf(':') + 1);
        packages[packages.length - 1] = packages[packages.length - 1].replace("\r\n", "");
        ModelPackage mp = new ModelPackage(packages.length);
        for (String _package : packages) {
            mp.setRow(new TableRowElementPackage(_package));
        }
        result = mp;
        return result;
    }
}
