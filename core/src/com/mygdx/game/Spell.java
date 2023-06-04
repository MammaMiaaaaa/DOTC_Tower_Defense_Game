package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spell extends DataHandling{
    float manaCost = Float.parseFloat(getdata(17,2));
    float damage = Float.parseFloat(getdata(16,2));
    float maxDuration;
    float duration;
    Texture circleAOE;
    float stateTime;
    float maxCooldown = Float.parseFloat(getdata(18,2));
    float cooldown = maxCooldown;
    BitmapFontCache font;

    enum State{
        ACTIVE,
        INACTIVE,
        PREPARE
    }
    Spell.State state = Spell.State.PREPARE;

    Spell(){
        this.InitializeAnimation();
    }
    public void InitializeAnimation() {
        font = new BitmapFontCache(MyGdxGame.font2);
    }
    public void draw(SpriteBatch batch){

    }

    public void drawAOE(SpriteBatch batch, float x, float y){
        if (state == State.PREPARE)
            batch.draw(circleAOE,x,y);
    }
    public void update(){
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
    }
    public void drawStatus(SpriteBatch batch,int x, int y){
        if (cooldown<=0){
            font.setText("R", x,y);
            font.setColor(Color.BLUE);

        }
        else {
            font.setText("CD",x-12,y);
            font.setColor(Color.RED);
        }
//        if (cooldown<=0){
//            font.setText(String.valueOf(cooldown), x,y);
//            font.setColor(Color.BLUE);
//
//        }
//        else {
//            font.setText(String.valueOf(cooldown),x-12,y);
//            font.setColor(Color.RED);
//        }
        font.draw(batch);
    }

}
