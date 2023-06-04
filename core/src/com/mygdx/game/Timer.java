package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Timer {
    float second;
    float minute;
    BitmapFontCache fontCache;

    Timer(){
        second = 0;
        minute = 0;
        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText(String.format("%f + %f", minute,second), 1500, 1025);

    }
    public void draw(SpriteBatch batch){
        fontCache.draw(batch);
    }
    public void update(){
        float delta = Gdx.graphics.getDeltaTime();
        second += delta;
        if (second >= 60){
            minute += 1;
            second = 0;
        }
        fontCache.setText(String.format("%02d:%02d", (int)minute,(int)second), 1500, 1025);
    }

}
