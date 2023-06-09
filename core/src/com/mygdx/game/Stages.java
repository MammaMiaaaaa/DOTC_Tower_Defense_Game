package com.mygdx.game;

import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Goblin;
import com.mygdx.game.sprites.Ogre;
import com.mygdx.game.sprites.Orc;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class Stages {
    protected boolean stageSuccesses;
    protected int highScore;
    protected int score;
    protected int stage;
    protected int kill = 0;
    protected int life = 0;
    protected int bonusGold;
    protected int bonusDiamond;
    protected ArrayList<Enemy> listStageEnemy = new ArrayList<>();
    public static List<Float> initialDNA;

    protected WaveEnemy w1;

    public Stages() {

    }
    public static void refreshInitialDNA(){
        initialDNA = new ArrayList<>();
        initialDNA.add((float) (100.0f * (random.nextFloat() * 0.4 + 0.8)));
        initialDNA.add((float) (100.0f * (random.nextFloat() * 0.4 + 0.8)));
        initialDNA.add((float) (10.0f * (random.nextFloat() * 0.4 + 0.8)));
        initialDNA.add((float) (1.0f * (random.nextFloat() * 0.4 + 0.8)));
        initialDNA.add((float) (1.0f * (random.nextFloat() * 0.4 + 0.8)));
    }

    public void addOrc(float spawnTime, Enemy.Lane lane) {
        refreshInitialDNA();

        Enemy e = new Orc(initialDNA, lane.ordinal(), spawnTime);
        e.setSpawnTime(spawnTime);
        e.setEnemyLane(lane);
        listStageEnemy.add(e);
    }

    public void addOgre(float spawnTime, Enemy.Lane lane) {
        refreshInitialDNA();

        Enemy e = new Ogre(initialDNA, lane.ordinal(), spawnTime);
        e.setSpawnTime(spawnTime);
        e.setEnemyLane(lane);
        listStageEnemy.add(e);
    }

    public void addGoblin(float spawnTime, Enemy.Lane lane) {
        refreshInitialDNA();

        Enemy e = new Goblin(initialDNA, lane.ordinal(), spawnTime);
        e.setSpawnTime(spawnTime);
        e.setEnemyLane(lane);
        listStageEnemy.add(e);
    }

    public void setEnemyType(Enemy e, Enemy.Type enemyType) {
        e.setEnemyType(enemyType);
    }

    public void addToArray(ArrayList<Enemy> listEnemy) {
        listEnemy.addAll(listStageEnemy);
    }

    public void addWave(Enemy.Type type, int jumlah, float spawnTime, float spawnInterval, int lane) {
        w1 = new WaveEnemy(type, jumlah, spawnTime, spawnInterval, lane);
        w1.addToArray(listStageEnemy);
    }

    public int calculateKill(ArrayList<Enemy> enemies) {
        int hasil = 0;
        for (Enemy e : enemies
        ) {
            if (e.state == Enemy.State.DEATH) {
                hasil++;
            }
        }
        return hasil;
    }

    public int getLife() {
        return life;
    }

    public boolean isStageSuccesses() {
        return stageSuccesses;
    }

    public void setStageSuccesses(boolean stageSuccesses) {
        this.stageSuccesses = stageSuccesses;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getBonusGold() {
        return bonusGold;
    }

    public void setBonusGold(int bonusGold) {
        this.bonusGold = bonusGold;
    }

    public int getBonusDiamond() {
        return bonusDiamond;
    }

    public void setBonusDiamond(int bonusDiamond) {
        this.bonusDiamond = bonusDiamond;
    }

    public ArrayList<Enemy> getListStageEnemy() {
        return listStageEnemy;
    }

    public void setListStageEnemy(ArrayList<Enemy> listStageEnemy) {
        this.listStageEnemy = listStageEnemy;
    }

    public WaveEnemy getW1() {
        return w1;
    }

    public void setW1(WaveEnemy w1) {
        this.w1 = w1;
    }
}
