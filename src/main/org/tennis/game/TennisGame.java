package org.tennis.game;

import org.tennis.utils.ConsoleScorePrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TennisGame {

    private static boolean isDeuce = false;
    private static Player playerWithAdvantage = null;
    private static boolean isGameWon = false;


    private static List<String> scoreLines;

    public static void printScore(String input) {
        resetGame();
        Map<Player, Score> score = initializeScore();

        for (char c : input.toCharArray()) {
            if (isGameWon) break;

            Player scoringPlayer = (c == 'A') ? Player.A : Player.B;
            Player opponent = (scoringPlayer == Player.A) ? Player.B : Player.A;
            updateScore(score, scoringPlayer, opponent);
        }

        ConsoleScorePrinter.getInstance().print(scoreLines);

    }

    private static void resetGame() {
        scoreLines = new ArrayList<>();
        isDeuce = false;
        playerWithAdvantage = null;
        isGameWon = false;
    }

    private static Map<Player, Score> initializeScore() {
        Map<Player, Score> score = new HashMap<>();
        score.put(Player.A, Score.LOVE);
        score.put(Player.B, Score.LOVE);
        return score;
    }

    private static void updateScore(Map<Player, Score> score, Player scoringPlayer, Player opponent) {
        Score scoringCurrentScore = score.get(scoringPlayer);
        Score opponentCurrentScore = score.get(opponent);

        if (isDeuce) {
            handleDeuce(scoringPlayer);
            return;
        }

        Score newScore = updateNonDeuceScore(scoringCurrentScore);

        score.put(scoringPlayer, newScore);

        if (newScore == Score.FORTY && opponentCurrentScore == Score.FORTY) {
            isDeuce = true;
            scoreLines.add("Deuce");
        } else {
            scoreLines.add(String.format("Player A: %d / Player B: %d", score.get(Player.A).getValue(), score.get(Player.B).getValue()));
        }
    }

    private static void handleDeuce(Player scoringPlayer) {
        if (playerWithAdvantage == null) {
            playerWithAdvantage = scoringPlayer;
            scoreLines.add(String.format("Player %s has advantage", scoringPlayer));
        } else if (scoringPlayer == playerWithAdvantage) {
            isGameWon = true;
            scoreLines.add(String.format("Player %s wins the game", scoringPlayer));
        } else {
            playerWithAdvantage = null;
            scoreLines.add("Deuce");
        }
    }

    private static Score updateNonDeuceScore(Score scoringCurrentScore) {
        return switch (scoringCurrentScore) {
            case LOVE -> Score.FIFTEEN;
            case FIFTEEN -> Score.THIRTY;
            case THIRTY -> Score.FORTY;
            default -> scoringCurrentScore;
        };
    }
}

