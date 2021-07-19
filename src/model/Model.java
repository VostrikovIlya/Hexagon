package model;

import conrol.Controller;


public class Model {
    public static final int HEX_BOARD_LEN = 7;
    private final Controller controller;
    private final Player player;
    private final Player player1;
    private final Bot bot;
    private final Trio trio;
    private final BooleanMut loadGame;
    private final IntegerMut gameBot;
    private final BooleanMut movePlayer;
    private Hex[][] board;
    private boolean endGame;
    private String labelEndGame;


    public Model(Controller controller) {
        this.controller = controller;
        this.trio = controller.getTrio();
        this.loadGame = controller.getBackMove();
        this.gameBot = controller.getGameBot();
        bot = new Bot();
        player = new Player();
        player1 = new Player();
        movePlayer = controller.getMovePlayer();
        movePlayer.value = true;
        endGame = false;
    }

    public void start() {
        while (gameBot.value == 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (gameBot.value == Player.PLAYER)
            gamePlayer();
        if (gameBot.value == Bot.BOT)
            gameBot();
    }

    public void gamePlayer() {
        this.board = controller.getBoard();
        while (!endGame) {
            if (loadGame.value) {
                board = controller.getBoard();
                loadGame.value = false;
            }
            if (movePlayer.value) {
                if (trio.getElementary() != null && trio.getFinite() != null) {
                    player.move(trio.getElementary(), trio.getFinite(), board, Player.PLAYER);
                    trio.setElementary(null);
                    trio.setFinite(null);
                    movePlayer.value = false;
                    loadGame.value = false;
                }
            } else {
                if (trio.getElementary() != null && trio.getFinite() != null) {
                    player1.move(trio.getElementary(), trio.getFinite(), board, Bot.BOT);
                    trio.setElementary(null);
                    trio.setFinite(null);
                    movePlayer.value = true;
                    loadGame.value = false;
                }
            }
            endGame(player, player1);
        }
        LabelEndGame(player, player1);
        controller.setLabelEndGame(labelEndGame);
    }

    public void gameBot() {
        this.board = controller.getBoard();
        while (!endGame) {
            if (loadGame.value) {
                board = controller.getBoard();
                loadGame.value = false;
            }
            if (movePlayer.value) {
                if (trio.getElementary() != null && trio.getFinite() != null) {
                    player.move(trio.getElementary(), trio.getFinite(), board, Player.PLAYER);
                    trio.setElementary(null);
                    trio.setFinite(null);
                    movePlayer.value = false;

                    loadGame.value = false;
                }
            } else {
                bot.moveBot(board);
                trio.setElementary(null);
                trio.setFinite(null);
                movePlayer.value = true;
                loadGame.value = false;
            }
            endGame(player, bot);
        }
        LabelEndGame(player, bot);
        controller.setLabelEndGame(labelEndGame);
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public void endGame(Player player, Player bot) {
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


    private void LabelEndGame(Player player, Player bot) {
        if (player.getSizeChips() > bot.getSizeChips())
            labelEndGame = "WIN PLAYER";
        if (bot.getSizeChips() > player.getSizeChips()) {
            if(gameBot.value == Bot.BOT)
                labelEndGame = "WIN BOT";
            if(gameBot.value == Player.PLAYER)
                labelEndGame = "WIN SECOND PLAYER";
        }
        if (bot.getSizeChips() == player.getSizeChips())
            labelEndGame = "DRAW";
    }


}
