package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class TitleScreen extends ScreenAdapter {
    YorkPirates game;

    private Texture title;

    private float logoScale = 0.33f;

    public TitleScreen(YorkPirates game){
        this.game = game;

        title = new Texture("libgdx.png");
    }

    @Override
    public void render(float delta){
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        game.batch.begin();
        float newheight = title.getHeight()*(game.camera.viewportWidth*logoScale / title.getWidth());
        game.batch.draw(title, game.camera.viewportWidth*((1-logoScale)/2), game.camera.viewportHeight/2-newheight/2, game.camera.viewportWidth*logoScale, newheight);
        game.batch.end();
    }

    private void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void dispose(){
        title.dispose();
    }
}
