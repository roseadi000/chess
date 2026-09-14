package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {


    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == PieceType.BISHOP) {
            Collection<ChessMove> moves = new ArrayList<>();

            //row++ col++
            int j = myPosition.getColumn();
            for (int i = myPosition.getRow(); i < 9; i++) {
                if (j > 8) {
                    break;
                }

                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece otherPiece = board.getPiece(pos);
                    if (otherPiece.getTeamColor() != this.pieceColor) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                j++;
            }

            //row++ col--
            j = myPosition.getColumn();
            for (int i = myPosition.getRow(); i < 9; i++) {
                if (j <= 0) {
                    break;
                }
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece otherPiece = board.getPiece(pos);
                    if (otherPiece.getTeamColor() != this.pieceColor) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                j--;
            }

            //row-- col++
            j = myPosition.getColumn();
            for (int i = myPosition.getRow(); i > 0; i--) {
                if (j > 8) {
                    break;
                }
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece otherPiece = board.getPiece(pos);
                    if (otherPiece.getTeamColor() != this.pieceColor) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                j++;
            }

            //row-- col--
            j = myPosition.getColumn();
            for (int i = myPosition.getRow(); i > 0; i--) {
                if (j <= 0) {
                    break;
                }
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece otherPiece = board.getPiece(pos);
                    if (otherPiece.getTeamColor() != this.pieceColor) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                j--;
            }
            //System.out.println(moves);
            return moves;
        }
        return List.of();
    }
}
