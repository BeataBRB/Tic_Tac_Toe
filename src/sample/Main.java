package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static TicTacToeSquare[] board = new TicTacToeSquare[9];
    private static int boardTracker;
    private static StringProperty xPlayer = new SimpleStringProperty("X player");
    private static StringProperty oPlayer = new SimpleStringProperty("O player");
    private static IntegerProperty xScore = new SimpleIntegerProperty(0);
    private static IntegerProperty oScore = new SimpleIntegerProperty(0);
    private static IntegerProperty deadHeat = new SimpleIntegerProperty(0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        MenuItem newGameItem = new MenuItem("_New Game");
        newGameItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        newGameItem.setOnAction(e -> newGame());

        MenuItem exitItem = new MenuItem("E_xit");
        exitItem.setOnAction(e -> Platform.exit());

        Menu gameMenu = new Menu("_Game");
        gameMenu.getItems().addAll(newGameItem, exitItem);

        Text xText = new Text();
        xText.textProperty().bind(Bindings.concat(xPlayer).concat(" wins ").concat(xScore.asString()));

        Text oText = new Text();
        oText.textProperty().bind(Bindings.concat(oPlayer).concat(" wins ").concat(oScore.asString()));

        Text deadHeatText = new Text();
        deadHeatText.textProperty().bind(Bindings.concat("Dead-Heat: ").concat(deadHeat.asString()));

        activateMnemonics(gameMenu, newGameItem, exitItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gameMenu);
        root.setTop(menuBar);

        GridPane layout = new GridPane();
        for (int i = 0; i < board.length; i++) {
            board[i] = new TicTacToeSquare();
            layout.add(board[i].button(), i / 3, i % 3);
        }
        root.setCenter(layout);

        stage.setScene(new Scene(root));
        stage.setTitle("Tic Tac Toe / Kółko i Krzyżyk");
        stage.show();
    }

    public static void evaluateBoard() {
        for (int i = 0, j = 0; i < 3; i++) {
            // Poziomy
            if (checkSet(j++, j++, j++)) {
                return;
            }
            // Pionowy
            if (checkSet(i, i + 3, i + 6)) {
                return;
            }
        }

        // Przekątna
        if (checkSet(0, 4, 8) || checkSet(2, 4, 6)) {
            return;
        }

        if (boardTracker == 8) {
            gameEndPrompt("No body wins. Dead-Heat!");
            deadHeat.setValue(deadHeat.getValue() + 1);
            return;
        }

        boardTracker++;
    }

    private static boolean checkSet(int square1, int square2, int square3) {
        if (boardTracker >= 4) {
            if (board[square1].equals(board[square2]) && board[square2].equals(board[square3])) {
                gameEndPrompt(checkWinner(board[square1].button().getText()) + " wins!");
                return true;
            }
        }
        return false;
    }

    private static void gameEndPrompt(String text) {
        endGame();

        Stage stage = new Stage();
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");

        Button reset = new Button("New Game");
        reset.setOnAction(e -> {
            stage.close();
            newGame();
        });
        reset.setDefaultButton(true);

        HBox layout = new HBox(20);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(label, reset);
        layout.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(layout));
        stage.sizeToScene();
        stage.show();
    }

    private static void reset(TicTacToeSquare[] board) {
        for (int i = 0; i < board.length; i++) {
            board[i].clear();
        }
    }

    private static void endGame() {
        for (int i = 0; i < board.length; i++) {
            board[i].button().setDisable(true);
        }
    }

    private void activateMnemonics(MenuItem... items) {
        for (MenuItem item : items) {
            item.setMnemonicParsing(true);
        }
    }

    private static void newGame() {
        boardTracker = 0;
        reset(board);
    }

    private static String checkWinner(String winner) {
        if (winner.equals("X")) {
            xScore.setValue(xScore.getValue() + 1);
            return xPlayer.getValue();
        } else {
            oScore.setValue(oScore.getValue() + 1);
            return oPlayer.getValue();
        }
    }
}
