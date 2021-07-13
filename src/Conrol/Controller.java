package Conrol;

import Model.*;
import View.View;

public class Controller {
    private final View view;

    public Controller(View view){
        this.view = view;
    }
    public void setLabelEndGame(String str){
        view.setSrt(str);
    }
    public Hex[][] getBoard(){
        return view.getBoard();
    }

    public Trio getTrio(){
        return view.getTrio();
    }

    public BooleanMut getBackMove(){
        return view.getBackMove();
    }
}
