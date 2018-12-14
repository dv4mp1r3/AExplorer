package adb.proccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessHelper {
    public static String executeCommand(String[] command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command).start();
        //todo:
        // Какая-то хрень при получении списка пакетов
        // Процесс чего-то ждет
        // Приходится ставить таймер на ожидание
        if (command[command.length - 1].equals("packages") && command.length == 7) {
            synchronized (process) {
                process.wait(2000);
            }
        } else {
            process.waitFor();
        }

        String result = "";
        String line;
        BufferedReader shellBR = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while (shellBR.ready() && (line = shellBR.readLine()) != null) {
            if (line.length() == 0) {
                continue;
            }
            result += line;
            result += "\r\n";
        }

        return result;
    }
}
