package org.tennis.utils;

import lombok.Getter;

import java.util.List;

public class ConsoleScorePrinter implements PrintUseCase{

    @Getter
    private static final ConsoleScorePrinter INSTANCE = new ConsoleScorePrinter();

    private ConsoleScorePrinter(){}

    public static ConsoleScorePrinter getInstance(){
        return INSTANCE;
    }

    @Override
    public void print(List<String> scoreLines) {
        scoreLines.forEach(System.out::println);
    }
}
