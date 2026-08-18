public final class Board {
    private static final int SIZE = 3;
    private static final char EMPTY = '\0';
    private static final int FIRST_POSITION = 1;
    private static final String SPACER_ROW = "     |     |     ";
    private static final String DIVIDER_ROW = "_____|_____|_____";
    private static final int[][] WINNING_LINES = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

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

    public boolean hasWinningLine(char symbol) {
        if (symbol == EMPTY) {
            return false;
        }

        for (int[] line : WINNING_LINES) {
            if (cells[line[0]] == symbol
                    && cells[line[1]] == symbol
                    && cells[line[2]] == symbol) {
                return true;
            }
        }

        return false;
    }

    public boolean isFull() {
        for (char cell : cells) {
            if (cell == EMPTY) {
                return false;
            }
        }

        return true;
    }

    public void reset() {
        for (int index = 0; index < cells.length; index++) {
            cells[index] = EMPTY;
        }
    }

    public String render() {
        StringBuilder board = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        for (int row = 0; row < SIZE; row++) {
            board.append(SPACER_ROW).append(lineSeparator);

            for (int column = 0; column < SIZE; column++) {
                int index = row * SIZE + column;
                board.append("  ")
                        .append(cellContent(index))
                        .append("  ");

                if (column < SIZE - 1) {
                    board.append('|');
                }
            }

            board.append(lineSeparator);

            if (row < SIZE - 1) {
                board.append(DIVIDER_ROW).append(lineSeparator);
            } else {
                board.append(SPACER_ROW);
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
