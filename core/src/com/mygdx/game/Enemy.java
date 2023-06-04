package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;


public class Enemy extends Hero{
    enum Type
    {
        Orc,
        Ogre,
        Goblin
    }
    enum State
    {
        DEATH,
        ATTACK,
        RUN,
        IDLE,
        DYING,
        FROZEN
    }
    enum Lane
    {
        ONE,
        TWO,
        THREE,
        FOUR
    }
    int HP;
    int maxHP;

    int damage;
    float attackCooldown;
    float stateTime;
    float spawnTime;
    Random rdm = new Random();
    int x = rdm.nextInt(3);
    float X = 2150, DX=0, DY=0, Speed=100;
    float Y;

    Enemy.State state = Enemy.State.RUN;
    Enemy.Type enemyType = Enemy.Type.Orc;
    Enemy.Lane enemyLane = Enemy.Lane.ONE;

    public Enemy()
    {
        this.InitializeAnimation();


    }



    public void InitializeAnimation() {
          }
    public void draw(SpriteBatch batch){    }
    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        if (state == State.FROZEN){
            DX = 0;
        }
        X += DX * Speed * delta;
        attackCooldown -= delta;
        if(X < 350 )
        {
            X = 350;
            if (state != State.DEATH){
                state = Enemy.State.ATTACK;
            }


        }
        if (state == State.DEATH){
            DX = 0;
        }
        if (HP <= 0){
            state = State.DEATH;
        }

//        Y += DY * Speed * delta;

        if(state == State.DYING && stateTime > 3f)
        {
            state = State.DEATH;
        }
        if (enemyLane == Lane.ONE){
            Y = 490;
        }
        else if (enemyLane == Lane.TWO){
            Y = 340;
        }
        else if (enemyLane == Lane.THREE){
            Y = 190;
        }
        else if (enemyLane == Lane.FOUR){
            Y = 40;
        }

//        else if (state == State.RUN){
//            DX = -1;
//        }
    }
    public void Attack(Castle c){
        if (state == State.ATTACK){
            c.HP =- damage;
        }
    }
    public void Attacked(Arrow a){
        if(state != State.DEATH) {
            HP -= a.damage;
            if (HP <= 0){
                state = State.DEATH;
            }

//            sound.play();
        }
    }
    public void Attacked(Spell s){
        if(state != State.DEATH) {
            HP -= s.damage;
            if (HP <= 0){
                state = State.DEATH;
            }

//            sound.play();
        }
    }
    public boolean CanAttack(){
        return attackCooldown <= 0 && state == State.ATTACK;
    }


    void Stop()
    {
        DX = 0;
        DY = 0;
    }
    public boolean isFrozen(float duration){

        return duration >= 0;
    }

    public Enemy.State getState() {
        return state;
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



}
