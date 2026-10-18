package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;
import result.ListGamesResultList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameService {

    private final DataAccess da;

    public GameService(DataAccess da) {
        this.da = da;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws InvalidAuthException {
        if (da.getAuth(listGamesRequest.authToken()) != null) {
            Map<Integer, GameData> games = da.getGamesList();
            return new ListGamesResult(getListGamesList(games));
        }
        else {
            throw new InvalidAuthException("Error: Please log in");
        }
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws InvalidAuthException, FieldEmptyException {
        if(createGameRequest.gameName() == null || createGameRequest.gameName().equals("{}")){
            throw new FieldEmptyException("Error: game name cannot be empty");
        }

        if (da.getAuth(createGameRequest.authToken()) != null) {
            int gameID = getGameID();
            ChessGame chessGame = new ChessGame();
            GameData gameData = new GameData(gameID, null, null,
                    createGameRequest.gameName(), chessGame);
            da.createGame(gameData);

            return new CreateGameResult(gameID);

        }
        else {
            throw new InvalidAuthException("Error: Please log in");
        }
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws InvalidAuthException, GameNotFoundException, ColorIsFullException, InvalidColorException, FieldEmptyException {
        if(joinGameRequest.playerColor() == null || joinGameRequest.playerColor().equals("")){
            throw new FieldEmptyException("Error: color cannot be empty");
        }

        AuthData auth = da.getAuth(joinGameRequest.authToken());
        if(auth != null) {
            GameData game = da.getGame(joinGameRequest.gameID());
            if(game != null){

                GameData updatedGame = updateUsername(auth.username() , joinGameRequest.playerColor(), game);
                da.updateGame(updatedGame);

                return new JoinGameResult();
            }
            else{
                throw new GameNotFoundException("Error: GameID not found");
            }
        }
        else {
            throw new InvalidAuthException("Error: Please log in");
        }
    }

    private GameData updateUsername(String username, String color, GameData game) throws ColorIsFullException, InvalidColorException {
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        if(color.equals("WHITE")){
            if (game.whiteUsername() == null){
                whiteUsername = username;
            }
            else{
                throw new ColorIsFullException("Error: Color is full. Please choose another color or game");
            }
        }
        else if(color.equals("BLACK")){
            if (game.blackUsername() == null){
                blackUsername = username;
            }
            else{
                throw new ColorIsFullException("Error: Color is full. Please choose another color or game");
            }
        }
        else{
            throw new InvalidColorException("Error: Please choose white or black");
        }

        return new GameData(game.gameID(), whiteUsername, blackUsername, game.gameName(), game.game());
    }

    private Integer getGameID(){
      Integer gameID = new Random().nextInt(10000);
      if (da.getGame(gameID) != null) {
          return getGameID();
      }
      else {
          return gameID;
      }
    }

    private List<ListGamesResultList> getListGamesList(Map<Integer, GameData> gameData){
        List<ListGamesResultList> games = new ArrayList<>();

        for(GameData game : gameData.values()){
            ListGamesResultList gamesResult = new ListGamesResultList(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName());
            games.add(gamesResult);
        }

        return games;
    }

}
