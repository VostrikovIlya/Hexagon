package Model;

import java.util.ArrayList;

public class Bot extends Player {
    public static final int BOT = 2;
    public Trio res;
    public int minmax(Hex[][] map, int side, int count, int a, int b) {
        if(count == 2) {
            return endGame(map);
        }

        ArrayList<Trio> moves = new ArrayList<>();
        Trio tmp;

        for(int i = 0; i < Model.HEX_BOARD_LEN; i++)
            for(int j = 0; j < Model.HEX_BOARD_LEN; j++)
                if(map[i][j] != null && map[i][j].getPlayer() == side){

                    tmp = answer(map, side, i, j, count, a,b);

                    if(tmp == null)
                        continue;

                    moves.add(tmp);
                }
        if(moves.size() == 0)
            return endGame(map);

        if(side == BOT){
            tmp = maxArrayList(moves);
        }
        else{
            tmp = minArrayList(moves);
        }

        res = tmp;
        return tmp.getTake();
    }

   public Trio answer(Hex[][] map, int side, int i, int j, int count, int a, int b)  {
        ArrayList<Trio> moves = generateMove(map,i,j);
        Trio tmp = null;
        for(Trio trio : moves){
            Hex[][] newMap = mb_move(map, trio, side);
            int gameEnd;
            if(side == PLAYER)
                gameEnd = minmax(newMap,BOT,count+1,a, b);
            else
                gameEnd = minmax(newMap,PLAYER, count+1, a, b);

            System.gc();
            trio.setTake(gameEnd);
            if(side == PLAYER) {
                if (gameEnd < b){
                    b = gameEnd;
                }
                else{
                    continue;
                }
            }
           if(side == BOT) {
               if (gameEnd > a) {
                   a = gameEnd;
               }
               else{
                   continue;
               }
           }
           if(a > b){
               break;
           }
        }
        if(moves.size() == 0)
            return null;
        if(side == BOT)
            tmp = maxArrayList(moves);
       if (side == PLAYER)
            tmp = minArrayList(moves);

       System.gc();
       return tmp;
   }

   private ArrayList<Trio> generateMove(Hex[][] map, int i, int j){
        ArrayList<Trio> moves = new ArrayList<>();
        for(int k = 0; k < Model.HEX_BOARD_LEN; k++)
            for(int l = 0; l < Model.HEX_BOARD_LEN; l++) {
                if(map[k][l] != null)
                    if (map[k][l].getPlayer() == NOT_PLAYER && Hex.hexDistance(map[i][j], map[k][l]) <= 2)
                        moves.add(new Trio(map[i][j], map[k][l], 0));
            }
       return moves;
   }

   private Hex[][] mb_move(Hex[][] map, Trio trio, int side){
        Hex[][] newMap = new Hex[7][7];
        for(int i = 0; i < Model.HEX_BOARD_LEN; i++)
            for(int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                try {
                    if(map[i][j] != null){
                        newMap[i][j] = map[i][j].clone();
                    }
                } catch (CloneNotSupportedException ex) {
                    ex.fillInStackTrace();
                }
            }

        move(trio.getElementary(), trio.getFinite(),newMap, side);

        return newMap;
   }
    private Trio maxArrayList(ArrayList<Trio> moves){
        Trio tmp = moves.get(0);
        for(Trio trio : moves){
            if(trio.getTake() > tmp.getTake())
                tmp = trio;
        }
        return tmp;
    }
    private Trio minArrayList(ArrayList<Trio> moves){
        Trio tmp = moves.get(0);
        for(Trio trio : moves){
            if(trio.getTake() < tmp.getTake())
                tmp = trio;
        }
        return tmp;
    }

    private int endGame(Hex[][] map){
        int count = 0;
        for(int i = 0; i < Model.HEX_BOARD_LEN; i++)
            for(int j = 0; j < Model.HEX_BOARD_LEN; j++)
                if(map[i][j] != null && map[i][j].getPlayer() == BOT) {
                    count++;
                }
        return count;
    }

    public void moveBot(Hex[][] map){
        int a = -1000, b = 1000;
        minmax(map,BOT, 0, a, b);
        move(res.getElementary(), res.getFinite(), map, BOT);
    }
}
