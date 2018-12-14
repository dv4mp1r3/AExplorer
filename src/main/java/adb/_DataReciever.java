package adb;

import adb.actions.ActionGetDirContent;
import adb.actions.DataReceiverAction;
import adb.actions.ActionRenameFile;
import gui.formExplorer;
import structure.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _DataReciever {

    public static String selectedDevice = "";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    private File saveLocation;

    public static String adbPath = "adb";

    /**
     * Пул потоков для всех запущенных не как сервис процессов adb
     */
    protected ExecutorService executorService;

    protected formExplorer form;

    public static final int ADB_PROCESS_NUMBER = 5;


    public _DataReciever(formExplorer form) {
        ExecutorService es = Executors.newFixedThreadPool(ADB_PROCESS_NUMBER);
        this.form = form;
        saveLocation = new File(Config.saveLocation());
        // if OS is Windows
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            adbPath = System.getProperty("user.dir")+"\\adb\\adb.exe";
        }
        else // nix-based system
        {
            String customAdbPath = Config.adbPath();
            // custom adb path is defined in settings.ini
            if (customAdbPath.length() > 0)
            {
                adbPath = customAdbPath;
            }
        }

        BufferedReader reader = null;
        InputStream processIn = null;
        try {
            // Иногда процесс не завершается
            // Например, когда выводится сообщение об ошибке перезапуска с правами рута
            // В таких случаях надо грохнуть процесс и сделать рестарт adb           
            if (Config.startAsRoot()) {
                new ProcessBuilder(adbPath, "shell \"su\"").start();
            }

            Process process = new ProcessBuilder(adbPath, "version").start();
            processIn = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(processIn));
            Logger.writeToLog(reader.readLine());
        } catch (IOException e) {
            Logger.writeToLog(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (processIn != null) {
                    processIn.close();
                }
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }

    }

    protected DataReceiverAction setActionProperties(DataReceiverAction action)
    {
        action.setAdbPath(adbPath);
        action.setForm(form);
        if (!selectedDevice.isEmpty())
        {
            action.setSelectedDevice(selectedDevice);
        }
        return action;
    }

    protected void performActionAsync(DataReceiverAction action)
    {
        setActionProperties(action);
        executorService.execute(action);
    }

    protected void performActionSync(DataReceiverAction action)
    {
        setActionProperties(action);
        action.run();
    }

    public ArrayList<String> getDevices(boolean log) {
        ArrayList<String> ret = new ArrayList<String>();
        InputStream processIN = null;
        BufferedReader br = null;
        Process process = null;
        try {
            process = new ProcessBuilder(adbPath, "devices").start();
            processIN = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(processIN));

            br.readLine();
            String line;
            while ((line = br.readLine()) != null && line.length() > 0) {
                String[] split = line.split("\t");
                if (split.length >= 1) {
                    ret.add(split[0]);
                }

            }
        } catch (IOException e) {
            Logger.writeToLog(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (processIN != null) {
                    processIN.close();
                }
            } catch (IOException e) {
               Logger.writeToLog(e);
            }

        }
        if (log) {
            Logger.writeToLog(ret.size() + LanguageStrings.getProperty("devicesLog"));
        }
        return ret;
    }

    public void connectDevice(final String ip) {
        if (ip != null && ip.length() > 0) {
            try {
                new ProcessBuilder(adbPath, "connect", ip).start();
                Logger.writeToLog(ip + LanguageStrings.getProperty("deviceConnectedLog"));
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }
    }

    public void asyncRenameFile(String oldValue, String newValue) {
        performActionAsync(new ActionRenameFile(oldValue, newValue));
    }

    public ModelSPFolders asyncGetDirContent(String dir) throws Exception {
        performActionAsync(new ActionGetDirContent(dir));
    }

    public void setSelectedDevice(String newDevice) {
        _DataReciever.selectedDevice = newDevice;
    }
    
    public File pullFile(String path, String dPath) {
        try {
            String dest = saveLocation.getAbsolutePath();
            if (!saveLocation.exists()) {
                saveLocation.mkdirs();
            }
            String[] splits = path.split("/");
            if (path.endsWith("/")) {
                String interrimsPath = "";
                for (int i = 0; i < splits.length - 1; i++) {
                    interrimsPath += splits[i] + '/';
                }

                path = interrimsPath + splits[splits.length - 1];
            }
            Logger.writeToLog("Pulling file "+path+" -------> "+dPath);
            Process p = new ProcessBuilder(adbPath, "-s", selectedDevice, "pull", path, dPath).start();
            p.waitFor();
            Logger.writeToLog("done");
            //Logger.writeToLog(path + LanguageStrings.getProperty("pullFromToLog") + dPath);

            return new File(dPath + '\\' + splits[splits.length - 1]);
        } catch (IOException e) {
            Logger.writeToLog("PullFile: can't load file " + path);
            Logger.writeToLog(e);
            return null;
        } catch (InterruptedException e) {
            Logger.writeToLog("PullFile: can't build process");
            Logger.writeToLog(e);
            return null;
        }
    }

    public void pushFile(String source, String destination) {
        try {
            Logger.writeToLog("Pushing file "+source+" -------> "+destination);
            Process process = new ProcessBuilder(adbPath, "-s", selectedDevice, "push", source, destination).start();
            process.waitFor();
            Logger.writeToLog("done");
            //Logger.writeToLog(source + LanguageStrings.getProperty("pushFailedLog") + destination);
        } catch (IOException e) {
            Logger.writeToLog("PushFile: can't load file " + source);
            Logger.writeToLog(e);
        } catch (InterruptedException e) {
            Logger.writeToLog("PushFile: can't load file " + source);
            Logger.writeToLog(e);
        }
    }

    public void deleteFile(String path) {
        Process p;
        InputStream processIN = null;
        BufferedReader reader = null;
        try {
            p = new ProcessBuilder(adbPath, "-s", selectedDevice, "ls", path).start();
            processIN = p.getInputStream();
            reader = new BufferedReader(new InputStreamReader(processIN));
            String line = reader.readLine();
            if (line == null) {
                Logger.writeToLog(path);
                Process process = new ProcessBuilder(adbPath, "-s", selectedDevice, "shell", "rm", path).start();
                process.waitFor();

            } else {
                reader.readLine();
                ArrayList<String> filesToDelete = new ArrayList<String>();

                while ((line = reader.readLine()) != null) {
                    String[] splits = line.split(" ");
                    filesToDelete.add(path + '/' + splits[3]);
                }
                if (filesToDelete.isEmpty()) {
                    Logger.writeToLog(path);
                    Process process = new ProcessBuilder(adbPath, "-s", selectedDevice, "shell", "rmdir", path).start();
                    process.waitFor();
                } else {
                    for (String toDel : filesToDelete) {
                        deleteFile(toDel);
                    }
                    deleteFile(path);
                }
            }
        } catch (IOException e) {
            Logger.writeToLog(path);
            Logger.writeToLog(e);
        } catch (InterruptedException e) {
            Logger.writeToLog(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (processIN != null) {
                    processIN.close();
                }
            } catch (IOException e) {
                Logger.writeToLog(e);
            }
        }
    }

    public String getSaveLocation() {
        if (saveLocation != null) {
            return saveLocation.getAbsolutePath();
        } else {
            return System.getProperty("user.dir") + "\\pull";
        }
    }

    public String makeScreenshot() throws IOException, InterruptedException {
        Date dt = new Date();
        String resultPath = "/sdcard/screen.png";
        String[] cmd1 = {adbPath, "shell", "screencap", "-p", resultPath};
        String screenCapResult = executeCommand(cmd1);
        if (screenCapResult.indexOf("not found") > 0)
        {
            throw new InterruptedException("Screencap not found on android device");
        }
        String dest = dt.toString().replaceAll(" ", "_") + ".png";
        dest = dest.replaceAll(":", "-");
        pullFile(resultPath, dest);
        String[] cmd2 = {adbPath, "shell", "rm", resultPath};
        executeCommand(cmd2);

        return dt.toString();
    }

    public static ModelPackage getPackages() throws IOException, InterruptedException {
        String[] args = {adbPath, "-s", selectedDevice, "shell", "pm", "list", "packages"};
        String res = executeCommand(args);
        String[] packages = res.split("\r\npackage:");

        packages[0] = packages[0].substring(packages[0].indexOf(':') + 1);
        packages[packages.length - 1] = packages[packages.length - 1].replace("\r\n", "");

        ModelPackage mp = new ModelPackage(packages.length);
        for (String _package : packages) {
            mp.setRow(new TableRowElementPackage(_package));
        }

        return mp;
    }

}
