package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class Goblin extends Orc {
    int bonusSpeed = 100;
    int maxSpeed = 175;
    Animation<TextureRegion> goblinRunningAnimation,goblinAtttackingAnimation,goblinDyingAnimation,goblinIdlingAnimation,frozenGoblinAnimation,magicGoblinRunningAnimation,magicGoblinAtttackingAnimation,magicGoblinDyingAnimation,magicGoblinIdlingAnimation;

    public Goblin() {
        super();
        HP = 100;
        attackCooldown = 3;
        enemyType = Type.Goblin;
    }
    public Goblin(float[] dna, int lane, float spawnTime){
        this();
        this.spawnTime = spawnTime;
        this.enemyLane = Enemy.Lane.values()[lane];
        this.dna = dna;
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
        Texture magicGoblinRunning = assetManager.get("MagicGoblinRunning.png", Texture.class);
        Texture magicGoblinAttacking = assetManager.get("MagicGoblinAttacking.png", Texture.class);
        Texture magicGoblinDying = assetManager.get("MagicGoblinDying.png", Texture.class);
        Texture magicGoblinIdling = assetManager.get("MagicGoblinIdling.png", Texture.class);

        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(goblinRunning, 300, 300, 12, true, false);
        goblinRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(goblinAttacking, 300, 300, 12, true, false);
        goblinAtttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(goblinDying, 300, 300, 15, true, false);
        goblinDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(goblinIdling, 300, 300, 18, true, false);
        orcIdlingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicGoblinRunning, 300, 300, 12, true, false);
        magicGoblinRunningAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicGoblinAttacking, 300, 300, 12, true, false);
        magicGoblinAtttackingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicGoblinDying, 300, 300, 15, true, false);
        magicGoblinDyingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(magicGoblinIdling, 300, 300, 18, true, false);
        magicGoblinIdlingAnimation = new Animation<>(0.05f, frames);

        frames = MyGdxGame.CreateAnimationFrames(frozenGoblin, 300, 300, 1, true, false);
        frozenGoblinAnimation = new Animation<>(0.05f, frames);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;
        // select goblin type
        if(dna[3] >= dna[4]){
            if(state == Enemy.State.RUN)// select frame from each state
                currentFrame = goblinRunningAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.ATTACK)
                currentFrame = goblinAtttackingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.DYING)
                currentFrame = goblinDyingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.IDLE)
                currentFrame = goblinIdlingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.FROZEN)
                currentFrame = frozenGoblinAnimation.getKeyFrame(stateTime,true);
        }
        else{
            if(state == Enemy.State.RUN)// select frame from each state
                currentFrame = magicGoblinRunningAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.ATTACK)
                currentFrame = magicGoblinAtttackingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.DYING)
                currentFrame = magicGoblinDyingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.IDLE)
                currentFrame = magicGoblinIdlingAnimation.getKeyFrame(stateTime, true);
            else if(state == Enemy.State.FROZEN)
                currentFrame = frozenGoblinAnimation.getKeyFrame(stateTime,true);
        }

        // select lane to draw enemy
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
            }
        }
    }
}
