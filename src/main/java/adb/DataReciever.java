package adb;

import adb.actions.*;
import gui.formExplorer;
import structure.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataReciever {

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


    public DataReciever(formExplorer form) {
        executorService = Executors.newFixedThreadPool(ADB_PROCESS_NUMBER);
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

    public void asyncRenameFile(String oldValue, String newValue, ModelSPFolders model) {
        performActionAsync(new ActionRenameFile(oldValue, newValue, model));
    }

    public void getDirContent(String dir){
        performActionAsync(new ActionGetDirContent(dir));
    }

    public void setSelectedDevice(String newDevice) {
        DataReciever.selectedDevice = newDevice;
    }
    
    public void pullFile(String path, String dPath) {
        performActionAsync(new ActionPullFile(saveLocation, path, dPath));
    }

    public void pushFile(String source, String destination) {
        performActionAsync(new ActionPushFile(source, destination));
    }

    public void deleteFile(String currentDir, String path) {
        performActionAsync(new ActionDeleteFile(currentDir, path));
    }

    public String getSaveLocation() {
        if (saveLocation != null) {
            return saveLocation.getAbsolutePath();
        } else {
            return System.getProperty("user.dir") + "\\pull";
        }
    }

    public void makeScreenshot(String currentDir) {
        performActionAsync(new ActionMakeScreenshot(currentDir, saveLocation));
    }

    public void getPackages() {
        performActionAsync(new ActionGetPackages());
    }

}
