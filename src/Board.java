public final class Board {
    private static final int SIZE = 3;
    private static final char EMPTY = '\0';
    private static final int FIRST_POSITION = 1;

    private final char[] cells = new char[SIZE * SIZE];

    public static boolean isValidPosition(int position) {
        return position >= FIRST_POSITION && position <= SIZE * SIZE;
    }

    public boolean isOccupied(int position) {
        validatePosition(position);
        return cells[toIndex(position)] != EMPTY;
    }

    void placeMark(int position, char symbol) {
        validatePosition(position);

        if (isOccupied(position)) {
            throw new IllegalStateException("Position is already occupied.");
        }

        cells[toIndex(position)] = symbol;
    }

    public String render() {
        StringBuilder board = new StringBuilder();

        for (int index = 0; index < cells.length; index++) {
            board.append(' ')
                    .append(cellContent(index))
                    .append(' ');

            boolean endOfRow = (index + 1) % SIZE == 0;
            boolean finalCell = index == cells.length - 1;

            if (!endOfRow) {
                board.append('|');
            } else if (!finalCell) {
                board.append(System.lineSeparator())
                        .append("---+---+---")
                        .append(System.lineSeparator());
            }
        }

        return board.toString();
    }

    private String cellContent(int index) {
        if (cells[index] == EMPTY) {
            return Integer.toString(index + 1);
        }

        return Character.toString(cells[index]);
    }

    private int toIndex(int position) {
        return position - FIRST_POSITION;
    }

    private void validatePosition(int position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Position must be between 1 and 9.");
        }
    }
}
