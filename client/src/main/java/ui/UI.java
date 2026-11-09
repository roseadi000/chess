package ui;

import result.ListGamesResult;
import result.ListGamesResultList;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class UI {

    public String[] prelogin() {
        System.out.println("Welcome to Chess CLI. Type 'help' to get started");
        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var result = line.toLowerCase().split(" ");

        return result;
    }

    public String[] postlogin() {
        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var result = line.toLowerCase().split(" ");

        return result;
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
}
