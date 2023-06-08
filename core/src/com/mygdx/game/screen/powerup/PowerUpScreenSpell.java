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

public class PowerUpScreenSpell extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;

    private Viewport viewport;
    private OrthographicCamera camera;
    SpriteBatch batch;
    int game_awal=20;
    int game_awal2=20;
    int game_awal3=20;
    int coin=10000;
    int diamond=50;
    int diamond1=5;
    int diamond2=5;
    int diamond3=5;
    int manaCost=30;
    int kekuatan_spell=100;
    int spell_Cooldown=30;
    ArrayList<String>data_upgrade3=new ArrayList<>();
    Stage stage;
    Label titleLabel, optionSoundLabel;
    TextButton playButton, optionButton, optionDoneButton;
    Window optionWindow;
    InputMultiplexer multiInput;
    BitmapFontCache fontCache,fontCache1,fontCache2,fontCache3,fontCache4,fontCache5,fontCache6,fontCache7,fontCache8;
    PowerUpScreenSpell thisScreen;

    public PowerUpScreenSpell() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public PowerUpScreenSpell(Game parent) {
        parentGame = parent;
        Path path = Paths.get("data_save/dataUpgrade.txt");
        boolean path_exits = Files.notExists(path);
        if (!path_exits) {
            readFile(data_upgrade3,2);
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

        coin = Integer.parseInt(data_upgrade3.get(0));
        diamond=Integer.parseInt(data_upgrade3.get(4));
        diamond1=Integer.parseInt(data_upgrade3.get(11));
        diamond2=Integer.parseInt(data_upgrade3.get(12));
        diamond3=Integer.parseInt(data_upgrade3.get(13));
        game_awal=Integer.parseInt(data_upgrade3.get(8));
        game_awal2= Integer.parseInt(data_upgrade3.get(9));
        game_awal3= Integer.parseInt(data_upgrade3.get(10));
        kekuatan_spell = Integer.parseInt(data_upgrade3.get(16));
        manaCost = Integer.parseInt(data_upgrade3.get(17));
        spell_Cooldown = Integer.parseInt(data_upgrade3.get(18));

        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText(String.valueOf(coin), 1450, 1050);

        fontCache1 = new BitmapFontCache(MyGdxGame.font);
        fontCache1.setColor(Color.BLACK);
        fontCache1.setText(String.valueOf(diamond), 1700, 1050);

        fontCache2 = new BitmapFontCache(MyGdxGame.font);
        fontCache2.setColor(Color.BLACK);
        fontCache2.setText(String.valueOf(diamond1), 1420, 815);

        fontCache3 = new BitmapFontCache(MyGdxGame.font);
        fontCache3.setColor(Color.BLACK);
        fontCache3.setText(String.valueOf(diamond2), 1420, 560);

        fontCache4 = new BitmapFontCache(MyGdxGame.font);
        fontCache4.setColor(Color.BLACK);
        fontCache4.setText(String.valueOf(diamond3), 1420, 305);

        fontCache5 = new BitmapFontCache(MyGdxGame.font2);
        fontCache5.setColor(Color.BLACK);
        fontCache5.setText("Meningkatkan kekuatan serangan spell menjadi "+kekuatan_spell , 665, 725);

        fontCache6 = new BitmapFontCache(MyGdxGame.font2);
        fontCache6.setColor(Color.BLACK);
        fontCache6.setText("Mengurangi mana cost menjadi "+manaCost, 665, 470);

        fontCache7 = new BitmapFontCache(MyGdxGame.font2);
        fontCache7.setColor(Color.BLACK);
        fontCache7.setText("Mengurangi cooldown spell menjadi "+ spell_Cooldown+ " detik ", 665, 214);

        if (game_awal >= 60) {
            fontCache2.setText("MAX", 1420, 815);
            fontCache5.setText("Tingkat kekuatan serangan spell maksimal" , 665, 725);
        }
        if (game_awal2 >= 60) {
            fontCache3.setText("MAX", 1420, 560);
            fontCache6.setText("Tingkat maximal mana cost sudah maksimal ", 665, 470);
        }
        if (game_awal3 >= 60) {
            fontCache4.setText("MAX", 1420, 305);
            fontCache7.setText("Tingkat durasi cooldown spell maksimal", 665, 214);
        }

        fontCache8=new BitmapFontCache(MyGdxGame.font3);
        fontCache8.setColor(Color.BLACK);
        fontCache8.setText("Power Up", 245, 1050);

        optionButton = new TextButton("Back", mySkin);
        optionButton.setHeight(70);
        optionButton.setWidth(70);
        optionButton.setPosition(50 - optionButton.getWidth() / 2, 1000);
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
        playButton.setHeight(150);
        playButton.setWidth(150);
        playButton.setPosition(100 - playButton.getWidth() / 2, 750);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    //readFile(data_upgrade1);
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
        optionButton.setHeight(150);
        optionButton.setWidth(150);
        optionButton.setPosition(100 - optionButton.getWidth()/ 2, 425);
        optionButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new PowerUpScreenSpell(parentGame));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);
        optionButton = new TextButton("+", mySkin);
        optionButton.setHeight(30);
        optionButton.setWidth(30);
        optionButton.setPosition(1375- optionButton.getWidth() / 2, 780);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (game_awal < 60 && diamond>=diamond1) {
                        kekuatan_spell+=20;
                        fontCache5.setText("Meningkatkan damage serangan spell menjadi "+kekuatan_spell , 665, 725);
                        game_awal += 10;
                        diamond -= diamond1;
                        fontCache1.setText(String.valueOf(diamond), 1725, 1050);
                        diamond1 += 5;
                        fontCache2.setText(String.valueOf(diamond1), 1420, 815);
                    }
                    if (game_awal >= 60) {
                        fontCache5.setText("Tingkat kekuatan serangan spell maksimal" , 665, 725);
                        fontCache2.setText("MAX", 1420, 815);

                    }
                    editFile(data_upgrade3,0,coin,2);
                    editFile(data_upgrade3,4,diamond,2);
                    editFile(data_upgrade3,11,diamond1,2);
                    editFile(data_upgrade3,8,game_awal,2);
                    editFile(data_upgrade3,16,kekuatan_spell,2);
                    data_upgrade3.clear();
                    readFile(data_upgrade3,2);
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
        optionButton.setPosition(1375 - optionButton.getWidth() / 2, 525);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (game_awal2 < 60 && diamond>=diamond2) {
                        manaCost-=5;
                        fontCache6.setText("Mengurangi mana cost menjadi "+manaCost, 665, 470);
                        game_awal2 += 10;
                        diamond -= diamond2;
                        fontCache1.setText(String.valueOf(diamond), 1725, 1050);
                        diamond2 += 5;
                        fontCache3.setText(String.valueOf(diamond2), 1420, 560);
                    }
                    if (game_awal2 >= 60) {
                        fontCache6.setText("Tingkat maximal mana cost sudah maksimal ", 665, 470);
                        fontCache3.setText("MAX", 1420, 560);

                    }
                    editFile(data_upgrade3,0,coin,2);
                    editFile(data_upgrade3,4,diamond,2);
                    editFile(data_upgrade3,12,diamond2,2);
                    editFile(data_upgrade3,9,game_awal2,2);
                    editFile(data_upgrade3,17,manaCost,2);
                    data_upgrade3.clear();
                    readFile(data_upgrade3,2);
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
        optionButton.setPosition(1375 - optionButton.getWidth() / 2, 270);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (game_awal3 < 60 && diamond>=diamond3) {
                        spell_Cooldown-= 5;
                        fontCache7.setText("Mengurangi cooldown spell menjadi "+ spell_Cooldown+ " detik ", 665, 214);
                        game_awal3 += 10;
                        diamond -= diamond3;
                        fontCache1.setText(String.valueOf(diamond), 1725, 1050);
                        diamond3 += 5;
                        fontCache4.setText(String.valueOf(diamond3), 1420, 305);
                    }
                    if (game_awal3 >= 60) {
                        fontCache7.setText("Tingkat durasi cooldown spell maksimal", 665, 214);
                        fontCache4.setText("MAX", 1420, 305);

                    }
                    editFile(data_upgrade3,0,coin,2);
                    editFile(data_upgrade3,4,diamond,2);
                    editFile(data_upgrade3,13,diamond3,2);
                    editFile(data_upgrade3,10,game_awal3,2);
                    editFile(data_upgrade3,18,spell_Cooldown,2);
                    data_upgrade3.clear();
                    readFile(data_upgrade3,2);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);

        optionButton = new TextButton("Castle", mySkin);
        optionButton.setHeight(150);
        optionButton.setWidth(150);
        optionButton.setPosition(100 - optionButton.getWidth()/2, 100);
        optionButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new PowerUpScreenCastle(parentGame));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
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
        Texture spell_screen = assetManager.get("spell_screen.png", Texture.class);

        batch.draw(spell_screen, 250, 20);
        Texture kotak_atas= assetManager.get("kotak_atas.png", Texture.class);
        batch.draw(kotak_atas,0,950,1920,130);
        Texture koin = assetManager.get("newcoin.png", Texture.class);

        batch.draw(koin, 1350, 1010);
        Texture diamond = assetManager.get("newdiamond.png", Texture.class);
        batch.draw(diamond, 1600, 1003);
        if (game_awal == 30) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 751);

        } else if (game_awal == 40) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 751);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 751);
        } else if (game_awal == 50) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 751);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 751);
            Texture coba2 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba2, 1105, 751);
        } else if (game_awal >= 60) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 751);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 751);
            Texture coba2 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba2, 1105, 751);
            Texture coba3 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba3, 1216, 751);
        }
        if (game_awal2 == 30) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 495);

        } else if (game_awal2 == 40) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 495);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 495);
        } else if (game_awal2 == 50) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 495);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 495);
            Texture coba2 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba2, 1105, 495);
        } else if (game_awal2 >= 60) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 495);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 495);
            Texture coba2 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba2, 1105, 495);
            Texture coba3 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba3, 1216, 495);
        }
        if (game_awal3 == 30) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 239);

        } else if (game_awal3 == 40) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 239);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 239);
        } else if (game_awal3 == 50) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 239);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 239);
            Texture coba2 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba2, 1105, 239);
        } else if (game_awal3 >= 60) {
            Texture coba = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba, 883, 239);
            Texture coba1 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba1, 994, 239);
            Texture coba2 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba2, 1105, 239);
            Texture coba3 = assetManager.get("kotak_kecil.png", Texture.class);

            batch.draw(coba3, 1216, 239);
        }
        fontCache.draw(batch);
        fontCache1.draw(batch);
        fontCache2.draw(batch);
        fontCache3.draw(batch);
        fontCache4.draw(batch);
        fontCache5.draw(batch);
        fontCache6.draw(batch);
        fontCache7.draw(batch);
        fontCache8.draw(batch);
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

