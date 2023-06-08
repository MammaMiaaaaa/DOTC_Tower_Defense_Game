package com.mygdx.game.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Arrow;
import com.mygdx.game.spell.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Arrows extends Spell {


    protected float interval = 0.5f;
    protected ArrayList<Arrow> listArrow = new ArrayList<>(); //list of arrow buat spell
    protected Arrow arrow;
    protected int arrowCount;
    protected int spellArrowCount = 5;
    protected float arrowSpellSpeed = 300;
    Random random;
    protected boolean durationStarted = false;

    public Arrows() {
        super();

        maxDuration = getMaxCooldown();
        duration = maxDuration;

        random = new Random();
    }

    @Override
    public void InitializeAnimation() {
        super.InitializeAnimation();

    }

    //function buat ngedraw semua arrow yang ada di list
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        if (state == State.ACTIVE) {
            for (Arrow a : listArrow) {
                a.draw(batch);
            }
        }
    }

    public float getArrowSpellSpeed() {
        return arrowSpellSpeed;
    }

    public void setArrowSpellSpeed(float arrowSpellSpeed) {
        this.arrowSpellSpeed = arrowSpellSpeed;
    }

    public ArrayList<Arrow> getListArrow() {
        return listArrow;
    }

    public void setListArrow(ArrayList<Arrow> listArrow) {
        this.listArrow = listArrow;
    }

    public boolean isDurationStarted() {
        return durationStarted;
    }

    public void setDurationStarted(boolean durationStarted) {
        this.durationStarted = durationStarted;
    }

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    // function buat mengatur tempat arrow spawn di lane mana dan spawntimenya kapan
    @Override
    public void update() {
        super.update();

        if (state == State.INACTIVE) return;
        maxDuration = getMaxCooldown();

        stateTime += Gdx.graphics.getDeltaTime();

        if (stateTime >= interval) {
            if (arrowCount > 0) {
                arrow = new Arrow();
                arrow.setDamage(damage);
                arrow.setSpeed(arrowSpellSpeed);

                // shoot arrow in Y range of 90 - 540
                arrow.setY(random.nextInt(450) + 90);

                listArrow.add(arrow);
                arrowCount--;
                stateTime = 0;
            }
        }

        if (durationStarted){
            duration -= Gdx.graphics.getDeltaTime();
        } else {
            duration = maxDuration;
        }

        if (duration <= 0){
            state = State.INACTIVE;
            durationStarted = false;
            duration = maxDuration;
        }




    }


    public int getArrowCount() {
        return arrowCount;
    }

    public int getSpellArrowCount() {
        return spellArrowCount;
    }

    public void setSpellArrowCount(int spellArrowCount) {
        this.spellArrowCount = spellArrowCount;
    }

    public void setArrowCount(int arrowCount) {
        this.arrowCount = arrowCount;
    }

    @Override
    public void drawStatus(SpriteBatch batch, int x, int y) {
        super.drawStatus(batch, x, y);
    }
}
