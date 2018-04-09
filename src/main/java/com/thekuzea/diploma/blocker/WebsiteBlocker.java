package com.thekuzea.diploma.blocker;

import com.thekuzea.diploma.model.Website;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.List;

public class WebsiteBlocker {

    private static boolean isMacOs = System.getProperty("os.name").contains("Mac");
    private static File hostsFile = new File((isMacOs) ? "/private/etc/hosts" : "/etc/hosts");
    private static StringBuilder fileData = new StringBuilder();

    public static void uploadFile(List<Website> websiteList) {
        if (websiteList != null) {
            initFileHeader();

            writeToFile(websiteList);
        }
    }

    private static void initFileHeader() {
        if (isMacOs) {
            fileData.append("##\n" +
                    "# Host Database\n" +
                    "#\n" +
                    "# localhost is used to configure the loopback interface\n" +
                    "# when the system is booting.  Do not change this entry.\n" +
                    "##\n" +
                    "127.0.0.1\tlocalhost\n" +
                    "255.255.255.255\tbroadcasthost\n" +
                    "::1             localhost\n");
        } else {
            fileData.append("127.0.0.1\tlocalhost\n" +
                    "127.0.1.1\tubuntu\n" +
                    "\n" +
                    "# The following lines are desirable for IPv6 capable hosts\n" +
                    "::1     ip6-localhost ip6-loopback\n" +
                    "fe00::0 ip6-localnet\n" +
                    "ff00::0 ip6-mcastprefix\n" +
                    "ff02::1 ip6-allnodes\n" +
                    "ff02::2 ip6-allrouters\n");
        }
    }

    private static void writeToFile(List<Website> websiteList) {
        try {
            try (PrintWriter out = new PrintWriter(hostsFile.getAbsoluteFile())) {
                out.print(fileData);

                for (Website it : websiteList) {
                    if (restrictionChecker(it) && !fileData.toString().contains(it.getWebsite())) {
                        out.println("127.0.0.1 " + it.getWebsite());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileData.setLength(0);
    }

    private static boolean restrictionChecker(Website website) {
        if (website.getForeverBlocked()) {
            return true;
        }

        return LocalTime.now().isAfter(website.getForbiddanceStart())
                && LocalTime.now().isBefore(website.getForbiddanceEnd());
    }

}
