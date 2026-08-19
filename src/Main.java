import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("TIC-TAC-TOE");

            if (readStartupMenu(scanner) == SessionAction.NEW_GAME) {
                runGameSessions(scanner);
            }

            System.out.println("Thanks for playing!");
        }
    }

    private static SessionAction readStartupMenu(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("[N] Start a new game");
            System.out.println("[Q] Quit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextLine()) {
                return SessionAction.QUIT;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("N")) {
                return SessionAction.NEW_GAME;
            }

            if (input.equalsIgnoreCase("Q")) {
                return SessionAction.QUIT;
            }

            System.out.println("Enter N to start or Q to quit.");
        }
    }

    private static void runGameSessions(Scanner scanner) {
        playerSessions:
        while (true) {
            System.out.println();
            Player playerOne = registerPlayer(scanner, "Player 1", 'X', null);
            Player playerTwo = registerPlayer(
                    scanner,
                    "Player 2",
                    'O',
                    playerOne.getName());

            System.out.println();
            System.out.println("Players:");
            displayPlayer(playerOne);
            displayPlayer(playerTwo);

            Game game = new Game(playerOne, playerTwo);

            while (true) {
                SessionAction action = playGame(scanner, game);

                switch (action) {
                    case RESET:
                        game.reset();
                        System.out.printf(
                                "%nThe board has been reset. %s (X) starts.%n",
                                playerOne.getName());
                        break;
                    case NEW_GAME:
                        System.out.println("\nStarting a game with new players.");
                        continue playerSessions;
                    case QUIT:
                        return;
                }
            }
        }
    }

    private static SessionAction playGame(Scanner scanner, Game game) {
        System.out.println();
        System.out.println(game.getBoard().render());

        while (game.getStatus() == GameStatus.IN_PROGRESS) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.printf(
                    "%n%s (%c), choose 1-9 or enter R, N, or Q: ",
                    currentPlayer.getName(),
                    currentPlayer.getSymbol());

            if (!scanner.hasNextLine()) {
                return SessionAction.QUIT;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("R")) {
                return SessionAction.RESET;
            }

            if (input.equalsIgnoreCase("N")) {
                return SessionAction.NEW_GAME;
            }

            if (input.equalsIgnoreCase("Q")) {
                return SessionAction.QUIT;
            }

            int position;

            try {
                position = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a whole number from 1 to 9.");
                continue;
            }

            MoveResult result = game.playMove(position);

            switch (result) {
                case SUCCESS:
                    System.out.println();
                    System.out.println(game.getBoard().render());
                    break;
                case OCCUPIED:
                    System.out.printf(
                            "Position %d is already occupied. Choose another position.%n",
                            position);
                    break;
                case OUT_OF_RANGE:
                    System.out.println("Position must be between 1 and 9.");
                    break;
                case GAME_OVER:
                    System.out.println("The game is already over.");
                    break;
            }
        }

        if (game.getStatus() == GameStatus.WIN) {
            Player winner = game.getWinner();
            System.out.printf(
                    "%n%s (%c) wins!%n",
                    winner.getName(),
                    winner.getSymbol());
        } else if (game.getStatus() == GameStatus.DRAW) {
            System.out.println("\nThe game ended in a draw.");
        }

        return readPostGameMenu(scanner);
    }

    private static SessionAction readPostGameMenu(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("[R] Reset with the same players");
            System.out.println("[N] New game with new players");
            System.out.println("[Q] Quit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextLine()) {
                return SessionAction.QUIT;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("R")) {
                return SessionAction.RESET;
            }

            if (input.equalsIgnoreCase("N")) {
                return SessionAction.NEW_GAME;
            }

            if (input.equalsIgnoreCase("Q")) {
                return SessionAction.QUIT;
            }

            System.out.println("Enter R to reset, N for new players, or Q to quit.");
        }
    }

    private static Player registerPlayer(
            Scanner scanner,
            String label,
            char symbol,
            String existingName) {
        while (true) {
            System.out.printf("%s, enter your name: ", label);
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be blank.");
                continue;
            }

            if (existingName != null && name.equalsIgnoreCase(existingName)) {
                System.out.println("Players must have different names.");
                continue;
            }

            return new Player(name, symbol);
        }
    }

    private static void displayPlayer(Player player) {
        System.out.printf("- %s (%c)%n", player.getName(), player.getSymbol());
    }
}
