package com.mygdx.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.game.sprites.*;
import com.mygdx.game.util.DataHandling;
import com.mygdx.game.util.Timer;

import java.util.*;

public class GameScreen extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;

    TextButton pauseButton, resumeButton, restartButton, exitButton, playAgainButton, backToMenuButton, fireBallButton, arrowsButton, freezeButton, arrowsUpgradeButton, fireballUpgradeButton, freezeUpgradeButton, heroDamageUpgradeButton;
    Stage stg;
    boolean isSurvival = false;

    InputListener inputListener;
    private int roundEnemyCount;

    private Viewport viewport;
    private OrthographicCamera camera;
    SpriteBatch batch;

    Texture FireBallIcon, ArrowsIcon, FreezeIcon, FireBallIconCD, ArrowsIconCD, FreezeIconCD, Coin;

    InputMultiplexer multiInput;
    Enemy enemyParent1, enemyParent2;

    Castle castle;
    Hero hero;
    Fireball fireball;
    Freeze freeze;
    Earthquake earthquake;
    Arrows spellArrows;

    Window pauseWindow, gameoverWindow;

    GameScreen thisScreen;

    Sound arrowSFX, arrowSpellSFX, fireballSpellSFX, freezeSpellSFX;

    int stageNumber;
    int kill = 0;
    int survGold;
    int freezeUpgradeCost, fireballUpgradeCost, arrowsUpgradeCost, heroDamageUpgradeCost;


    BitmapFontCache fontCache, castleHPNumber, castleManaNumber, survGoldText;
    Random random = new Random();
    Stages stage;

    ArrayList<Enemy> listEnemy = new ArrayList<>();
    ArrayList<Enemy> tempListRoundEnemy = new ArrayList<>();
    ArrayList<String> stageHighScore = new ArrayList<>();

    Timer timer;

    float circleX;
    float circleY;

    int roundNumber = 0;

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
        // instantiate all class
        castle = new Castle();
        hero = new Hero();
        hero.setAttackCooldown(0);
        fireball = new Fireball();
        fireball.setCooldown(0);
        freeze = new Freeze();
        freeze.setCooldown(0);
        earthquake = new Earthquake();
        spellArrows = new Arrows();
        spellArrows.setCooldown(0);

        // init money and costs
        survGold = 2500;
        freezeUpgradeCost = 500;
        fireballUpgradeCost = 500;
        arrowsUpgradeCost = 500;
        heroDamageUpgradeCost = 500;


        // init stages
        stage = new Stages();
        stage.setStage(stageNumber);


        assetManager = ((MyGdxGame) parentGame).getAssetManager();
        thisScreen = this;
        timer = new Timer();

        // init camera and viewport
        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        OrthographicCamera stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stg = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        // init multiplexer
        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stg);

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);

        // init sfx
        arrowSFX = assetManager.get("sfx/arrowSFX.mp3", Sound.class);
        arrowSpellSFX = assetManager.get("sfx/arrowsSFX.mp3", Sound.class);
        fireballSpellSFX = assetManager.get("sfx/fireballSFX.mp3", Sound.class);
        freezeSpellSFX = assetManager.get("sfx/freezeSFX.mp3", Sound.class);

        // init fontcache
        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText("Stage. 1", 1200, 1025);

        // init bitmap font
        castleHPNumber = new BitmapFontCache(MyGdxGame.font);
        castleHPNumber.setColor(Color.RED);
        castleHPNumber.setText("HP : " + (int) castle.getHP(), 300, 50);
        castleManaNumber = new BitmapFontCache(MyGdxGame.font);
        castleManaNumber.setColor(Color.BLUE);
        castleManaNumber.setText("Mana : " + (int) castle.getMana(), 600, 50);
        survGoldText = new BitmapFontCache(MyGdxGame.font);
        survGoldText.setColor(Color.GOLD);

        // init musuh
        switch (stageNumber) {
            case 0:
                // case survival
                isSurvival = true;

                // enemy awal
                stage.addOrc(1, Enemy.Lane.TWO);
                stage.addOrc(3, Enemy.Lane.ONE);
                stage.addOgre(10, Enemy.Lane.FOUR);
                stage.addOgre(8.5f, Enemy.Lane.FOUR);
                stage.addGoblin(30, Enemy.Lane.ONE);
                stage.addGoblin(12, Enemy.Lane.THREE);

                stage.addOrc(8, Enemy.Lane.TWO);
                stage.addOrc(9, Enemy.Lane.ONE);
                stage.addOgre(12, Enemy.Lane.THREE);
                stage.addOgre(9.5f, Enemy.Lane.FOUR);
                stage.addGoblin(20, Enemy.Lane.ONE);
                stage.addGoblin(22, Enemy.Lane.THREE);

                stage.addToArray(listEnemy);


                // set init enemy round
                roundEnemyCount = 12;

                // set init templistroundenemy
                tempListRoundEnemy.addAll(listEnemy);

                break;
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
                break;
            case 2:
                stage.addOrc(1, Enemy.Lane.THREE);
                stage.addOgre(5, Enemy.Lane.ONE);
                stage.addOgre(10, Enemy.Lane.FOUR);
                stage.addOrc(15, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Orc, 2, 20f, 1f, 2);
                stage.addOgre(25, Enemy.Lane.FOUR);
                stage.addOgre(30, Enemy.Lane.ONE);
                stage.addOrc(35, Enemy.Lane.THREE);
                stage.addOgre(40, Enemy.Lane.TWO);
                stage.addOgre(45, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Orc, 4, 50f, 1f, 1);
                stage.addToArray(listEnemy);
                break;
            case 3:
                stage.addOrc(1, Enemy.Lane.ONE);
                stage.addOgre(5, Enemy.Lane.THREE);
                stage.addOrc(10, Enemy.Lane.FOUR);
                stage.addOrc(15, Enemy.Lane.TWO);
                stage.addGoblin(20, Enemy.Lane.THREE);
                stage.addWave(Enemy.Type.Ogre, 3, 25, 1f, 2);
                stage.addGoblin(30, Enemy.Lane.ONE);
                stage.addOrc(35, Enemy.Lane.THREE);
                stage.addOgre(40, Enemy.Lane.FOUR);
                stage.addOgre(45, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Orc, 3, 50, 1f, 1);
                stage.addGoblin(55, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Goblin, 4, 58, 1f, 1);
                stage.addToArray(listEnemy);
                break;
            case 4:
                stage.addOrc(1, Enemy.Lane.FOUR);
                stage.addOgre(5, Enemy.Lane.THREE);
                stage.addOgre(10, Enemy.Lane.TWO);
                stage.addGoblin(15, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Ogre, 2, 17, 1f, 3);
                stage.addWave(Enemy.Type.Ogre, 3, 20, 1f, 1);
                stage.addOrc(25, Enemy.Lane.ONE);
                stage.addOgre(30, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Orc, 3, 35, 1f, 2);
                stage.addWave(Enemy.Type.Ogre, 4, 42, 1f, 1);
                stage.addOgre(45, Enemy.Lane.FOUR);
                stage.addGoblin(50, Enemy.Lane.TWO);
                stage.addToArray(listEnemy);
                break;
            case 5:
                stage.addOgre(1, Enemy.Lane.THREE);
                stage.addOgre(5, Enemy.Lane.TWO);
                stage.addGoblin(10, Enemy.Lane.ONE);
                stage.addOrc(15, Enemy.Lane.FOUR);
                stage.addOgre(17, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Ogre, 4, 20, 1f, 1);
                stage.addGoblin(29, Enemy.Lane.ONE);
                stage.addOrc(30, Enemy.Lane.TWO);
                stage.addOgre(35, Enemy.Lane.THREE);
                stage.addWave(Enemy.Type.Orc, 4, 40, 1f, 1);
                stage.addGoblin(47, Enemy.Lane.FOUR);
                stage.addGoblin(50, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Ogre, 2, 52, 1f, 3);
                stage.addWave(Enemy.Type.Goblin, 4, 56, 1f, 1);
                stage.addGoblin(62, Enemy.Lane.ONE);
                stage.addOrc(65, Enemy.Lane.TWO);
                stage.addOgre(67, Enemy.Lane.THREE);
                stage.addToArray(listEnemy);
                break;
            case 6:
                stage.addWave(Enemy.Type.Orc, 3, 1, 1f, 2);
                stage.addOrc(7, Enemy.Lane.ONE);
                stage.addOgre(10, Enemy.Lane.THREE);
                stage.addOrc(15, Enemy.Lane.FOUR);
                stage.addGoblin(20, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Ogre, 2, 25, 1f, 3);
                stage.addWave(Enemy.Type.Goblin, 3, 30, 1f, 1);
                stage.addGoblin(38, Enemy.Lane.FOUR);
                stage.addGoblin(40, Enemy.Lane.TWO);
                stage.addOgre(45, Enemy.Lane.ONE);
                stage.addOgre(50, Enemy.Lane.THREE);
                stage.addWave(Enemy.Type.Ogre, 3, 53, 1f, 2);
                stage.addWave(Enemy.Type.Goblin, 4, 59, 1f, 1);
                stage.addOgre(67, Enemy.Lane.THREE);
                stage.addOrc(70, Enemy.Lane.ONE);
                stage.addToArray(listEnemy);
                break;
            case 7:
                stage.addOrc(1, Enemy.Lane.FOUR);
                stage.addOrc(5, Enemy.Lane.TWO);
                stage.addOgre(10, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Goblin, 4, 15, 1f, 1);
                stage.addGoblin(20, Enemy.Lane.FOUR);
                stage.addGoblin(25, Enemy.Lane.TWO);
                stage.addOgre(30, Enemy.Lane.THREE);
                stage.addOgre(35, Enemy.Lane.ONE);
                stage.addGoblin(40, Enemy.Lane.FOUR);
                stage.addGoblin(45, Enemy.Lane.TWO);
                stage.addWave(Enemy.Type.Ogre, 3, 50, 1f, 2);
                stage.addWave(Enemy.Type.Goblin, 3, 55, 1f, 2);
                stage.addGoblin(60, Enemy.Lane.TWO);
                stage.addGoblin(65, Enemy.Lane.THREE);
                stage.addOgre(70, Enemy.Lane.ONE);
                stage.addOgre(75, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Ogre, 2, 80, 1f, 3);
                stage.addWave(Enemy.Type.Goblin, 3, 85, 1f, 2);
                stage.addWave(Enemy.Type.Goblin, 4, 92, 1f, 1);
                stage.addOrc(98, Enemy.Lane.FOUR);
                stage.addOgre(101, Enemy.Lane.ONE);
                stage.addToArray(listEnemy);
                break;
            case 8:
                stage.addWave(Enemy.Type.Goblin, 4, 1, 0f, 1);
                stage.addGoblin(10, Enemy.Lane.THREE);
                stage.addGoblin(15, Enemy.Lane.ONE);
                stage.addOgre(18, Enemy.Lane.TWO);
                stage.addOgre(20, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Goblin, 4, 25, 0.5f, 1);
                stage.addWave(Enemy.Type.Ogre, 4, 33, 0.5f, 1);
                stage.addWave(Enemy.Type.Goblin, 3, 39, 0.5f, 2);
                stage.addOrc(45, Enemy.Lane.ONE);
                stage.addOrc(50, Enemy.Lane.TWO);
                stage.addOgre(54, Enemy.Lane.THREE);
                stage.addOgre(57, Enemy.Lane.FOUR);
                stage.addGoblin(60, Enemy.Lane.THREE);
                stage.addGoblin(65, Enemy.Lane.ONE);
                stage.addGoblin(67, Enemy.Lane.TWO);
                stage.addGoblin(70, Enemy.Lane.FOUR);
                stage.addWave(Enemy.Type.Orc, 4, 73, 0f, 1);
                stage.addWave(Enemy.Type.Ogre, 4, 81, 0f, 1);
                stage.addWave(Enemy.Type.Goblin, 4, 91, 0f, 1);
                stage.addOgre(95, Enemy.Lane.THREE);
                stage.addGoblin(98, Enemy.Lane.ONE);
                stage.addWave(Enemy.Type.Goblin, 3, 100, 0f, 1);
                stage.addWave(Enemy.Type.Orc, 4, 106, 0f, 1);
                stage.addWave(Enemy.Type.Orc, 2, 113, 0f, 2);
                stage.addToArray(listEnemy);
                break;
        }

        // pause button
        pauseButton = new TextButton("", mySkin);
        pauseButton.setHeight(35);
        pauseButton.setWidth(35);
        pauseButton.setPosition(1780, 1005);
        pauseButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    gamePause();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stg.addActor(pauseButton);


        // pause window
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

        resumeButton = new TextButton("Resume", mySkin);
        resumeButton.setWidth(120);
        resumeButton.setHeight(36);
        resumeButton.setPosition(resumeButton.getWidth() / 2 - resumeButton.getWidth() / 2 + 175, 228);
        resumeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    gameResume();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        pauseWindow.addActor(resumeButton);

        // restart button
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

        // exit button
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


        // spells
        // fireball button
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
                if (fireball.getCooldown() <= 0F && castle.getMana() >= fireball.getCooldown()) {
                    fireball.setState(Fireball.State.PREPARE);
                    circleX = x + 1250;
                    circleY = y - 100;
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
                if (fireball.getCooldown() <= 0 && castle.getMana() >= fireball.getCooldown()) {
                    fireball.setState(Fireball.State.PREPARE);
                    circleX = x + 1250;
                    circleY = y - 100;
                }
            }

        });
        stg.addActor(fireBallButton);


        // freeze button
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
                if (freeze.getCooldown() <= 0 && castle.getMana() >= freeze.getCooldown()) {
                    freeze.setState(Spell.State.PREPARE);
                    circleX = x + 1400;
                    circleY = y - 100;
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
                if (freeze.getCooldown() <= 0 && castle.getMana() >= freeze.getCooldown()) {
                    freeze.setState(Spell.State.PREPARE);
                    circleX = x + 1400;
                    circleY = y - 100;
                }

            }

        });
        stg.addActor(freezeButton);

        // arrow spell button
        arrowsButton = new TextButton("Arrows", mySkin);
        arrowsButton.setHeight(100);
        arrowsButton.setWidth(100);
        arrowsButton.setPosition(1700, 50);
        arrowsButton.setColor(Color.WHITE);

        arrowsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (castle.getMana() >= spellArrows.getManaCost() && spellArrows.getCooldown() <= 0) {

                        spellArrows.setArrowCount(spellArrows.getSpellArrowCount());
                        spellArrows.setState(Spell.State.ACTIVE);

                        spellArrows.setDurationStarted(true);

                        castle.setMana(castle.getMana() - spellArrows.getManaCost());
                        spellArrows.setCooldown(spellArrows.getMaxCooldown());

                        // play sound
                        arrowSpellSFX.stop();
                        arrowSpellSFX.play();
                    }

                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stg.addActor(arrowsButton);

        // upgrade buttons
        // fireball upgrade button
        fireballUpgradeButton = new TextButton(String.valueOf(fireballUpgradeCost), mySkin);
        fireballUpgradeButton.setHeight(50);
        fireballUpgradeButton.setWidth(100);
        fireballUpgradeButton.setPosition(1400, 200);
        fireballUpgradeButton.setColor(Color.WHITE);
        fireballUpgradeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (fireballUpgradeCost <= survGold) {
                        fireball.setDamage((float) (fireball.getDamage() * 1.1));
                        fireball.setManaCost((float) (fireball.getManaCost() * 1.02));
                        fireball.setMaxCooldown((float) (fireball.getMaxCooldown() * 0.95));


                        survGold -= fireballUpgradeCost;
                        fireballUpgradeCost += 100;
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stg.addActor(fireballUpgradeButton);

        // freeze upgrade button
        freezeUpgradeButton = new TextButton(String.valueOf(freezeUpgradeCost), mySkin);
        freezeUpgradeButton.setHeight(50);
        freezeUpgradeButton.setWidth(100);
        freezeUpgradeButton.setPosition(1550, 200);
        freezeUpgradeButton.setColor(Color.WHITE);
        freezeUpgradeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (freezeUpgradeCost <= survGold) {
                        freeze.setMaxDuration((float) (freeze.getMaxDuration() * 1.02));
                        freeze.setManaCost((float) (freeze.getManaCost() * 1.02));
                        freeze.setMaxCooldown((float) (freeze.getMaxCooldown() * 0.95));


                        survGold -= freezeUpgradeCost;
                        freezeUpgradeCost += 100;
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stg.addActor(freezeUpgradeButton);

        // spell arrows upgrade button
        arrowsUpgradeButton = new TextButton(String.valueOf(arrowsUpgradeCost), mySkin);
        arrowsUpgradeButton.setHeight(50);
        arrowsUpgradeButton.setWidth(100);
        arrowsUpgradeButton.setPosition(1700, 200);
        arrowsUpgradeButton.setColor(Color.WHITE);
        arrowsUpgradeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (arrowsUpgradeCost <= survGold) {
                        spellArrows.setDamage((float) (spellArrows.getDamage() * 1.1));
                        spellArrows.setManaCost((float) (spellArrows.getManaCost() * 1.02));
                        spellArrows.setSpellArrowCount(spellArrows.getSpellArrowCount() + 1);
                        spellArrows.setArrowSpellSpeed((float) (spellArrows.getArrowSpellSpeed() * 1.02));


                        survGold -= arrowsUpgradeCost;
                        arrowsUpgradeCost += 100;
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stg.addActor(arrowsUpgradeButton);

        // in general upgrade player button
        heroDamageUpgradeButton = new TextButton(String.valueOf(heroDamageUpgradeCost), mySkin);
        heroDamageUpgradeButton.setHeight(50);
        heroDamageUpgradeButton.setWidth(100);
        heroDamageUpgradeButton.setPosition(1250, 200);
        heroDamageUpgradeButton.setColor(Color.WHITE);
        heroDamageUpgradeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (heroDamageUpgradeCost <= survGold) {
                        hero.setDamage((float) (hero.getDamage() * 1.01));
                        hero.setAttackCooldownAwal((float) (hero.getAttackCooldownAwal() * 0.98));

                        float oldDeficitHP = castle.getMaxHP() - castle.getHP();
                        castle.setMaxHP((float) (castle.getMaxHP() * 1.01));
                        castle.setHP(castle.getMaxHP() - oldDeficitHP);

                        castle.setMaxMana((float) (castle.getMaxMana() * 1.01));


                        survGold -= heroDamageUpgradeCost;
                        heroDamageUpgradeCost += 100;
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        stg.addActor(heroDamageUpgradeButton);


        // game over window
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


        // play again button
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

        // back to menu button
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

    private void gameResume() {
        state = State.Running;
        ((MyGdxGame) parentGame).PlayInGameMusic();
        pauseWindow.setVisible(false);
    }

    private void gamePause() {
        pauseWindow.setVisible(true);
        state = State.Paused;
        ((MyGdxGame) parentGame).PauseInGameMusic();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
        ((MyGdxGame) parentGame).PlayInGameMusic();
    }

    @Override
    public void render(float delta) {
        // init
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // background
        Texture background = assetManager.get("In Game.png", Texture.class);

        // spell icons / textures
        FreezeIcon = assetManager.get("FreezeButton.png", Texture.class);
        ArrowsIcon = assetManager.get("ArrowsButton.png", Texture.class);
        FireBallIcon = assetManager.get("FireBallButton.png", Texture.class);
        FreezeIconCD = assetManager.get("FreezeButtonCD.png", Texture.class);
        ArrowsIconCD = assetManager.get("ArrowsButtonCD.png", Texture.class);
        FireBallIconCD = assetManager.get("FireBallButtonCD.png", Texture.class);
        Coin = assetManager.get("newcoin.png", Texture.class);
        Texture Mana = assetManager.get("mana.png", Texture.class);
        Texture Heart = assetManager.get("heart.png", Texture.class);
        Texture Castle = assetManager.get("castle.png", Texture.class);
        Texture Pause = assetManager.get("pausebutton.png", Texture.class);

        batch.draw(background, 0, 0);
        castle.draw(batch);
        hero.draw(batch);


        // draw player's arrow
        for (Arrow a : hero.getListArrow()) {
            a.draw(batch);
        }

        // draw enemy
        for (Enemy enemy : listEnemy) {
            if (enemy.getSpawnTime() <= timer.getSecond() + timer.getMinute() * 60) {
                enemy.setDX(-1);
                enemy.draw(batch);

            }
        }

        if (fireball.getState() == Spell.State.PREPARE && fireball.getCooldown() <= 0) {
            fireball.drawAOE(batch, circleX, circleY);
        }
        if (freeze.getState() == Spell.State.PREPARE && freeze.getCooldown() <= 0) {
            freeze.drawAOE(batch, circleX, circleY);
        }
        spellArrows.draw(batch);


        // switch different state
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
        survGoldText.draw(batch);
        timer.draw(batch);
        fireball.draw(batch, circleX, circleY);

        //draw spell text status
        // fireball.drawStatus(batch,1440,40);
        // freeze.drawStatus(batch,1590,40);
        // spellArrows.drawStatus(batch,1740,40);
        for (Enemy e: listEnemy
             ) {
            if (e.state!= Enemy.State.DEATH){
                e.drawHP(batch,(int)e.getX()+90,(int)e.getY()+50);
                e.drawDamageTaken(batch);
            }

        }


        stg.act();
        stg.draw();
        //draw spell buttons icon
        if (fireball.getCooldown() <= 0) {
            batch.draw(FireBallIcon, 1375, 25);
        } else {
            batch.draw(FireBallIconCD, 1375, 25);
        }
        if (freeze.getCooldown() <= 0) {
            batch.draw(FreezeIcon, 1525, 25);
        } else {
            batch.draw(FreezeIconCD, 1525, 25);
        }
        if (spellArrows.getCooldown() <= 0) {
            batch.draw(ArrowsIcon, 1675, 25);
        } else {
            batch.draw(ArrowsIconCD, 1675, 25);
        }
        batch.draw(Pause, 1750, 975);
        batch.draw(Castle, 1225, 25);
        batch.draw(Coin, 850, 5);
        batch.draw(Mana, 540, 5);
        batch.draw(Heart, 230, 5);
        batch.end();
    }

    public void update() {
        // check for survival
        boolean end = !isSurvival;

        // set end to false if there is still enemy alive
        for (Enemy e : listEnemy
        ) {
            if (e.state != Enemy.State.DEATH) {
                end = false;
                break;
            }
        }


        // if end is true, check if player win or lose
        if (end) {
            for (Enemy e : listEnemy
            ) {
                if (e.state == Enemy.State.DEATH) {
                    kill++;
                }
            }

            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 1) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 0, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 2) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 1, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 3) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 2, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 4) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 3, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 5) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 4, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 6) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 5, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 7) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 6, calculateScore(stage), 1);
            }
            if (calculateScore(stage) > stage.getHighScore() && stageNumber == 8) {
                stageHighScore.clear();
                readFile(stageHighScore, 1);
                editFile(stageHighScore, 7, calculateScore(stage), 1);
            }

            // dispose and stop music
            thisScreen.dispose();
            ((MyGdxGame) parentGame).StopInGameMusic();

            parentGame.setScreen(new StageSuccess(parentGame, kill, (int) castle.getHP(), calculateScore(stage)));

        }

        // check if player loses
        if (castle.getHP() <= 0) {
            castle.setHP(0);
            for (Enemy e : listEnemy
            ) {
                if (e.state == Enemy.State.DEATH) {
                    kill++;
                }
            }

            state = State.Paused;
            ((MyGdxGame) parentGame).StopInGameMusic();
            thisScreen.dispose();
//            parentGame.setScreen(new StageSelection(parentGame));

            parentGame.setScreen(new StageFailed(parentGame, kill, (int) castle.getHP(), 0));
//            gameoverWindow.setVisible(true);

        }

        // main loop for all enemy
        for (Enemy e : listEnemy) {
            // call the enemy's update
            e.update();

            // check if enemy is in range of castle
            if (e.CanAttack()) {
                castle.takeDamage(e);
                e.setAttackCooldown(3);
            }

            // check if enemy is in range of fireball
            if (fireball.CanAttack(e, circleX, circleY) && fireball.getState() == Spell.State.ACTIVE) {
                e.Attacked(fireball);
                fireball.setTotalDamage(fireball.getTotalDamage() + fireball.getDamage());
                e.drawDamageTaken(batch,fireball);
            }

            // check if enemy is in range of freeze
            if (freeze.CanAttack(e, circleX, circleY) && freeze.getState() == Spell.State.ACTIVE) {
                e.Attacked(freeze);
                e.drawDamageTaken(batch,freeze);
                if (e.isFrozen(freeze.getDuration())) {
                    e.state = Enemy.State.FROZEN;
                } else {
                    e.state = Enemy.State.RUN;
                }

            }

            // check if enemy is in range of arrows (from player)
            for (Arrow a : hero.getListArrow()) {
                if (a.CanAttack(e) && a.getState() == Arrow.State.ACTIVE) {
                    e.Attacked(a);
                    e.drawDamageTaken(batch,a);
                    a.setState(Arrow.State.INACTIVE);
                }
            }

            // check if enemy is in range of arrows (from spell)
            for (Arrow a : spellArrows.getListArrow()) {
                if (a.CanAttack(e) && a.getState() == Arrow.State.ACTIVE) {
                    e.Attacked(a);
                    e.drawDamageTaken(batch,a);
                    a.setState(Arrow.State.INACTIVE);
                }
            }


        }
        if (fireball.getState() == Spell.State.ACTIVE){
            fireball.drawTotalDamageGiven(batch, circleX,circleY+400);
        }

        // update the arrow (from player, not spell)
        for (Arrow a : hero.getListArrow()) {
            a.update();
        }

        // call update
        castle.update();
        hero.update();

        // update spell upgrade cost
        fireballUpgradeButton.setText(String.valueOf(fireballUpgradeCost));
        freezeUpgradeButton.setText(String.valueOf(freezeUpgradeCost));
        arrowsUpgradeButton.setText(String.valueOf(arrowsUpgradeCost));
        heroDamageUpgradeButton.setText(String.valueOf(heroDamageUpgradeCost));

        // update spell cooldown
        if (fireball.getState() != Spell.State.PREPARE) fireball.update();
        if (freeze.getState() != Spell.State.PREPARE) freeze.update();

        // update timer
        timer.update();

        spellArrows.update();
        for (Arrow a : spellArrows.getListArrow()) {
            a.update();
        }

        this.updateStageNumber(stageNumber);

        if (Gdx.input.isTouched()) {
//            isTouched();
            if (fireball.getState() == Spell.State.PREPARE) {
                circleX = Gdx.input.getX() - 150;
                circleY = 928 - Gdx.input.getY();
            }
        } else {
            hero.setState(Hero.State.IDLE);
        }
        if (!Gdx.input.isTouched() && fireball.getState() == Fireball.State.PREPARE) {
            if (castle.getMana() >= fireball.getManaCost() && fireball.getCooldown() <= 0) {
                fireball.setState(Fireball.State.ACTIVE);
                fireball.setCooldown(fireball.getMaxCooldown());
                castle.setMana(castle.getMana() - fireball.getManaCost());

                // play sound
                fireballSpellSFX.stop();
                fireballSpellSFX.play();
            } else {
                fireball.setState(Spell.State.INACTIVE);
            }
        }

        if (!Gdx.input.isTouched() && freeze.getState() == Freeze.State.PREPARE) {
            if (castle.getMana() >= freeze.getManaCost() && freeze.getCooldown() <= 0) {
                freeze.setState(Freeze.State.ACTIVE);
                freeze.setCooldown(freeze.getMaxCooldown());
                castle.setMana(castle.getMana() - freeze.getManaCost());

                // play sound
                freezeSpellSFX.stop();
                freezeSpellSFX.play();
            } else if (freeze.getDuration() <= 0) {
                freeze.setState(Spell.State.INACTIVE);
            }
        }
        if (freeze.getState() == Spell.State.ACTIVE) {
            freeze.duration -= Gdx.graphics.getDeltaTime();
        }

        // update HP and Mana text
        castleHPNumber.setText(" : " + (int) castle.getHP(), 300, 50);
        castleManaNumber.setText(" : " + (int) castle.getMana(), 600, 50);
        survGoldText.setText(" : " + survGold, 900, 50);

        // for survival
        if (isSurvival) {
            boolean allEnemyDied = true;

            for (Enemy e :
                    listEnemy) {
                if (e.state != Enemy.State.DEATH) {
                    allEnemyDied = false;
                    break;
                }
            }
            for (Enemy e :
                    listEnemy) {
                if (e.state == Enemy.State.DEATH && !e.isGoldDroped()) {
                    // add gold to player
                    survGold += e.getGoldDrop();

                    // set gold droped to true
                    // this is to prevent gold drop more than once
                    e.setGoldDroped(true);

                    // add mana to player
                    castle.setMana(castle.getMana() + 1);
                }
            }

            if (allEnemyDied) generateNewRound();
        }
    }

    private void generateNewRound() {
        roundEnemyCount++;
        roundNumber++;

        listEnemy.clear();

        levelUpAllOldDNA(tempListRoundEnemy);

        for (int i = 0; i < roundEnemyCount; i++) {
            float randomTypeGenerator = random.nextFloat();

            // Genetic Algorithm kerja di sini
            List<Float> tempDNA = generateDNA(tempListRoundEnemy);

            // generate random lane
            int randomLane = random.nextInt(Enemy.Lane.values().length);

            // generate random spawn time
            float randomSpawnTime = random.nextFloat() * 15 + 5 * random.nextFloat();
            random.setSeed(System.currentTimeMillis());

            // randomly pick from the enemyParent
            if (randomTypeGenerator < 0.475) {
                // get the enemyParent1 classname
                String enemyParent1ClassName = enemyParent1.getClass().getSimpleName();
                switch (enemyParent1ClassName) {
                    case "Orc":
                        listEnemy.add(new Orc(tempDNA, randomLane, randomSpawnTime));
                        break;
                    case "Goblin":
                        listEnemy.add(new Goblin(tempDNA, randomLane, randomSpawnTime));
                        break;
                    case "Ogre":
                        listEnemy.add(new Ogre(tempDNA, randomLane, randomSpawnTime));
                        break;
                }

            } else if (randomTypeGenerator < 0.95) {
                // get the enemyParent2 classname
                String enemyParent2ClassName = enemyParent2.getClass().getSimpleName();
                switch (enemyParent2ClassName) {
                    case "Orc":
                        listEnemy.add(new Orc(tempDNA, randomLane, randomSpawnTime));
                        break;
                    case "Goblin":
                        listEnemy.add(new Goblin(tempDNA, randomLane, randomSpawnTime));
                        break;
                    case "Ogre":
                        listEnemy.add(new Ogre(tempDNA, randomLane, randomSpawnTime));
                        break;
                }
            } else {
                // randomly pick from all enemy type
                int randomTypeGeneratorFromAllEnemyType = random.nextInt(3);
                switch (randomTypeGeneratorFromAllEnemyType) {
                    case 0:
                        listEnemy.add(new Orc(tempDNA, randomLane, randomSpawnTime));
                        break;
                    case 1:
                        listEnemy.add(new Goblin(tempDNA, randomLane, randomSpawnTime));
                        break;
                    case 2:
                        listEnemy.add(new Ogre(tempDNA, randomLane, randomSpawnTime));
                        break;
                }
            }

        }


        tempListRoundEnemy.clear();
        tempListRoundEnemy.addAll(listEnemy);

    }

    private List<Float> generateDNA(List<Enemy> listRoundEnemy) {

        // calculate all fitness total
        float fitnessTotal = 0;
        for (Enemy e :
                listRoundEnemy) {
            fitnessTotal += e.getFitness();
        }

        // calculate all fitness percentage
        float[] fitnessPercentage = new float[listRoundEnemy.size()];
        int i = 0;
        for (Enemy e :
                listRoundEnemy) {
            fitnessPercentage[i] = e.getFitness() / fitnessTotal;
            i++;
        }

        // calculate all fitness percentage cumulative
        float[] fitnessPercentageCumulative = new float[listRoundEnemy.size()];
        fitnessPercentageCumulative[0] = fitnessPercentage[0];
        for (int j = 1; j < fitnessPercentage.length; j++) {
            fitnessPercentageCumulative[j] = fitnessPercentageCumulative[j - 1] + fitnessPercentage[j];
        }

        // generate random number
        float randomNumber = random.nextFloat();

        // find 2 parent based on random number
        enemyParent1 = null;
        enemyParent2 = null;
        for (int j = 0; j < fitnessPercentageCumulative.length; j++) {
            if (randomNumber <= fitnessPercentageCumulative[j]) {
                enemyParent1 = tempListRoundEnemy.get(j);
                break;
            }
        }
        randomNumber = random.nextFloat();
        for (int j = 0; j < fitnessPercentageCumulative.length; j++) {
            if (randomNumber <= fitnessPercentageCumulative[j]) {
                enemyParent2 = tempListRoundEnemy.get(j);
                break;
            }
        }

        // generate child DNA, uniform crossover
        List<Float> childDNA = new ArrayList<>();
        childDNA.add(0f);
        childDNA.add(0f);
        childDNA.add(0f);
        childDNA.add(0f);
        childDNA.add(0f);

        for (int j = 0; j < childDNA.size(); j++) {
            if (random.nextBoolean()) {
                childDNA.set(j, enemyParent1.getDna().get(j));
            } else {
                childDNA.set(j, enemyParent2.getDna().get(j));
            }
        }

        float mutationChance = random.nextFloat();

        if (mutationChance < 0.1) {
            // mutation options
            // 1. single-point Mutation
            // 2. Modified Single-point mutation
            // 3. Swap Mutations
            // 4. Shuffle Mutations
            // 5. Inversion Mutation
            // choose randomly between four mutations
            int mutationType = random.nextInt(5);

            if (mutationType == 0) {
                // single-point mutation
                int randomIndex = random.nextInt(childDNA.size());
                childDNA.set(randomIndex, (float) (childDNA.get(randomIndex) * (random.nextFloat() * 0.4 + 0.8)));
                System.out.println("Single-point Mutation");
            } else if (mutationType == 1) {
                // modifies single-mutation
                int randomIndex1 = random.nextInt(childDNA.size());
                int randomIndex2 = random.nextInt(childDNA.size());
                if (randomIndex1 > randomIndex2) {
                    int temp = randomIndex1;
                    randomIndex1 = randomIndex2;
                    randomIndex2 = temp;
                }
                for (int j = randomIndex1; j <= randomIndex2; j++) {
                    childDNA.set(j, (float) (childDNA.get(j) * (random.nextFloat() * 0.4 + 0.8)));
                }
                System.out.println("Modified Single-Point Mutation");
            } else if (mutationType == 2) {
                // swap mutation
                int randomIndex1 = random.nextInt(childDNA.size());
                int randomIndex2 = random.nextInt(childDNA.size());
                float temp = childDNA.get(randomIndex1);
                childDNA.set(randomIndex1, childDNA.get(randomIndex2));
                childDNA.set(randomIndex2, temp);
                System.out.println("Swap Mutation");
            } else if(mutationType == 3) {
                // shuffle mutation
                Collections.shuffle(childDNA);
                System.out.println("Shuffle Mutation");
            } else {
                // Inversion Mutations
                int randomIndex1 = random.nextInt(childDNA.size());
                int randomIndex2 = random.nextInt(childDNA.size());
                if (randomIndex1 > randomIndex2) {
                    int temp = randomIndex1;
                    randomIndex1 = randomIndex2;
                    randomIndex2 = temp;
                }
                for (int j = 0; j <= (randomIndex1-randomIndex2)/2; j++) {
                    float temp=childDNA.get(randomIndex1+j);
                    childDNA.set(randomIndex1+j, childDNA.get(randomIndex2-j));
                    childDNA.set(randomIndex2-j, temp);
                }
                System.out.println("Inverse Mutation");
            }
        }


        // print child dna
        System.out.println("------------------");
        System.out.println("New Enemy");
        System.out.println("Child DNA : ");
        for (float v : childDNA) System.out.print(v + " ");
        System.out.println();
        System.out.println("------------------");

        return childDNA;

    }

    private void levelUpAllOldDNA(List<Enemy> listRoundEnemy) {
        for (Enemy e :
                listRoundEnemy) {

            // masih belum tau kenapa ini 5 kali lipat dijankannya
            // level up all the old MaxHealth in the DNA
            e.getDna().set(0, e.getDna().get(0) * 1.05f);

            // level up all old speed in the DNA
            e.getDna().set(1, e.getDna().get(1) * 1.05f);

            // level up all old damage in the DNA
            e.getDna().set(2, e.getDna().get(2) * 1.05f);

            // level up all old physical resistance in the DNA
            e.getDna().set(3, e.getDna().get(3) * 1.05f);

            // level up all old magical resistance in the DNA
            e.getDna().set(4, e.getDna().get(4) * 1.05f);


        }
    }


    public void updateStageNumber(int stg) {
        if (isSurvival) {
            fontCache.setText(String.format("Round = %d", roundNumber), 1200, 1025);
        } else {
            stageNumber = stg;
            fontCache.setText(String.format("Stage = %d", stageNumber), 1200, 1025);
        }

    }

    public boolean isTouched() {
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

    public int calculateScore(Stages stages) {
        stages.setLife((int) castle.getHP());
        return stages.calculateKill(listEnemy) * 10 + stages.getLife() * 10;
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
        // escape key calls the pause menu
        if (keycode == Input.Keys.ESCAPE) {
            if (state == State.Paused) {
                state = State.Running;
                gameResume();
            } else {
                state = State.Paused;
                gamePause();
            }
        }

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
            hero.Attack();

            // play sfx
            arrowSFX.stop();
            arrowSFX.play();


            Vector2 position = new Vector2(screenX, screenY);
            position = viewport.unproject(position);
            if (position.y >= 700) {
                position.y = 700;
            } else if (position.y <= 200) {
                position.y = 200;
            }
            hero.getArrow().setY(position.y - 80);
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
            hero.Attack();
            Vector2 position = new Vector2(screenX, screenY);
            position = viewport.unproject(position);
            if (position.y >= 700) {
                position.y = 700;
            } else if (position.y <= 200) {
                position.y = 200;
            }
            hero.getArrow().setY(position.y - 80);
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
