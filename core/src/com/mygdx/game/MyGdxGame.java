package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screen.LoadingScreen;

public class MyGdxGame extends Game implements InputProcessor {
	public static final int WORLD_WIDTH = 1920;
	public static final int WORLD_HEIGHT = 1080;

	public static BitmapFont font,font2,font3;
	BitmapFontCache fontCache;

	AssetManager assetManager;

	Music menuMusic = null;
	Music ingameMusic = null;

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load("In Game.png", Texture.class);
		assetManager.load("OrcRunning.png", Texture.class);
		assetManager.load("OrcAttacking.png", Texture.class);
		assetManager.load("OrcDying.png", Texture.class);
		assetManager.load("OrcIdling.png",Texture.class);
		assetManager.load("OgreRunning.png", Texture.class);
		assetManager.load("OgreAttacking.png", Texture.class);
		assetManager.load("OgreDying.png", Texture.class);
		assetManager.load("OgreIdling.png",Texture.class);
		assetManager.load("GoblinRunning.png", Texture.class);
		assetManager.load("GoblinAttacking.png", Texture.class);
		assetManager.load("GoblinIdling.png",Texture.class);
		assetManager.load("sfx/arrowSFX.mp3", Sound.class);
		assetManager.load("sfx/arrowsSFX.mp3", Sound.class);
		assetManager.load("sfx/fireballSFX.mp3", Sound.class);
		assetManager.load("sfx/freezeSFX.mp3", Sound.class);
		assetManager.load("GoblinDying.png", Texture.class);
		assetManager.load("MagicOrcRunning.png", Texture.class);
		assetManager.load("MagicOrcAttacking.png", Texture.class);
		assetManager.load("MagicOrcDying.png", Texture.class);
		assetManager.load("MagicOrcIdling.png",Texture.class);
		assetManager.load("MagicOgreRunning.png", Texture.class);
		assetManager.load("MagicOgreAttacking.png", Texture.class);
		assetManager.load("MagicOgreDying.png", Texture.class);
		assetManager.load("MagicOgreIdling.png",Texture.class);
		assetManager.load("MagicGoblinRunning.png", Texture.class);
		assetManager.load("MagicGoblinAttacking.png", Texture.class);
		assetManager.load("MagicGoblinIdling.png",Texture.class);
		assetManager.load("MagicGoblinDying.png", Texture.class);
		assetManager.load("FullCastle.png", Texture.class);
		assetManager.load("HalfCastle.png", Texture.class);
		assetManager.load("DestroyedCastle.png", Texture.class);
		assetManager.load("mana.png", Texture.class);
		assetManager.load("heart.png", Texture.class);
		assetManager.load("background.png",Texture.class);
		assetManager.load("StageSelectionBackground.png",Texture.class);
		assetManager.load("HeroIdling.png",Texture.class);
		assetManager.load("HeroAttacking.png",Texture.class);
		assetManager.load("HeroDying.png",Texture.class);
		assetManager.load("MainMenuMusic.mp3", Music.class);
		assetManager.load("InGameMusic.mp3", Music.class);
		assetManager.load("Arrow.png",Texture.class);
		assetManager.load("circleAOE.png", Texture.class);
		assetManager.load("newcoin.png", Texture.class);
		assetManager.load("castle.png", Texture.class);
		assetManager.load("pausebutton.png", Texture.class);
		assetManager.load("kotak_atas.png", Texture.class);
		assetManager.load("newdiamond.png", Texture.class);
		assetManager.load("exit.png", Texture.class);
		assetManager.load("startbut.png", Texture.class);
		assetManager.load("upgradebut.png", Texture.class);
		assetManager.load("bbstage.png", Texture.class);
		assetManager.load("bbupgrade.png", Texture.class);
		assetManager.load("berlian.png", Texture.class);
		assetManager.load("benteng2.png", Texture.class);
		assetManager.load("coin.png", Texture.class);
		assetManager.load("monster.png", Texture.class);
		assetManager.load("kotak.png", Texture.class);
		assetManager.load("kotak_castle.png", Texture.class);
		assetManager.load("kotak_kecil.png", Texture.class);
		assetManager.load("hero_screen.png", Texture.class);
		assetManager.load("spell_screen.png", Texture.class);
		assetManager.load("castle_screen.png", Texture.class);
		assetManager.load("upgrade_background.png", Texture.class);
		assetManager.load("benteng.png", Texture.class);
		assetManager.load("Golem.png", Texture.class);
		assetManager.load("Tower.png", Texture.class);
		assetManager.load("MenuScreen.png", Texture.class);
		assetManager.load("LoadingScreen.png", Texture.class);
		assetManager.load("FrozenOrc.png",Texture.class);
		assetManager.load("FrozenOgre.png",Texture.class);
		assetManager.load("FrozenGoblin.png",Texture.class);
		assetManager.load("FireBallAnimation.png", Texture.class);
		assetManager.load("monster2.png", Texture.class);
		assetManager.load("failed.png", Texture.class);
		assetManager.load("ArrowsButton.png", Texture.class);
		assetManager.load("ArrowsButtonCD.png", Texture.class);
		assetManager.load("FreezeButton.png", Texture.class);
		assetManager.load("FreezeButtonCD.png", Texture.class);
		assetManager.load("FireBallButton.png", Texture.class);
		assetManager.load("FireBallButtonCD.png", Texture.class);




//		assetManager.load("Roboto.ttf", BitmapFont.class);


		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ARCADE_N.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 25;
		parameter.color = Color.WHITE;
		parameter.flip = false;
		font = generator.generateFont(parameter); // font size 12 pixels
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size = 20;
		parameter1.color = Color.WHITE;
		parameter1.flip = false;
		font2 = generator.generateFont(parameter1);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.size = 50;
		parameter2.color = Color.WHITE;
		parameter2.flip = false;
		font3 = generator.generateFont(parameter2);
		generator.dispose();


		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		// First, let's define the params and then load our smaller font
		FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		mySmallFont.fontFileName = "ARCADE_N.TTF";
		mySmallFont.fontParameters.size = 16;
		mySmallFont.fontParameters.flip = true;
		assetManager.load("smallfont.ttf", BitmapFont.class, mySmallFont);

