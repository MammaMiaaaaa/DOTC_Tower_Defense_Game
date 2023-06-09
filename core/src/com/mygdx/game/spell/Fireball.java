package com.mygdx.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.spell.Spell;
import com.mygdx.game.sprites.Enemy;

public class Fireball extends Spell {
    Animation<TextureRegion> fireballAnimation;
    private float totalDamage;
    protected BitmapFontCache fontTotalDamageGiven;
    
//    Texture circleAOE;
public Fireball() {
        super();
        state = State.INACTIVE;
        duration = 0.35f;
        maxDuration = 0.35f;
        totalDamage = 0;
        fontTotalDamageGiven = new BitmapFontCache(MyGdxGame.font30px);
    }

    @Override
    public void InitializeAnimation() {
        super.InitializeAnimation();
        MyGdxGame parentGame = (MyGdxGame) Gdx.app.getApplicationListener();

        AssetManager assetManager = parentGame.getAssetManager();

        circleAOE = assetManager.get("circleAOE.png", Texture.class);
        Texture fireBall = assetManager.get("FireBallAnimation.png",Texture.class);

        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(fireBall, 300, 300, 10, true, false);
        fireballAnimation = new Animation<>(0.05f, frames);

    }

    public void draw(SpriteBatch batch,float x,float y) {
        super.draw(batch);
        TextureRegion currentFrame = null;
        if (state == State.ACTIVE){
            currentFrame = fireballAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame,x,y);
        }
    }

    @Override
    public void drawAOE(SpriteBatch batch, float x, float y) {
        super.drawAOE(batch,x,y);

//        if (state == State.PREPARE)
//        batch.draw(circleAOE,x,y);
    }

    @Override
    public void update() {
        super.update();
       float delta = Gdx.graphics.getDeltaTime();
//        cooldown -= delta;
       if (state == State.ACTIVE && duration > 0){
           duration -= delta;
//            System.out.println(duration);
       }
       else {
           state = State.INACTIVE;
           duration = maxDuration;
       }
    }
    public boolean CanAttack(Enemy e, float X, float Y){
        if(e.state == Enemy.State.DEATH)
            return false;
        float radius = 200;

        float dx = (X ) - e.getX();
        float dy = (Y ) - e.getY();
        float d = dx*dx + dy*dy;
        return (Math.sqrt(d) <= radius);
    }

    @Override
    public void drawStatus(SpriteBatch batch, int x, int y) {
        super.drawStatus(batch, x, y);
    }
    public void drawTotalDamageGiven(SpriteBatch batch,float x, float y){


        fontTotalDamageGiven.setText("TOTAL DAMAGE : "+String.valueOf(totalDamage), x,y);
        fontTotalDamageGiven.setColor(Color.GOLD);


        fontTotalDamageGiven.draw(batch);
    }

    public float getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(float totalDamage) {
        this.totalDamage = totalDamage;
    }


}
