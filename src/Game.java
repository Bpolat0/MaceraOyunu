import java.util.Scanner;

public class Game {
    private Scanner input = new Scanner(System.in);

    public void start() {
        System.out.println("Macera Oyununa Hoşgeldiniz !");
        System.out.print("Lütfen bir isim giriniz : ");
        //String playerName = input.nextLine();
        Player player = new Player("Ali");
        System.out.println("Sayın " + player.getName() + " bu karanlık ve sisli adaya hoşgeldiniz !! Burada yaşananların hepsi gerçek !");
        System.out.println("Lütfen bir karakter seçiniz !");
        player.selectChar();
        Location location = null;

        while (true) {
            player.printInfo();

            NormalLocation[] LocList = {new SafeHouse(player), new ToolStore(player)};
            BattleLocation[] battleLocList = {new Cave(player), new Forest(player), new River(player), new Maden(player)};

            System.out.println("##########################");
            for (NormalLocation normalLocation : LocList) {
                System.out.println("ID : " + normalLocation.getLocID() +
                        "\tBölge İsmi : " + normalLocation.getName());
            }
            for (BattleLocation battleLocation : battleLocList) {
                System.out.println("ID : " + battleLocation.getLocID() +
                        "\tBölge İsmi : " + battleLocation.getName());
            }
            System.out.println("ID : 0 " + "\tBölge İsmi : Çıkış Yap");
            System.out.println("##########################");
            System.out.print("Lütfen bir bölge seçiniz : ");
            int selectLoc = input.nextInt();
            switch (selectLoc) {
                case 0:
                    location = null;
                    break;
                case 1:
                    if (player.getInventory().Food && player.getInventory().Water && player.getInventory().FireWood) {
                        System.out.println("Adadan kaçmak için gerekli tüm eşyaları topladınız. Oyun bitti :)");
                        break;
                    }
                    location = new SafeHouse(player);
                    break;
                case 2:
                    location = new ToolStore(player);
                    break;
                case 3:
                    if (player.getInventory().Food) {
                        System.out.println("Bu bölgenin ödülünü ele geçirmiştiniz !");
                        location = new SafeHouse(player);
                        break;
                    }
                    location = new Cave(player);
                    break;
                case 4:
                    if (player.getInventory().FireWood) {
                        System.out.println("Bu bölgenin ödülünü ele geçirmiştiniz !");
                        location = new SafeHouse(player);
                        break;
                    }
                    location = new Forest(player);
                    break;
                case 5:
                    if (player.getInventory().Water) {
                        System.out.println("Bu bölgenin ödülünü ele geçirmiştiniz !");
                        location = new SafeHouse(player);
                        break;
                    }
                    location = new River(player);
                    break;
                case 6:
                    location = new Maden(player);
                    break;
                default:
                    System.out.println("Lütfen geçerli bir bölge giriniz !");
            }


            if (location == null) {
                System.out.println("Bu karanlık ve sisli adadan çabuk vazgeçtin !");
                break;
            }
            if (!location.onLocation()) {
                System.out.println("GAME OVER!");
                break;
            }
        }
    }


}


