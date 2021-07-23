package model;

public class Model {
    public static final int HEX_BOARD_LEN = 7;
    private final Player player;
    private final Bot bot;
    private Hex[][] board;

    public Model() {
        bot = new Bot();
        player = new Player();
    }

    public void setBoard(Hex[][] board) {
        this.board = board;
    }

    public Bot getBot() {
        return bot;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean endGame() {
        boolean endGame = true;
        for (int i = 0; i < HEX_BOARD_LEN; i++) {
            for (int j = 0; j < HEX_BOARD_LEN; j++) {
                if (board[i][j] != null && board[i][j].getPlayer() == Player.NOT_PLAYER) {
                    endGame = false;
                    if (player.containsChips(board[i][j]))
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
        if (endGame)
            return true;
        if (!player.isHaveMove(board) || player.getSizeChips() == 0)
            endGame = true;
        if (!bot.isHaveMove(board) || bot.getSizeChips() == 0) {
            endGame = true;
        }
        return endGame;
    }


    public String LabelEndGame(int gameBot) {
        if (player.getSizeChips() > bot.getSizeChips())
            return  "WIN PLAYER";
        if (bot.getSizeChips() > player.getSizeChips()) {
            if(gameBot == Bot.BOT)
                return   "WIN BOT";
            if(gameBot == Player.PLAYER)
                return  "WIN SECOND PLAYER";
        }
        if (bot.getSizeChips() == player.getSizeChips())
            return  "DRAW";
        return "";
    }
}
