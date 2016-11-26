package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    float speed = 0.5f;


    public AnimationsPlayer(Texture texture, int width, int height, int col, int row) {
        TextureRegion[][] templateRegion = TextureRegion.split(texture, width, height);
        completeTextureRegion = new TextureRegion[row * col];
        int index = 0;

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++, index++) {
                completeTextureRegion[index] = templateRegion[i][j];
            }
        }

        animation = new Animation(0.1f,templateRegion[0][0]);
    }

    public void selectAnimation(int row, int col) { // Воспроизведение анимации на опредленном столбце
        completeAnimation = new TextureRegion[col];

        if(row != 1) {
            int numberFor = row * col;
            int numberI = numberFor - col;
            int index = 0;

            for (int i = numberI; i < numberFor; i++, index++) {
                completeAnimation[index] = completeTextureRegion[i];
            }
        } else {
            int tmp = 0;
            int index = 0;

            for (int i = tmp; i < col; i++, index++) {
                completeAnimation[index] = completeTextureRegion[i];
            }
        }
        //animation = new Animation(speed, completeAnimation);
        deleteFrame();
    }

    public void selectAnimation(int row, int col, float speed) {
        this.speed = speed;
        selectAnimation(row,col);
    }

    private void deleteFrame() { // Нужен для того что бы не отображался определенный кадр в анимации
        TextureRegion[] seeCompleteTextureRegion = new TextureRegion[completeAnimation.length];
        int index = 0;

        for(int i = 1; i < completeAnimation.length; i++, index++) { // Убираем 1 кадр
            seeCompleteTextureRegion[index] = completeAnimation[i];
            System.out.println(index);
            System.out.println(seeCompleteTextureRegion[index].getRegionX());
        }

        for(int i = 1; i < seeCompleteTextureRegion.length; i++) { // Проверка кадров
            if(seeCompleteTextureRegion[i] != null) {
                System.out.println("[de]" + seeCompleteTextureRegion[i].getRegionX());
            } else {
                System.out.println("[Warning] i = " + i + " null");
            }
        }

        animation = new Animation(speed, completeDeleteFrame(seeCompleteTextureRegion));
    }

    private TextureRegion[] completeDeleteFrame(TextureRegion[] region) {
        TextureRegion[] seeCompleteTextureRegions = new TextureRegion[region.length-1];
        int index = 0;
        for(int i = 0; i < region.length-1; i++, index++) {
            if(region[i] != null) {
                seeCompleteTextureRegions[index] = region[i];
            }
        }
        return seeCompleteTextureRegions;
    }

    public void selectState(boolean bool) {
        if(bool) {
            testMessage = "Animation ON(LOOP)";
            isTime = true;
        } else {
            testMessage = "Animation OFF(NORMAL)";
            animation = new Animation(speed,completeAnimation[0]);
            isTime = false;
        }
    }


    public TextureRegion playAnimation() { // Запуск анимации(Не реагирует на параметр изменения состояния движения графики)
        if(isTime) {
            return animation.getKeyFrame(time += Gdx.graphics.getDeltaTime(), true);
        }
        else { return animation.getKeyFrame(time = 0); }
    }
}