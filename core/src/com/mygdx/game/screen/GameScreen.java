package com.mygdx.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import com.mygdx.game.spell.*;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Hero;
import com.mygdx.game.util.DataHandling;
import com.mygdx.game.util.Timer;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;

    TextButton pauseButton, resumeButton, restartButton, exitButton,playAgainButton,backToMenuButton,fireBallButton,arrowsButton,freezeButton;
    Stage stg;

    InputListener inputListener;

    private Viewport viewport;
    private OrthographicCamera camera;
    SpriteBatch batch;

    Texture circleAOE;

    InputMultiplexer multiInput;

    Castle castle;
    Hero hero;
    Fireball fireball;
    Freeze freeze;
    Earthquake earthquake;
    Arrows arrows;

    Window pauseWindow,gameoverWindow;

    GameScreen thisScreen;

    int stageNumber;
    int kill = 0;
    BitmapFontCache fontCache,castleHPNumber,castleManaNumber;
    Random randomizer = new Random();
    Stages stage;

    ArrayList<Enemy> listEnemy = new ArrayList<>();
    ArrayList<String>stageHighScore = new ArrayList<>();

    Timer timer;
    WaveEnemy w1;

    float circleX;
    float circleY;

    float gameTime = 0;

    public enum State {
        Running, Paused
    }

    State state = State.Running;

    public GameScreen() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public GameScreen(Game parent) {
        parentGame = parent;
        this.Initialize();
    }

    public GameScreen(Game parent, int stage) {
        parentGame = parent;
        stageNumber = stage;

        this.Initialize();
    }


    public void Initialize() {
        castle = new Castle();
        hero = new Hero();
        hero.setAttackCooldown(0);
        fireball = new Fireball();
        fireball.setCooldown(0);
        freeze = new Freeze();
        freeze.setCooldown(0);
        earthquake = new Earthquake();
        arrows =new Arrows();
        arrows.setCooldown(0);


        stage = new Stages();
        stage.setStage(stageNumber);


        assetManager = ((MyGdxGame) parentGame).getAssetManager();

        thisScreen = this;

        timer = new Timer();

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        OrthographicCamera stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stg = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stg);

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);

        circleAOE = assetManager.get("circleAOE.png",Texture.class);

        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText("Stage. 1", 1200, 1025);

        castleHPNumber = new BitmapFontCache(MyGdxGame.font);
        castleHPNumber.setColor(Color.RED);
        castleHPNumber.setText("HP : "+ (int) castle.getHP(),300,50);
        castleManaNumber = new BitmapFontCache(MyGdxGame.font);
        castleManaNumber.setColor(Color.BLUE);
        castleManaNumber.setText("Mana : "+ (int) castle.getMana(),600,50);

        switch (stageNumber) {
            case 1:
                stage.addOrc(1, Enemy.Lane.TWO);
                stage.addOrc(5, Enemy.Lane.THREE);
                stage.addOrc(10, Enemy.Lane.ONE);
                stage.addOrc(15, Enemy.Lane.FOUR);
                stage.addOrc(20, Enemy.Lane.ONE);
                stage.addOrc(25, Enemy.Lane.FOUR);
                stage.addOrc(30, Enemy.Lane.THREE);
                stage.addOrc(35, Enemy.Lane.TWO);
                stage.addOrc(40, Enemy.Lane.FOUR);
                stage.addOrc(45, Enemy.Lane.ONE);
                stage.addOrc(50, Enemy.Lane.THREE);
                stage.addOrc(55, Enemy.Lane.FOUR);
                stage.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Orc,4,5f,1f);
//                w1.addToArray(listEnemy);
                break;
            case 2:
                stage.addOrc(1, Enemy.Lane.THREE);
                stage.addOgre(5, Enemy.Lane.ONE);
                stage.addOgre(10, Enemy.Lane.FOUR);
                stage.addOrc(15, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Orc, 2, 20f, 1f,2);
                stage.addOgre(25, Enemy.Lane.FOUR);
                stage.addOgre(30, Enemy.Lane.ONE);
                stage.addOrc(35, Enemy.Lane.THREE);
                stage.addOgre(40, Enemy.Lane.TWO);
                stage.addOgre(45, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Orc, 4, 50f, 1f,1);
                stage.addToArray(listEnemy);

