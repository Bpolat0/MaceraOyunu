public class SafeHouse extends NormalLocation {

    public SafeHouse(Player player) {
        super(player, "Güvenli Ev --> Burası sizin için güvenli bir ev, düşman yoktur", 1);
    }

    @Override
    public boolean onLocation() {
        System.out.println("Güvenli evdesiniz !");
        System.out.println("Canınız yenilendi !");
        this.getPlayer().setHealth(this.getPlayer().getOriginalHealth());
        return true;
    }
}
