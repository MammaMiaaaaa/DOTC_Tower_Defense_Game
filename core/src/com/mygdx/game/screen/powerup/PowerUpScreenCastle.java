package com.mygdx.game.screen.powerup;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.util.DataHandling;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screen.MenuScreen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PowerUpScreenCastle extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;

    private Viewport viewport;
    private OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFontCache fontCache,fontCache1,fontCache2,fontCache3,fontCache4;
    int game_awal=20;
    int coin=4000;
    int coin2=500;
    int diamond=50;
    int max_hp_castle=100;
    Stage stage;
    Label titleLabel, optionSoundLabel;
    TextButton playButton, optionButton, optionDoneButton;
    Window optionWindow;
    ArrayList<String>data_upgrade2=new ArrayList<>();
    InputMultiplexer multiInput;

    PowerUpScreenCastle thisScreen;

    public PowerUpScreenCastle() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public PowerUpScreenCastle(Game parent) {
        parentGame = parent;
        Path path = Paths.get("data_save/dataUpgrade.txt");
        boolean path_exits = Files.notExists(path);
        if (!path_exits) {
            readFile(data_upgrade2,2);
        }
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

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);
        coin= Integer.parseInt(data_upgrade2.get(0));
        coin2=Integer.parseInt(data_upgrade2.get(3));
        diamond=Integer.parseInt(data_upgrade2.get(4));
        game_awal=Integer.parseInt(data_upgrade2.get(7));
        max_hp_castle=Integer.parseInt(data_upgrade2.get(19));
        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText(String.valueOf(coin), 1450, 1050);

        fontCache1 = new BitmapFontCache(MyGdxGame.font);
        fontCache1.setColor(Color.BLACK);
        fontCache1.setText(String.valueOf(coin2), 1610, 770);

        fontCache2=new BitmapFontCache(MyGdxGame.font);
        fontCache2.setColor(Color.BLACK);
        fontCache2.setText(String.valueOf(diamond), 1700, 1050);

        fontCache3=new BitmapFontCache(MyGdxGame.font2);
        fontCache3.setColor(Color.BLACK);
        fontCache3.setText("Meningkatkan maximal HP castle menjadi "+max_hp_castle, 725, 671);
        if (game_awal >= 60) {
            fontCache1.setText("MAX", 1610, 770);
            fontCache3.setText("Tingkat maximal HP castle sudah maksimal ", 725, 671);
        }
        fontCache4=new BitmapFontCache(MyGdxGame.font3);
        fontCache4.setColor(Color.BLACK);
        fontCache4.setText("Power Up", 245, 1050);

        optionButton = new TextButton("Back", mySkin);
        optionButton.setHeight(42);
        optionButton.setWidth(42);
        optionButton.setPosition(78, 1005);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new MenuScreen(parentGame));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);

        playButton = new TextButton("Hero", mySkin);
        playButton.setHeight(90);
        playButton.setWidth(90);
        playButton.setPosition(65, 740);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new PowerUpScreen(parentGame));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        optionButton = new TextButton("Spell", mySkin);
        optionButton.setHeight(70);
        optionButton.setWidth(100);
        optionButton.setPosition(110 - optionButton.getWidth() / 2, 435);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new PowerUpScreenSpell(parentGame));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);

        optionButton = new TextButton("Castle", mySkin);
        optionButton.setHeight(85);
        optionButton.setWidth(100);
        optionButton.setPosition(110 - optionButton.getWidth() / 2, 100);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new PowerUpScreenCastle(parentGame));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);
        optionButton = new TextButton("+", mySkin);
        optionButton.setHeight(30);
        optionButton.setWidth(30);
        optionButton.setPosition(1575 - optionButton.getWidth() / 2, 740);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (game_awal < 60 && coin>=coin2) {
                        max_hp_castle+=20;
                        fontCache3.setText("Meningkatkan maximal HP castle menjadi "+max_hp_castle, 725, 671);
                        game_awal += 10;
                        coin -= coin2;
                        fontCache.setText(String.valueOf(coin), 1450, 1050);
                        coin2 += 200;
                        fontCache1.setText(String.valueOf(coin2), 1610, 770);
                    }
                    if (game_awal >= 60) {
                        fontCache3.setText("Tingkat maximal HP castle sudah maksimal ", 725, 671);
                        fontCache1.setText("MAX", 1610, 770);

                    }
                    editFile(data_upgrade2,0,coin,2);
                    editFile(data_upgrade2,4,diamond,2);
                    editFile(data_upgrade2,3,coin2,2);
                    editFile(data_upgrade2,7,game_awal,2);
                    editFile(data_upgrade2,19,max_hp_castle,2);
                    data_upgrade2.clear();
                    readFile(data_upgrade2,2);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);
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

        Texture background = assetManager.get("upgrade_background.png", Texture.class);

        batch.draw(background, 0, 0);
        Texture benteng = assetManager.get("benteng.png", Texture.class);

        batch.draw(benteng, 10, 180);
        batch.draw(background, 0, 0);
        Texture castle_screen = assetManager.get("castle_screen.png", Texture.class);

        batch.draw(castle_screen, 250, 20);
        Texture kotak_atas= assetManager.get("kotak_atas.png", Texture.class);
        batch.draw(kotak_atas,0,950,1920,130);
        Texture kotak= assetManager.get("kotak_castle.png", Texture.class);

        batch.draw(kotak, 722, 696);
        Texture koin = assetManager.get("newcoin.png", Texture.class);

        batch.draw(koin, 1350, 1010);
        Texture diamond = assetManager.get("newdiamond.png", Texture.class);
        batch.draw(diamond, 1600, 1003);
        if (game_awal == 30) {
            Texture coba = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba, 992, 696);

        } else if (game_awal == 40) {
            Texture coba = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba, 992, 696);
            Texture coba1 = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba1, 1127, 696);
        } else if (game_awal == 50) {
            Texture coba = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba, 992, 696);
            Texture coba1 = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba1, 1127, 696);
            Texture coba2 = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba2, 1262, 696);
        } else if (game_awal>=60) {
            Texture coba = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba, 992, 696);
            Texture coba1 = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba1, 1127, 696);
            Texture coba2 = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba2, 1262, 696);
            Texture coba3 = assetManager.get("kotak_castle.png", Texture.class);

            batch.draw(coba3, 1397, 696);
        }
        fontCache.draw(batch);
        fontCache1.draw(batch);
        fontCache2.draw(batch);
        fontCache3.draw(batch);
        fontCache4.draw(batch);

        stage.act();
        stage.draw();

        Texture BackUpgrade = assetManager.get("bbupgrade.png", Texture.class);
        Texture Hero = assetManager.get("hero.png", Texture.class);
        Texture Castle = assetManager.get("castleupgrade.png", Texture.class);
        Texture Spell = assetManager.get("spell.png", Texture.class);



        batch.draw(BackUpgrade,63, 990);
        batch.draw(Hero,-50, 700);
        batch.draw(Castle,15, 90);
        batch.draw(Spell,-10, 380);
        batch.end();
        update();


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