//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 51f, 1f);
//                w1.addToArray(listEnemy);
                break;
            case 3:
                stage.addOrc(1, Enemy.Lane.ONE);
                stage.addOgre(5, Enemy.Lane.THREE);
                stage.addOrc(10, Enemy.Lane.FOUR);
                stage.addOrc(15, Enemy.Lane.TWO);
                stage.addGoblin(20, Enemy.Lane.THREE);
                stage.addWave(Enemy.Type.Ogre, 3, 25, 1f,2);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 26, 1f);
//                w1.addToArray(listEnemy);
                stage.addGoblin(30, Enemy.Lane.ONE);
                stage.addOrc(35, Enemy.Lane.THREE);
                stage.addOgre(40, Enemy.Lane.FOUR);
                stage.addOgre(45, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Orc, 3, 50, 1f,1);
                stage.addGoblin(55, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Goblin, 4, 58, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 56, 1f);
//                w1.addToArray(listEnemy);
                stage.addToArray(listEnemy);
                break;
            case 4:
                stage.addOrc(1, Enemy.Lane.FOUR);
                stage.addOgre(5, Enemy.Lane.THREE);
                stage.addOgre(10, Enemy.Lane.TWO);
                stage.addGoblin(15, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Ogre, 2, 17, 1f,3);
                stage.addWave(Enemy.Type.Ogre, 3, 20, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 21, 1f);
//                w1.addToArray(listEnemy);
                stage.addOrc(25, Enemy.Lane.ONE);
                stage.addOgre(30, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Orc, 3, 35, 1f,2);
                stage.addWave(Enemy.Type.Ogre, 4, 42, 1f,1);
                stage.addOgre(45, Enemy.Lane.FOUR);
                stage.addGoblin(50, Enemy.Lane.TWO);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 41, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 51, 1f);
//                w1.addToArray(listEnemy);
                stage.addToArray(listEnemy);
                break;
            case 5:
                stage.addOgre(1, Enemy.Lane.THREE);
                stage.addOgre(5, Enemy.Lane.TWO);
                stage.addGoblin(10, Enemy.Lane.ONE);
                stage.addOrc(15, Enemy.Lane.FOUR);
                stage.addOgre(17, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Ogre, 4, 20, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 21, 1f);
//                w1.addToArray(listEnemy);
                stage.addGoblin(29, Enemy.Lane.ONE);
                stage.addOrc(30, Enemy.Lane.TWO);
                stage.addOgre(35, Enemy.Lane.THREE);
                stage.addWave(Enemy.Type.Orc, 4, 40, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 46, 1f);
//                w1.addToArray(listEnemy);
                stage.addGoblin(47, Enemy.Lane.FOUR);
                stage.addGoblin(50, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Ogre, 2, 52, 1f,3);
                stage.addWave(Enemy.Type.Goblin, 4, 56, 1f,1);
                stage.addGoblin(62, Enemy.Lane.ONE);
                stage.addOrc(65, Enemy.Lane.TWO);
                stage.addOgre(67, Enemy.Lane.THREE);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 71, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 81, 1f);
//                w1.addToArray(listEnemy);
                stage.addToArray(listEnemy);
                break;
            case 6:
                stage.addWave(Enemy.Type.Orc, 3, 1, 1f,2);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 1, 1f);
//                w1.addToArray(listEnemy);
                stage.addOrc(7, Enemy.Lane.ONE);
                stage.addOgre(10, Enemy.Lane.THREE);
                stage.addOrc(15, Enemy.Lane.FOUR);
                stage.addGoblin(20, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Ogre, 2, 25, 1f,3);
                stage.addWave(Enemy.Type.Goblin, 3, 30, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 36, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 46, 1f);
//                w1.addToArray(listEnemy);
                stage.addGoblin(38, Enemy.Lane.FOUR);
                stage.addGoblin(40, Enemy.Lane.TWO);
                stage.addOgre(45, Enemy.Lane.ONE);
                stage.addOgre(50, Enemy.Lane.THREE);
                stage.addWave(Enemy.Type.Ogre, 3, 53, 1f,2);
                stage.addWave(Enemy.Type.Goblin, 4, 59, 1f,1);
                stage.addOgre(67, Enemy.Lane.THREE);
                stage.addOrc(70, Enemy.Lane.ONE);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 76, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 86, 1f);
