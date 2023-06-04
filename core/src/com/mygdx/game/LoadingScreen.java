package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen, InputProcessor{
    Game parentGame;
    AssetManager assetManager;

    private Viewport viewport;
    private OrthographicCamera camera;
    SpriteBatch batch;

    BitmapFontCache text, title, loading;

    public LoadingScreen() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public LoadingScreen(Game parent) {
        parentGame = parent;
        this.Initialize();
    }

    public void Initialize() {
        assetManager = ((MyGdxGame)parentGame).getAssetManager();

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        text = new BitmapFontCache(MyGdxGame.font);
        text.setColor(Color.WHITE);

        title = new BitmapFontCache(MyGdxGame.font);
        title.setColor(Color.WHITE);

        loading = new BitmapFontCache(MyGdxGame.font);
        loading.setColor(Color.BLACK);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if(assetManager.isFinished())
        {
            Texture loadingScreen = assetManager.get("LoadingScreen.png", Texture.class);
            batch.draw(loadingScreen, 0,0);
        }

        loading.draw(batch);

        if (!assetManager.update())
        {
            title.draw(batch);
            text.draw(batch);
        }


        batch.end();

        this.update();
    }

    public void update() {
        if(assetManager.update())
        {
            loading.setText("", 590, 220);
        }
        else {
            title.setText("Defend Of The Castle", 640, 950);
            float progress = assetManager.getProgress() * 100;
            String loadtext = String.format("Loading %.2f%%", progress);
            text.setText(loadtext, 700, 220);
        }
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

    @Override
    public boolean keyDown(int keycode) {
        if(assetManager.isFinished()) {
            this.dispose();
            parentGame.setScreen(new MenuScreen(parentGame));
        }
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
        if(assetManager.isFinished()) {
            this.dispose();
            parentGame.setScreen(new MenuScreen(parentGame));
        }
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
