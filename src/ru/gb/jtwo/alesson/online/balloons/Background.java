package ru.gb.jtwo.alesson.online.balloons;

import ru.gb.jtwo.alesson.online.Core.GameObject;
import ru.gb.jtwo.alesson.online.Core.MainCanvas;
import ru.gb.jtwo.alesson.online.Core.Sprite;

import java.awt.*;

public class Background implements GameObject {
    private  Color color;
    private float counterTime;
    @Override
    public void update(MainCanvas canvas, float deltaTime) {
        counterTime += deltaTime;
        if(counterTime > 5){
            color = new Color((int) (Math.random() * 255),
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255));
            counterTime -= 5;
        }

    }

    @Override
    public void render(MainCanvas canvas, Graphics g) {
            canvas.setBackground(color);
    }
}
