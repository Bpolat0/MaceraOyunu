public abstract class NormalLocation extends Location {

    public NormalLocation(Player player, String name, int LocId) {
        super(player, name, LocId);
    }

    @Override
    public boolean onLocation() {
        return true;
    }
}
