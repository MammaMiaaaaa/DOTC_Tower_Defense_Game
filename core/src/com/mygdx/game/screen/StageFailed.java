package com.mygdx.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.util.DataHandling;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

public class StageFailed extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;
    int coin_bonus_stage1=50;
    int diamond_bonus_stage1=2;
    int kill;
    int life;
    int score;
    int coin;
    int diamond;
    private Viewport viewport;
    private OrthographicCamera camera, stageCamera;
    SpriteBatch batch;
    BitmapFontCache fontCache1,fontCache2,fontCache3,fontCache4,fontCache5;
    Stage stage;
    Label titleLabel, koinLabel, diamondLabel;
    TextButton playButton, optionButton;
    Window optionWindow;
    InputMultiplexer multiInput;

    ArrayList<String> dataUpgrade = new ArrayList<>();

    StageFailed thisScreen;
    public StageFailed () {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public StageFailed (Game parent,int kill, int life, int score) {
        this.kill = kill;
        this.life = life;
        this.score = score;
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

        stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        readFile(dataUpgrade,2);

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);

        fontCache1 = new BitmapFontCache(MyGdxGame.font);
        fontCache1.setColor(Color.BLACK);
        fontCache1.setText(String.valueOf(kill), 720, 730);

        fontCache2 = new BitmapFontCache(MyGdxGame.font);
        fontCache2.setColor(Color.BLACK);
        fontCache2.setText(String.valueOf(life), 720, 630);

        fontCache3 = new BitmapFontCache(MyGdxGame.font);
        fontCache3.setColor(Color.BLACK);
        fontCache3.setText(String.valueOf(score), 720, 530);

        fontCache4 = new BitmapFontCache(MyGdxGame.font);
        fontCache4.setColor(Color.WHITE);
        fontCache4.setText(String.valueOf(coin_bonus_stage1), 750, 435);

        fontCache5 = new BitmapFontCache(MyGdxGame.font);
        fontCache5.setColor(Color.WHITE);
        fontCache5.setText(String.valueOf(diamond_bonus_stage1), 1020, 435);

        titleLabel = new Label("STAGE FAILED", mySkin);
        Label.LabelStyle style = new Label.LabelStyle(titleLabel.getStyle());
        style.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        titleLabel.setStyle(style);
        titleLabel.setWidth(640);
        titleLabel.setX(600);
        titleLabel.setY(1000);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);

        titleLabel = new Label("KILL", mySkin);
        Label.LabelStyle style1 = new Label.LabelStyle(titleLabel.getStyle());
        style1.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        titleLabel.setStyle(style1);
        titleLabel.setWidth(640);
        titleLabel.setX(300);
        titleLabel.setY(700);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);

        titleLabel = new Label("LIFE", mySkin);
        Label.LabelStyle style2 = new Label.LabelStyle(titleLabel.getStyle());
        style2.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        titleLabel.setStyle(style2);
        titleLabel.setWidth(640);
        titleLabel.setX(300);
        titleLabel.setY(600);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);

        titleLabel = new Label("SCORE", mySkin);
        Label.LabelStyle style3 = new Label.LabelStyle(titleLabel.getStyle());
        style3.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        titleLabel.setStyle(style3);
        titleLabel.setWidth(640);
        titleLabel.setX(300);
        titleLabel.setY(500);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);

        titleLabel = new Label("BONUS", mySkin);
        Label.LabelStyle style4 = new Label.LabelStyle(titleLabel.getStyle());
        style4.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        titleLabel.setStyle(style4);
        titleLabel.setWidth(640);
        titleLabel.setX(300);
        titleLabel.setY(405);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);

        coin = Integer.parseInt(getData(0));
        diamond = Integer.parseInt(getData(4));
        editFile(dataUpgrade,0,coin+coin_bonus_stage1,2);
        editFile(dataUpgrade,4,diamond+diamond_bonus_stage1,2);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
//        ((MyGdxGame) parentGame).PlayMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Texture background = assetManager.get("upgrade_background.png", Texture.class);

        batch.draw(background, 0, 0);
        Texture benteng2 = assetManager.get("benteng2.png", Texture.class);

        batch.draw(benteng2, 10, 20);
        Texture coin = assetManager.get("coin.png", Texture.class);

        batch.draw(coin, 900, 390);
        Texture diamond = assetManager.get("berlian.png", Texture.class);

        batch.draw(diamond, 1100, 390);
        Texture monster = assetManager.get("monster2.png", Texture.class);

        batch.draw(monster, 180, 20);
        Texture failed = assetManager.get("failed.png", Texture.class);

        batch.draw(failed, 1300, 350);
        fontCache1.draw(batch);
        fontCache2.draw(batch);
        fontCache3.draw(batch);
        fontCache4.draw(batch);
        fontCache5.draw(batch);
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
        thisScreen.dispose();
        parentGame.setScreen(new StageSelection(parentGame));
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
