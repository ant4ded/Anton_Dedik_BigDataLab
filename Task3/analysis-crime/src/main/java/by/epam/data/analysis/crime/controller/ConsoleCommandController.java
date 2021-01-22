package by.epam.data.analysis.crime.controller;

import by.epam.data.analysis.crime.controller.command.ConsoleCommandProvider;
import by.epam.data.analysis.crime.controller.reader.ArgumentsReader;
import lombok.SneakyThrows;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class ConsoleCommandController implements CommandLineRunner {
    private static final String EXIT_CODE = "3";

    private final ArgumentsReader reader;
    private final ConsoleCommandProvider provider;

    @Autowired
    public ConsoleCommandController(ArgumentsReader reader, ConsoleCommandProvider provider) {
        this.reader = reader;
        this.provider = provider;
    }

    @SneakyThrows
    @Override
    public void run(String... args) {
        Options options = new Options();
        Option propertyOption = Option.builder()
                .longOpt("D")
                .argName("property=value")
                .hasArgs()
                .valueSeparator()
                .numberOfArgs(2)
                .desc("use value for given properties")
                .build();

        options.addOption(propertyOption);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("D")) {
            Properties properties = cmd.getOptionProperties("D");
            provider.getConsoleCommand("1").execute(properties);
        }
//        while (true) {
//            String chosen = reader.read();
//            if (chosen.equals(EXIT_CODE)) {
//                break;
//            }
//            provider.getConsoleCommand(chosen).execute(properties);
//        }
    }
}
