package com.mygdx.game;

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
//    Rectangle healtbar;

    Castle castle;
    Arrow arrow;
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
    Stages stages1,stages2,stages3,stages4,stages5,stages6,stages7,stages8;

    ArrayList<Enemy> listEnemy = new ArrayList<>();
//    ArrayList<Arrow> listArrow = new ArrayList<>();
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
        if (stage == 1) {
            stageNumber = stage;
//            stages1.stageSuccesses = stage - 1;
        }
        else if (stage == 2) {

            stageNumber = stage;
//            stages2.stageSuccesses = stage - 1;
        }
        else if (stage == 3) {
            stageNumber = stage;
//            stages3.stageSuccesses = stage - 1;
        }
        else if (stage == 4) {
            stageNumber = stage;
//            stages4.stageSuccesses = stage - 1;
        }
        else if (stage == 5) {

            stageNumber = stage;
//            stages5.stageSuccesses = stage - 1;
        }
        else if (stage == 6) {
            stageNumber = stage;
//            stages6.stageSuccesses = stage - 1;
        }
        else if (stage == 7) {
            stageNumber = stage;
//            stages7.stageSuccesses = stage - 1;
        }
        else if (stage == 8) {
            stageNumber = stage;
//            stages8.stageSuccesses = stage - 1;
        }

        this.Initialize();
    }


    public void Initialize() {
        castle = new Castle();
        hero = new Hero();
        hero.attackCooldown = 0;
        fireball = new Fireball();
        fireball.cooldown = 0;
        freeze = new Freeze();
        freeze.cooldown = 0;
        earthquake = new Earthquake();
        arrows =new Arrows();
        arrows.cooldown = 0;

        stages1 = new Stages();
        stages1.stage = 1;
        stages1.score = this.calculateScore(stages1);

        stages2 = new Stages();
        stages2.stage = 2;
        stages2.score = this.calculateScore(stages2);

        stages3 = new Stages();
        stages3.stage = 3;
        stages3.score = this.calculateScore(stages3);

        stages4 = new Stages();
        stages4.stage = 4;
        stages4.score = this.calculateScore(stages4);

        stages5 = new Stages();
        stages5.stage = 5;
        stages5.score = this.calculateScore(stages5);

        stages6 = new Stages();
        stages6.stage = 6;
        stages6.score = this.calculateScore(stages6);

        stages7 = new Stages();
        stages7.stage = 7;
        stages7.score = this.calculateScore(stages7);

        stages8 = new Stages();
        stages8.stage = 8;
        stages8.score = this.calculateScore(stages8);

        assetManager = ((MyGdxGame) parentGame).getAssetManager();

        stageHighScore.add(String.valueOf(stages1.highScore));
        stageHighScore.add(String.valueOf(stages2.highScore));
        stageHighScore.add(String.valueOf(stages3.highScore));
        stageHighScore.add(String.valueOf(stages4.highScore));
        stageHighScore.add(String.valueOf(stages5.highScore));
        stageHighScore.add(String.valueOf(stages6.highScore));
        stageHighScore.add(String.valueOf(stages7.highScore));
        stageHighScore.add(String.valueOf(stages8.highScore));
//        Path path = Paths.get("data_save/dataHighscore.txt");
//        boolean path_exits = Files.notExists(path);
//        if (path_exits == true) {
//            writeFile(stageHighScore);
//        }






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
        castleHPNumber.setText("HP : "+ (int) castle.HP,300,50);
        castleManaNumber = new BitmapFontCache(MyGdxGame.font);
        castleManaNumber.setColor(Color.BLUE);
        castleManaNumber.setText("Mana : "+ (int) castle.mana,600,50);

//        healtbar = new Rectangle(100, 100, 500, 100);


        switch (stageNumber) {
            case 1:
                stages1.addOrc(1, Enemy.Lane.TWO);
                stages1.addOrc(5, Enemy.Lane.THREE);
                stages1.addOrc(10, Enemy.Lane.ONE);
                stages1.addOrc(15, Enemy.Lane.FOUR);
                stages1.addOrc(20, Enemy.Lane.ONE);
                stages1.addOrc(25, Enemy.Lane.FOUR);
                stages1.addOrc(30, Enemy.Lane.THREE);
                stages1.addOrc(35, Enemy.Lane.TWO);
                stages1.addOrc(40, Enemy.Lane.FOUR);
                stages1.addOrc(45, Enemy.Lane.ONE);
                stages1.addOrc(50, Enemy.Lane.THREE);
                stages1.addOrc(55, Enemy.Lane.FOUR);
                stages1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Orc,4,5f,1f);
//                w1.addToArray(listEnemy);
                break;
            case 2:
                stages2.addOrc(1, Enemy.Lane.THREE);
                stages2.addOgre(5, Enemy.Lane.ONE);
                stages2.addOgre(10, Enemy.Lane.FOUR);
                stages2.addOrc(15, Enemy.Lane.TWO);
                stages2.addWave(Enemy.Type.Orc, 2, 20f, 1f,2);
                stages2.addOgre(25, Enemy.Lane.FOUR);
                stages2.addOgre(30, Enemy.Lane.ONE);
                stages2.addOrc(35, Enemy.Lane.THREE);
                stages2.addOgre(40, Enemy.Lane.TWO);
                stages2.addOgre(45, Enemy.Lane.FOUR);
                stages2.addWave(Enemy.Type.Orc, 4, 50f, 1f,1);
                stages2.addToArray(listEnemy);

//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 51f, 1f);
//                w1.addToArray(listEnemy);
                break;
            case 3:
                stages3.addOrc(1, Enemy.Lane.ONE);
                stages3.addOgre(5, Enemy.Lane.THREE);
                stages3.addOrc(10, Enemy.Lane.FOUR);
                stages3.addOrc(15, Enemy.Lane.TWO);
                stages3.addGoblin(20, Enemy.Lane.THREE);
                stages3.addWave(Enemy.Type.Ogre, 3, 25, 1f,2);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 26, 1f);
