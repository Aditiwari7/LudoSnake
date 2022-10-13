package com.example.ludosnake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public Tile(int x, int y){
        setWidth(LudoSnake.tileSize);
        setHeight(LudoSnake.tileSize);
        setFill(Color.RED);
        setStroke(Color.BLACK);
    }
}
