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

            Board board = new Board();
            System.out.println(board.render());
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
