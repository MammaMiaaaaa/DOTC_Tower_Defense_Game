package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Earthquake extends Spell {
    Animation<TextureRegion> fireballAnimation;
    Texture circleAOE;

    Earthquake() {
        super();
        state = State.INACTIVE;
    }

    @Override
    public void InitializeAnimation() {
        super.InitializeAnimation();
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

        AssetManager assetManager = parentGame.getAssetManager();

        circleAOE = assetManager.get("circleAOE.png", Texture.class);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void drawAOE(SpriteBatch batch, float x, float y) {
//            batch.draw(circleAOE,x,y);
        if (state == State.PREPARE)
            batch.draw(circleAOE, x, y);


    }
}