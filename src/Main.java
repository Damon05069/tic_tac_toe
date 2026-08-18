import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("TIC-TAC-TOE");
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
            System.out.println();

            Game game = new Game(playerOne, playerTwo);
            System.out.println(game.getBoard().render());
            playGame(scanner, game);
        }
    }

    private static void playGame(Scanner scanner, Game game) {
        while (game.getStatus() == GameStatus.IN_PROGRESS && scanner.hasNextLine()) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.printf(
                    "%n%s (%c), choose a position from 1 to 9: ",
                    currentPlayer.getName(),
                    currentPlayer.getSymbol());

            String input = scanner.nextLine().trim();
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
