package com.mygdx.game;

import java.util.ArrayList;

public class Stages {
    boolean stageSuccesses;
    int highScore;
    int score;
    int stage;
    int kill = 0;
    int life = 0;
    int bonusGold;
    int bonusDiamond;
    ArrayList<Enemy> listStageEnemy = new ArrayList<>();

    WaveEnemy w1;

    public Stages(){

    }
//    public void addEnemy(int jumlah, Enemy.Lane lane){
//
//        for (int i = 0; i < jumlah; i++){
//            Enemy e = new Enemy();
//            listStageEnemy.add(e);
//        }
//    }
    public void addOrc(float spawnTime,Enemy.Lane lane){
            Enemy e = new Orc();
            e.spawnTime = spawnTime;
            e.enemyLane = lane;
            listStageEnemy.add(e);
    }
    public void addOgre(float spawnTime,Enemy.Lane lane){
        Enemy e = new Ogre();
        e.spawnTime = spawnTime;
        e.enemyLane = lane;
        listStageEnemy.add(e);
    }
    public void addGoblin(float spawnTime,Enemy.Lane lane){
        Enemy e = new Goblin();
        e.spawnTime = spawnTime;
        e.enemyLane = lane;
        listStageEnemy.add(e);
    }
    public void setEnemyType(Enemy e, Enemy.Type enemyType){
        e.enemyType = enemyType;
    }

    public void addToArray(ArrayList<Enemy> listEnemy){
        for (Enemy e: listStageEnemy) {
            listEnemy.add(e);
        }
    }

    public void addWave(Enemy.Type type, int jumlah, float spawnTime, float spawnInterval,int lane){
        w1 = new WaveEnemy(type,jumlah,spawnTime,spawnInterval,lane);
        w1.addToArray(listStageEnemy);
    }
    public int calculateKill(ArrayList<Enemy> enemies){
        int hasil = 0;
        for (Enemy e:enemies
             ) {
            if (e.state == Enemy.State.DEATH){
                hasil++;
            }
        }
        return hasil;
    }
    public int getLife() {
        return life;
    }
}
