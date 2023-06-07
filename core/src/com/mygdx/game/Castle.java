package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.util.DataHandling;

public class Castle extends DataHandling {
    protected float maxHP;
    protected float HP;
    protected float maxMana;
    protected float mana;


    //buat ngasih tanda condition sekarang dari castlenya
    enum Condition
    {
        FULL,
        HALF,
        DESTROYED
    }
    public Castle(){
        maxHP = Float.parseFloat(getData(19));
        HP = maxHP;
        mana = 100;
        maxMana = 100;
        condition = Condition.FULL;
    }
    MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

    AssetManager assetManager = parentGame.getAssetManager();
    float X = -70, Y = -120;
    Castle.Condition condition = Castle.Condition.FULL;

    //hp berkurang jika musuh berhasil menyerang
    public void takeDamage(Enemy e){
        HP -= e.getDamage();
        e.setDamageGiven(e.getDamageGiven() + e.getDamage());
    }

    //cek castle condition dan regen mana
    public void update(){
//        HP = HP - Gdx.graphics.getDeltaTime()*10;
        float delta = Gdx.graphics.getDeltaTime();
        if(HP <= (maxHP/2) && HP > 0){
            condition = Castle.Condition.HALF;
        }
        else if(HP <= 0){
            condition = Castle.Condition.DESTROYED;
        }
        if (mana < maxMana){
            mana += delta;
        }
        if (mana > maxMana)
            mana = maxMana;
    }
    Texture fullCastle = assetManager.get("FullCastle.png",Texture.class);
    Texture halfCastle = assetManager.get("HalfCastle.png",Texture.class);
    Texture destroyedCastle = assetManager.get("DestroyedCastle.png",Texture.class);

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getHP() {
        return HP;
    }

    public void setHP(float HP) {
        this.HP = HP;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
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

    public MyGdxGame getParentGame() {
        return parentGame;
    }

    public void setParentGame(MyGdxGame parentGame) {
        this.parentGame = parentGame;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Texture getFullCastle() {
        return fullCastle;
    }

    public void setFullCastle(Texture fullCastle) {
        this.fullCastle = fullCastle;
    }

    public Texture getHalfCastle() {
        return halfCastle;
    }

    public void setHalfCastle(Texture halfCastle) {
        this.halfCastle = halfCastle;
    }

    public Texture getDestroyedCastle() {
        return destroyedCastle;
    }

    public void setDestroyedCastle(Texture destroyedCastle) {
        this.destroyedCastle = destroyedCastle;
    }

    //draw castle sesuai condition
    public void draw(SpriteBatch batch)
    {
        Texture currentFrame = null;
        if(condition == Castle.Condition.FULL)
            currentFrame = fullCastle;
        else if (condition == Castle.Condition.HALF)
            currentFrame = halfCastle;
        else if (condition == Castle.Condition.DESTROYED)
            currentFrame = destroyedCastle;
        batch.draw(currentFrame,X ,Y );

    }
}
