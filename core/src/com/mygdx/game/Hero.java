package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Hero extends DataHandling{
    enum State
    {
        ATTACK,
        IDLE,
        DYING,
        DEATH
    }

    int damage = Integer.parseInt(getdata(14,2));
    float attackCooldownAwal = Float.parseFloat(getdata(15,2));
    float attackCooldown = attackCooldownAwal;

    float stateTime;
    float X = -150, Y = 300;
    Arrow arrow;
    ArrayList<Arrow> listArrow = new ArrayList<>();

    Hero(){
        this.InitializeAnimation();
    }

    Animation<TextureRegion> heroAttackingAnimation,heroIdlingAnimation,heroDyingAnimation;
    Hero.State state = State.IDLE;
    public void InitializeAnimation() {
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = parentGame.getAssetManager();


        Texture heroAttacking = assetManager.get("HeroAttacking.png", Texture.class);
        Texture heroIdling = assetManager.get("HeroIdling.png", Texture.class);
        Texture heroDying = assetManager.get("HeroDying.png", Texture.class);


        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(heroAttacking, 500, 500, 10, false, false);
        heroAttackingAnimation = new Animation<TextureRegion>(0.07f, frames);

        frames = MyGdxGame.CreateAnimationFrames(heroIdling, 500, 500, 10, false, false);
        heroIdlingAnimation = new Animation<TextureRegion>(0.07f, frames);

        frames = MyGdxGame.CreateAnimationFrames(heroDying, 500, 500, 10, false, false);
        heroDyingAnimation = new Animation<TextureRegion>(0.07f, frames);
    }
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;
        if(state == Hero.State.IDLE ){
            currentFrame = heroIdlingAnimation.getKeyFrame(stateTime, true);
        }
        else if(state == Hero.State.ATTACK)
            currentFrame = heroAttackingAnimation.getKeyFrame(stateTime, true);
        else if(state == Hero.State.DYING)
            currentFrame = heroDyingAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame,X ,Y );
    }
    public void update()
    {

        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        attackCooldown -= delta;

    }
    public void Attack(){
        arrow = new Arrow();
        listArrow.add(arrow);
        attackCooldown = attackCooldownAwal;
        arrow.damage = damage;
//        Vector2 position = new Vector2(screenX, screenY);
//        position = viewport.unproject(position);
//        if (position.y >= 700) {
//            position.y = 700;
//        } else if (position.y <= 200) {
//            position.y = 200;
//        }
//        arrow.Y = position.y - 80;

    }
}
