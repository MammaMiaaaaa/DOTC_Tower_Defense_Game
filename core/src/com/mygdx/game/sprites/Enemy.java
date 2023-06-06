package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Arrow;
import com.mygdx.game.Castle;
import com.mygdx.game.spell.Spell;

import java.util.Random;


public abstract class Enemy extends Hero {
    public enum Type
    {
        Orc,
        Ogre,
        Goblin
    }
    public enum State
    {
        DEATH,
        ATTACK,
        RUN,
        IDLE,
        DYING,
        FROZEN
    }
    public enum Lane
    {
        ONE,
        TWO,
        THREE,
        FOUR
    }

    protected int HP;
    protected int maxHP;

    protected int damage;
    protected float attackCooldown;
    protected float stateTime;
    protected float spawnTime;
    protected Random rdm = new Random();
    protected int x = rdm.nextInt(3);
    protected float X, DX, DY, Speed;
    protected float Y;

    protected float physicalResistance, magicalResistance;
    protected float[] dna = new float[5];
    protected float fitness = 0;

    public State state = Enemy.State.RUN;
    Type enemyType = Enemy.Type.Orc;

    protected

    Lane enemyLane = Enemy.Lane.ONE;

    public Enemy()
    {
        this.InitializeAnimation();

        // initialize default value
        X = 2150;
        DX = 0;
        DY = 0;

        // initialize default dna
        // maxHP
        dna[0] = 100;
        // speed
        dna[1] = 100;
        // damage
        dna[2] = 7;
        // physical resistance
        dna[3] = 1;
        // magical resistance
        dna[4] = 1;

        // set according to DNA
        maxHP = (int)dna[0];
        Speed = dna[1];
        damage = (int)dna[2];
        physicalResistance = dna[3];
        magicalResistance = dna[4];

    }

    public Enemy(float[] dna){
        this();
        this.dna = dna;
    }


    public abstract void InitializeAnimation();
    public abstract void draw(SpriteBatch batch);
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
            c.setHP(-damage);
        }
    }
    public void Attacked(Arrow a){
        if(state != State.DEATH) {
            HP -= a.getDamage();
            if (HP <= 0){
                state = State.DEATH;
            }

//            sound.play();
        }
    }
    public void Attacked(Spell s){
        if(state != State.DEATH) {
            HP -= s.getDamage();
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public float getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    public Random getRdm() {
        return rdm;
    }

    public void setRdm(Random rdm) {
        this.rdm = rdm;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getDX() {
        return DX;
    }

    public void setDX(float DX) {
        this.DX = DX;
    }

    public float getDY() {
        return DY;
    }

    public void setDY(float DY) {
        this.DY = DY;
    }

    public float getSpeed() {
        return Speed;
    }

    public void setSpeed(float speed) {
        Speed = speed;
    }



    public Type getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(Type enemyType) {
        this.enemyType = enemyType;
    }

    public Lane getEnemyLane() {
        return enemyLane;
    }

    public float getPhysicalResistance() {
        return physicalResistance;
    }

    public void setPhysicalResistance(float physicalResistance) {
        this.physicalResistance = physicalResistance;
    }

    public float getMagicalResistance() {
        return magicalResistance;
    }

    public void setMagicalResistance(float magicalResistance) {
        this.magicalResistance = magicalResistance;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public float[] getDna() {
        return dna;
    }

    public void setDna(float[] dna) {
        this.dna = dna;
    }

    public void setEnemyLane(Lane enemyLane) {
        this.enemyLane = enemyLane;
    }
}
