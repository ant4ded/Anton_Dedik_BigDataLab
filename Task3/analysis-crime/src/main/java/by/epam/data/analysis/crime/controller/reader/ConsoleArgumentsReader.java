package by.epam.data.analysis.crime.controller.reader;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleArgumentsReader implements ArgumentsReader {
    public static final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