//                w1.addToArray(listEnemy);
                stages3.addGoblin(30, Enemy.Lane.ONE);
                stages3.addOrc(35, Enemy.Lane.THREE);
                stages3.addOgre(40, Enemy.Lane.FOUR);
                stages3.addOgre(45, Enemy.Lane.TWO);
                stages3.addWave(Enemy.Type.Orc, 3, 50, 1f,1);
                stages3.addGoblin(55, Enemy.Lane.ONE);
                stages5.addWave(Enemy.Type.Goblin, 4, 58, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 56, 1f);
//                w1.addToArray(listEnemy);
                stages3.addToArray(listEnemy);
                break;
            case 4:
                stages4.addOrc(1, Enemy.Lane.FOUR);
                stages4.addOgre(5, Enemy.Lane.THREE);
                stages4.addOgre(10, Enemy.Lane.TWO);
                stages4.addGoblin(15, Enemy.Lane.ONE);
                stages4.addWave(Enemy.Type.Ogre, 2, 17, 1f,3);
                stages4.addWave(Enemy.Type.Ogre, 3, 20, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 21, 1f);
//                w1.addToArray(listEnemy);
                stages4.addOrc(25, Enemy.Lane.ONE);
                stages4.addOgre(30, Enemy.Lane.TWO);
                stages4.addWave(Enemy.Type.Orc, 3, 35, 1f,2);
                stages4.addWave(Enemy.Type.Ogre, 4, 42, 1f,1);
                stages4.addOgre(45, Enemy.Lane.FOUR);
                stages4.addGoblin(50, Enemy.Lane.TWO);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 41, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 51, 1f);
//                w1.addToArray(listEnemy);
                stages4.addToArray(listEnemy);
                break;
            case 5:
                stages5.addOgre(1, Enemy.Lane.THREE);
                stages5.addOgre(5, Enemy.Lane.TWO);
                stages5.addGoblin(10, Enemy.Lane.ONE);
                stages5.addOrc(15, Enemy.Lane.FOUR);
                stages5.addOgre(17, Enemy.Lane.FOUR);
                stages5.addWave(Enemy.Type.Ogre, 4, 20, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 21, 1f);
//                w1.addToArray(listEnemy);
                stages5.addGoblin(29, Enemy.Lane.ONE);
                stages5.addOrc(30, Enemy.Lane.TWO);
                stages5.addOgre(35, Enemy.Lane.THREE);
                stages5.addWave(Enemy.Type.Orc, 4, 40, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 46, 1f);
//                w1.addToArray(listEnemy);
                stages5.addGoblin(47, Enemy.Lane.FOUR);
                stages5.addGoblin(50, Enemy.Lane.ONE);
                stages5.addWave(Enemy.Type.Ogre, 2, 52, 1f,3);
                stages5.addWave(Enemy.Type.Goblin, 4, 56, 1f,1);
                stages5.addGoblin(62, Enemy.Lane.ONE);
                stages5.addOrc(65, Enemy.Lane.TWO);
                stages5.addOgre(67, Enemy.Lane.THREE);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 71, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 81, 1f);
