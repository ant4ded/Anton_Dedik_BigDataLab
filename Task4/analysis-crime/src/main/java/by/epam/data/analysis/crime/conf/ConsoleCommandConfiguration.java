package by.epam.data.analysis.crime.conf;

import by.epam.data.analysis.crime.controller.command.ConsoleCommand;
import by.epam.data.analysis.crime.controller.command.DefaultAnswerConsoleCommand;
import by.epam.data.analysis.crime.controller.command.DownloadAndSaveCrimesConsoleCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsoleCommandConfiguration {
    @Bean
    public Map<String, ConsoleCommand> consoleCommandMapBean(
            @Autowired DownloadAndSaveCrimesConsoleCommand downloadAndSaveCrimesConsoleCommand,
            @Autowired DefaultAnswerConsoleCommand defaultAnswerConsoleCommand) {
        Map<String, ConsoleCommand> map = new HashMap<>();
        map.put("1", downloadAndSaveCrimesConsoleCommand);
        map.put(null, defaultAnswerConsoleCommand);
        return map;
    }
}
