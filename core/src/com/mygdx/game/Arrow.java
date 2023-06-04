package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Arrow {
    int damage = 100;
    float X = 0, Y = 500, DX=1, DY=0, Speed=400;
    float stateTime;

    enum State{
        ACTIVE,
        INACTIVE
    }

    Arrow.State state = State.ACTIVE;

    Texture currentFrame;

    MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

    AssetManager assetManager = parentGame.getAssetManager();

    Texture arrow = assetManager.get("Arrow.png",Texture.class);

    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        X += DX * Speed * delta;

//        Y += DY * Speed * delta;
//        if (X > 2000 || Y > 1200){
//
//        }
        if(X >= 1850){
            state = State.INACTIVE;
        }

    }

    public void draw(SpriteBatch batch)
    {
        currentFrame = null;
        if (state == State.ACTIVE){
            currentFrame = arrow;
            batch.draw(currentFrame,X ,Y );
        }

    }
    public boolean CanAttack(Enemy e){
        if(e.getState() == Enemy.State.DEATH || e.getState() == Enemy.State.IDLE)
            return false;
        float radius = 100;

        float dx = (X + 60) - e.getX();
        float dy = (Y - 60) - e.getY();
        float d = dx*dx + dy*dy;
        return (Math.sqrt(d) <= radius);
    }
}
