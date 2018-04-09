package com.thekuzea.diploma.blocker;

import com.thekuzea.diploma.model.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppBlocker {

    private static Process proc = null;
    private static int index = (System.getProperty("os.name").contains("Mac")) ? 1 : 2 ;

    public static void parseProcessList(List<App> appList) throws IOException {
        String line;

        try {
            for(App it : appList) {
                if(restrictionChecker(it)) {
                    BufferedReader reader = getProcessList();
                    while ((line = reader.readLine()) != null) {
                        Pattern pattern = Pattern.compile(it.getName().toLowerCase());
                        Matcher matcher = pattern.matcher(line.toLowerCase());

                        if (matcher.find()) {
                            killProcess(line);
                            break;
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) { }
    }

    private static boolean restrictionChecker(App app) {
        if(app.getForeverBlocked()) {
            return true;
        }

        return LocalTime.now().isAfter(app.getForbiddanceStart())
                && LocalTime.now().isBefore(app.getForbiddanceEnd());
    }

    private static BufferedReader getProcessList() {
        try {
            proc = Runtime.getRuntime().exec("ps -e");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream stream = proc.getInputStream();
        return new BufferedReader(new InputStreamReader(stream));
    }

    private static void killProcess(String processData) {
        try {
            String check = processData.split(" ")[index];
            proc = Runtime.getRuntime().exec("kill " + check);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
