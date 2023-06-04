package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class MenuScreen extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;

    private Viewport viewport;
    private OrthographicCamera camera;
    SpriteBatch batch;

    int game_awal=20;
    int game_awal2=20;
    int game_awal3=20;
    int game_awal4=20;
    int game_awal5=20;
    int game_awal6=20;
    int coin=1000;
    int coin2=500;
    int coin3=500;
    int coin4=500;
    int diamond=10;
    int diamond1=5;
    int diamond2=5;
    int diamond3=5;
    int damage=50;
    float speed=1;
    int manaCost=30;
    int kekuatan_spell=100;
    int spellCooldown=30;
    int max_hp_castle=120;

    ArrayList<String> data_upgrade=new ArrayList<>();
    Stage stage;
    Label titleLabel, optionSoundLabel;
    TextButton playButton, exitButton, powerUpButton, optionDoneButton;
    Window optionWindow;

    InputMultiplexer multiInput;

    MenuScreen thisScreen;

    public MenuScreen() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public MenuScreen(Game parent) {
        parentGame = parent;
        this.Initialize();
    }

    public void Initialize() {
        assetManager = ((MyGdxGame) parentGame).getAssetManager();
        thisScreen = this;

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        OrthographicCamera stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        data_upgrade.add(String.valueOf(coin));
        data_upgrade.add(String.valueOf(coin2));
        data_upgrade.add(String.valueOf(coin3));
        data_upgrade.add(String.valueOf(coin4));
        data_upgrade.add(String.valueOf(diamond));
        data_upgrade.add(String.valueOf(game_awal));
        data_upgrade.add(String.valueOf(game_awal2));
        data_upgrade.add(String.valueOf(game_awal3));
        data_upgrade.add(String.valueOf(game_awal4));
        data_upgrade.add(String.valueOf(game_awal5));
        data_upgrade.add(String.valueOf(game_awal6));
        data_upgrade.add(String.valueOf(diamond1));
        data_upgrade.add(String.valueOf(diamond2));
        data_upgrade.add(String.valueOf(diamond3));
        data_upgrade.add(String.valueOf(damage));
        data_upgrade.add(String.valueOf(speed));
        data_upgrade.add(String.valueOf(kekuatan_spell));
        data_upgrade.add(String.valueOf(manaCost));
        data_upgrade.add(String.valueOf(spellCooldown));
        data_upgrade.add(String.valueOf(max_hp_castle));
        writeFile(data_upgrade,2);

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);



        titleLabel = new Label("", mySkin);//Defend Of The Castle
        Label.LabelStyle style = new Label.LabelStyle(titleLabel.getStyle());
        style.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        titleLabel.setStyle(style);
        titleLabel.setWidth(680);
        titleLabel.setX(20);
        titleLabel.setY(900);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);

        playButton = new TextButton("Play", mySkin);
        playButton.setHeight(120);
        playButton.setWidth(380);
        playButton.setPosition(1450, 650);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new StageSelection(parentGame));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        powerUpButton = new TextButton("Power Up", mySkin);
        powerUpButton.setHeight(120);
        powerUpButton.setWidth(380);
        powerUpButton.setPosition(1450, 400);
        powerUpButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    //Codingan Gwan
                    thisScreen.dispose();
                    parentGame.setScreen(new PowerUpScreen(parentGame));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(powerUpButton);

        exitButton = new TextButton("Exit", mySkin);
        exitButton.setHeight(120);
        exitButton.setWidth(380);
        exitButton.setPosition(1450, 150);
        exitButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    System.exit(1);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(exitButton);

        optionWindow = new Window("Option", mySkin);
        optionWindow.setHeight(320);
        optionWindow.setWidth(480);
        optionWindow.setPosition(320 - optionWindow.getWidth() / 2, 240 - optionWindow.getHeight() / 2);
        optionWindow.setMovable(false);
        optionWindow.setModal(true);
        //optionWindow.setResizable(true);
        optionWindow.setVisible(false);
        optionWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(optionWindow);

//        optionSoundLabel = new Label("Sound :", mySkin);
//        optionSoundLabel.setAlignment(Align.right);
//        optionSoundLabel.setY(250);
//        optionSoundLabel.setX(0);
//        optionSoundLabel.setWidth(optionWindow.getWidth() / 2);
//        optionWindow.addActor(optionSoundLabel);

        optionDoneButton = new TextButton("Done",mySkin);
        optionDoneButton.setWidth(120);
        optionDoneButton.setHeight(36);
        optionDoneButton.setPosition(optionWindow.getWidth()/2-optionDoneButton.getWidth()/2,72);
        optionDoneButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight())
                {
                    optionWindow.setVisible(false);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        optionWindow.addActor(optionDoneButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
        ((MyGdxGame) parentGame).PlayMenuMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Texture background = assetManager.get("MenuScreen.png", Texture.class);

        batch.draw(background,0,0);

        batch.end();
        update();

        stage.act();
        stage.draw();
    }

    public void update()
    {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
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

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
