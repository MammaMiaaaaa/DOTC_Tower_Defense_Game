package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class Orc extends Enemy {

    public Orc() {
        super();
        HP = 100;
        enemyType = Type.Orc;
    }
    public Orc(float[] dna, int lane, float spawnTime){
        this();
        this.spawnTime = spawnTime;
        this.enemyLane = Enemy.Lane.values()[lane];
        this.dna = dna;
    }

    Animation<TextureRegion> orcRunningAnimation,orcAttackingAnimation,orcDyingAnimation,orcIdlingAnimation,frozenOrcAnimation,magicOrcRunningAnimation,magicOrcAttackingAnimation,magicOrcDyingAnimation,magicOrcIdlingAnimation;
    @Override
    public void InitializeAnimation() {
        damage = 5;
        attackCooldown = 3;
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

        AssetManager assetManager = parentGame.getAssetManager();


        Texture orcRunning = assetManager.get("OrcRunning.png", Texture.class);
        Texture orcAttacking = assetManager.get("OrcAttacking.png", Texture.class);
        Texture orcDying = assetManager.get("OrcDying.png", Texture.class);
        Texture orcIdling = assetManager.get("OrcIdling.png",Texture.class);
        Texture frozenOrc = assetManager.get("FrozenOrc.png",Texture.class);
        Texture magicOrcRunning = assetManager.get("MagicOrcRunning.png", Texture.class);
        Texture magicOrcAttacking = assetManager.get("MagicOrcAttacking.png", Texture.class);
        Texture magicOrcDying = assetManager.get("MagicOrcDying.png", Texture.class);
        Texture magicOrcIdling = assetManager.get("MagicOrcIdling.png",Texture.class);

        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(orcRunning, 300, 300, 12, true, false);
        orcRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(orcAttacking, 300, 300, 12, true, false);
        orcAttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(orcDying, 300, 300, 15, true, false);
        orcDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(orcIdling, 300, 300, 18, true, false);
        orcIdlingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(frozenOrc, 300, 300, 1, true, false);
        frozenOrcAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOrcRunning, 300, 300, 12, true, false);
        magicOrcRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOrcAttacking, 300, 300, 12, true, false);
        magicOrcAttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOrcDying, 300, 300, 15, true, false);
        magicOrcDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicOrcIdling, 300, 300, 18, true, false);
        magicOrcIdlingAnimation = new Animation<>(0.05f, frames);
    }

    @Override
    public void draw(SpriteBatch batch) {

        TextureRegion currentFrame = null;
        //select orc type
        if(dna[3]>= dna[4]){
            if(state == Enemy.State.RUN)// select frame from each state
                currentFrame = orcRunningAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.ATTACK)
                currentFrame = orcAttackingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.DYING)
                currentFrame = orcDyingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.IDLE)
                currentFrame = orcIdlingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.FROZEN)
                currentFrame = frozenOrcAnimation.getKeyFrame(stateTime, true);
        }
        else{
            if(state == Enemy.State.RUN)// select frame from each state
                currentFrame = magicOrcRunningAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.ATTACK)
                currentFrame = magicOrcAttackingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.DYING)
                currentFrame = magicOrcDyingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.IDLE)
                currentFrame = magicOrcIdlingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.FROZEN)
                currentFrame = frozenOrcAnimation.getKeyFrame(stateTime, true);
        }

        // select lane to draw enemy
        if (enemyLane == Orc.Lane.ONE && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Orc.Lane.TWO && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Orc.Lane.THREE && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Orc.Lane.FOUR && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
    }

    @Override
    public void update() {
        super.update();
    }
}
