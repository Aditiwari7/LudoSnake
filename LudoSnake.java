package com.example.ludosnake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LudoSnake extends Application {

    Group tileGroup = new Group();

    public static final int tileSize = 40;
    int height = 10;
    int width = 10;
    int yLine = 430;
    int xLine = 40;

    int diceValue = 1;

    Label randResult;
    Button gameButton;
    Player player1, player2;
    boolean gameStart=true, player1Turn=true, player2Turn=false;

    public Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize+88);
        root.getChildren().addAll(tileGroup);

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Tile tile = new Tile(tileSize, tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                tileGroup.getChildren().addAll(tile);
            }
        }

        player1 = new Player(tileSize, Color.WHITE);
        player2 = new Player(tileSize-10, Color.BLACK);

        randResult = new Label("Game Not Started");
        randResult.setTranslateX(150);
        randResult.setTranslateY(410);

        Button player1Button = new Button("Player 1");
        player1Button.setTranslateX(10);
        player1Button.setTranslateY(yLine);
        player1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(player1Turn){
                        getDiceValue();
                        randResult.setText("Player 1 - " + String.valueOf(diceValue));
                        player1.movePlayer(diceValue);

                        player1Turn = false;
                        player2Turn = true;
//                        player1.playerAtSnakeOrLadder();
                        gameOver();
                    }
                }
            }
        });

        Button player2Button = new Button("Player 2");
        player2Button.setTranslateX(300);
        player2Button.setTranslateY(yLine);
        player2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(player2Turn){
                        getDiceValue();
                        randResult.setText("Player 2 - " + String.valueOf(diceValue));
                        player2.movePlayer(diceValue);

                        player2Turn = false;
                        player1Turn = true;
//                        player2.playerAtSnakeOrLadder();
                        gameOver();
                    }
                }
            }
        });

        gameButton = new Button("Start Game");
        gameButton.setTranslateX(150);
        gameButton.setTranslateY(yLine);
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                randResult.setText("Started");
                gameStart = true;
                gameButton.setText("Game Going");
            }
        });

        Image img = new Image("C:\\Users\\aditi\\IdeaProjects\\LudoSnake\\src\\istockphoto-531466314-612x612.jpg");
//        img = new Image("C:\\Users\\aditi\\IdeaProjects\\LudoSnake\\src\\istockphoto-531466314-612x612.jpg");
        ImageView boardImage = new ImageView();
        boardImage.setImage(img);
        boardImage.setFitHeight(tileSize*height);
        boardImage.setFitWidth(tileSize*width);

        tileGroup.getChildren().addAll(boardImage, randResult, player1.getGamePiece(), player2.getGamePiece(), player1Button, player2Button, gameButton);

        return root;
    }

    void gameOver(){
        if(player1.getWinningStatus() == true){
            randResult.setText("Player 1 Won");
            gameButton.setText("Start Again");
            gameStart = false;
        }
        else if(player2.getWinningStatus() == true){
            randResult.setText("Player 2 Won");
            gameButton.setText("Start Again");
            gameStart = false;
        }
    }
    private void getDiceValue(){
        diceValue = (int)(Math.random()*6 +1);

    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(LudoSnake.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("LudoSnake");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long currTime = System.currentTimeMillis();
                long dt = currTime - Player.lastMovementTime;

                if(dt > 1000){
                    Player.lastMovementTime = currTime;

                    player1.playerAtSnakeOrLadder();
                    player2.playerAtSnakeOrLadder();
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}