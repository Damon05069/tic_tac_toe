import java.util.Objects;

public final class Game {
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;

    private Player currentPlayer;
    private Player winner;
    private GameStatus status;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = Objects.requireNonNull(playerOne, "Player one is required.");
        this.playerTwo = Objects.requireNonNull(playerTwo, "Player two is required.");
        board = new Board();
        currentPlayer = playerOne;
        winner = null;
        status = GameStatus.IN_PROGRESS;
    }

    public MoveResult playMove(int position) {
        if (status != GameStatus.IN_PROGRESS) {
            return MoveResult.GAME_OVER;
        }

        if (!Board.isValidPosition(position)) {
            return MoveResult.OUT_OF_RANGE;
        }

        if (board.isOccupied(position)) {
            return MoveResult.OCCUPIED;
        }

        board.placeMark(position, currentPlayer.getSymbol());

        if (board.hasWinningLine(currentPlayer.getSymbol())) {
            winner = currentPlayer;
            status = GameStatus.WIN;
            return MoveResult.SUCCESS;
        }

        if (board.isFull()) {
            status = GameStatus.DRAW;
            return MoveResult.SUCCESS;
        }

        switchCurrentPlayer();
        return MoveResult.SUCCESS;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getWinner() {
        return winner;
    }

    public void reset() {
        board.reset();
        currentPlayer = playerOne;
        winner = null;
        status = GameStatus.IN_PROGRESS;
    }

    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
    }
}