//                w1.addToArray(listEnemy);
                stages5.addToArray(listEnemy);
                break;
            case 6:
                stages6.addWave(Enemy.Type.Orc, 3, 1, 1f,2);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 1, 1f);
//                w1.addToArray(listEnemy);
                stages6.addOrc(7, Enemy.Lane.ONE);
                stages6.addOgre(10, Enemy.Lane.THREE);
                stages6.addOrc(15, Enemy.Lane.FOUR);
                stages6.addGoblin(20, Enemy.Lane.TWO);
                stages6.addWave(Enemy.Type.Ogre, 2, 25, 1f,3);
                stages6.addWave(Enemy.Type.Goblin, 3, 30, 1f,1);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 36, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 46, 1f);
//                w1.addToArray(listEnemy);
                stages6.addGoblin(38, Enemy.Lane.FOUR);
                stages6.addGoblin(40, Enemy.Lane.TWO);
                stages6.addOgre(45, Enemy.Lane.ONE);
                stages6.addOgre(50, Enemy.Lane.THREE);
                stages6.addWave(Enemy.Type.Ogre, 3, 53, 1f,2);
                stages6.addWave(Enemy.Type.Goblin, 4, 59, 1f,1);
                stages6.addOgre(67, Enemy.Lane.THREE);
                stages6.addOrc(70, Enemy.Lane.ONE);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 76, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 86, 1f);
//                w1.addToArray(listEnemy);
                stages6.addToArray(listEnemy);
                break;
            case 7:
                stages7.addOrc(1, Enemy.Lane.FOUR);
                stages7.addOrc(5, Enemy.Lane.TWO);
                stages7.addOgre(10, Enemy.Lane.ONE);
                stages7.addWave(Enemy.Type.Goblin, 4, 15, 1f,1);
                stages7.addGoblin(20, Enemy.Lane.FOUR);
                stages7.addGoblin(25, Enemy.Lane.TWO);
                stages7.addOgre(30, Enemy.Lane.THREE);
                stages7.addOgre(35, Enemy.Lane.ONE);
                stages7.addGoblin(40, Enemy.Lane.FOUR);
                stages7.addGoblin(45, Enemy.Lane.TWO);
                stages7.addWave(Enemy.Type.Ogre, 3, 50, 1f,2);
                stages7.addWave(Enemy.Type.Goblin, 3, 55, 1f,2);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 56, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 66, 1f);
//                w1.addToArray(listEnemy);
                stages7.addGoblin(60, Enemy.Lane.TWO);
                stages7.addGoblin(65, Enemy.Lane.THREE);
                stages7.addOgre(70, Enemy.Lane.ONE);
                stages7.addOgre(75, Enemy.Lane.FOUR);
                stages7.addWave(Enemy.Type.Ogre, 2, 80, 1f,3);
                stages7.addWave(Enemy.Type.Goblin, 3, 85, 1f,2);
                stages7.addWave(Enemy.Type.Goblin, 4, 92, 1f,1);
                stages7.addOrc(98, Enemy.Lane.FOUR);
                stages7.addOgre(101, Enemy.Lane.ONE);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 91, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 101, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 111, 1f);
//                w1.addToArray(listEnemy);
                stages7.addToArray(listEnemy);
                break;
            case 8:
                stages8.addWave(Enemy.Type.Goblin, 4, 1, 0f,1);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 1, 1f);
//                w1.addToArray(listEnemy);
                stages8.addGoblin(10, Enemy.Lane.THREE);
                stages8.addGoblin(15, Enemy.Lane.ONE);
                stages8.addOgre(18, Enemy.Lane.TWO);
                stages8.addOgre(20, Enemy.Lane.FOUR);
                stages8.addWave(Enemy.Type.Goblin, 4, 25, 0.5f,1);
                stages8.addWave(Enemy.Type.Ogre, 4, 33, 0.5f,1);
                stages8.addWave(Enemy.Type.Goblin, 3, 39, 0.5f,2);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 36, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 46, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 56, 1f);
//                w1.addToArray(listEnemy);
                stages8.addOrc(45, Enemy.Lane.ONE);
                stages8.addOrc(50, Enemy.Lane.TWO);
                stages8.addOgre(54, Enemy.Lane.THREE);
                stages8.addOgre(57, Enemy.Lane.FOUR);
                stages8.addGoblin(60, Enemy.Lane.THREE);
                stages8.addGoblin(65, Enemy.Lane.ONE);
                stages8.addGoblin(67, Enemy.Lane.TWO);
                stages8.addGoblin(70, Enemy.Lane.FOUR);
                stages8.addWave(Enemy.Type.Orc, 4, 73, 0f,1);
                stages8.addWave(Enemy.Type.Ogre, 4, 81, 0f,1);
                stages8.addWave(Enemy.Type.Goblin, 4, 91, 0f,1);
                stages8.addOgre(95, Enemy.Lane.THREE);
                stages8.addGoblin(98, Enemy.Lane.ONE);
                stages8.addWave(Enemy.Type.Goblin, 3, 100, 0f,1);
                stages8.addWave(Enemy.Type.Orc, 4, 106, 0f,1);
                stages8.addWave(Enemy.Type.Orc, 2, 113, 0f,2);
