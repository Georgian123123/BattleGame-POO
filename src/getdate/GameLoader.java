package getdate;

import java.util.ArrayList;

import fileio.FileSystem;

public final class GameLoader {
    private Integer numberOfPlayers = -1;
    private Integer rounds = -1;
    private FileSystem fs = null;

    public GameLoader(final String mInputPath, final String mOutputPath) {
        try {
            fs = new FileSystem(mInputPath, mOutputPath);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private ArrayList<ArrayList<Character>> getMap(final Integer mapX,
                                                   final Integer mapY,
                                                   final FileSystem fs) {

        String myString;
        ArrayList<ArrayList<Character>> map = new ArrayList<ArrayList<Character>>();
        char[] types = new char[mapY];
        for (Integer i = 0; i < mapX; ++i) {
            try {
                myString = fs.nextWord();
                types = myString.toCharArray();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            ArrayList<Character> mapTypes = new ArrayList<Character>();
            for (Integer j = 0; j < mapY; ++j) {
                mapTypes.add(j, types[j]);
            }
            map.add(i, mapTypes);
        }
        return map;
    }

    public Map load() {
        ArrayList<ArrayList<Character>> map = new ArrayList<ArrayList<Character>>();
        Integer length = 0;
        Integer height = 0;

        try {
            length = fs.nextInt();
            height = fs.nextInt();
            map = getMap(length, height, fs);
            numberOfPlayers = fs.nextInt();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return new Map(length, height, numberOfPlayers, map, rounds);
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        String typeOfPlayer;
        Integer positionX;
        Integer positionY;
        try {
            for (Integer j = 0; j < numberOfPlayers; ++j) {
                typeOfPlayer = fs.nextWord();
                positionX = fs.nextInt();
                positionY = fs.nextInt();
                Player newPlayer = new Player(typeOfPlayer, positionX, positionY);
                players.add(j, newPlayer);
            }
            rounds = fs.nextInt();
            for (Integer i = 0; i < rounds; ++i) {
                String moves = fs.nextWord();
                for (Player player : players) {
                    player.setMoves(moves);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return players;
    }

    public Integer getRounds() {
        return rounds;
    }

    public FileSystem getFs() {
        return fs;
    }
}
