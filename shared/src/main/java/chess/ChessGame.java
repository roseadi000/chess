package chess;

import java.util.*;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor team;
    private ChessPosition startPosition;
    private ChessMove move;
    private TeamColor teamColor;
    private ChessBoard board;
    private ChessBoard newBoard;

    private ChessPosition getKingPosition (TeamColor color, ChessBoard board) {
        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPosition pos = new ChessPosition(r, c);
                if (board.getPiece(pos) != null) {
                    if ((board.getPiece(pos).getTeamColor() == color) && (board.getPiece(pos).getPieceType() == ChessPiece.PieceType.KING)) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    private boolean checkOpponentPositions (ChessPosition kingPosition, TeamColor kingColor, ChessBoard board) {
        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPosition pos = new ChessPosition(r, c);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null) {
                    if (piece.getTeamColor() != kingColor) {
                        Collection<ChessMove> moves = piece.pieceMoves(board, pos);
                        for (ChessMove move : moves) {
                            if (move.getEndPosition().equals(kingPosition)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        this.startPosition = startPosition;
        ChessPiece piece = board.getPiece(startPosition);

        if (piece == null) {
            return null;
        }

        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        List<ChessMove> validMoves = new ArrayList<>();

           for (ChessMove move : moves) {
                this.newBoard = board.copyBoard();
                newBoard.addPiece(move.getEndPosition(), piece);
                newBoard.removePiece(startPosition);

                if (!isInCheck(piece.getTeamColor())) {
                    validMoves.add(move);
                }
            }
            return validMoves;

    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        this.move = move;
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> validMoves = validMoves(startPosition);

        if (validMoves == null) {
            throw new InvalidMoveException("No valid moves");
        }
        else if (piece.getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException("Not your turn");
        }

        if (validMoves.contains(move)) {
            if (move.getPromotionPiece() != null) {
                ChessPiece promotionPiece = new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
                board.addPiece(endPosition, promotionPiece);
                board.removePiece(startPosition);
            }
            else {
                board.addPiece(endPosition, piece);
                board.removePiece(startPosition);
            }

            if (getTeamTurn() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            }
            else {
                setTeamTurn(TeamColor.WHITE);
            }
        }
        else {
            throw new InvalidMoveException("Not a valid move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        this.teamColor = teamColor;
        ChessPosition kingPosition = getKingPosition(teamColor, newBoard);
        return checkOpponentPositions(kingPosition, teamColor, newBoard);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        this.teamColor = teamColor;

        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPosition pos = new ChessPosition(r, c);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null) {
                    if (piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> validMoves = validMoves(pos);

                        if (validMoves.size() > 0) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        this.teamColor = teamColor;
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
        this.newBoard = board.copyBoard();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return team == chessGame.team && Objects.equals(startPosition, chessGame.startPosition) && Objects.equals(move, chessGame.move) && teamColor == chessGame.teamColor && Objects.equals(board, chessGame.board) && Objects.equals(newBoard, chessGame.newBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, startPosition, move, teamColor, board, newBoard);
    }
}
