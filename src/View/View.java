package View;

import Model.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class View {
    private final Group HexGroup = new Group();
    private final Hex[][] board = new Hex[7][7];
    private final Trio trio;
    private Scene scene;
    private String str;
    private FlowPane root;
    /*
    Creates a scene with hexagonal fields and the players ' starting chips
     */
    public View(){
        scene = new Scene(create());
        scene.setFill(Color.rgb(2,32,39));
        trio = new Trio(null,null, 0);
    }

    private Parent create() {
        root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(630, 550);
        root.getChildren().addAll(HexGroup);
        root.setOnMouseClicked((e)->{
            if(str != null){
                endScene();
            }
        });

        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                if (i + j > 2 && i + j < 10) {
                    board[i][j] = makeHex(i,j);
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
    private Hex makeHex(int i, int j){
        Hex hex = new Hex(i, j, Player.NOT_PLAYER);
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
}
