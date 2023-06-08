package com.mygdx.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Arrow;
import com.mygdx.game.spell.Spell;

import java.util.ArrayList;

public class Arrows extends Spell {


    protected float interval = 0.5f;
    protected float spawnTime = 0;
    protected ArrayList<Arrow> listArrow = new ArrayList<>(); //list of arrow buat spell
    protected Arrow arrow;
    public Arrows() {
        super();
        duration = 3f;
    }

    @Override
    public void InitializeAnimation() {
        super.InitializeAnimation();

    }

    //function buat ngedraw semua arrow yang ada di list
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        
        if (state == State.ACTIVE){
            float delta = Gdx.graphics.getDeltaTime();
            for (Arrow a: listArrow) {
                // if(a.getSpawnTime() > delta){

                // }
                a.draw(batch);
            }
        }
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public float getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    public ArrayList<Arrow> getListArrow() {
        return listArrow;
    }

    public void setListArrow(ArrayList<Arrow> listArrow) {
        this.listArrow = listArrow;
    }

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    //function buat mengatur tempat arrow spawn di lane mana dan spawntimenya kapan
    @Override
    public void update() {
        super.update();

        if (state == State.ACTIVE){
        //     int j = 0;
        //     while(j < 4){
        //         if(interval < 0){
        //             for (int i = 0; i < 4; i++){
        //                 arrow = new Arrow();
        //                 arrow.setDamage((int) damage);
        //                 if (i == 0){
        //                     arrow.setY(540);
        //                 }
        //                 else if (i== 1){
        //                     arrow.setY(390);
        //                 }
        //                 else if (i== 2){
        //                     arrow.setY(240);
        //                 }
        //                 else {
        //                     arrow.setY(90);
        //                 }
        //                 arrow.setSpawnTime(j);
        //                 listArrow.add(arrow);
        //             }
        //             j++;
        //         }
            while (duration > 0){

                duration -= Gdx.graphics.getDeltaTime();
                if (duration<= spawnTime){
                    for (int i = 0; i < 4; i++){
                        arrow = new Arrow();
                        arrow.setDamage((int) damage);
                        if (i == 0){
                            arrow.setY(540);
                        }
                        else if (i== 1){
                            arrow.setY(390);
                        }
                        else if (i== 2){
                            arrow.setY(240);
                        }
                        else {
                            arrow.setY(90);
                        }
                        listArrow.add(arrow);
                    }
                    spawnTime = spawnTime -  interval;
                }
            }
        }
        //     state = Arrows.State.INACTIVE;
        else if (state == State.INACTIVE){
//            duration = maxDuration;
        }
    }

    @Override
    public void drawStatus(SpriteBatch batch, int x, int y) {
        super.drawStatus(batch, x, y);
    }
}
