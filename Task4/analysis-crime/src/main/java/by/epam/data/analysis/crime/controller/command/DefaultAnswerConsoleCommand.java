package by.epam.data.analysis.crime.controller.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class DefaultAnswerConsoleCommand implements ConsoleCommand {
    @Override
    public void execute(Properties properties) {
        log.info("Please, choose existent option.");
    }
}
