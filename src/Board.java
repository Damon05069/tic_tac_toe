public final class Board {
    private static final int SIZE = 3;
    private static final char EMPTY = '\0';

    private final char[] cells = new char[SIZE * SIZE];

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
}