//                w1.addToArray(listEnemy);
                stage.addToArray(listEnemy);
                break;
            case 7:
                stage.addOrc(1, Enemy.Lane.FOUR);
                stage.addOrc(5, Enemy.Lane.TWO);
                stage.addOgre(10, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Goblin, 4, 15, 1f,1);
                stage.addGoblin(20, Enemy.Lane.FOUR);
                stage.addGoblin(25, Enemy.Lane.TWO);
                stage.addOgre(30, Enemy.Lane.THREE);
                stage.addOgre(35, Enemy.Lane.ONE);
                stage.addGoblin(40, Enemy.Lane.FOUR);
                stage.addGoblin(45, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Ogre, 3, 50, 1f,2);
                stage.addWave(Enemy.Type.Goblin, 3, 55, 1f,2);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 56, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 66, 1f);
//                w1.addToArray(listEnemy);
                stage.addGoblin(60, Enemy.Lane.TWO);
                stage.addGoblin(65, Enemy.Lane.THREE);
                stage.addOgre(70, Enemy.Lane.ONE);
                stage.addOgre(75, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Ogre, 2, 80, 1f,3);
                stage.addWave(Enemy.Type.Goblin, 3, 85, 1f,2);
                stage.addWave(Enemy.Type.Goblin, 4, 92, 1f,1);
                stage.addOrc(98, Enemy.Lane.FOUR);
                stage.addOgre(101, Enemy.Lane.ONE);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 91, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 101, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 111, 1f);
//                w1.addToArray(listEnemy);
                stage.addToArray(listEnemy);
                break;
            case 8:
                stage.addWave(Enemy.Type.Goblin, 4, 1, 0f,1);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 1, 1f);
//                w1.addToArray(listEnemy);
                stage.addGoblin(10, Enemy.Lane.THREE);
                stage.addGoblin(15, Enemy.Lane.ONE);
                stage.addOgre(18, Enemy.Lane.TWO);
                stage.addOgre(20, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Goblin, 4, 25, 0.5f,1);
                stage.addWave(Enemy.Type.Ogre, 4, 33, 0.5f,1);
                stage.addWave(Enemy.Type.Goblin, 3, 39, 0.5f,2);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 36, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 46, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 56, 1f);
//                w1.addToArray(listEnemy);
                stage.addOrc(45, Enemy.Lane.ONE);
                stage.addOrc(50, Enemy.Lane.TWO);
                stage.addOgre(54, Enemy.Lane.THREE);
                stage.addOgre(57, Enemy.Lane.FOUR);
                stage.addGoblin(60, Enemy.Lane.THREE);
                stage.addGoblin(65, Enemy.Lane.ONE);
                stage.addGoblin(67, Enemy.Lane.TWO);
                stage.addGoblin(70, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Orc, 4, 73, 0f,1);
                stage.addWave(Enemy.Type.Ogre, 4, 81, 0f,1);
                stage.addWave(Enemy.Type.Goblin, 4, 91, 0f,1);
                stage.addOgre(95, Enemy.Lane.THREE);
                stage.addGoblin(98, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Goblin, 3, 100, 0f,1);
                stage.addWave(Enemy.Type.Orc, 4, 106, 0f,1);
                stage.addWave(Enemy.Type.Orc, 2, 113, 0f,2);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 106, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 116, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 126, 1f);
//                w1.addToArray(listEnemy);
                stage.addToArray(listEnemy);
                break;
        }


        pauseButton = new TextButton("Pause", mySkin);
        pauseButton.setHeight(100);
        pauseButton.setWidth(200);
        pauseButton.setPosition(100, 900);
        pauseButton.setColor(Color.WHITE);
        pauseButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    pauseWindow.setVisible(true);
                    state = State.Paused;
                    ((MyGdxGame) parentGame).PauseInGameMusic();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stg.addActor(pauseButton);

        pauseWindow = new Window("Pause", mySkin);
        pauseWindow.setHeight(320);
        pauseWindow.setWidth(480);
        pauseWindow.setPosition(700, 500);
        pauseWindow.setMovable(false);
        pauseWindow.setModal(true);
        //optionWindow.setResizable(true);
        pauseWindow.setVisible(false);
        pauseWindow.getTitleLabel().setAlignment(Align.center);
        stg.addActor(pauseWindow);
