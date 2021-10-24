package getdate;

import java.util.ArrayList;

public final class Map {
    private Integer length;
    private Integer height;
    private Integer numberOfPlayers;
    private Integer rounds;
    private ArrayList<ArrayList<Character>> map;

    public Map() {
        length = -1;
        height = -1;
        numberOfPlayers = -1;
        this.map = null;
    }

    public Map(final Integer length, final Integer height,
               final Integer numberOfPlayers,
               final ArrayList<ArrayList<Character>> map,
               final Integer rounds) {
        this.length = length;
        this.height = height;
        this.numberOfPlayers = numberOfPlayers;
        this.map = map;
        this.rounds = rounds;
    }

    public Integer getRounds() {
        return rounds;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(final Integer length) {
        this.length = length;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(final Integer height) {
        this.height = height;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(final Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<ArrayList<Character>> getMap() {
        return map;
    }

    public void setMap(final ArrayList<ArrayList<Character>> map) {
        this.map = map;
    }

    public boolean isValidInput() {
        boolean membersInst = numberOfPlayers != null && map != null;
        boolean mapX = length != null;
        boolean mapY = height != null;
        return membersInst && mapX && mapY;
    }
}
