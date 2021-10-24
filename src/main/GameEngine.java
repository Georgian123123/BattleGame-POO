package main;

import players.Knight;
import players.Pyromancer;
import players.Rogue;
import players.Wizard;
import getdate.Map;
import getdate.Player;

import fileio.FileSystem;

import java.util.ArrayList;

final class GameEngine {
    public static final Integer HP1 = 400;
    public static final Integer DMG1 = 20;
    public static final Integer HP2 = 600;
    public static final Integer HP3 = 900;
    public static final Integer HP4 = 500;
    public static final Integer DMG2 = 35;
    public static final Integer DMG3 = 200;
    public static final Integer DMG4 = 40;
    public static final Integer DMG6 = 200;
    public static final Integer DMG7 = 100;
    public static final Integer DMG8 = 350;
    public static final Integer DMG9 = 150;

    private ArrayList<Player> myPlayers = new ArrayList<Player>();

    private void setMyPlayers(final ArrayList<Player> myPlayers) {
        this.myPlayers = myPlayers;
    }

    private ArrayList<Player>
    setPlayers(final ArrayList<Player> thePlayers,
               final ArrayList<ArrayList<Character>> theMap) {
        ArrayList<Player> newPlayers = new ArrayList<Player>();
        for (Player player : thePlayers) {
            if (player.getTypeOfCharacter().equals("W")) {
                Player newPlayer = new Wizard(HP1, 0, DMG1,
                        DMG2, false,
                        player.getPositionX(), player.getPositionY());
                ArrayList<Character> array = theMap.get(player.getPositionX());
                newPlayer.setLand(array.get(player.getPositionY()));
                newPlayer.setAllMoves(player.getMoves());
                newPlayers.add(newPlayer);
            }
            if (player.getTypeOfCharacter().equals("R")) {
                Player newPlayer = new Rogue(HP2, 0, DMG3, DMG4,
                        false, player.getPositionX(), player.getPositionY());
                ArrayList<Character> array = theMap.get(player.getPositionX());
                newPlayer.setLand(array.get(player.getPositionY()));
                newPlayer.setAllMoves(player.getMoves());
                newPlayers.add(newPlayer);
            }
            if (player.getTypeOfCharacter().equals("K")) {
                Player newPlayer = new Knight(DMG6, DMG7,
                        false, HP3, 0, player.getPositionX(),
                        player.getPositionY());
                ArrayList<Character> array = theMap.get(player.getPositionX());
                newPlayer.setLand(array.get(player.getPositionY()));
                newPlayer.setAllMoves(player.getMoves());
                newPlayers.add(newPlayer);
            }
            if (player.getTypeOfCharacter().equals("P")) {
                Player newPlayer = new Pyromancer(HP4, 0, DMG8,
                        DMG9, 50, false,
                        player.getPositionX(), player.getPositionY());
                ArrayList<Character> array = theMap.get(player.getPositionX());
                newPlayer.setLand(array.get(player.getPositionY()));
                newPlayer.setAllMoves(player.getMoves());
                newPlayers.add(newPlayer);
            }
        }
        return newPlayers;
    }

    private void movePlayers(final Integer moveX,
                             final Integer movey,
                             final Integer playerPosition,
                             final Map theMap) {
        ArrayList<ArrayList<Character>> map = theMap.getMap();
        Player player = getPlayers().get(playerPosition);
        if (player.getCanMove() && player.isAlive()) {
            player.setPositionX(player.getPositionX() + moveX);
            player.setPositionY(player.getPositionY() + movey);
            Character land;
            if (player.getPositionY() >= 0 && player.getPositionX() >= 0) {
                land = map.get(player.getPositionX()).get(player.getPositionY());
                player.setLand(land);
            }
        }
        if (player.getHowManyRounds() > 0) {
            player.setHp(player.getHp() - player.getExtraDmg());
            player.setHowManyRounds(player.getHowManyRounds() - 1);
            if (player.getHp() <= 0) {
                player.setAlive(false);
            }
            if (player.getHowManyRounds() == 0) {
                player.setCanMove(true);
            }
        }
    }

    private boolean isAnyPlayerThere(final Player player1,
                                     final Player player2) {

        return player1.getPositionX() == player2.getPositionX()
                && player1.getPositionY() == player2.getPositionY()
                && !player1.isFought() && !player2.isFought();
    }

    private ArrayList<Player> getPlayers() {
        return this.myPlayers;
    }

    public void checkFights() {
        for (Player player : getPlayers()) {
            for (Player newPlayer : getPlayers()) {
                if (!newPlayer.equals(player)) {
                    if (isAnyPlayerThere(player, newPlayer)
                            && player.isAlive() && newPlayer.isAlive()) {
                        if (newPlayer instanceof Wizard) {
                            player.setFought(true);
                            newPlayer.setFought(true);
                            player.fight((Wizard) newPlayer);
                        }
                        if (newPlayer instanceof Rogue) {
                            player.setFought(true);
                            newPlayer.setFought(true);
                            player.fight((Rogue) newPlayer);
                        }
                        if (newPlayer instanceof Knight) {
                            player.setFought(true);
                            newPlayer.setFought(true);
                            player.fight((Knight) newPlayer);
                        }
                        if (newPlayer instanceof Pyromancer) {
                            player.setFought(true);
                            newPlayer.setFought(true);
                            player.fight((Pyromancer) newPlayer);
                        }
                    }
                }
            }
        }
    }

    public void restartFights() {
        for (Player player : getPlayers()) {
            player.setFought(false);
        }
    }

    public void finishGame(final FileSystem fs) {
        try {
            for (Player player : getPlayers()) {
                if (!player.isAlive()) {
                    fs.writeWord(player.getTypeOfCharacter());
                    fs.writeCharacter(' ');
                    fs.writeWord("dead");
                } else {
                    fs.writeWord(player.getTypeOfCharacter());
                    fs.writeCharacter(' ');
                    fs.writeInt(player.getLvl());
                    fs.writeCharacter(' ');
                    fs.writeInt(player.getXp());
                    fs.writeCharacter(' ');
                    fs.writeInt(player.getHp());
                    fs.writeCharacter(' ');
                    fs.writeInt(player.getPositionX());
                    fs.writeCharacter(' ');
                    fs.writeInt(player.getPositionY());
                }
                fs.writeNewLine();
            }
            fs.close();
        } catch (Exception e1) {
            e1.printStackTrace()
            ;
        }
    }

    void startGame(final ArrayList<Player> thePlayers,
                   final Map theMap,
                   final Integer rounds, final FileSystem fs) {
        setMyPlayers(setPlayers(thePlayers, theMap.getMap()));
        for (Integer round = 0; round < rounds; ++round) {
            String letsMove = getPlayers().get(0).getMoves().get(round);
            char[] move;
            move = letsMove.toCharArray();

            for (Integer go = 0; go < move.length; ++go) {
                if (move[go] == 'R') {
                    movePlayers(0, 1, go, theMap);
                } else if (move[go] == 'L') {
                    movePlayers(0, -1, go, theMap);
                } else if (move[go] == 'U') {
                    movePlayers(-1, 0, go, theMap);
                } else if (move[go] == 'D') {
                    movePlayers(1, 0, go, theMap);
                } else if (move[go] == '_') {
                    //nothing
                    movePlayers(0, 0, go, theMap);
                }
            }
            checkFights();
            restartFights();
        }
        finishGame(fs);
    }
}
