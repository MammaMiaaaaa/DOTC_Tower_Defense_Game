package com.mygdx.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
import com.mygdx.game.util.DataHandling;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Stages;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StageSelection extends DataHandling implements Screen, InputProcessor {
    Game parentGame;
    AssetManager assetManager;

    private OrthographicCamera camera;
    SpriteBatch batch;

    Stages stages;

    int highscore1 = 0;
    int highscore2 = 0;
    int highscore3 = 0;
    int highscore4 = 0;
    int highscore5 = 0;
    int highscore6 = 0;
    int highscore7 = 0;
    int highscore8 = 0;
    Stage stage;
    Label titleLabel, optionSoundLabel;
    TextButton backButton,oneButton,twoButton,threeButton,fourButton,fiveButton,sixButton,sevenButton,eightButton,survButton;
    Window powerUpWindow,optionWindow;

    InputMultiplexer multiInput;

    StageSelection thisScreen;

    BitmapFontCache highScoreTitle;
    BitmapFontCache highScore1;
    BitmapFontCache highScore2;
    BitmapFontCache highScore3;
    BitmapFontCache highScore4;
    BitmapFontCache highScore5;
    BitmapFontCache highScore6;
    BitmapFontCache highScore7;
    BitmapFontCache highScore8;
    ArrayList<String>stageHighScore = new ArrayList<>();

    public StageSelection() {
        parentGame = (Game) Gdx.app.getApplicationListener();
        this.Initialize();
    }

    public StageSelection(Game parent) {
        parentGame = parent;
        this.Initialize();
    }

    public void Initialize() {
        // init
        assetManager = ((MyGdxGame) parentGame).getAssetManager();
        thisScreen = this;

        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));
        stageHighScore.add(String.valueOf(highscore1));

        Path path = Paths.get("data_save/dataHighscore.txt");
        boolean path_exits = Files.notExists(path);
        if (path_exits) {
            writeFile(stageHighScore,1);
        }

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        Viewport viewport = new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        OrthographicCamera stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        stageHighScore.clear();
        readFile(stageHighScore,1);

        highscore1 = Integer.parseInt(stageHighScore.get(0));
        highscore2 = Integer.parseInt(stageHighScore.get(1));
        highscore3 = Integer.parseInt(stageHighScore.get(2));
        highscore4 = Integer.parseInt(stageHighScore.get(3));
        highscore5 = Integer.parseInt(stageHighScore.get(4));
        highscore6 = Integer.parseInt(stageHighScore.get(5));
        highscore7 = Integer.parseInt(stageHighScore.get(6));
        highscore8 = Integer.parseInt(stageHighScore.get(7));

        Skin mySkin = assetManager.get("uiskin.json", Skin.class);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 90;
        parameter.color = Color.WHITE;
        parameter.flip = false;
        BitmapFont font = generator.generateFont(parameter);




        titleLabel = new Label("Stage Selection", mySkin);
        Label.LabelStyle style = new Label.LabelStyle(titleLabel.getStyle());
        style.font = generator.generateFont(parameter);
        titleLabel.setStyle(style);
        titleLabel.setWidth(640);
        titleLabel.setX(650);
        titleLabel.setY(950);
        titleLabel.setAlignment(Align.center);
        titleLabel.setColor(Color.BLACK);
        stage.addActor(titleLabel);


        // back button
        backButton = new TextButton("Back", mySkin);
        backButton.setHeight(100);
        backButton.setWidth(200);
        backButton.setPosition(1500, 100);
        backButton.setColor(Color.WHITE);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    parentGame.setScreen(new MenuScreen(parentGame));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);

        // levels
        // survival button
        survButton = new TextButton("Survival", mySkin);
        survButton.setVisible(true);
        survButton.setHeight(50);
        survButton.setWidth(50);
        survButton.setPosition(300, 500);
        survButton.setColor(Color.WHITE);

        survButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,0));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(survButton);

        // level 1
        oneButton = new TextButton("1", mySkin);
        oneButton.setHeight(50);
        oneButton.setWidth(50);
        oneButton.setPosition(200, 100);
        oneButton.setColor(Color.WHITE);

        oneButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,1));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(oneButton);

        // level 2
        twoButton = new TextButton("2", mySkin);
        twoButton.setHeight(50);
        twoButton.setWidth(50);
        twoButton.setPosition(300, 150);
        twoButton.setColor(Color.WHITE);
        twoButton.setVisible(false);
        twoButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,2));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(twoButton);

        // level 3
        threeButton = new TextButton("3", mySkin);
        threeButton.setHeight(50);
        threeButton.setWidth(50);
        threeButton.setPosition(400, 200);
        threeButton.setColor(Color.WHITE);
        threeButton.setVisible(false);
        threeButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,3));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(threeButton);

        // level 4
        fourButton = new TextButton("4", mySkin);
        fourButton.setHeight(50);
        fourButton.setWidth(50);
        fourButton.setPosition(500, 250);
        fourButton.setColor(Color.WHITE);
        fourButton.setVisible(false);
        fourButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,4));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(fourButton);

        // level 5
        fiveButton = new TextButton("5", mySkin);
        fiveButton.setHeight(50);
        fiveButton.setWidth(50);
        fiveButton.setPosition(600, 300);
        fiveButton.setColor(Color.WHITE);
        fiveButton.setVisible(false);
        fiveButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,5));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(fiveButton);

        // level 6
        sixButton = new TextButton("6", mySkin);
        sixButton.setHeight(50);
        sixButton.setWidth(50);
        sixButton.setPosition(700, 350);
        sixButton.setColor(Color.WHITE);
        sixButton.setVisible(false);
        sixButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,6));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(sixButton);

        // level 7
        sevenButton = new TextButton("7", mySkin);
        sevenButton.setHeight(50);
        sevenButton.setWidth(50);
        sevenButton.setPosition(800, 400);
        sevenButton.setColor(Color.WHITE);
        sevenButton.setVisible(false);
        sevenButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,7));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(sevenButton);

        // level 8
        eightButton = new TextButton("8", mySkin);
        eightButton.setHeight(50);
        eightButton.setWidth(50);
        eightButton.setPosition(900, 450);
        eightButton.setColor(Color.WHITE);
        eightButton.setVisible(false);
        eightButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    thisScreen.dispose();
                    ((MyGdxGame) parentGame).StopMenuMusic();
                    parentGame.setScreen(new GameScreen(parentGame,8));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(eightButton);




        parameter.size = 30;
        BitmapFont font2 = generator.generateFont(parameter);

        highScoreTitle = new BitmapFontCache(font2);
        highScoreTitle.setColor(Color.BLACK);
        highScoreTitle.setText("High Score :",50, 1050);

        highScore1 = new BitmapFontCache(font2);
        highScore1.setColor(Color.BLACK);
        highScore1.setText("Stage 1: "+highscore1,50, 1000);

        highScore2 = new BitmapFontCache(font2);
        highScore2.setColor(Color.BLACK);
        highScore2.setText("Stage 2: "+highscore2,50, 950);

        highScore3 = new BitmapFontCache(font2);
        highScore3.setColor(Color.BLACK);
        highScore3.setText("Stage 3: "+highscore3,50, 900);

        highScore4 = new BitmapFontCache(font2);
        highScore4.setColor(Color.BLACK);
        highScore4.setText("Stage 4: "+highscore4,50, 850);

        highScore5 = new BitmapFontCache(font2);
        highScore5.setColor(Color.BLACK);
        highScore5.setText("Stage 5: "+highscore5,50, 800);

        highScore6 = new BitmapFontCache(font2);
        highScore6.setColor(Color.BLACK);
        highScore6.setText("Stage 6: "+highscore6,50, 750);

        highScore7 = new BitmapFontCache(font2);
        highScore7.setColor(Color.BLACK);
        highScore7.setText("Stage 7: "+highscore7,50, 700);

        highScore8 = new BitmapFontCache(font2);
        highScore8.setColor(Color.BLACK);
        highScore8.setText("Stage 8: "+highscore8,50, 650);
        generator.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Texture background = assetManager.get("StageSelectionBackground.png", Texture.class);

        batch.draw(background,0,0);
        highScoreTitle.draw(batch);
        highScore1.draw(batch);
        highScore2.draw(batch);
        highScore3.draw(batch);
        highScore4.draw(batch);
        highScore5.draw(batch);
        highScore6.draw(batch);
        highScore7.draw(batch);
        highScore8.draw(batch);

        batch.end();

        stage.act();
        stage.draw();

        this.update();
    }
    public void update() {

        // check if highscore is not 0, if not, show the button
        if (highscore1!=0){
            twoButton.setVisible(true);
        }
        if (highscore2!=0){
            threeButton.setVisible(true);
        }
        if (highscore3!=0){
            fourButton.setVisible(true);
        }
        if (highscore4!=0){
            fiveButton.setVisible(true);
        }
        if (highscore5!=0){
            sixButton.setVisible(true);
        }
        if (highscore6!=0){
            sevenButton.setVisible(true);
        }
        if (highscore7!=0){
            eightButton.setVisible(true);
        }

    }

    // input listener
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

    @Override
    public void resize(int width, int height) {

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
}
