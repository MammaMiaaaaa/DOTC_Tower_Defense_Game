package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
                Enemy e = new Orc();
                listEnemyWave.add(e);
                e.spawnTime = spawnTime;
                spawnTime += spawnInterval;
            }
        } else if (type == Enemy.Type.Ogre) {
            for (int i = 0; i< jumlah; i++){
                Enemy e = new Ogre();
                listEnemyWave.add(e);
                e.spawnTime = spawnTime;
                spawnTime += spawnInterval;
            }
        }else if (type == Enemy.Type.Goblin) {
            for (int i = 0; i< jumlah; i++){
                Enemy e = new Goblin();
                listEnemyWave.add(e);
                e.spawnTime = spawnTime;
                spawnTime += spawnInterval;
            }
        }
        if (lane == 1){
            for(int i = 0; i < listEnemyWave.size(); i++){
                if (i == 0){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.ONE;
                }else if (i == 1){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.TWO;
                }else if (i == 2){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.THREE;
                }else if (i == 3){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.FOUR;
                }

            }
        } else if (lane == 2){
            for(int i = 0; i < listEnemyWave.size(); i++){
                if (i == 0){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.TWO;
                }else if (i == 1){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.THREE;
                }else if (i == 2) {
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.FOUR;
                }

            }
        }
        else if (lane == 3){
            for(int i = 0; i < listEnemyWave.size(); i++){
                 if (i == 0){
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.THREE;
                }else if (i == 1) {
                    listEnemyWave.get(i).enemyLane = Enemy.Lane.FOUR;
                }

            }
        }
    }
    public void drawWaveEnemy(float time){
        batch.begin();
        for (Enemy e: listEnemyWave) {
            if (e.spawnTime >= time){
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
        for (Enemy e: listEnemyWave) {
            listEnemy.add(e);
        }
    }
}
