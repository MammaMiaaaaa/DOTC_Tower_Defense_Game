package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Goblin;
import com.mygdx.game.sprites.Ogre;
import com.mygdx.game.sprites.Orc;

import java.util.ArrayList;

public class WaveEnemy {
    int waveType;
    SpriteBatch batch = new SpriteBatch();
    ArrayList<Enemy> listEnemyWave = new ArrayList<>();
    WaveEnemy(){

    }
    WaveEnemy(Enemy.Type type, int jumlah,float spawnTime, float spawnInterval, int lane){

        if (type == Enemy.Type.Orc){
            for (int i = 0; i< jumlah; i++){
                Stages.refreshInitialDNA();
                Orc e = new Orc(Stages.initialDNA, lane, spawnTime);
                listEnemyWave.add(e);
                e.setSpawnTime(spawnTime);
                spawnTime += spawnInterval;
            }
        } else if (type == Enemy.Type.Ogre) {
            for (int i = 0; i< jumlah; i++){
                Stages.refreshInitialDNA();
                Ogre e = new Ogre(Stages.initialDNA, lane, spawnTime);
                listEnemyWave.add(e);
                e.setSpawnTime(spawnTime);
                spawnTime += spawnInterval;
            }
        }else if (type == Enemy.Type.Goblin) {
            for (int i = 0; i< jumlah; i++){
                Stages.refreshInitialDNA();
                Enemy e = new Goblin(Stages.initialDNA, lane, spawnTime);
                listEnemyWave.add(e);
                e.setSpawnTime(spawnTime);
                spawnTime += spawnInterval;
            }
        }
        if (lane == 1){
            for(int i = 0; i < listEnemyWave.size(); i++){
                if (i == 0){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.ONE);
                }else if (i == 1){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.TWO);
                }else if (i == 2){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.THREE);
                }else if (i == 3){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.FOUR);
                }

            }
        } else if (lane == 2){
            for(int i = 0; i < listEnemyWave.size(); i++){
                if (i == 0){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.TWO);
                }else if (i == 1){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.THREE);
                }else if (i == 2) {
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.FOUR);
                }

            }
        }
        else if (lane == 3){
            for(int i = 0; i < listEnemyWave.size(); i++){
                 if (i == 0){
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.THREE);
                }else if (i == 1) {
                    listEnemyWave.get(i).setEnemyLane(Enemy.Lane.FOUR);
                }

            }
        }
    }
    public void drawWaveEnemy(float time){
        batch.begin();
        for (Enemy e: listEnemyWave) {
            if (e.getSpawnTime() >= time){
                e.draw(batch);
            }
        }
        batch.end();
    }
    public void update(){
        for (Enemy e : listEnemyWave) {
            e.update();
        }
    }
    public void addToArray(ArrayList<Enemy> listEnemy){
        listEnemy.addAll(listEnemyWave);
    }
}
