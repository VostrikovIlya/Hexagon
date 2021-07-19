package conrol;

import model.*;
import view.View;

public class Controller {
    private final View view;

    public Controller(View view) {
        this.view = view;
    }

    public void setLabelEndGame(String str) {
        view.setSrt(str);
    }

    public Hex[][] getBoard() {
        return view.getBoard();
    }

    public Trio getTrio() {
        return view.getTrio();
    }

    public BooleanMut getBackMove() {
        return view.getBackMove();
    }

    public IntegerMut getGameBot() {
        return view.getGameBot();
    }

    public BooleanMut getMovePlayer(){
        return view.getMovePlayer();
    }
}
