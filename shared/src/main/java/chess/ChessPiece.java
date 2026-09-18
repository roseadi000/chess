package chess;

import javax.print.attribute.standard.PrintQuality;
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

    private Collection<ChessMove> BishopMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        //row++ col++
        int j = myPosition.getColumn() + 1;
        for (int i = myPosition.getRow() + 1; i < 9; i++) {
            if (j > 8) {
                break;
            }

            ChessPosition pos = new ChessPosition(i, j);
            ChessPiece otherPiece = board.getPiece(pos);
            if (otherPiece == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else {
                if (otherPiece.getTeamColor() != this.pieceColor) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                    break;
                }
                else {
                    break;
                }
            }
            j++;
        }

        //row++ col--
        j = myPosition.getColumn() - 1;
        for (int i = myPosition.getRow() + 1; i < 9; i++) {
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
                    break;
                }
                else {
                    break;
                }
            }
            j--;
        }

        //row-- col++
        j = myPosition.getColumn() + 1;
        for (int i = myPosition.getRow() - 1; i > 0; i--) {
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
                    break;
                }
                else {
                    break;
                }
            }
            j++;
        }

        //row-- col--
        j = myPosition.getColumn() - 1;
        for (int i = myPosition.getRow() - 1; i > 0; i--) {
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
                    break;
                }
                else {
                    break;
                }
            }
            j--;
        }
        //System.out.println(moves);
        return moves;
    }

    private Collection<ChessMove> KingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        List<List<Integer>> movements = List.of(List.of(-1, -1), List.of(-1, 0), List.of(-1, 1),
                List.of(0, -1), List.of(0, 1),
                List.of(1, -1), List.of(1, 0), List.of(1, 1));

        for (List<Integer> m : movements) {
            int nR = r + m.get(0);
            int nC = c + m.get(1);
            if ((nR < 8) && (nC < 8)) {
                if ((nR > 0) && (nC > 0)) {
                    ChessPosition pos = new ChessPosition(nR, nC);
                    if ((board.getPiece(pos) == null) || (board.getPiece(pos).getTeamColor() != this.pieceColor)){
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
            }
        }

        return moves;
    }

   private Collection <ChessMove> KnightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        List<List<Integer>> movements = List.of(List.of(-2, -1), List.of(-2, 1),
               List.of(-1, -2), List.of(-1, 2),
               List.of(1, -2), List.of(1, 2),
               List.of(2, -1), List.of(2, 1));

        for (List<Integer> m : movements) {
            int nR = r + m.get(0);
            int nC = c + m.get(1);

            if ((nR <= 8) && (nC <= 8)) {
                if ((nR > 0) && (nC > 0)){
                    ChessPosition pos = new ChessPosition(nR, nC);
                    if ((board.getPiece(pos) == null || (board.getPiece(pos).getTeamColor() != this.pieceColor))) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
            }
        }

        return moves;
   }

   private Collection <ChessMove> PawnMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
       int r = myPosition.getRow();
       int c = myPosition.getColumn();

       if (this.pieceColor == ChessGame.TeamColor.WHITE) {
           ChessPosition pos = new ChessPosition(r + 1, c);
           if (board.getPiece(pos) == null) {
               if (r == 7) {
                for (PieceType piece : PieceType.values()) {
                    if ((piece == PieceType.KING) || (piece == PieceType.PAWN)) {
                        continue;
                    }
                    ChessMove move = new ChessMove(myPosition, pos, piece);
                    moves.add(move);
                }
               } else {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
           }
           if (r == 2) {
               ChessPosition twoW = new ChessPosition(r + 2, c);
               if ((board.getPiece(twoW) == null) && (board.getPiece(pos) == null)) {
                   ChessMove move = new ChessMove(myPosition, twoW, null);
                   moves.add(move);
               }
           }
           if ((r < 8) && (r > 0)) {
               ChessPosition right = new ChessPosition(r + 1, c + 1);
               ChessPosition left = new ChessPosition(r + 1, c - 1);
               if ((c < 8) && (c >= 0)) {
                   if ((board.getPiece(right) != null) && (board.getPiece(right).getTeamColor() != this.pieceColor)) {
                       if (r == 7) {
                           for (PieceType piece : PieceType.values()) {
                               if ((piece == PieceType.KING) || (piece == PieceType.PAWN)) {
                                   continue;
                               }
                               ChessMove move = new ChessMove(myPosition, right, piece);
                               moves.add(move);
                           }
                       } else {
                           ChessMove move = new ChessMove(myPosition, right, null);
                           moves.add(move);
                       }
                   }
               }
               if ((c <= 8) && (c > 1)) {
                   if ((board.getPiece(left) != null) && (board.getPiece(left).getTeamColor() != this.pieceColor)) {
                       if (r == 7) {
                           for (PieceType piece : PieceType.values()) {
                               if ((piece == PieceType.KING) || (piece == PieceType.PAWN)) {
                                   continue;
                               }
                               ChessMove move = new ChessMove(myPosition, left, piece);
                               moves.add(move);
                           }
                       } else {
                           ChessMove move = new ChessMove(myPosition, left, null);
                           moves.add(move);
                       }
                   }
               }
           }

       }
       else if (this.pieceColor == ChessGame.TeamColor.BLACK) {
           ChessPosition pos = new ChessPosition(r - 1, c);
           if (board.getPiece(pos) == null) {
               if (r == 2) {
                   for (PieceType piece : PieceType.values()) {
                       if ((piece == PieceType.KING) || (piece == PieceType.PAWN)) {
                           continue;
                       }
                       ChessMove move = new ChessMove(myPosition, pos, piece);
                       moves.add(move);
                   }
               } else {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
           }
       if (r == 7) {
            ChessPosition twoB = new ChessPosition(r - 2, c);
            if ((board.getPiece(twoB) == null) && (board.getPiece(pos) == null)) {
                ChessMove move = new ChessMove(myPosition, twoB, null);
                moves.add(move);
            }
       }
           if ((r < 8) && (r > 0)) {
               ChessPosition right = new ChessPosition(r - 1, c + 1);
               ChessPosition left = new ChessPosition(r - 1, c - 1);
               if ((c < 8) && (c >= 0)) {
                   if ((board.getPiece(right) != null) && (board.getPiece(right).getTeamColor() != this.pieceColor)) {
                       if (r == 2) {
                           for (PieceType piece : PieceType.values()) {
                               if ((piece == PieceType.KING) || (piece == PieceType.PAWN)) {
                                   continue;
                               }
                               ChessMove move = new ChessMove(myPosition, right, piece);
                               moves.add(move);
                           }
                       } else {
                           ChessMove move = new ChessMove(myPosition, right, null);
                           moves.add(move);
                       }
                   }
               }
               if ((c <= 8) && (c > 1)) {
                   if ((board.getPiece(left) != null) && (board.getPiece(left).getTeamColor() != this.pieceColor)) {
                       if (r == 2) {
                           for (PieceType piece : PieceType.values()) {
                               if ((piece == PieceType.KING) || (piece == PieceType.PAWN)) {
                                   continue;
                               }
                               ChessMove move = new ChessMove(myPosition, left, piece);
                               moves.add(move);
                           }
                       } else {
                           ChessMove move = new ChessMove(myPosition, left, null);
                           moves.add(move);
                       }
                   }
                   }

           }
       }

        return moves;
   }

   private Collection<ChessMove> QueenMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        //row++ col ++
       int j = myPosition.getColumn() + 1;
       for (int i = myPosition.getRow() + 1; i < 9; i++) {
           if (j <= 8) {
               ChessPosition pos = new ChessPosition(i, j);
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }
           j++;
       }

       //row++ col=0
       for (int i = myPosition.getRow() + 1; i < 9; i++) {
               ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               } else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   } else {
                       break;
                   }
               }
           }

       //row++ col--
       j = myPosition.getColumn() - 1;
       for (int i = myPosition.getRow() + 1; i < 9; i++) {
           if (j > 0) {
               ChessPosition pos = new ChessPosition(i, j);
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }
           j--;
       }

       //row=0 col++
       for (j = myPosition.getColumn() + 1; j < 9; j++) {
               ChessPosition pos = new ChessPosition(myPosition.getRow(), j);
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }

       //row=0 col--
       for (j = myPosition.getColumn() - 1; j > 0; j--) {
               ChessPosition pos = new ChessPosition(myPosition.getRow(), j);
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }

       //row-- col++
       j = myPosition.getColumn() + 1;
       for (int i = myPosition.getRow() - 1; i > 0; i--) {
           if (j <= 8) {
               ChessPosition pos = new ChessPosition(i, j);
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }
           j++;
       }

       //row-- col=0
       for (int i = myPosition.getRow() - 1; i > 0; i--) {
               ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }

       //row-- col--
       j = myPosition.getColumn() - 1;
       for (int i = myPosition.getRow() - 1; i > 0; i--) {
           if (j > 0) {
               ChessPosition pos = new ChessPosition(i, j);
               if (board.getPiece(pos) == null) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
               }
               else {
                   if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                       ChessMove move = new ChessMove(myPosition, pos, null);
                       moves.add(move);
                       break;
                   }
                   else {
                       break;
                   }
               }
           }
           j--;
       }

        return moves;
   }

   private Collection<ChessMove> RookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        //row++ col=0
       for(int i = myPosition.getRow() + 1; i < 9; i++) {
           ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
           if (board.getPiece(pos) == null) {
               ChessMove move = new ChessMove(myPosition, pos, null);
               moves.add(move);
           }
           else {
               if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
                   break;
               }
               else {
                   break;
               }
           }
       }

       //row-- col=0
       for(int i = myPosition.getRow() - 1; i > 0; i--) {
           ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
           if (board.getPiece(pos) == null) {
               ChessMove move = new ChessMove(myPosition, pos, null);
               moves.add(move);
           }
           else {
               if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
                   break;
               }
               else {
                   break;
               }
           }
       }

       //row=0 col++
       for(int j = myPosition.getColumn() + 1; j < 9; j++) {
           ChessPosition pos = new ChessPosition(myPosition.getRow(), j);
           if (board.getPiece(pos) == null) {
               ChessMove move = new ChessMove(myPosition, pos, null);
               moves.add(move);
           }
           else {
               if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
                   break;
               }
               else {
                   break;
               }
           }
       }

       //row=0 col--
       for(int j = myPosition.getColumn() - 1; j > 0; j--) {
           ChessPosition pos = new ChessPosition(myPosition.getRow(), j);
           if (board.getPiece(pos) == null) {
               ChessMove move = new ChessMove(myPosition, pos, null);
               moves.add(move);
           }
           else {
               if (board.getPiece(pos).getTeamColor() != this.pieceColor) {
                   ChessMove move = new ChessMove(myPosition, pos, null);
                   moves.add(move);
                   break;
               }
               else {
                   break;
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
            return BishopMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.KING) {
            return KingMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.KNIGHT) {
            return KnightMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.PAWN) {
            return PawnMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.QUEEN) {
            return QueenMoves(board, myPosition);
        }
        else if (piece.getPieceType() == PieceType.ROOK) {
            return RookMoves(board, myPosition);
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
