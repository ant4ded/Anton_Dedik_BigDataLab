package by.epam.data.analysis.crime.controller;

import by.epam.data.analysis.crime.service.impl.CrimeService;
import by.epam.data.analysis.crime.service.impl.StopAndSearchService;
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
    private static final String ARGUMENT_API_STOP_AND_SEARCH = "stop_and_search";
    private static final String ARGUMENT_API_CRIME = "crime";

    private final CrimeService crimeService;
    private final StopAndSearchService stopAndSearchService;

    // TODO: 10.02.2021 tests
    @Autowired
    public ConsoleCommandController(CrimeService crimeService, StopAndSearchService stopAndSearchService) {
        this.crimeService = crimeService;
        this.stopAndSearchService = stopAndSearchService;
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
                .numberOfArgs(3)
                .desc("Use value for given properties.")
                .build();
        options.addOption(propertyOption);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        long start = System.currentTimeMillis();
        if (cmd.hasOption("D")) {
            Properties properties = cmd.getOptionProperties("D");
            if (Boolean.parseBoolean(properties.getProperty(ARGUMENT_API_STOP_AND_SEARCH))) {
                stopAndSearchService.download(properties);
            }
            if (Boolean.parseBoolean(properties.getProperty(ARGUMENT_API_CRIME))) {
                crimeService.download(properties);
            }
        }
        long end = System.currentTimeMillis();
        log.info("Executing: " + ((end - start) / 1000) + " seconds.");
    }
}
