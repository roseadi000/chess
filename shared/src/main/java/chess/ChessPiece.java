package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {


    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    private Collection<ChessMove> diagonalSlideMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        //row++ col++
        int j = myPosition.getColumn() + 1;
        for (int i = myPosition.getRow() + 1; i < 9; i++) {
            if (j > 8){
                break;
            }

            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
            j++;
        }

        //row++ col--
        j = myPosition.getColumn() - 1;
        for (int i = myPosition.getRow() + 1; i < 9; i++) {
            if (j <= 0){
                break;
            }

            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
            j--;
        }

        //row-- col++
        j = myPosition.getColumn() + 1;
        for (int i = myPosition.getRow() - 1; i > 0; i--) {
            if (j > 8){
                break;
            }

            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
            j++;
        }

        //row-- col--
        j = myPosition.getColumn() - 1;
        for (int i = myPosition.getRow() - 1; i > 0; i--) {
            if (j <= 0){
                break;
            }

            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
            j--;
        }

        return moves;
    }

    private Collection<ChessMove> straightSlideMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        //row++ col=0
        for (int i = myPosition.getRow() + 1; i < 9; i++) {
            ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
        }

        //row-- col=0
        for (int i = myPosition.getRow() - 1; i > 0; i--) {
            ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
        }

        //row=0 col++
        for (int j = myPosition.getColumn() + 1; j < 9; j++) {
            ChessPosition pos = new ChessPosition(myPosition.getRow(), j);
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
        }

        //row=0 col--
        for (int j = myPosition.getColumn() - 1; j > 0; j--) {
            ChessPosition pos = new ChessPosition(myPosition.getRow(), j);
            if (board.getPiece(pos) == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
                break;
            }
            else {
                break;
            }
        }

        return moves;

    }

    private Collection<ChessMove> pawnPromotion(ChessPosition myPosition, ChessPosition pos) {
        List<ChessMove> moves = new ArrayList<>();

        ChessMove bishop = new ChessMove(myPosition, pos, PieceType.BISHOP);
        ChessMove knight = new ChessMove(myPosition, pos, PieceType.KNIGHT);
        ChessMove queen = new ChessMove(myPosition, pos, PieceType.QUEEN);
        ChessMove rook = new ChessMove(myPosition, pos, PieceType.ROOK);

        moves.add(bishop);
        moves.add(knight);
        moves.add(queen);
        moves.add(rook);

        return moves;
    }

    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        moves.addAll(diagonalSlideMoves(board, myPosition));

        return moves;
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        moves.addAll(straightSlideMoves(board, myPosition));

        return moves;
    }

    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        moves.addAll(diagonalSlideMoves(board, myPosition));
        moves.addAll(straightSlideMoves(board, myPosition));

        return moves;
    }

    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        List<List<Integer>> movements = List.of(List.of(1, 1), List.of(1, 0), List.of(1, -1),
                List.of(0, 1), List.of(0, -1),
                List.of(-1, 1), List.of(-1, 0), List.of(-1, -1));

        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        for (List<Integer> m : movements) {
            int nR = r + m.get(0);
            int nC = c + m.get(1);

            if ((nR < 9) && (nR > 0)) {
                if ((nC < 9) && (nC > 0)) {
                    ChessPosition pos = new ChessPosition(nR, nC);
                    if ((board.getPiece(pos) == null) || (board.getPiece(pos).getTeamColor() != this.pieceColor)) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        List<List<Integer>> movements = List.of(List.of(2, 1), List.of(2, -1),
                List.of(1, 2), List.of(1, -2),
                List.of(-1, 2), List.of(-1, -2),
                List.of(-2, 1), List.of(-2, -1));

        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        for (List<Integer> m : movements) {
            int nR = r + m.get(0);
            int nC = c + m.get(1);

            if ((nR < 9) && (nR > 0)) {
                if ((nC < 9) && (nC > 0)) {
                    ChessPosition pos = new ChessPosition(nR, nC);
                    if ((board.getPiece(pos) == null) || (board.getPiece(pos).getTeamColor() != this.pieceColor)) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> PawnMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        if (this.pieceColor == ChessGame.TeamColor.WHITE) {
            if (r < 9) {
                ChessPosition pos = new ChessPosition(r + 1, c);
                if (board.getPiece(pos) == null) {
                    if (r == 7) {
                        moves.addAll(pawnPromotion(myPosition, pos));
                    }
                    else {
                        if (r == 2) {
                            ChessPosition twoPos = new ChessPosition(r + 2, c);
                            if (board.getPiece(twoPos) == null) {
                                ChessMove move = new ChessMove(myPosition, twoPos, null);
                                moves.add(move);
                            }
                        }
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                ChessPosition rightPos = new ChessPosition(r + 1, c + 1);
                ChessPosition leftPos = new ChessPosition(r + 1, c -1);

                if ((c >= 0) && (c < 8)) {
                    if ((board.getPiece(rightPos) != null) && (board.getPiece(rightPos).getTeamColor() != this.pieceColor)) {
                        if (r == 7) {
                            moves.addAll(pawnPromotion(myPosition, rightPos));
                        }
                        else{
                            ChessMove move = new ChessMove(myPosition, rightPos, null);
                            moves.add(move);
                        }
                    }
                }
                if ((c > 1) && (c <= 8)) {
                    if ((board.getPiece(leftPos) != null) && (board.getPiece(leftPos).getTeamColor() != this.pieceColor)) {
                        if (r == 7) {
                            moves.addAll(pawnPromotion(myPosition, leftPos));
                        }
                        else{
                            ChessMove move = new ChessMove(myPosition, leftPos, null);
                            moves.add(move);
                        }
                    }
                }
            }
        }
        else {
            if (r > 0) {
                ChessPosition pos = new ChessPosition(r - 1, c);
                if (board.getPiece(pos) == null) {
                    if (r == 2) {
                        moves.addAll(pawnPromotion(myPosition, pos));
                    }
                    else {
                        if (r == 7) {
                            ChessPosition twoPos = new ChessPosition(r - 2, c);
                            if (board.getPiece(twoPos) == null) {
                                ChessMove move = new ChessMove(myPosition, twoPos, null);
                                moves.add(move);
                            }
                        }
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
                ChessPosition rightPos = new ChessPosition(r - 1, c + 1);
                ChessPosition leftPos = new ChessPosition(r - 1, c -1);

                if ((c >= 0) && (c < 8)) {
                    if ((board.getPiece(rightPos) != null) && (board.getPiece(rightPos).getTeamColor() != this.pieceColor)) {
                        if (r == 2) {
                            moves.addAll(pawnPromotion(myPosition, rightPos));
                        }
                        else{
                            ChessMove move = new ChessMove(myPosition, rightPos, null);
                            moves.add(move);
                        }
                    }
                }
                if ((c > 1) && (c <= 8)) {
                    if ((board.getPiece(leftPos) != null) && (board.getPiece(leftPos).getTeamColor() != this.pieceColor)) {
                        if (r == 2) {
                            moves.addAll(pawnPromotion(myPosition, leftPos));
                        }
                        else{
                            ChessMove move = new ChessMove(myPosition, leftPos, null);
                            moves.add(move);
                        }
                    }
                }
            }
        }

        return moves;
    }

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
            return bishopMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.KING) {
            return kingMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.KNIGHT) {
            return knightMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.PAWN) {
            return PawnMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.QUEEN) {
            return queenMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.ROOK) {
            return rookMoves(board, myPosition);
        }
        return List.of();
    }

    @Override
    public String toString() {
        if (this.pieceColor == ChessGame.TeamColor.WHITE){
            if (this.type == PieceType.PAWN) {
                return "P";
            }
            else if (this.type == PieceType.ROOK) {
                return "R";
            }
            else if (this.type == PieceType.KNIGHT) {
                return "N";
            }
            else if (this.type == PieceType.BISHOP) {
                return "B";
            }
            else if (this.type == PieceType.QUEEN) {
                return "Q";
            }
            else {
                return "K";
            }
        }
        else {
            if (this.type == PieceType.PAWN) {
                return "p";
            }
            else if (this.type == PieceType.ROOK) {
                return "r";
            }
            else if (this.type == PieceType.KNIGHT) {
                return "n";
            }
            else if (this.type == PieceType.BISHOP) {
                return "b";
            }
            else if (this.type == PieceType.QUEEN) {
                return "q";
            }
            else {
                return "k";
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
