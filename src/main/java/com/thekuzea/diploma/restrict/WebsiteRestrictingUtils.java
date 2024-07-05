package com.thekuzea.diploma.restrict;

import com.thekuzea.diploma.model.Website;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import static com.thekuzea.diploma.restrict.OperatingSystemUtils.MAC;
import static com.thekuzea.diploma.restrict.OperatingSystemUtils.OS_NAME;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebsiteRestrictingUtils {

    private static final boolean IS_MAC_OS = System.getProperty(OS_NAME).contains(MAC);

    private static final String MAC_HOSTS_PATH = "/private/etc/hosts";

    private static final String LINUX_HOSTS_PATH = "/etc/hosts";

    private static final String LOCALHOST_IPV4_ADDRESS = "127.0.0.1";

    private static final File HOSTS_FILE = new File(IS_MAC_OS ? MAC_HOSTS_PATH : LINUX_HOSTS_PATH);

    private static final String HOSTS_FILE_HEADER;

    static {
        if (IS_MAC_OS) {
            HOSTS_FILE_HEADER = """
                    ##
                    # Host Database
                    #
                    # localhost is used to configure the loopback interface
                    # when the system is booting.  Do not change this entry.
                    ##
                    127.0.0.1 localhost
                    255.255.255.255 broadcasthost
                    ::1             localhost
                    """;
        } else {
            HOSTS_FILE_HEADER = """
                    127.0.0.1 localhost
                    127.0.1.1 ubuntu
                    #
                    # The following lines are desirable for IPv6 capable hosts
                    ::1     ip6-localhost ip6-loopback
                    fe00::0 ip6-localnet
                    ff00::0 ip6-mcastprefix
                    ff02::1 ip6-allnodes
                    ff02::2 ip6-allrouters
                    """;
        }
    }

    public static void updateHostsFileOnDemand(final List<Website> websiteList) {
        if (!CollectionUtils.isEmpty(websiteList)) {
            final StringBuilder hostsFileContent = new StringBuilder();
            hostsFileContent.append(HOSTS_FILE_HEADER);
            updateHostsFile(websiteList, hostsFileContent);
        }
    }

    private static void updateHostsFile(final List<Website> websiteList, final StringBuilder hostsFileContent) {
        try {
            try (final PrintWriter out = new PrintWriter(HOSTS_FILE.getAbsoluteFile())) {
                out.print(hostsFileContent);

                for (final Website website : websiteList) {
                    if (website.isRestricted() && !hostsFileContent.toString().contains(website.getUrl())) {
                        out.println(LOCALHOST_IPV4_ADDRESS + " " + website.getUrl());
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error occurred during hosts file update, reason: {}", e.getMessage());
        }
    }
}
