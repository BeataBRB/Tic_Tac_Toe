package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    private Button[][] board;
    private Label textX;
    private Label textY;
    private Button buttonReset;
    private char tab[][];
    private int counterX = 0;
    private int counterY = 0;
    private String [] symbol = {"X", "O"};
    private int player = 0;
    private  int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = new GridPane();

        board = new Button[3][3];

        int k1 = 11;
        int w1 = 10;
        for(int w = 0; w < board.length; w++){
            for(int k = 0; k < board.length; k++){
                board[w][k] = new Button("");
                board[w][k].setId("plansza-pole");
                board[w][k].setPrefSize(100,100);
                gridPane.add(board[w][k], w+ w1, k+ k1);
                gridPane.setHgap(10);
                gridPane.setVgap(10);
            }
        }

        Label textLicznik = new Label("Win:");
        gridPane.add(textLicznik, 2,8);

        textX = new Label("X -> 0");
        gridPane.add(textX, 2,9);

        textY = new Label("O -> 0");
        gridPane.add(textY, 2,10);

        Button buttonNewGame = new Button("New Game");
        gridPane.add(buttonNewGame, w1 +1, k1 +4);
        buttonNewGame.setId("button-new-game");

        // Scena.
        Scene scene = new Scene(gridPane, 650, 650);
        // Okna.
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();

        for(int i=0; i<3; i++) {
            for(int j = 0; j < 3; j++){
                int finalJ = j;
                int finalI = i;
                board[i][j].setOnAction(action -> {
                    click(finalI, finalJ);
                });
            }
        }

        buttonNewGame.setOnAction(action -> {
            for(int w = 0; w<3; w++){
                for(int k =0; k<3; k++){
                    board[w][k].setText("");
                    board[w][k].setDisable(false);
                }
            }
            player = 0;
            textX.setText("X -> " + counterX);
            textY.setText("O -> " + counterY);
            counter = 0;
        });
    }

    public void click(int i, int j){
        if(!board[i][j].isDisable()){
            board[i][j].setText(symbol[player]);
            board[i][j].setDisable(true);
            checkIfPlayerWon();
            player = (player + 1) % 2;
            counter++;
        }
        // ruch komputera:
        if(player == 1 && counter < 10){
            Random generator = new Random();
            int min = 0;
            int max = 2;
            int randComputerI = generator.nextInt((max - min) + 1) + min;
            int randComputerJ = generator.nextInt((max - min) + 1) + min;
            click(randComputerI, randComputerJ);
        }
    }

    public void  checkIfPlayerWon(){
        if(board[0][0].getText().equals(symbol[player]) && board[1][1].getText().equals(symbol[player])&& board[2][2].getText().equals(symbol[player]) ){
            if(board[0][0].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[0][0].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
        else if(board[0][0].getText().equals(symbol[player]) && board[0][1].getText().equals(symbol[player])&&
                board[0][2].getText().equals(symbol[player]) ){
            if(board[0][0].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[0][0].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
        else if(board[1][0].getText().equals(symbol[player]) && board[1][1].getText().equals(symbol[player])&&
                board[1][2].getText().equals(symbol[player]) ){
            if(board[1][0].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[1][0].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
        else if(board[2][0].getText().equals(symbol[player]) && board[2][1].getText().equals(symbol[player])&&
                board[2][2].getText().equals(symbol[player]) ){
            if(board[2][0].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[2][0].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
        else if(board[0][0].getText().equals(symbol[player]) && board[1][0].getText().equals(symbol[player])&&
                board[2][0].getText().equals(symbol[player]) ){
            if(board[0][0].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[0][0].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
        else if(board[0][1].getText().equals(symbol[player]) && board[1][1].getText().equals(symbol[player])&&
                board[2][1].getText().equals(symbol[player]) ){
            if(board[1][1].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[1][1].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
        else if(board[2][0].getText().equals(symbol[player]) && board[1][1].getText().equals(symbol[player])&&
                board[0][2].getText().equals(symbol[player]) ){
            if(board[2][0].getText().equals("X")){
                textX.setText("X -> " + ++counterX);
            }else if(board[2][0].getText().equals("O")){
                textY.setText("O -> " + ++counterY);
            }
            alert();
        }
    }
    public void alert(){
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                board[i][j].setDisable(true);
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText(null);
        alert.setContentText("Win: " + symbol[player]);
        player = -1;
        alert.showAndWait();
    }

}

