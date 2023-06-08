package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Arrow;
import com.mygdx.game.Castle;
import com.mygdx.game.screen.GameScreen;
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

    protected float HP;
    protected float maxHP;
    

    protected float damage;
    private float damageGiven;
    protected float attackCooldown;
    protected float stateTime;
    protected float spawnTime;
    protected Random rdm = new Random();
    protected int x = rdm.nextInt(3);
    protected float X, DX, DY, Speed;
    protected float Y;
    protected int goldDrop;

    protected float physicalResistance, magicalResistance;
    protected float[] dna = new float[5];
    protected float fitness = 0;


    private boolean isFitnessCalculated = false;
    private boolean isGoldDroped = false;

    public State state = Enemy.State.RUN;
    Type enemyType = Enemy.Type.Orc;

    protected Lane enemyLane = Enemy.Lane.ONE;

    public Enemy()
    {
        this.InitializeAnimation();

        // initialize default value
        X = 2150;
        DX = 0;
        DY = 0;
        goldDrop = 10000;

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
        maxHP = dna[0];
        Speed = dna[1];
        damage =  dna[2];
        physicalResistance = dna[3];
        magicalResistance = dna[4];

    }

    public float getDamageGiven() {
        return damageGiven;
    }

    public void setDamageGiven(float damageGiven) {
        this.damageGiven = damageGiven;
    }

    public Enemy(float[] dna, Lane lane, float spawnTime){
        this();
        this.spawnTime = spawnTime;
        this.enemyLane = lane;
        this.dna = dna;
    }


    public abstract void InitializeAnimation();
    public abstract void draw(SpriteBatch batch);
    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();


        stateTime += delta;

        // check if frozen
        if (state == State.FROZEN){
            DX = 0;
        }

        // speed calculation
        X += DX * Speed * delta;

        // attack cooldown calculation
        attackCooldown -= delta;

        // attack base
        if(X < 350 )
        {
            X = 350; // stop at castle

            if (state != State.DEATH){
                // change state
                state = Enemy.State.ATTACK;
            }
            
        }

        // stop if death
        if (state == State.DEATH){
            DX = 0;
        }

        // change state to death if HP <= 0
        if (HP <= 0){
            state = State.DEATH;
            calculateFitness();


        }

        if(state == State.DYING && stateTime > 3f)
        {
            state = State.DEATH;
        }

        // hardcode to set Y according to Lane
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


    }
    private void calculateFitness() {
        if (isFitnessCalculated){
            return;
        }
        isFitnessCalculated = true;

        fitness = (2150 - X)/2 + 10 * getDamageGiven();
    }

    public void Attacked(Arrow a){
        if(state != State.DEATH) {
            HP -= a.getDamage() - physicalResistance;
//            sound.play();
        }
    }
    public void Attacked(Spell s){
        if(state != State.DEATH) {
            HP -= s.getDamage() - magicalResistance;

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

    public float getHP() {
        return HP;
    }

    public void setHP(float HP) {
        this.HP = HP;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
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

    public int getGoldDrop() {
        return goldDrop;
    }

    public boolean isGoldDroped() {
        return isGoldDroped;
    }

    public void setGoldDroped(boolean goldDroped) {
        isGoldDroped = goldDroped;
    }
}
