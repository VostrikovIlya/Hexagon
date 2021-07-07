package Model;

public class Model {
    public static final int HEX_BOARD_LEN = 7;
    private  Hex[][] board;
    private final Player player;
    private final Bot bot;
    private boolean movePlayer;
    private  Trio trio;
    private boolean endGame;
    private String labelEndGame;


    public Model(){
        bot = new Bot();
        player = new Player();
        movePlayer = true;
        endGame = false;
    }

    public void start(){
        while(!endGame){
            if(movePlayer){
                if(trio.getElementary() != null && trio.getFinite() != null){
                    player.move(trio.getElementary(),trio.getFinite(), board, Player.PLAYER);
                    trio.setElementary(null);
                    trio.setFinite(null);
                    movePlayer = false;
                }
            }
            else {
                bot.moveBot(board);
                trio.setElementary(null);
                trio.setFinite(null);
                movePlayer = true;
            }
            endGame();
        }
        LabelEndGame();
    }

    public void endGame(){
        endGame = true;
        for(int i = 0 ;i < HEX_BOARD_LEN; i++) {
            for (int j = 0; j < HEX_BOARD_LEN; j++) {
                if (board[i][j] != null && board[i][j].getPlayer() == Player.NOT_PLAYER) {
                    endGame = false;
                    if(player.containsChips(board[i][j]))
                        player.removeChips(board[i][j]);
                    if (bot.containsChips(board[i][j]))
                        bot.removeChips(board[i][j]);
                }
                if (board[i][j] != null && board[i][j].getPlayer() == Player.PLAYER) {
                    if (bot.containsChips(board[i][j]))
                        bot.removeChips(board[i][j]);
                    player.addChips(board[i][j]);
                }
                if (board[i][j] != null && board[i][j].getPlayer() == Bot.BOT) {
                    if (player.containsChips(board[i][j])) {
                        player.removeChips(board[i][j]);
                    }
                    bot.addChips(board[i][j]);
                }
            }
        }
        if(endGame)
            return;
        if(!player.isHaveMove(board) || player.getSizeChips() == 0)
            endGame = true;
        if(!bot.isHaveMove(board) || bot.getSizeChips() == 0) {
            endGame = true;
        }
    }

    private void LabelEndGame(){
        if(player.getSizeChips() > bot.getSizeChips())
            labelEndGame = new String("WIN PLAYER");
        if(bot.getSizeChips() > player.getSizeChips())
            labelEndGame = new String("WIN BOT");
        if(bot.getSizeChips() == player.getSizeChips())
            labelEndGame = new String("DRAW");
    }

    public String getLabelEndGame(){return labelEndGame;}

    public  void setBoard(Hex[][] board){
        this.board = board;
    }

    public void setTrio(Trio trio){this.trio = trio;}

    public void setEndGame(boolean endGame){ this.endGame = endGame;}
}
