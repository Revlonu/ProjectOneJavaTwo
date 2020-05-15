package ru.gb.jtwo.alesson.online;

import java.awt.*;

public abstract class Background {



    public static void  background(MainCanvas canvas){

        Color color = new Color((int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255));
        canvas.setBackground(color);
    }
}
