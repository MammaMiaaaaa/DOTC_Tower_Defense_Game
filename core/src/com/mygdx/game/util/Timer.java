package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

public class Timer {

    protected float second;
    protected float minute;
    protected BitmapFontCache fontCache;

    public Timer(){
        second = 0;
        minute = 0;
        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText(String.format("%f + %f", minute,second), 1500, 1025);

    }
    public void draw(SpriteBatch batch){
        fontCache.draw(batch);
    }

    public float getSecond() {
        return second;
    }

    public void setSecond(float second) {
        this.second = second;
    }

    public float getMinute() {
        return minute;
    }

    public void setMinute(float minute) {
        this.minute = minute;
    }

    public BitmapFontCache getFontCache() {
        return fontCache;
    }

    public void setFontCache(BitmapFontCache fontCache) {
        this.fontCache = fontCache;
    }

    public void update(){
        float delta = Gdx.graphics.getDeltaTime();
        System.out.println(second);
        second += delta;
        if (second >= 60){
            minute += 1;
            second = 0;
        }
        fontCache.setText(String.format("%02d:%02d", (int)minute,(int)second), 1500, 1025);
    }

}
