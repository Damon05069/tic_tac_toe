public final class Player {
    private final String name;
    private final char symbol;

    public Player(String name, char symbol) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank.");
        }

        this.name = name.trim();
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}