//                w1 = new WaveEnemy(Enemy.Type.Orc, 4, 106, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Ogre, 4, 116, 1f);
//                w1.addToArray(listEnemy);
//                w1 = new WaveEnemy(Enemy.Type.Goblin, 4, 126, 1f);
//                w1.addToArray(listEnemy);
                stages8.addToArray(listEnemy);
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
                    fireball.state = Fireball.State.INACTIVE;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (fireball.cooldown<= 0 && castle.mana>= fireball.cooldown){
                    fireball.state = Fireball.State.PREPARE;
                    circleX = x + 1250;
                    circleY = y-100;
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
                if (fireball.cooldown<= 0 && castle.mana>= fireball.cooldown){
                    fireball.state = Fireball.State.PREPARE;
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
                    freeze.state = Freeze.State.INACTIVE;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (freeze.cooldown<= 0 && castle.mana>= freeze.cooldown){
                    freeze.state = Spell.State.PREPARE;
                    circleX = x + 1400;
                    circleY = y-100;
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
                if (freeze.cooldown<= 0 && castle.mana>= freeze.cooldown){
                    freeze.state = Spell.State.PREPARE;
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
                    if (castle.mana >= arrows.manaCost && arrows.cooldown <= 0){
                        arrows.state = Arrows.State.ACTIVE;
                        castle.mana -= arrows.manaCost;
                        arrows.cooldown = arrows.maxCooldown;
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
                    parentGame.setScreen(new GameScreen(parentGame, stages1.stage));
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
        for (Arrow a : hero.listArrow) {
            a.draw(batch);
        }
//        arrow.draw(batch);

//        w1.drawWaveEnemy(gameTime);

        for (Enemy enemy : listEnemy) {
            if (enemy.spawnTime <= timer.second + timer.minute * 60) {
                enemy.DX = -1;
                enemy.draw(batch);

            }
        }
        if (fireball.state == Spell.State.PREPARE && fireball.cooldown <= 0){
            fireball.drawAOE(batch,circleX,circleY);
        }
        if (freeze.state == Spell.State.PREPARE && freeze.cooldown <= 0){
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
            if (calculateScore(stages1) > stages1.highScore && stageNumber == 1){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,0,calculateScore(stages1),1);
            }
            if (calculateScore(stages2) > stages2.highScore && stageNumber == 2){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,1,calculateScore(stages2),1);
            }
            if (calculateScore(stages3) > stages3.highScore && stageNumber == 3){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,2,calculateScore(stages3),1);
            }
            if (calculateScore(stages4) > stages2.highScore && stageNumber == 4){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,3,calculateScore(stages4),1);
            }
            if (calculateScore(stages5) > stages2.highScore && stageNumber == 5){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,4,calculateScore(stages5),1);
            }
            if (calculateScore(stages6) > stages2.highScore && stageNumber == 6){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,5,calculateScore(stages6),1);
            }
            if (calculateScore(stages7) > stages2.highScore && stageNumber == 7){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,6,calculateScore(stages7),1);
            }
            if (calculateScore(stages8) > stages2.highScore && stageNumber == 8){
                stageHighScore.clear();
                readFile(stageHighScore,1);
                editFile(stageHighScore,7,calculateScore(stages8),1);
            }
            thisScreen.dispose();
            ((MyGdxGame) parentGame).StopInGameMusic();
            if (stageNumber == 1){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages1)));
            }
            else if (stageNumber == 2){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages2)));
            }
            else if (stageNumber == 3){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages3)));
            }
            else if (stageNumber == 4){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages4)));
            }
            else if (stageNumber == 5){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages5)));
            }
            else if (stageNumber == 6){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages6)));
            }
            else if (stageNumber == 7){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages7)));
            }
            else if (stageNumber == 8){
                parentGame.setScreen(new StageSuccess(parentGame,kill,(int)castle.HP,calculateScore(stages8)));
            }

        }
