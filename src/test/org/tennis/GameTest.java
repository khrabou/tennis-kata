package org.tennis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tennis.game.TennisGame;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void should_return_score_when_a_scores() {
        String expected = "Player A: 15 / Player B: 0";
        String input = "A";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void should_return_score_when_two_players_score() {
        String expected = "Player A: 15 / Player B: 0" + System.lineSeparator()
                + "Player A: 15 / Player B: 15";
        String input = "AB";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void should_increment_when_current_score_is_fifteen() {
        String expected = "Player A: 15 / Player B: 0" + System.lineSeparator()
                + "Player A: 30 / Player B: 0";
        String input = "AA";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void should_increment_when_current_score_is_thirty() {
        String expected = "Player A: 15 / Player B: 0" + System.lineSeparator() +
                "Player A: 30 / Player B: 0" + System.lineSeparator() +
                "Player A: 40 / Player B: 0";
        String input = "AAA";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void should_have_advantage_when_scores_and_current_score_is_forty() {
        String expected = "Player A: 15 / Player B: 0" + System.lineSeparator() +
                "Player A: 15 / Player B: 15" + System.lineSeparator() +
                "Player A: 15 / Player B: 30" + System.lineSeparator() +
                "Player A: 15 / Player B: 40" + System.lineSeparator() +
                "Player A: 30 / Player B: 40" + System.lineSeparator() +
                "Deuce" + System.lineSeparator() +
                "Player A has advantage";

        String input = "ABBBAAA";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void should_deuce_when_lose_advantage() {
        String expected = "Player A: 15 / Player B: 0" + System.lineSeparator() +
                "Player A: 15 / Player B: 15" + System.lineSeparator() +
                "Player A: 15 / Player B: 30" + System.lineSeparator() +
                "Player A: 15 / Player B: 40" + System.lineSeparator() +
                "Player A: 30 / Player B: 40" + System.lineSeparator() +
                "Deuce" + System.lineSeparator() +
                "Player A has advantage" + System.lineSeparator() +
                "Deuce";

        String input = "ABBBAAAB";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void should_finish_the_game_when_player_win() {
        String expected = "Player A: 15 / Player B: 0" + System.lineSeparator() +
                "Player A: 15 / Player B: 15" + System.lineSeparator() +
                "Player A: 15 / Player B: 30" + System.lineSeparator() +
                "Player A: 15 / Player B: 40" + System.lineSeparator() +
                "Player A: 30 / Player B: 40" + System.lineSeparator() +
                "Deuce" + System.lineSeparator() +
                "Player A has advantage" + System.lineSeparator() +
                "Player A wins the game";

        String input = "ABBBAAAA";

        TennisGame.printScore(input);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }
}