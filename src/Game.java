import java.util.Objects;

public final class Game {
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;

    private Player currentPlayer;
    private GameStatus status;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = Objects.requireNonNull(playerOne, "Player one is required.");
        this.playerTwo = Objects.requireNonNull(playerTwo, "Player two is required.");
        board = new Board();
        currentPlayer = playerOne;
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

    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
    }
}
