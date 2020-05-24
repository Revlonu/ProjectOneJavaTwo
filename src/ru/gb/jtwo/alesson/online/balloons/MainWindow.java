package ru.gb.jtwo.alesson.online.balloons;

import ru.gb.jtwo.alesson.online.Animal;
import ru.gb.jtwo.alesson.online.Bird;
import ru.gb.jtwo.alesson.online.Cat;
import ru.gb.jtwo.alesson.online.Core.GameObject;
import ru.gb.jtwo.alesson.online.Core.MainCanvas;
import ru.gb.jtwo.alesson.online.Core.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private int countGameObject = 10;

    GameObject[] gameObjects = new GameObject[countGameObject];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }

    private MainWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        MainCanvas canvas = new MainCanvas(this);
        initApplication();
        add(canvas);
        setTitle("Circles");
        setVisible(true);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    addGameObject(new Ball());
                } else if(e.getButton() == MouseEvent.BUTTON3){
                    removeGameObject();
                }

            }
        });
    }

    private void initApplication() {
        gameObjects[0] = new Background();
        for (int i = 1; i < gameObjects.length; i++) {
            gameObjects[i] = new Ball();
        }
    }

    public void onDrawFrame(MainCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(MainCanvas canvas, float deltaTime) {
        for (int i = 0; i < countGameObject; i++) {
            gameObjects[i].update(canvas, deltaTime);
        }
    }

    private void render(MainCanvas canvas, Graphics g) {
        for (int i = 0; i < countGameObject; i++) {
            gameObjects[i].render(canvas, g);
        }
    }
    public void addGameObject(GameObject s) {
        if (gameObjects.length == countGameObject) {
            GameObject[] copy = new GameObject[gameObjects.length * 2];
            System.arraycopy(gameObjects, 0, copy, 0, gameObjects.length);
            gameObjects = copy;
        }
        gameObjects[countGameObject++] = s;
    }
    public void removeGameObject(){
        if(countGameObject > 1) {
            countGameObject--;
        }
    }



    private static void method1(Animal a) {
        a.name = "Barsik";
    }

    private static void sum(int a, int b) {

    }

    private static void typecastExample() {
        Cat c = new Cat("Barsik");
        Bird b = new Bird("Chijik");

        Animal[] zoo = {c, b};

        for (int i = 0; i < zoo.length; i++) {
            zoo[i].walk();

            if (zoo[i] instanceof Bird) {
                ( (Bird) zoo[i] ).fly();
            }
        }
    }
}
