package com.thekuzea.diploma.restrict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.model.RestrictedEntity;

import static com.thekuzea.diploma.common.constant.GlobalConstants.SPACE;
import static com.thekuzea.diploma.restrict.OperatingSystemUtils.MAC;
import static com.thekuzea.diploma.restrict.OperatingSystemUtils.OS_NAME;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppRestrictingUtils {

    private static final String GET_APP_LIST_COMMAND = "ps -e";

    private static final String KILL_COMMAND = "kill";

    private static final int MAC_PROCESS_ID_INDEX = 1;

    private static final int LINUX_PROCESS_ID_INDEX = 2;

    private static final int PROCESS_ID_INDEX = System.getProperty(OS_NAME).contains(MAC) ? MAC_PROCESS_ID_INDEX : LINUX_PROCESS_ID_INDEX;

    public static void queryProcessListAndKillOnDemand(final List<App> appList) {
        final List<String> processInformationList = getProcessInformationList();
        if (CollectionUtils.isEmpty(processInformationList)) {
            return;
        }

        appList.stream()
                .filter(RestrictedEntity::isRestricted)
                .forEach(app -> killProcessOnMatch(app, processInformationList));
    }

    private static void killProcessOnMatch(final App app, final List<String> processInformationList) {
        final Pattern appNamePattern = Pattern.compile(app.getName().toLowerCase());

        processInformationList.stream()
                .filter(processInformation -> appNamePattern.matcher(processInformation.toLowerCase()).find())
                .toList()
                .forEach(AppRestrictingUtils::killProcess);
    }

    private static void killProcess(final String processInformation) {
        try {
            final String processId = processInformation.split(SPACE)[PROCESS_ID_INDEX];
            executeCommand(KILL_COMMAND, processId);
        } catch (IOException e) {
            log.error("Kill command failure. reason: {}", e.getMessage());
        }
    }

    private static List<String> getProcessInformationList() {
        Process process;
        try {
            process = executeCommand(GET_APP_LIST_COMMAND);
        } catch (IOException e) {
            log.error("Unable to get process list, reason: {}", e.getMessage());
            return Collections.emptyList();
        }

        final InputStream stream = process.getInputStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(stream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.lines().toList();
    }

    private static Process executeCommand(final String... commandArgs) throws IOException {
        return Runtime.getRuntime().exec(commandArgs);
    }
}
