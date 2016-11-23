package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AnimationsPlayer {


     /* Временные переменные  */
     boolean bool;
     boolean templates = false;
     public static String testMessage = "Test Message";
    /*---------------------------------*/


    public static Animation animation;
    float time;
    boolean isTime = true; // Нужна для остановки обновления кадров, false = не обновлять, true = обновлять
    public static Animation.PlayMode state = Animation.PlayMode.NORMAL;
    TextureRegion[] region;


    public AnimationsPlayer(Texture texture, int MROW, boolean isTime) {
        this.isTime = isTime;
        TextureRegion[][] frames = TextureRegion.split(texture, 100, 100);
        region = new TextureRegion[MROW];

        for(int i = 0; i < MROW; i++) {
            region[i] = frames[0][i];
            System.out.println(region[i].getRegionX());
        }
        animation = new Animation(0.5f, region);
    }

    public AnimationsPlayer(Texture texture, int MROW, boolean isTime, float speed) { // Консруктор со скоростью
        this.isTime = isTime;
        TextureRegion[][] frames = TextureRegion.split(texture, 100, 100);
        region = new TextureRegion[MROW];

        for(int i = 0; i < MROW; i++) {
            region[i] = frames[0][i];
            System.out.println(region[i].getRegionX());
        }
        animation = new Animation(speed, region);
    }

    public void selectStateHard(boolean templatebool) { // Определяем состояние(основа управления анимацией персонажа)(Сложный способ, ниже будет способ проще который работает с InputEvent())
        if(!templatebool && !templates) {
            System.out.println("ifJobs");
            state = Animation.PlayMode.NORMAL;
            bool = true;
            templates = true;
            testMessage = "Animation OFF(NORMAL)";
        } else if(templatebool && !templates) {
            System.out.println("ifJobs2");
            state = Animation.PlayMode.LOOP;
            bool = false;
            templates = true;
            testMessage = "Animation ON(LOOP)";
        }
        if(Gdx.input.justTouched()) {
            System.out.println("justTouched");
            System.out.println(bool);
            if(!bool) {
                state = Animation.PlayMode.NORMAL;
                bool = true;
                testMessage = "Animation OFF(NORMAL)";
                isTime = false;
            } else {
                state = Animation.PlayMode.LOOP;
                bool = false;
                testMessage = "Animation ON(LOOP)";
                isTime = true;
            }
        }
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
        if(isTime) { return animation.getKeyFrame(time += Gdx.graphics.getDeltaTime()); }
        else { return animation.getKeyFrame(time = 0); }
    }
}