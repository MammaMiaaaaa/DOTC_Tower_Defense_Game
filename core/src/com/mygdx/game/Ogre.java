package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ogre extends Orc {

    int HP_Regen = 10;
    float regenCD = 5;


    public Ogre() {
        super();
        Speed = 75;
        HP = 100;
        maxHP = 120;
        enemyType = Type.Ogre;


    }
    Animation<TextureRegion> ogreRunningAnimation,ogreAttackingAnimation,ogreDyingAnimation,ogreIdlingAnimation,ogreFrozenAnimation;

    @Override
    public void InitializeAnimation() {
        super.InitializeAnimation();
        damage = 3;
        attackCooldown = 3;
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

        AssetManager assetManager = parentGame.getAssetManager();


        Texture ogreRunning = assetManager.get("OgreRunning.png", Texture.class);
        Texture ogreAttacking = assetManager.get("OgreAttacking.png", Texture.class);
        Texture ogreDying = assetManager.get("OgreDying.png", Texture.class);
        Texture ogreIdling = assetManager.get("OgreIdling.png", Texture.class);
        Texture frozenOgre = assetManager.get("FrozenOgre.png",Texture.class);

        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(ogreRunning, 300, 300, 12, true, false);
        ogreRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(ogreAttacking, 300, 300, 11, true, false);
        ogreAttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(ogreDying, 300, 300, 15, true, false);
        ogreDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(ogreIdling, 300, 300, 18, true, false);
        ogreIdlingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(frozenOgre, 300, 300, 1, true, false);
        ogreFrozenAnimation = new Animation<>(0.05f, frames);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;
        if(state == Enemy.State.RUN)
            currentFrame = ogreRunningAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.ATTACK)
            currentFrame = ogreAttackingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.DYING)
            currentFrame = ogreDyingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.IDLE)
            currentFrame = ogreIdlingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.FROZEN)
            currentFrame = ogreFrozenAnimation.getKeyFrame(stateTime, true);
        if (enemyLane == Ogre.Lane.ONE && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Ogre.Lane.TWO && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Ogre.Lane.THREE && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Ogre.Lane.FOUR && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }

    }

    @Override
    public void update() {
        super.update();

        regenCD -= Gdx.graphics.getDeltaTime();
        if(regenCD <= 0 && state != State.DEATH && HP <= maxHP)
        {
            HP += HP_Regen;
            regenCD = 5.0f;
        }

    }
}
