package com.mygdx.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.spell.Spell;
import com.mygdx.game.sprites.Enemy;

public class Freeze extends Spell {
//    Texture circleAOE;

    public Freeze() {
        super();
        state = State.INACTIVE;
        duration = 3f;
        maxDuration = 3f;
        damage = 3f;
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
    public boolean CanAttack(Enemy e, float X, float Y){
        if(e.state == Enemy.State.DEATH)
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
//        cooldown -= delta;
        if (state == State.ACTIVE && duration > 0){
            duration -= delta;
//            System.out.println(duration);
        }
        else {
            state = State.INACTIVE;
            duration = maxDuration;
        }
    }

    @Override
    public void drawStatus(SpriteBatch batch, int x, int y) {
        super.drawStatus(batch, x, y);
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
    public float getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(float maxDuration) {
        this.maxDuration = maxDuration;
    }
}
