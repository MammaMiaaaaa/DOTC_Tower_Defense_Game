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
    public Orc(float[] dna){
        this();
        this.dna = dna;
    }

    Animation<TextureRegion> orcRunningAnimation,orcAttackingAnimation,orcDyingAnimation,orcIdlingAnimation,frozenOrcAnimation;
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
    }

    @Override
    public void draw(SpriteBatch batch) {

        TextureRegion currentFrame = null;
        if(state == Enemy.State.RUN)
            currentFrame = orcRunningAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.ATTACK)
            currentFrame = orcAttackingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.DYING)
            currentFrame = orcDyingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.IDLE)
            currentFrame = orcIdlingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.FROZEN)
            currentFrame = frozenOrcAnimation.getKeyFrame(stateTime, true);
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