//        else if (castle.HP <= 0){
//
//            thisScreen.dispose();
//            ((MyGdxGame) parentGame).StopInGameMusic();
//            parentGame.setScreen(new MenuScreen(parentGame));
//        }
        if (castle.HP <= 0){
            castle.HP = 0;
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

            parentGame.setScreen(new StageFailed(parentGame,kill,(int)castle.HP,0));
//            gameoverWindow.setVisible(true);

        }
        for (Enemy e : listEnemy) {
            e.update();
            if (e.CanAttack()){
                castle.takeDamage(e);
                e.attackCooldown = 3;
            }
            if (fireball.CanAttack(e,circleX,circleY) && fireball.state == Spell.State.ACTIVE){
                e.Attacked(fireball);
            }
            if (freeze.CanAttack(e,circleX,circleY) && freeze.state == Spell.State.ACTIVE){
                e.Attacked(freeze);
                if (e.isFrozen(freeze.duration)){
                    e.state = Enemy.State.FROZEN;
                }
                else {
                    e.state = Enemy.State.RUN;
                }

            }
            for (Arrow a : hero.listArrow) {
                if (a.CanAttack(e) && a.state == Arrow.State.ACTIVE) {
                    e.Attacked(a);
                    a.state = Arrow.State.INACTIVE;
                }
            }
            for (Arrow a : arrows.listArrow) {
                if (a.CanAttack(e) && a.state == Arrow.State.ACTIVE) {
                    e.Attacked(a);
                    a.state = Arrow.State.INACTIVE;
                }
            }


        }
        for (Arrow a : hero.listArrow) {
            a.update();
        }
        castle.update();
        hero.update();

        if (fireball.state != Spell.State.PREPARE) fireball.update();
        if (freeze.state != Spell.State.PREPARE) freeze.update();
//        if (castle.HP <= 0){
//            hero.state = Hero.State.DYING;
//        }

        timer.update();

        arrows.update();
        for (Arrow a:arrows.listArrow) {
            a.update();
        }
//        System.out.println(arrows.listArrow.size());



        this.setStageNumber(stageNumber);

        if (Gdx.input.isTouched()){
            isTouched();
            if (fireball.state == Spell.State.PREPARE){
                circleX = Gdx.input.getX()-150;
                circleY = 928-Gdx.input.getY();
            }
        }else {
            hero.state = Hero.State.IDLE;
        }
        if (!Gdx.input.isTouched() && fireball.state == Fireball.State.PREPARE){
            if (castle.mana >= fireball.manaCost && fireball.cooldown<= 0){
                fireball.state = Fireball.State.ACTIVE;
                fireball.cooldown = fireball.maxCooldown;
                castle.mana -= fireball.manaCost;
            }else {
                fireball.state = Spell.State.INACTIVE;
            }
        }
        if (!Gdx.input.isTouched() && freeze.state == Freeze.State.PREPARE){
            if (castle.mana >= freeze.manaCost && freeze.cooldown<= 0){
                freeze.state = Freeze.State.ACTIVE;
                freeze.cooldown = freeze.maxCooldown;
                castle.mana -= freeze.manaCost;
            }else if (freeze.duration<= 0) {
                fireball.state = Spell.State.INACTIVE;
            }
        }
        if (freeze.state == Spell.State.ACTIVE){
            freeze.duration -= Gdx.graphics.getDeltaTime();
        }
        castleHPNumber.setText("HP : "+ (int) castle.HP,300,50);
        castleManaNumber.setText("Mana : " + (int) castle.mana,600,50);
    }

    public void setStageNumber(int stg) {
        stageNumber = stg;
        fontCache.setText(String.format("Stage. %d", stageNumber), 1200, 1025);
    }

    public boolean isTouched(){
            if (state != State.Paused && hero.attackCooldown <= 0) {
                hero.Attack();
                hero.state = Hero.State.ATTACK;
                Vector2 position = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                position = viewport.unproject(position);
                if (position.y >= 700) {
                    position.y = 700;
                } else if (position.y <= 200) {
                    position.y = 200;
                }
                hero.arrow.Y = position.y - 80;

                return true;
            }
        return false;
    }
    public int calculateScore(Stages stages){
        stages.life = (int) castle.HP;
        return stages.calculateKill(listEnemy) * 10 + stages.life*10;
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
        if (state != State.Paused && hero.attackCooldown <= 0) {
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
            hero.arrow.Y = position.y - 80;
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
            hero.Y = position.y - 220;
        }
        if (state != State.Paused && hero.attackCooldown <= 0) {
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
            hero.arrow.Y = position.y - 80;
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
            hero.Y = position.y - 220;
        }


//        player.setX(position.x);
//        player.setY(position.y);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    public Stages getStages1() {
        return stages1;
    }
}
