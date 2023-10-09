import java.util.Scanner;

public abstract class Location {
    private Player player;
    private String name;
    private int locID;
    public static Scanner input = new Scanner(System.in);

    public Location(Player player, String name, int locID) {
        this.player = player;
        this.name = name;
        this.locID = locID;
    }

    public abstract boolean onLocation();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocID() {
        return locID;
    }

    public void setLocID(int ID) {
        this.locID = ID;
    }

}

