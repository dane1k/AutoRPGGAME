package com.company;

import java.util.Random;

public class Main {

    public static int[] heroesHealth = {270, 280, 260, 300, 500, 200}; //воин, маг и кинетик and medic
    public static int[] heroesDamage = {20, 15, 25, 0, 10, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Heal", "Golem", "Lucky"};
    //                                                                                       ^ ЧМО ЕБАННОЕ
    public static int bossHealth = 700;
    public static int bossDamage = 50;

    public static boolean golemDamage = true;
    public static String bossDefenceType = "";
    public static int roundNumber = 0;

    public static void chooseBossDefenceType() {
        Random random = new Random();
        int randNum = random.nextInt(heroesAttackType.length);  // 0 1 2
        bossDefenceType = heroesAttackType[randNum];
        System.out.println("Boss choose: " + bossDefenceType);
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefenceType) {
                    Random r = new Random();
                    int coefficient = r.nextInt(8) + 2; // 0 1 2 3 4 5 6 7
                    if (bossHealth < heroesDamage[i] * coefficient) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coefficient;
                        if (heroesHealth[3] > 0) {
                            if (heroesHealth[i] < 100) {
                                for (int d = 0; d < 1; d++) {
                                    heroesHealth[i] = heroesHealth[i] + 50;
                                    System.out.println(heroesAttackType[i] + " was healed from HEAL MAN (on 50HP)");
                                }
                            }
                        }
                    }
                    System.out.println("Critical Damage: " + heroesDamage[i] * coefficient);
                } else {
                    if (bossHealth < heroesDamage[i]) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void golemMinusDamage(){
        if(heroesHealth[4] > 0){
            golemDamage = true;
            for (int i = 0; i < 1; i++) {
                heroesHealth[i] = heroesHealth[i] + 10;
            }
        } else{
            golemDamage = false;
        }

    }
    public static void bossHits() {
        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] < bossDamage) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                    golemMinusDamage();
                }
            }

        }
    }

    public static void main(String[] args) {
        printDStatistics();
        isGameFinished();
        while (!isGameFinished()){
            round();
        }

    }
    public static Boolean isGameFinished(){
        if(bossHealth <= 0){
            System.out.println("Heroes won!");
            return true;
        }
//        if(heroesHealth[0] <= 0 && heroesHealth[1] <=0 && heroesHealth[2] <=0){
//            System.out.println("Boss won!");
//            return true;
//        }
//        return false;
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if(heroesHealth[i]>0){
                allHeroesDead = false;
                break;    //найдя живого героя остальных можем не проверять
            }
        }

        if (allHeroesDead){
            System.out.println("BOSS WON!");
        }
        return allHeroesDead;
    }

    private static void round() {
        roundNumber++;
        chooseBossDefenceType();
        bossHits();
        heroesHit();
        printDStatistics();
    }


    public static void printDStatistics() {
        System.out.println(roundNumber+" ROUND **************");
        System.out.println("Boss health: "+ bossHealth + " ["+ bossDamage+ "]" );
        for (int i = 0; i < heroesHealth.length ; i++) {
            System.out.println(heroesAttackType[i]+" health: "+ heroesHealth[i]+" ["+ heroesDamage[i]+ "]" );
        }
        System.out.println();
    }


}
