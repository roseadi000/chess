package ui;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class UI {

    public String[] prelogin() {
        String helpMenu = """
                Login <username> <password> - Login in to play
                Register <username> <password> <email> - Don't have an account? Register
                Quit - Exit the program""";

        System.out.println("Welcome to Chess CLI. Type 'help' to get started");
        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var result = line.toLowerCase().split(" ");

        return result;
    }

    public String[] postlogin() {
        String helpMenu = """
                Logout - Logout of your account
                create <Name> - Create a chess game
                list - See the list of chess games
                join <ID> - Join a chess game
                observe <ID> - Watch a chess game
                Quit - Exit the program""";

        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var result = line.toLowerCase().split(" ");

        return result;
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