//        stageNumber = 0;

        resumeButton = new TextButton("Resume", mySkin);
        resumeButton.setWidth(120);
        resumeButton.setHeight(36);
        resumeButton.setPosition(resumeButton.getWidth() / 2 - resumeButton.getWidth() / 2 + 175, 228);
        resumeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    state = State.Running;
                    ((MyGdxGame) parentGame).PlayInGameMusic();
                    pauseWindow.setVisible(false);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        pauseWindow.addActor(resumeButton);

        restartButton = new TextButton("Restart", mySkin);
        restartButton.setWidth(120);
        restartButton.setHeight(36);
        restartButton.setPosition(resumeButton.getWidth() / 2 - resumeButton.getWidth() / 2 + 175, 150);
        restartButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopInGameMusic();
                    parentGame.setScreen(new GameScreen(parentGame, stageNumber));
                    ((MyGdxGame) parentGame).PlayInGameMusic();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        pauseWindow.addActor(restartButton);

        exitButton = new TextButton("Back to Menu", mySkin);
        exitButton.setWidth(120);
        exitButton.setHeight(36);
        exitButton.setPosition(exitButton.getWidth() / 2 - exitButton.getWidth() / 2 + 175, 72);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopInGameMusic();
                    parentGame.setScreen(new MenuScreen(parentGame));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        pauseWindow.addActor(exitButton);

        fireBallButton = new TextButton("Fireball", mySkin);
        fireBallButton.setHeight(100);
        fireBallButton.setWidth(100);
        fireBallButton.setPosition(1400, 50);
        fireBallButton.setColor(Color.WHITE);
        fireBallButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    fireball.setState(Fireball.State.INACTIVE);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (fireball.getCooldown() <= 0 && castle.getMana() >= fireball.getCooldown()){
                    fireball.setState(Fireball.State.PREPARE);
                    circleX = x + 1250;
                    circleY = y-100;
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
                if (fireball.getCooldown() <= 0 && castle.getMana() >= fireball.getCooldown()){
                    fireball.setState(Fireball.State.PREPARE);
                    circleX = x + 1250;
                    circleY = y-100;
                }
            }

        });
        stg.addActor(fireBallButton);

        freezeButton = new TextButton("Freeze", mySkin);
        freezeButton.setHeight(100);
        freezeButton.setWidth(100);
        freezeButton.setPosition(1550, 50);
        freezeButton.setColor(Color.WHITE);
        freezeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    freeze.setState(Freeze.State.INACTIVE);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (freeze.getCooldown() <= 0 && castle.getMana() >= freeze.getCooldown()){
                    freeze.setState(Spell.State.PREPARE);
                    circleX = x + 1400;
                    circleY = y-100;
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
                if (freeze.getCooldown() <= 0 && castle.getMana() >= freeze.getCooldown()){
                    freeze.setState(Spell.State.PREPARE);
                    circleX = x + 1400;
                    circleY = y-100;
                }

            }

        });
        stg.addActor(freezeButton);

        arrowsButton = new TextButton("Arrows", mySkin);
        arrowsButton.setHeight(100);
        arrowsButton.setWidth(100);
        arrowsButton.setPosition(1700, 50);
        arrowsButton.setColor(Color.WHITE);
        arrowsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (castle.getMana() >= arrows.getManaCost() && arrows.getCooldown() <= 0){
                        arrows.setState(Arrows.State.ACTIVE);
                        castle.setMana(castle.getMana() - arrows.getManaCost());
                        arrows.setCooldown(arrows.getMaxCooldown());
                    }

                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stg.addActor(arrowsButton);

        gameoverWindow = new Window("Game Over", mySkin);
        gameoverWindow.setHeight(320);
        gameoverWindow.setWidth(480);
        gameoverWindow.setPosition(700, 500);
        gameoverWindow.setMovable(false);
        gameoverWindow.setModal(true);
        //optionWindow.setResizable(true);
        gameoverWindow.setVisible(false);
        gameoverWindow.getTitleLabel().setAlignment(Align.center);
        stg.addActor(gameoverWindow);


        playAgainButton = new TextButton("Play Again", mySkin);
        playAgainButton.setWidth(240);
        playAgainButton.setHeight(72);
        playAgainButton.setPosition(playAgainButton.getWidth() / 2 - playAgainButton.getWidth() / 2 + 125, 170);
        playAgainButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new GameScreen(parentGame, stage.getStage()));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        gameoverWindow.addActor(playAgainButton);

        backToMenuButton = new TextButton("Back to Menu", mySkin);
        backToMenuButton.setWidth(240);
        backToMenuButton.setHeight(72);
        backToMenuButton.setPosition(backToMenuButton.getWidth() / 2 - backToMenuButton.getWidth() / 2 + 125, 70);
        backToMenuButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopInGameMusic();
                    parentGame.setScreen(new MenuScreen(parentGame));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        gameoverWindow.addActor(backToMenuButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
        ((MyGdxGame) parentGame).PlayInGameMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Texture background = assetManager.get("In Game.png", Texture.class);


        gameTime = gameTime + Gdx.graphics.getDeltaTime();


        batch.draw(background, 0, 0);


        castle.draw(batch);
        hero.draw(batch);
        for (Arrow a : hero.getListArrow()) {
            a.draw(batch);
        }
//        arrow.draw(batch);

//        w1.drawWaveEnemy(gameTime);

        for (Enemy enemy : listEnemy) {
            if (enemy.getSpawnTime() <= timer.getSecond() + timer.getMinute() * 60) {
                enemy.setDX(-1);
                enemy.draw(batch);

            }
        }
        if (fireball.getState() == Spell.State.PREPARE && fireball.getCooldown() <= 0){
            fireball.drawAOE(batch,circleX,circleY);
        }
        if (freeze.getState() == Spell.State.PREPARE && freeze.getCooldown() <= 0){
            freeze.drawAOE(batch,circleX,circleY);
        }
        arrows.draw(batch);


        switch (state) {
            case Running:
                this.update();
                break;
            case Paused:
                //don't update
                break;
        }
        fontCache.draw(batch);
        castleHPNumber.draw(batch);
        castleManaNumber.draw(batch);
        timer.draw(batch);
        fireball.draw(batch,circleX,circleY);
        fireball.drawStatus(batch,1440,40);
        freeze.drawStatus(batch,1590,40);
        arrows.drawStatus(batch,1740,40);


        batch.end();
        stg.act();
        stg.draw();

    }

    public void update() {
//        System.out.println(stageNumber);
        boolean end = true;
        for (Enemy e :listEnemy
             ) {
            if (e.state != Enemy.State.DEATH) {
                end = false;
                break;
            }
        }
        if (end){
            for (Enemy e :listEnemy
            ) {
                if (e.state == Enemy.State.DEATH){
                    kill++;
                }
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 1){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,0,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 2){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,1,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 3){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,2,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 4){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,3,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 5){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,4,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 6){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,5,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 7){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,6,calculateScore(stage),1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 8){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,7,calculateScore(stage),1);
            }
            thisScreen.dispose();
            ((MyGdxGame) parentGame).StopInGameMusic();
            if (stageNumber == 1){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 2){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 3){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 4){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 5){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 6){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 7){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }
            else if (stageNumber == 8){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int) castle.getHP(),calculateScore(stage)));
            }

        }