		FreetypeFontLoader.FreeTypeFontLoaderParameter bigFontUI = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		bigFontUI.fontFileName = "ARCADE_N.TTF";
		bigFontUI.fontParameters.size = 32;
		bigFontUI.fontParameters.flip = false;
		assetManager.load("bigfontui.ttf", BitmapFont.class, bigFontUI);

		SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("uiskin.atlas");
		assetManager.load("uiskin.json", Skin.class, skinParam);

		this.setScreen(new LoadingScreen(this));
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}
	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {

		assetManager.dispose();
	}
	//	public void update() throws InterruptedException {
//
////		for (Enemy e: enemyList) {
////			e.update();
////		}
//
//	}
	public static TextureRegion[] CreateAnimationFrames(Texture tex, int frameWidth, int frameHeight, int frameCount, boolean flipx, boolean flipy)
	{
		TextureRegion[][] tmp = TextureRegion.split(tex,frameWidth, frameHeight);
		TextureRegion[] frames = new TextureRegion[frameCount];
		int index = 0;
		int row = tex.getHeight() / frameHeight;
		int col = tex.getWidth() / frameWidth;
		for (int i = 0; i < row && index < frameCount; i++) {
			for (int j = 0; j < col && index < frameCount; j++) {
				frames[index] = tmp[i][j];
				frames[index].flip(flipx, flipy);
				index++;
			}
		}
		return frames;
	}
	public void PlayMenuMusic(){
		if(menuMusic == null)
			menuMusic = assetManager.get("MainMenuMusic.mp3", Music.class);
		menuMusic.setLooping(true);
		if(!menuMusic.isPlaying())
			menuMusic.play();
	}
	public void StopMenuMusic(){
		if (menuMusic.isPlaying())
			menuMusic.stop();
	}
	public void PlayInGameMusic(){
		if(ingameMusic == null)
			ingameMusic = assetManager.get("InGameMusic.mp3", Music.class);
		ingameMusic.setLooping(true);
		if(!ingameMusic.isPlaying())
			ingameMusic.play();
	}
	public void StopInGameMusic(){
			ingameMusic.stop();
	}
	public void PauseInGameMusic(){
		ingameMusic.pause();

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
