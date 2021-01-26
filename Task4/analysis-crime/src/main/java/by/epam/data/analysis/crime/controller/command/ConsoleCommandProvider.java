package by.epam.data.analysis.crime.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsoleCommandProvider {
    private final Map<String, ConsoleCommand> map;

    @Autowired
    public ConsoleCommandProvider(@Qualifier("consoleCommandMapBean")Map<String, ConsoleCommand> map) {
        this.map = map;
    }

    public ConsoleCommand getConsoleCommand(String key) {
        return map.containsKey(key) ? map.get(key) : map.get(null);
    }
}
