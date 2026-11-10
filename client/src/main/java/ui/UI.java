package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import result.ListGamesResult;
import result.ListGamesResultList;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class UI {

    public String[] prelogin() {
        System.out.println("Welcome to Chess CLI. Type 'help' to get started");
        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var result = line.split(" ");

        return result;
    }

    public String[] postlogin() {
        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var result = line.split(" ");

        return result;
    }

    public void displayChessBoardWhite(ChessBoard board){
        int colorNum = 1;
        StringBuilder chessBoard = new StringBuilder("\u001b[30;103;1m\u2003      " +
                "a\u2003   b\u2003   c\u2003   d\u2003   e\u2003   f\u2003   g\u2003   h       \u001b[0m\n");
        for(int r = 8; r > 0; r--){
            chessBoard.append(String.format("\u001b[30;103;1m  %d  \u001b[0m", r));
            for(int c = 8; c > 0; c--){
                ChessPiece piece = board.getPiece(new ChessPosition(r, c));
                String pieceCode = getChessPieceCode(piece);
                int pieceColorCode = 0;
                int squareColorCode = 0;
                if(colorNum % 2 != 0) {
                    squareColorCode = 105;
                }
                else{
                    squareColorCode = 100;
                }
                if(piece != null) {
                    if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        pieceColorCode = 30;
                    } else {
                        pieceColorCode = 30;
                    }
                }
                String box = String.format("\u001b[%d;%d;1m %s \u001b[0m", pieceColorCode, squareColorCode, pieceCode);
                chessBoard.append(box);
                colorNum++;
            }
            chessBoard.append(String.format("\u001b[30;103;1m  %d  \u001b[0m\n", r));
            colorNum++;
        }
        chessBoard.append("\u001b[30;103;1m\u2003      a\u2003   b\u2003   " +
                "c\u2003   d\u2003   e\u2003   f\u2003   g\u2003   h       \u001b[0m\n");

        System.out.println(chessBoard);
    }

    public void displayChessBoardBlack(ChessBoard board){
        int colorNum = 1;
        StringBuilder chessBoard = new StringBuilder("\u001b[30;103;1m\u2003      " +
                "a\u2003   b\u2003   c\u2003   d\u2003   e\u2003   f\u2003   g\u2003   h       \u001b[0m\n");
        for(int r = 1; r < 9; r++){
            chessBoard.append(String.format("\u001b[30;103;1m  %d  \u001b[0m", r));
            for(int c = 1; c < 9; c++){
                ChessPiece piece = board.getPiece(new ChessPosition(r, c));
                String pieceCode = getChessPieceCode(piece);
                int pieceColorCode = 0;
                int squareColorCode = 0;
                if(colorNum % 2 != 0) {
                    squareColorCode = 105;
                }
                else{
                    squareColorCode = 100;
                }
                if(piece != null) {
                    if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        pieceColorCode = 30;
                    } else {
                        pieceColorCode = 30;
                    }
                }
                String box = String.format("\u001b[%d;%d;1m %s \u001b[0m", pieceColorCode, squareColorCode, pieceCode);
                chessBoard.append(box);
                colorNum++;
            }
            chessBoard.append(String.format("\u001b[30;103;1m  %d  \u001b[0m\n", r));
            colorNum++;
        }
        chessBoard.append("\u001b[30;103;1m\u2003      a\u2003   b\u2003   " +
                "c\u2003   d\u2003   e\u2003   f\u2003   g\u2003   h       \u001b[0m\n");

        System.out.println(chessBoard);
    }

    public static void printMessage(String message){
        System.out.println(message);
    }

    public String formatGamesList(ListGamesResult games){
        StringBuilder list = new StringBuilder("Active Games:");
        int index = 1;
        for(ListGamesResultList game : games.games()){
            list.append(String.format("""
                    \n%d. %s
                       White: %s
                       Black: %s""", index, game.gameName(),
                    game.whiteUsername(), game.blackUsername()));
            index++;
        }

        return list.toString();
    }

    private String getChessPieceCode(ChessPiece piece) {
        if(piece == null){
            return EMPTY;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return WHITE_BISHOP;
            }
            return BLACK_BISHOP;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.KING){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return WHITE_KING;
            }
            return BLACK_KING;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return WHITE_KNIGHT;
            }
            return BLACK_KNIGHT;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.PAWN){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return WHITE_PAWN;
            }
            return BLACK_PAWN;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return WHITE_QUEEN;
            }
            return BLACK_QUEEN;
        }
        else{
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return WHITE_ROOK;
            }
            return BLACK_ROOK;
        }
    }

}
