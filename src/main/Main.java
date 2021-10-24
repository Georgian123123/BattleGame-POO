package main;

import getdate.GameLoader;
import getdate.Map;
import getdate.Player;

import java.util.ArrayList;

public final class Main {
    private Main() {
        // just to trick checkstyle
    }

    public static void main(final String[] args) {
        GameLoader gameLoader = new GameLoader(args[0], args[1]);
        Map myMap = gameLoader.load();
        ArrayList<Player> myPlayers = gameLoader.getPlayers();
        GameEngine theGame = new GameEngine();
        theGame.startGame(myPlayers, myMap, gameLoader.getRounds(), gameLoader.getFs());
    }
}
