package com.mygdx.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.util.DataHandling;
import com.mygdx.game.MyGdxGame;

public class Spell extends DataHandling {
    protected float manaCost = Float.parseFloat(getData(17));
    protected float damage = Float.parseFloat(getData(16));

    protected Texture circleAOE;
    protected float stateTime;
    protected float maxCooldown = Float.parseFloat(getData(18));
    protected float cooldown = maxCooldown;
    protected BitmapFontCache font;
    protected float maxDuration;
    public float duration;

    public enum State{
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
        cooldown -= delta;
    }

    public float getManaCost() {
        return manaCost;
    }

    public void setManaCost(float manaCost) {
        this.manaCost = manaCost;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }


    public Texture getCircleAOE() {
        return circleAOE;
    }

    public void setCircleAOE(Texture circleAOE) {
        this.circleAOE = circleAOE;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getMaxCooldown() {
        return maxCooldown;
    }

    public void setMaxCooldown(float maxCooldown) {
        this.maxCooldown = maxCooldown;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public BitmapFontCache getFont() {
        return font;
    }

    public void setFont(BitmapFontCache font) {
        this.font = font;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void drawStatus(SpriteBatch batch, int x, int y){
//        if (cooldown<=0){
//            font.setText("R", x,y);
//            font.setColor(Color.BLUE);
//
//        }
//        else {
//            font.setText("CD",x-12,y);
//            font.setColor(Color.RED);
//        }
        if (cooldown<=0){
            font.setText(String.valueOf(cooldown), x,y);
            font.setColor(Color.BLUE);

        }
        else {
            font.setText(String.valueOf(cooldown),x-12,y);
            font.setColor(Color.RED);
        }
        font.draw(batch);
    }

}
