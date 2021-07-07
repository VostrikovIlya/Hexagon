package Conrol;

import Model.*;
import View.View;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model){
        this.model = model;
        this.view = view;
        model.setBoard(view.getBoard());
        model.setTrio(view.getTrio());
    }
    public void setLabelEndGame(){
        view.setSrt(model.getLabelEndGame());
    }
}
