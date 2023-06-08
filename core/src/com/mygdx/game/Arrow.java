package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Enemy;

public class Arrow {
    protected float damage = 100;
    protected float X = 0, Y = 500, DX=1, DY=0, Speed=400;
    protected float stateTime;
    protected float spawnTime;

    //state dari arrow
    public enum State{
        ACTIVE,
        INACTIVE
    }

    //state arrow active saat diinisialisasi
    Arrow.State state = State.ACTIVE;
    Texture currentFrame;

    MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

    AssetManager assetManager = parentGame.getAssetManager();

    Texture arrow = assetManager.get("Arrow.png",Texture.class);

    //update arrow biar bisa gerak sama jadi inactive kalo udah diluar map
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

    //buat draw arrow ke dalam batch
    public void draw(SpriteBatch batch)
    {
        currentFrame = null;
        if (state == State.ACTIVE){
            currentFrame = arrow;
            batch.draw(currentFrame,X ,Y );
        }

    }
    //cek arrow apakah mengenai enemy yang ada
    public boolean CanAttack(Enemy e){
        if(e.state == Enemy.State.DEATH || e.state == Enemy.State.IDLE)
            return false;

        float radius = 100;
        float dx = (X + 60) - e.getX();
        float dy = (Y - 60) - e.getY();
        float d = dx*dx + dy*dy;

        return (Math.sqrt(d) <= radius);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getDX() {
        return DX;
    }

    public void setDX(float DX) {
        this.DX = DX;
    }

    public float getDY() {
        return DY;
    }

    public void setDY(float DY) {
        this.DY = DY;
    }

    public float getSpeed() {
        return Speed;
    }

    public void setSpeed(float speed) {
        Speed = speed;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }
    public float getSpawnTime() {
        return spawnTime;
    }
}
