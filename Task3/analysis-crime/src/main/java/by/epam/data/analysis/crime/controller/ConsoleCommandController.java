package by.epam.data.analysis.crime.controller;

import by.epam.data.analysis.crime.service.CrimeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class ConsoleCommandController implements CommandLineRunner {
    private final CrimeService crimeService;

    @Autowired
    public ConsoleCommandController(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @SneakyThrows
    @Override
    public void run(String... args) {
        Options options = new Options();
        Option propertyOption = Option.builder()
                .longOpt("D")
                .argName("property=value")
                .hasArg()
                .hasArgs()
                .valueSeparator()
                .numberOfArgs(2)
                .desc("Use value for given properties.")
                .build();
        options.addOption(propertyOption);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        long start = System.currentTimeMillis();
        if (cmd.hasOption("D")) {
            Properties properties = cmd.getOptionProperties("D");
            crimeService.downloadAndSave(properties);
        }
        long end = System.currentTimeMillis();
        log.info("Executing: " + ((end-start) / 1000) + " seconds.");
    }
}
