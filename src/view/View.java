package view;

import model.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;

public class View {
    private final Group HexGroup = new Group();
    private final Hex[][] board = new Hex[7][7];
    private final Scene scene;
    private final Trio trio;
    private final BooleanMut backMove;
    private final BooleanMut movePlayer;
    private final IntegerMut gameBot;
    private final FlowPane root;
    private String stringEnd;

    public View() {
        root = new FlowPane();
        scene = new Scene(root);
        scene.setFill(Color.rgb(2, 32, 39));
        trio = new Trio(null, null, 0);
        movePlayer = new BooleanMut(true);
        gameBot = new IntegerMut();
        backMove = new BooleanMut();
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(800, 600);
        root.setHgap(50);
        root.setStyle("-fx-background-color: null;");
        startScene();
    }

    private void create() {
        root.getChildren().clear();
        root.getChildren().addAll(HexGroup);
        root.setOnMouseClicked((e) -> {
            if (stringEnd != null) {
                endScene();
            }
        });

        Button button = new Button("LoadSave");
        button.setMaxSize(100, 50);
        button.setOnMouseClicked((e) -> {
            backMove.value = true;
            setBoard(loadGame());
        });

        Button button1 = new Button("SaveGame");
        button1.setMaxSize(100, 50);
        button1.setOnMouseClicked((e) -> saveGame());

        root.getChildren().add(button);
        root.getChildren().add(button1);

        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                if (i + j > 2 && i + j < 10) {
                    board[i][j] = makeHex(i, j, Player.NOT_PLAYER);
                    HexGroup.getChildren().add(board[i][j]);
                }
            }
        }
        setChipsStart();
    }

    private Hex makeHex(int i, int j, int player) {
        Hex hex = new Hex(i, j, player);
        hex.setOnMouseClicked((event) -> {
            if (hex.getPlayer() == Player.PLAYER && movePlayer.value
                    || hex.getPlayer() == Bot.BOT && !movePlayer.value) {
                if (hex.getClick()) {
                    hex.setClick(false);
                    trio.setElementary(null);
                    trio.setFinite(null);
                    illuminationMove(hex, false);
                } else {
                    hex.setClick(true);
                    if (trio.getElementary() != null) {
                        trio.getElementary().setClick(false);
                        illuminationMove(trio.getElementary(), false);
                    }
                    trio.setElementary(hex);
                    illuminationMove(hex, true);
                }
            } else if (hex.getIllumination() && trio.getElementary() != null) {
                trio.getElementary().setClick(false);
                illuminationMove(trio.getElementary(), false);
                trio.setFinite(hex);
            }
        });
        return hex;
    }

    public Hex[][] getBoard() {
        return board;
    }

    public Trio getTrio() {
        return trio;
    }

    public Scene getScene() {
        return scene;
    }

    public BooleanMut getBackMove() {
        return backMove;
    }

    public IntegerMut getGameBot() {
        return gameBot;
    }

    public BooleanMut getMovePlayer() {
        return movePlayer;
    }

    public void setSrt(String str) {
        this.stringEnd = str;
    }

    private void setChipsStart() {
        board[3][0].setPlayer(Bot.BOT);
        board[6][3].setPlayer(Bot.BOT);
        board[0][6].setPlayer(Bot.BOT);
        board[6][0].setPlayer(Player.PLAYER);
        board[0][3].setPlayer(Player.PLAYER);
        board[3][6].setPlayer(Player.PLAYER);
    }

    public void setBoard(Hex[][] boardTmp) {
        if (boardTmp != null) {
            HexGroup.getChildren().clear();
            for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
                for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                    if (boardTmp[i][j] != null) {
                        board[i][j] = makeHex(i, j, boardTmp[i][j].getPlayer());
                        HexGroup.getChildren().add(board[i][j]);
                    }
                }
            }
        }
    }

    private void illuminationMove(Hex hex, boolean light) {
        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                if (board[i][j] != null) {
                    int distance = Hex.hexDistance(hex, board[i][j]);
                    if ((distance <= 2) && (board[i][j].getPlayer() == Player.NOT_PLAYER))
                        board[i][j].setIllumination(light);
                }
            }
        }
    }

    public void startScene() {
        Label label1 = new Label("BOT");
        Label label2 = new Label("PLAYER");
        label1.setFont(Font.font(32));
        label1.setTextFill(Color.WHITESMOKE);
        label2.setFont(Font.font(32));
        label2.setTextFill(Color.WHITESMOKE);
        label1.setOnMouseClicked(event -> {
            gameBot.value = Bot.BOT;
            create();
        });
        label2.setOnMouseClicked(event -> {
            gameBot.value = Player.PLAYER;
            create();
        });
        root.getChildren().add(label1);
        root.getChildren().add(label2);
    }

    public void endScene() {
        Label label = new Label(stringEnd);
        label.setFont(Font.font(32));
        label.setTextFill(Color.WHITESMOKE);
        root.getChildren().clear();
        root.getChildren().add(label);
    }

    public void saveGame() {
        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("game.dat"))) {
            ois.writeObject(board);
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }
    }

    public Hex[][] loadGame() {
        try (ObjectInputStream ous = new ObjectInputStream(new FileInputStream("game.dat"))) {
            return (Hex[][]) ous.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.fillInStackTrace();
        }
        return null;
    }
}
