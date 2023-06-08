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
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

public class PowerUpScreen extends DataHandling implements Screen, InputProcessor  {
    Game parentGame;
    AssetManager assetManager;
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

    ArrayList<String>data_upgrade=new ArrayList<>();
    DataHandling dataHandling;
    private Viewport viewport;
    BitmapFontCache fontCache,fontCache1,fontCache2,fontCache3,fontCache4,fontCache5,fontCache6;
    private OrthographicCamera camera;
    SpriteBatch batch;

    Stage stage;
    Label titleLabel, koinLabel, diamondLabel;
    TextButton playButton, optionButton;
    Window optionWindow;
    InputMultiplexer multiInput;
    PowerUpScreen thisScreen;
    public PowerUpScreen() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public PowerUpScreen(Game parent) {
        parentGame = parent;
        Path path = Paths.get("data_save/dataUpgrade.txt");
        boolean path_exits = Files.notExists(path);
        if (!path_exits) {
            readFile(data_upgrade,2);
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

        fontCache6=new BitmapFontCache(MyGdxGame.font3);
        fontCache6.setColor(Color.BLACK);
        fontCache6.setText("Power Up", 245, 1050);

        coin= Integer.parseInt(data_upgrade.get(0));
        coin2=Integer.parseInt(data_upgrade.get(1));
        coin3=Integer.parseInt(data_upgrade.get(2));
        diamond=Integer.parseInt(data_upgrade.get(4));
        game_awal=Integer.parseInt(data_upgrade.get(5));
        game_awal2= Integer.parseInt(data_upgrade.get(6));
        damage = Integer.parseInt(data_upgrade.get(14));
        speed = Float.parseFloat(data_upgrade.get(15));

        fontCache = new BitmapFontCache(MyGdxGame.font);
        fontCache.setColor(Color.BLACK);
        fontCache.setText(String.valueOf(coin), 1450, 1050);

        fontCache1 = new BitmapFontCache(MyGdxGame.font);
        fontCache1.setColor(Color.BLACK);
        fontCache1.setText(String.valueOf(coin2), 1630, 680);

        fontCache2 = new BitmapFontCache(MyGdxGame.font);
        fontCache2.setColor(Color.BLACK);
        fontCache2.setText(String.valueOf(coin3), 1630, 345);

        fontCache3=new BitmapFontCache(MyGdxGame.font);
        fontCache3.setColor(Color.BLACK);
        fontCache3.setText(String.valueOf(diamond), 1700, 1050);

        fontCache4=new BitmapFontCache(MyGdxGame.font2);
        fontCache4.setColor(Color.BLACK);
        fontCache4.setText("Meningkatkan kekuatan serangan hero menjadi "+damage, 750, 570);

        fontCache5=new BitmapFontCache(MyGdxGame.font2);
        fontCache5.setColor(Color.BLACK);
        fontCache5.setText("Mengurangi cooldown serangan hero menjadi "+String.format("%.2f", speed), 750, 240);

        if (game_awal >= 60) {
            fontCache1.setText("MAX", 1630, 680);
            fontCache4.setText("Tingkat kekuatan serangan hero maksimal", 750, 570);
        }
        if (game_awal2 >= 60) {
            fontCache2.setText("MAX", 1630, 350);
            fontCache5.setText("Tingkat speed serangan hero maksimal", 750, 240);
        }
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

        optionButton = new TextButton("+", mySkin);
        optionButton.setHeight(30);
        optionButton.setWidth(30);
        optionButton.setPosition(1600 - optionButton.getWidth() / 2, 645);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (game_awal < 60&& coin>=coin2) {
                        damage+=10;
                        fontCache4.setText("Meningkatkan kekuatan serangan hero menjadi "+damage, 750, 570);
                        game_awal += 10;
                        coin -= coin2;
                        fontCache.setText(String.valueOf(coin), 1450, 1050);
                        coin2 += 200;
                        fontCache1.setText(String.valueOf(coin2), 1630, 680);
                    }
                    if (game_awal >= 60) {
                        fontCache4.setText("Tingkat kekuatan serangan hero maksimal", 750, 570);
                        fontCache1.setText("MAX", 1630, 680);
                    }
                    editFile(data_upgrade,0,coin,2);
                    editFile(data_upgrade,1,coin2,2);
                    editFile(data_upgrade,5,game_awal,2);
                    editFile(data_upgrade,14,damage,2);


                    data_upgrade.clear();
                    readFile(data_upgrade,2);
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
        optionButton.setPosition(1600 - optionButton.getWidth() / 2, 315);
        optionButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    if (game_awal2 < 60 && coin>=coin3) {
                        speed-=0.1;
                        fontCache5.setText("Mengurangi cooldown serangan hero menjadi "+String.format("%.2f", speed), 750, 240);
                        game_awal2 += 10;
                        coin -= coin3;
                        fontCache.setText(String.valueOf(coin), 1450, 1050);
                        coin3 += 200;
                        fontCache2.setText(String.valueOf(coin3), 1630, 350);
                    }

                    if (game_awal2 >= 60) {
                        fontCache5.setText("Tingkat speed serangan hero maksimal", 750, 240);
                        fontCache2.setText("MAX", 1630, 350);
                    }
                    editFile(data_upgrade,0,coin,2);
                    editFile(data_upgrade,2,coin3,2);
                    editFile(data_upgrade,6,game_awal2,2);
                    editFile(data_upgrade,15,speed,2);
                    data_upgrade.clear();
                    readFile(data_upgrade,2);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionButton);

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
        Texture kotak_atas= assetManager.get("kotak_atas.png", Texture.class);
        batch.draw(kotak_atas,0,950,1920,130);
        Texture hero_screen = assetManager.get("hero_screen.png", Texture.class);

        batch.draw(hero_screen, 250, 20);
        Texture koin = assetManager.get("newcoin.png", Texture.class);

        batch.draw(koin, 1350, 1010);
        Texture diamond = assetManager.get("newdiamond.png", Texture.class);
        batch.draw(diamond, 1600, 1003);
        if(game_awal==30) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1015, 605);

        }
        else if(game_awal==40) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1015, 605);
            Texture coba1 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba1, 1150, 605);
        }
        else if(game_awal==50) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1015, 605);
            Texture coba1 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba1, 1150, 605);
            Texture coba2 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba2, 1285, 605);
        }
        else if(game_awal>=60) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1015, 605);
            Texture coba1 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba1, 1150, 605);
            Texture coba2 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba2, 1285, 605);
            Texture coba3 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba3, 1420, 605);
        }
        if(game_awal2==30) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1016, 274);

        }
        else if(game_awal2==40) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1016, 274);
            Texture coba1 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba1, 1151, 274);
        }
        else if(game_awal2==50) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1016, 274);
            Texture coba1 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba1, 1151, 274);
            Texture coba2 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba2, 1286, 274);
        }
        else if(game_awal2>=60) {
            Texture coba = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba, 1016, 274);
            Texture coba1 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba1, 1151, 274);
            Texture coba2 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba2, 1286, 274);
            Texture coba3 = assetManager.get("kotak.png", Texture.class);

            batch.draw(coba3, 1421, 274);
        }

        fontCache.draw(batch);
        fontCache1.draw(batch);
        fontCache2.draw(batch);
        fontCache3.draw(batch);
        fontCache4.draw(batch);
        fontCache5.draw(batch);
        fontCache6.draw(batch);

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
