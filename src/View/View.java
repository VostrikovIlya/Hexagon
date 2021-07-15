package View;

import Model.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
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
    private final Trio trio;
    private final Scene scene;
    private String str;
    private FlowPane root;
    private final BooleanMut backMove;
    /*
    Creates a scene with hexagonal fields and the players ' starting chips
     */
    public View(){
        backMove = new BooleanMut();
        scene = new Scene(create());
        scene.setFill(Color.rgb(2,32,39));
        trio = new Trio(null,null, 0);
    }

    private Parent create() {
        root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(800, 600);
        root.getChildren().addAll(HexGroup);
        root.setOnMouseClicked((e)->{
            if(str != null){
                endScene();
            }
        });

        Button button = new Button("LoadSave");
        button.setMaxSize(100,50);
        button.setOnMouseClicked((e)->{
            backMove.value = true;
            setBoard();
        });

        Button button1 = new Button("SaveGame");
        button1.setMaxSize(100,50);
        button1.setOnMouseClicked((e)-> saveGame());

        root.getChildren().add(button);
        root.getChildren().add(button1);

        root.setHgap(50);
        root.setStyle("-fx-background-color: null;");

        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                if (i + j > 2 && i + j < 10) {
                    board[i][j] = makeHex(i,j, Player.NOT_PLAYER);
                    HexGroup.getChildren().add(board[i][j]);
                }
            }
        }
        setChipsStart();
        return root;
    }

    public Scene getScene(){return scene;}
    /*
    The method overrides clicking on the field and generates the field.
    Takes the coordinates of the field that creates.
     */
    private Hex makeHex(int i, int j, int player){
        Hex hex = new Hex(i, j, player);
        hex.setOnMouseClicked(e ->{
            if(hex.getClick()) {
                hex.setClick(false);
                trio.setElementary(null);
                trio.setFinite(null);
                illuminationMove(hex, false);
            }
            else{
                if(hex.getPlayer() == Player.PLAYER) {
                    if(trio.getElementary() != null && trio.getElementary().getClick()) {
                        trio.getElementary().setClick(false);
                        illuminationMove(trio.getElementary(), false);
                    }
                    trio.setElementary(hex);
                    illuminationMove(hex, true);
                    hex.setClick(true);
                }
                else {
                    if(hex.getIllumination() && trio.getElementary() != null){
                        trio.getElementary().setClick(false);
                        illuminationMove(trio.getElementary(), false);
                        trio.setFinite(hex);
                    }
                }
            }
        });
        return hex;
    }

    public Hex[][] getBoard(){return board;}

    public Trio getTrio(){return trio;}

    public void endScene(){
        Label label = new Label(str);
        label.setFont(Font.font(32));
        label.setTextFill(Color.WHITESMOKE);
        root.getChildren().clear();
        root.getChildren().add(label);
    }

    public void setSrt(String str){ this.str = str;}

    /*
    Sets the initial chips of the players
     */
    private void setChipsStart(){
        board[3][0].setPlayer(Bot.BOT);
        board[6][3].setPlayer(Bot.BOT);
        board[0][6].setPlayer(Bot.BOT);
        board[6][0].setPlayer(Player.PLAYER);
        board[0][3].setPlayer(Player.PLAYER);
        board[3][6].setPlayer(Player.PLAYER);
    }
    /*
    Highlights the possible fields that the player can go to
    Accepts the field in which the player is located and the state of the backlight
     */
    private void illuminationMove(Hex hex, boolean light){
        for(int i = 0; i < Model.HEX_BOARD_LEN; i++)
            for(int j = 0; j < Model.HEX_BOARD_LEN; j++){
                if(board[i][j] != null){
                    int distance = Hex.hexDistance(hex, board[i][j]);
                    if (distance <= 2 && board[i][j].getPlayer() == Player.NOT_PLAYER)
                        board[i][j].setIllumination(light);
                }
            }
     }

     public void setBoard()  {
        Hex[][] boardTmp = loadGame();
         if(boardTmp != null){
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
     public BooleanMut getBackMove(){return backMove;}

    public  void saveGame()  {
        try(ObjectOutputStream ois  = new ObjectOutputStream(new FileOutputStream("game.dat"))){
            ois.writeObject(board);
        }catch (IOException ex){
            ex.fillInStackTrace();
        }
    }

    public  Hex[][] loadGame(){
        try(ObjectInputStream ous  = new ObjectInputStream(new FileInputStream("game.dat"))){
            return  (Hex[][]) ous.readObject();
        }catch (IOException | ClassNotFoundException ex){
            ex.fillInStackTrace();
        }
        return null;
    }
}
