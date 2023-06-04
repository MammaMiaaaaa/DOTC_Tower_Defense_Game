package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Castle extends DataHandling {
    float maxHP;
    float HP;
    float maxMana;
    float mana;


    //buat ngasih tanda condition sekarang dari castlenya
    enum Condition
    {
        FULL,
        HALF,
        DESTROYED
    }
    Castle(){
        maxHP = Float.parseFloat(getdata(19));
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
        HP -= e.damage;
    }
    //cek castle condition
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
