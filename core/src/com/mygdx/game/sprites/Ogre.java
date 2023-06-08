package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

import java.util.List;

public class Ogre extends Orc {

    int HP_Regen = 10;
    float regenCD = 5;



    public Ogre(List<Float> dna, int lane, float spawnTime) {
        super(dna, lane, spawnTime);
        enemyType = Type.Ogre;
    }
    Animation<TextureRegion> ogreRunningAnimation,ogreAttackingAnimation,ogreDyingAnimation,ogreIdlingAnimation,ogreFrozenAnimation,magicOgreRunningAnimation,magicOgreDyingAnimation,magicOgreIdlingAnimation,magicOgreAttackingAnimation;

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
        Texture magicOgreRunning = assetManager.get("MagicOgreRunning.png", Texture.class);
        Texture magicOgreAttacking = assetManager.get("MagicOgreAttacking.png", Texture.class);
        Texture magicOgreDying = assetManager.get("MagicOgreDying.png", Texture.class);
        Texture magicOgreIdling = assetManager.get("MagicOgreIdling.png", Texture.class);

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

        frames = MyGdxGame.CreateAnimationFrames(magicOgreRunning, 300, 300, 12, true, false);
        magicOgreRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOgreAttacking, 300, 300, 11, true, false);
        magicOgreAttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOgreDying, 300, 300, 15, true, false);
        magicOgreDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOgreIdling, 300, 300, 18, true, false);
        magicOgreIdlingAnimation = new Animation<>(0.05f, frames);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;
        // select ogre type
        if(dna.get(3) >= dna.get(4)){
            if(state == Enemy.State.RUN)// select frame from each state
                currentFrame = ogreRunningAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.ATTACK)
                currentFrame = ogreAttackingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.DYING)
                currentFrame = ogreDyingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.IDLE)
                currentFrame = ogreIdlingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.FROZEN)
                currentFrame = ogreFrozenAnimation.getKeyFrame(stateTime, true);
        }
        else{
            if(state == Enemy.State.RUN)// select frame from each state
                currentFrame = magicOgreRunningAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.ATTACK)
                currentFrame = magicOgreAttackingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.DYING)
                currentFrame = magicOgreDyingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.IDLE)
                currentFrame = ogreIdlingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.FROZEN)
                currentFrame = ogreFrozenAnimation.getKeyFrame(stateTime, true);
        }
        
        // select lane to draw enemy
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
