package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Arrow;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

import static com.mygdx.game.util.DataHandling.getData;

public class Hero  {
    public enum State
    {
        ATTACK,
        IDLE,
        DYING,
        DEATH
    }

    protected float damage = Float.parseFloat(getData(14));
    protected float attackCooldownAwal = Float.parseFloat(getData(15));
    protected float attackCooldown = attackCooldownAwal;

    protected float stateTime;
    protected float X = -150, Y = 300;
    protected Arrow arrow;
    protected ArrayList<Arrow> listArrow = new ArrayList<>();

    public Hero(){
        this.InitializeAnimation();
    }

    Animation<TextureRegion> heroAttackingAnimation,heroIdlingAnimation,heroDyingAnimation;
    protected State state = State.IDLE;
    public void InitializeAnimation() {
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = parentGame.getAssetManager();


        Texture heroAttacking = assetManager.get("HeroAttacking.png", Texture.class);
        Texture heroIdling = assetManager.get("HeroIdling.png", Texture.class);
        Texture heroDying = assetManager.get("HeroDying.png", Texture.class);


        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(heroAttacking, 500, 500, 10, false, false);
        heroAttackingAnimation = new Animation<>(0.07f, frames);

        frames = MyGdxGame.CreateAnimationFrames(heroIdling, 500, 500, 10, false, false);
        heroIdlingAnimation = new Animation<>(0.07f, frames);

        frames = MyGdxGame.CreateAnimationFrames(heroDying, 500, 500, 10, false, false);
        heroDyingAnimation = new Animation<>(0.07f, frames);
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

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getAttackCooldownAwal() {
        return attackCooldownAwal;
    }

    public void setAttackCooldownAwal(float attackCooldownAwal) {
        this.attackCooldownAwal = attackCooldownAwal;
    }

    public float getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(float attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
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

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    public ArrayList<Arrow> getListArrow() {
        return listArrow;
    }

    public void setListArrow(ArrayList<Arrow> listArrow) {
        this.listArrow = listArrow;
    }

    public Animation<TextureRegion> getHeroAttackingAnimation() {
        return heroAttackingAnimation;
    }

    public void setHeroAttackingAnimation(Animation<TextureRegion> heroAttackingAnimation) {
        this.heroAttackingAnimation = heroAttackingAnimation;
    }

    public Animation<TextureRegion> getHeroIdlingAnimation() {
        return heroIdlingAnimation;
    }

    public void setHeroIdlingAnimation(Animation<TextureRegion> heroIdlingAnimation) {
        this.heroIdlingAnimation = heroIdlingAnimation;
    }

    public Animation<TextureRegion> getHeroDyingAnimation() {
        return heroDyingAnimation;
    }

    public void setHeroDyingAnimation(Animation<TextureRegion> heroDyingAnimation) {
        this.heroDyingAnimation = heroDyingAnimation;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void Attack(){
        arrow = new Arrow();
        listArrow.add(arrow);

        attackCooldown = attackCooldownAwal;
        arrow.setDamage(damage);
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
