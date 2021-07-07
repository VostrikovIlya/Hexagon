package Model;

import java.util.HashSet;

public class Player {
    public final static int PLAYER = 1;
    public final static int NOT_PLAYER = 0;
    public HashSet<Hex> chips = new HashSet<>();


    public void move(Hex startPoint, Hex finishPoint, Hex[][] board, int side){
        int distance = Hex.hexDistance(startPoint,finishPoint);
        if(finishPoint.getPlayer() == NOT_PLAYER && distance <= 2 && distance != 0){
            board[finishPoint.getX()][finishPoint.getY()].setPlayer(side);

            if(distance == 2) {
                board[startPoint.getX()][startPoint.getY()].setPlayer(NOT_PLAYER);
            }

            for(int i = 0; i < Model.HEX_BOARD_LEN; i++)
                for(int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                    if(board[i][j] != null)
                        if (Hex.hexDistance(finishPoint, board[i][j]) == 1 && board[i][j].getPlayer() != NOT_PLAYER)
                            board[i][j].setPlayer(side);

                }


        }
    }
    public void addChips(Hex hex){
        chips.add(hex);
    }

    public void removeChips(Hex hex){
        chips.remove(hex);
    }

    public boolean containsChips(Hex hex){
        return chips.contains(hex);
    }

    public int getSizeChips(){
        return chips.size();
    }

    public boolean isHaveMove(Hex[][] board){
        for(Hex hex : chips){
            for(int i = 0; i < 7; i++)
                for(int j = 0; j < 7; j++) {
                    if(board[i][j] != null) {
                        if (board[i][j].getPlayer() == NOT_PLAYER && Hex.hexDistance(hex, board[i][j]) <= 2)
                            return true;
                    }
                }
        }
        return false;
    }
}
