package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Arrows extends Spell{

    float interval = 0.5f;
    float spawnTime = duration;
    ArrayList<Arrow> listArrow = new ArrayList<>();
    Arrow arrow;
    Arrows() {
        super();

    }

    @Override
    public void InitializeAnimation() {
        duration = 1;
        maxDuration = 3;
        super.InitializeAnimation();

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (state == State.ACTIVE){
            for (Arrow a: listArrow) {

                a.draw(batch);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (state == State.ACTIVE){

            while (duration > 0){

                duration -= Gdx.graphics.getDeltaTime();
                if (duration<= spawnTime){
                    for (int i = 0; i < 4; i++){
                        arrow = new Arrow();
                        arrow.damage = (int) damage;
                        if (i == 0){
                            arrow.Y = 540;
                        }
                        else if (i== 1){
                            arrow.Y = 390;
                        }
                        else if (i== 2){
                            arrow.Y = 240;
                        }
                        else if (i== 3){
                            arrow.Y = 90;
                        }
                        listArrow.add(arrow);
                    }
                    spawnTime = spawnTime -  interval;
                }
            }

//            Enemy e = new Goblin();
//            listEnemyWave.add(e);
//            e.spawnTime = spawnTime;
//            spawnTime += spawnInterval;
        }
        else if (state == State.INACTIVE){
            duration = maxDuration;
        }


    }

    @Override
    public void drawStatus(SpriteBatch batch, int x, int y) {
        super.drawStatus(batch, x, y);
    }
}