//        else if (castle.HP <= 0){
//
//            thisScreen.dispose();
//            ((MyGdxGame) parentGame).StopInGameMusic();
//            parentGame.setScreen(new MenuScreen(parentGame));
//        }
        if (castle.getHP() <= 0){
            castle.setHP(0);
            for (Enemy e :listEnemy
            ) {
                if (e.state == Enemy.State.DEATH){
                    kill++;
                }
            }

            state = State.Paused;
            ((MyGdxGame) parentGame).StopInGameMusic();
            thisScreen.dispose();
//            parentGame.setScreen(new StageSelection(parentGame));

            parentGame.setScreen(new StageFailed(parentGame,kill,(int) castle.getHP(),0));
//            gameoverWindow.setVisible(true);

        }
        for (Enemy e : listEnemy) {
            e.update();
            if (e.CanAttack()){
                castle.takeDamage(e);
                e.setAttackCooldown(3);
            }
            if (fireball.CanAttack(e,circleX,circleY) && fireball.getState() == Spell.State.ACTIVE){
                e.Attacked(fireball);
            }
            if (freeze.CanAttack(e,circleX,circleY) && freeze.getState() == Spell.State.ACTIVE){
                e.Attacked(freeze);
                if (e.isFrozen(freeze.getDuration())){
                    e.state = Enemy.State.FROZEN;
                }
                else {
                    e.state = Enemy.State.RUN;
                }

            }
            for (Arrow a : hero.getListArrow()) {
                if (a.CanAttack(e) && a.getState() == Arrow.State.ACTIVE) {
                    e.Attacked(a);
                    a.setState(Arrow.State.INACTIVE);
                }
            }
            for (Arrow a : arrows.getListArrow()) {
                if (a.CanAttack(e) && a.getState() == Arrow.State.ACTIVE) {
                    e.Attacked(a);
                    a.setState(Arrow.State.INACTIVE);
                }
            }


        }
        for (Arrow a : hero.getListArrow()) {
            a.update();
        }
        castle.update();
        hero.update();

        if (fireball.getState() != Spell.State.PREPARE) fireball.update();
        if (freeze.getState() != Spell.State.PREPARE) freeze.update();
