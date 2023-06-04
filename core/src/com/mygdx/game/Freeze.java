package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Freeze extends Spell {
    Animation<TextureRegion> fireballAnimation;
//    Texture circleAOE;

    Freeze() {
        super();
        state = State.INACTIVE;
        duration = 3f;
        damage = 0f;
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
        super.drawAOE(batch,x,y);
//            batch.draw(circleAOE,x,y);
//        if (state == State.PREPARE)
//            batch.draw(circleAOE, x, y);
    }
    public boolean CanAttack(Enemy e,float X, float Y){
        if(e.getState() == Enemy.State.DEATH)
            return false;
        float radius = 200;

        float dx = (X ) - e.getX();
        float dy = (Y ) - e.getY();
        float d = dx*dx + dy*dy;
        return (Math.sqrt(d) <= radius);
    }
    @Override
    public void update() {
        super.update();
        float delta = Gdx.graphics.getDeltaTime();
        cooldown -= delta;
        if (state == State.ACTIVE && duration > 0){
            duration -= delta;
//            System.out.println(duration);
        }
        else {
            state = State.INACTIVE;
            duration = 3f;
        }
    }

    @Override
    public void drawStatus(SpriteBatch batch, int x, int y) {
        super.drawStatus(batch, x, y);
    }
}
