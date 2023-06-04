package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Goblin extends Orc{
    int bonusSpeed = 100;
    int maxSpeed = 175;
    Animation<TextureRegion> goblinRunningAnimation,goblinAtttackingAnimation,goblinDyingAnimation,goblinIdlingAnimation,frozenGoblinAnimation;

    public Goblin() {
        super();
        Speed = 125;
        HP = 100;
        maxHP = 100;
        damage = 7;
        attackCooldown = 3;
        enemyType = Type.Goblin;
    }

    @Override
    public void InitializeAnimation() {
        super.InitializeAnimation();
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

        AssetManager assetManager = parentGame.getAssetManager();


        Texture goblinRunning = assetManager.get("GoblinRunning.png", Texture.class);
        Texture goblinAttacking = assetManager.get("GoblinAttacking.png", Texture.class);
        Texture goblinDying = assetManager.get("GoblinDying.png", Texture.class);
        Texture goblinIdling = assetManager.get("GoblinIdling.png", Texture.class);
        Texture frozenGoblin = assetManager.get("FrozenGoblin.png",Texture.class);

        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(goblinRunning, 300, 300, 12, true, false);
        goblinRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(goblinAttacking, 300, 300, 12, true, false);
        goblinAtttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(goblinDying, 300, 300, 15, true, false);
        goblinDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(goblinIdling, 300, 300, 18, true, false);
        orcIdlingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(frozenGoblin, 300, 300, 1, true, false);
        frozenGoblinAnimation = new Animation<>(0.05f, frames);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;
        if(state == Enemy.State.RUN)
            currentFrame = goblinRunningAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.ATTACK)
            currentFrame = goblinAtttackingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.DYING)
            currentFrame = goblinDyingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.IDLE)
            currentFrame = goblinIdlingAnimation.getKeyFrame(stateTime, true);
        else if(state == Enemy.State.FROZEN)
            currentFrame = frozenGoblinAnimation.getKeyFrame(stateTime,true);
        if (enemyLane == Goblin.Lane.ONE && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Goblin.Lane.TWO && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Goblin.Lane.THREE && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
        else if (enemyLane == Goblin.Lane.FOUR && state != State.DEATH){
            batch.draw(currentFrame,X ,Y );
        }
    }

    @Override
    public void update() {
        super.update();
        if(HP <= (maxHP/2)){
            if (Speed<maxSpeed){
                Speed += bonusSpeed;
//                System.out.println(speed);
//                System.out.println(DX);
//                System.out.println();
//                System.out.println(X);
            }
        }
    }
}