//        if (castle.HP <= 0){
//            hero.state = Hero.State.DYING;
//        }

        timer.update();

        arrows.update();
        for (Arrow a: arrows.getListArrow()) {
            a.update();
        }
//        System.out.println(arrows.listArrow.size());



        this.setStageNumber(stageNumber);

        if (Gdx.input.isTouched()){
            isTouched();
            if (fireball.getState() == Spell.State.PREPARE){
                circleX = Gdx.input.getX()-150;
                circleY = 928-Gdx.input.getY();
            }
        }else {
            hero.setState(Hero.State.IDLE);
        }
        if (!Gdx.input.isTouched() && fireball.getState() == Fireball.State.PREPARE){
            if (castle.getMana() >= fireball.getManaCost() && fireball.getCooldown() <= 0){
                fireball.setState(Fireball.State.ACTIVE);
                fireball.setCooldown(fireball.getMaxCooldown());
                castle.setMana(castle.getMana() - fireball.getManaCost());
            }else {
                fireball.setState(Spell.State.INACTIVE);
            }
        }
        if (!Gdx.input.isTouched() && freeze.getState() == Freeze.State.PREPARE){
            if (castle.getMana() >= freeze.getManaCost() && freeze.getCooldown() <= 0){
                freeze.setState(Freeze.State.ACTIVE);
                freeze.setCooldown(freeze.getMaxCooldown());
                castle.setMana(castle.getMana() - freeze.getManaCost());
            }else if (freeze.getDuration() <= 0) {
                fireball.setState(Spell.State.INACTIVE);
            }
        }
        if (freeze.getState() == Spell.State.ACTIVE){
            freeze.duration -= Gdx.graphics.getDeltaTime();
        }
        castleHPNumber.setText("HP : "+ (int) castle.getHP(),300,50);
        castleManaNumber.setText("Mana : " + (int) castle.getMana(),600,50);
    }

    public void setStageNumber(int stg) {
        stageNumber = stg;
        fontCache.setText(String.format("Stage. %d", stageNumber), 1200, 1025);
    }

    public boolean isTouched(){
            if (state != State.Paused && hero.getAttackCooldown() <= 0) {
                hero.Attack();
                hero.setState(Hero.State.ATTACK);
                Vector2 position = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                position = viewport.unproject(position);
                if (position.y >= 700) {
                    position.y = 700;
                } else if (position.y <= 200) {
                    position.y = 200;
                }
                hero.getArrow().setY(position.y - 80);

                return true;
            }
        return false;
    }
    public int calculateScore(Stages stages){
        stages.setLife((int) castle.getHP());
        return stages.calculateKill(listEnemy) * 10 + stages.getLife() *10;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // handle input
    @Override
    public boolean keyDown(int keycode) {


        return true;
    }

    @Override
    public boolean keyUp(int keycode) {


        return true;

    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (state != State.Paused && hero.getAttackCooldown() <= 0) {
//            arrow = new Arrow();
//            listArrow.add(arrow);
//
//            hero.attackCooldown = 0.5f;
            hero.Attack();
            Vector2 position = new Vector2(screenX, screenY);
            position = viewport.unproject(position);
            if (position.y >= 700) {
                position.y = 700;
            } else if (position.y <= 200) {
                position.y = 200;
            }
            hero.getArrow().setY(position.y - 80);
//            hero.attackCooldown = 1;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (state != State.Paused) {
            Vector2 position = new Vector2(screenX, screenY);
            position = viewport.unproject(position);
            if (position.y >= 700) {
                position.y = 700;
            } else if (position.y <= 200) {
                position.y = 200;
            }
            hero.setY(position.y - 220);
        }
        if (state != State.Paused && hero.getAttackCooldown() <= 0) {
//            arrow = new Arrow();
//            listArrow.add(arrow);
//            hero.attackCooldown = 1f;
            hero.Attack();
            Vector2 position = new Vector2(screenX, screenY);
            position = viewport.unproject(position);
            if (position.y >= 700) {
                position.y = 700;
            } else if (position.y <= 200) {
                position.y = 200;
            }
            hero.getArrow().setY(position.y - 80);
//            hero.attackCooldown = 1;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (state != State.Paused) {
            Vector2 position = new Vector2(screenX, screenY);
            position = viewport.unproject(position);
            if (position.y >= 700) {
                position.y = 700;
            } else if (position.y <= 200) {
                position.y = 200;
            }
            hero.setY(position.y - 220);
        }


//        player.setX(position.x);
//        player.setY(position.y);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    public Stages getstage() {
        return stage;
    }
}
