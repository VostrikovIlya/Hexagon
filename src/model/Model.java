package model;

import conrol.Controller;

public class Model {
    public static final int HEX_BOARD_LEN = 7;
    private final Controller controller;
    private final Player player;
    private final Bot bot;
    private final Trio trio;
    private final BooleanMut backMove;
    private Hex[][] board;
    private boolean movePlayer;
    private boolean endGame;
    private String labelEndGame;


    public Model(Controller controller) {
        this.controller = controller;
        this.board = controller.getBoard();
        this.trio = controller.getTrio();
        this.backMove = controller.getBackMove();
        bot = new Bot();
        player = new Player();
        movePlayer = true;
        endGame = false;
    }

    public void start() {
        while (!endGame) {
            if (backMove.value) {
                board = controller.getBoard();
                backMove.value = false;
            }
            if (movePlayer) {
                if (trio.getElementary() != null && trio.getFinite() != null) {
                    player.move(trio.getElementary(), trio.getFinite(), board, Player.PLAYER);
                    trio.setElementary(null);
                    trio.setFinite(null);
                    movePlayer = false;

                    backMove.value = false;
                }
            } else {
                bot.moveBot(board);
                trio.setElementary(null);
                trio.setFinite(null);
                movePlayer = true;
                backMove.value = false;
            }
            endGame();
        }
        LabelEndGame();
        controller.setLabelEndGame(labelEndGame);
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public void endGame() {
        endGame = true;
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
            return;
        if (!player.isHaveMove(board) || player.getSizeChips() == 0)
            endGame = true;
        if (!bot.isHaveMove(board) || bot.getSizeChips() == 0) {
            endGame = true;
        }
    }


    private void LabelEndGame() {
        if (player.getSizeChips() > bot.getSizeChips())
            labelEndGame = "WIN PLAYER";
        if (bot.getSizeChips() > player.getSizeChips())
            labelEndGame = "WIN BOT";
        if (bot.getSizeChips() == player.getSizeChips())
            labelEndGame = "DRAW";
    }


}
