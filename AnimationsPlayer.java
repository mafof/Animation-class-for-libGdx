package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.lang.reflect.Array;


public class AnimationsPlayer {


     /* Временные переменные  */
     public static String testMessage = "Test Message";
    /*---------------------------------*/

    TextureRegion[] completeTextureRegion;
    TextureRegion[] completeAnimation;
    public static Animation animation;
    float time;
    boolean isTime = true; // Нужна для остановки обновления кадров, false = не обновлять, true = обновлять
    public static Animation.PlayMode state = Animation.PlayMode.LOOP;


    public AnimationsPlayer(Texture texture, int width, int height, int col, int row) {
        this.isTime = isTime;

        TextureRegion[][] templateRegion = TextureRegion.split(texture, width, height);
        completeTextureRegion = new TextureRegion[row * col];
        int index = 0;

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++, index++) {
                completeTextureRegion[index] = templateRegion[i][j];
            }
        }

        animation = new Animation(0.5f,templateRegion[0][0]);
        System.out.println(animation.getKeyFrames());
    }

    public void selectAnimation(int row, int col) {
        completeAnimation = new TextureRegion[col];

        if(row != 1) {
            int numberFor = row * col;
            int numberI = numberFor - col;
            int index = 0;

            for (int i = numberI; i < numberFor; i++, index++) {
                completeAnimation[index] = completeTextureRegion[i];
            }
        } else if( row == 1 ) {
            int tmp = 0;
            int index = 0;

            for (int i = tmp; i < col; i++, index++) {
                System.out.println("[3na4] i " + i);
                completeAnimation[index] = completeTextureRegion[i];
            }
        }
        animation = new Animation(0.5f, completeAnimation);
    }

    public void selectState(boolean bool) { // if true to State = LOOP, else false to State = NORMAL
        if(bool) {
            state = Animation.PlayMode.LOOP;
            testMessage = "Animation ON(LOOP)";
            isTime = true;
        } else {
            state = Animation.PlayMode.NORMAL;
            testMessage = "Animation OFF(NORMAL)";
            isTime = false;
        }
    }


    public TextureRegion playAnimation() { // Запуск анимации(Не реагирует на параметр изменения состояния движения графики)
        if(isTime) { return animation.getKeyFrame(time += Gdx.graphics.getDeltaTime(), true); }
        else { return animation.getKeyFrame(time = 0); }
    }
}