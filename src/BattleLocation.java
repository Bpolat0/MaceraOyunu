import java.util.Random;

public abstract class BattleLocation extends Location {
    private Obstacle obstacle;
    private String award;
    private int maxObstacle;

    public BattleLocation(Player player, String name, int locID, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name, locID);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;
    }

    @Override
    public boolean onLocation() {
        int obsNumber = this.randomObstacleNumber();
        System.out.println("Şuan buradasınız : " + this.getName());
        System.out.println("Dikkatli ol! Burada " + obsNumber + " tane " + this.getObstacle().getName() + "Yaşıyor !");
        System.out.print("<S>avaş veya <K>aç");
        String selectCase = input.nextLine().toUpperCase();
        if (selectCase.equals("S") && combat(obsNumber)) {
            if (this.getLocID() == 3) {
                this.getPlayer().getInventory().Food = true;
                System.out.println("Tebrikler ödülü ele geçirdiniz : Food");
            }
            if (this.getLocID() == 4) {
                this.getPlayer().getInventory().FireWood = true;
                System.out.println("Tebrikler ödülü ele geçirdiniz : FireWood");
            }
            if (this.getLocID() == 5) {
                this.getPlayer().getInventory().Water = true;
                System.out.println("Tebrikler ödülü ele geçirdiniz : Water");
            }
            System.out.println("Tüm düşmanları yendiniz !");
            if (this.getLocID() == 6) {
                randomItem();
            }
            return true;
        }

        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("Öldünüz !");
            return false;
        }
        return true;
    }

    public boolean combat(int obsNumber) {
        for (int i = 1; i <= obsNumber; i++) {
            this.getObstacle().setHealth(this.getObstacle().getOriginalHealth());
            playerStats();
            obstacleStats(i);
            if (firstHit() == 1) {
                playerHit();
            } else if (firstHit() == 0) {
                obstacleHit();
            }
            while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                playerHit();
                obstacleHit();
            }

            if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                System.out.println("Düşmanı yendiniz !");
                System.out.println(this.getObstacle().getAward() + " para kazandınız !");
                this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getAward());
                System.out.println("Güncel Paranız : " + this.getPlayer().getMoney());
            } else {
                return false;
            }

        }
        return true;
    }

    public void randomItem() {
        double random = Math.random() * 100;
        double secondRandom = Math.random() * 100;
        if (random < 15) {
            if (secondRandom < 20) {
                getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(3));
                System.out.println("Tüfek Kazandınız !");
            } else if (secondRandom < 50) {
                getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(2));
                System.out.println("Kılıç Kazandınız !");
            } else {
                getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(1));
                System.out.println("Tabanca Kazandınız !");
            }
        } else if (random < 30) {
            if (secondRandom < 20) {
                getPlayer().getInventory().setArmor(Armor.getArmorObjByID(3));
                System.out.println("Ağır Zırh Kazandınız !");
            } else if (secondRandom < 50) {
                getPlayer().getInventory().setArmor(Armor.getArmorObjByID(2));
                System.out.println("Orta Zırh Kazandınız !");
            } else {
                getPlayer().getInventory().setArmor(Armor.getArmorObjByID(1));
                System.out.println("Hafif Zırh Kazandınız !");
            }
        } else if (random < 55) {
            if (secondRandom < 20) {
                getPlayer().setMoney(getPlayer().getMoney() + 10);
            } else if (secondRandom < 50) {
                getPlayer().setMoney(getPlayer().getMoney() + 5);
            } else {
                getPlayer().setMoney(getPlayer().getMoney() + 1);
            }
        } else {
            System.out.println("Hiç item kazanamadınız !");
        }
    }

    public boolean playerHit() {
        System.out.println("<V>ur veya <K>aç");
        String selectCombat = input.nextLine().toUpperCase();
        if (selectCombat.equals("V")) {
            System.out.println("Siz vurdunuz !");
            this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
            afterHit();
        } else {
            return false;
        }
        return true;
    }

    public void obstacleHit() {
        if (this.getObstacle().getHealth() > 0) {
            System.out.println();
            System.out.println("Canavar size vurdu !");
            int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getAvoid();
            if (obstacleDamage < 0) {
                obstacleDamage = 0;
            }
            this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
            afterHit();
        }
    }

    public int firstHit() {
        Random r = new Random();
        return r.nextInt(2);
    }

    public void afterHit() {
        System.out.println("Canınız : " + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName() + " Canı : " + this.getObstacle().getHealth());
        System.out.println("--------");

    }

    public void playerStats() {
        System.out.println("Oyuncu Değerleri");
        System.out.println("------------------------------");
        System.out.println("Sağlık : " + this.getPlayer().getHealth());
        System.out.println("Hasar : " + this.getPlayer().getTotalDamage());
        System.out.println("Silah : " + this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Zırh : " + this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Kaçınma : " + this.getPlayer().getInventory().getArmor().getAvoid());
        System.out.println("Para : " + this.getPlayer().getMoney());
        System.out.println();

    }

    public void obstacleStats(int i) {
        System.out.println(i + "." + this.getObstacle().getName() + " Değerleri");
        System.out.println("------------------------------");
        System.out.println("Sağlık : " + this.getObstacle().getHealth());
        System.out.println("Hasar : " + this.getObstacle().getDamage());
        System.out.println("Ödül : " + this.getObstacle().getAward());
    }

    public int randomObstacleNumber() {
        Random r = new Random();
        return r.nextInt(this.getMaxObstacle()) + 1;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxObstacle() {
        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }


}
