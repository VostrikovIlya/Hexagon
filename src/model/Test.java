package model;

public class Test {
    public static int failed;
    public static int count;

    public static void testMove1() {
        Hex[][] board = new Hex[7][7];
        Player player = new Player();
        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                board[i][j] = new Hex(i, j, Player.NOT_PLAYER);
            }
        }
        check(player.move(board[3][0], board[3][2], board, Player.PLAYER) == false);
    }

    public static void testMove2() {
        Hex[][] board = new Hex[7][7];
        Player player = new Player();
        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                board[i][j] = new Hex(i, j, Player.NOT_PLAYER);
            }
        }
        board[3][0].setPlayer(Player.PLAYER);
        check(player.move(board[3][0], board[3][2], board, Player.PLAYER) == true);
    }

    public static void testMove3() {
        Hex[][] board = new Hex[7][7];
        Player player = new Player();
        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                board[i][j] = new Hex(i, j, Player.NOT_PLAYER);
            }
        }
        board[3][0].setPlayer(Player.PLAYER);
        check(player.move(board[3][0], board[3][4], board, Player.PLAYER) == false);
    }

    public static void testMove4() {
        Hex[][] board = new Hex[7][7];
        Player player = new Player();
        for (int i = 0; i < Model.HEX_BOARD_LEN; i++) {
            for (int j = 0; j < Model.HEX_BOARD_LEN; j++) {
                board[i][j] = new Hex(i, j, Player.NOT_PLAYER);
            }
        }
        board[3][0].setPlayer(Player.PLAYER);
        board[3][2].setPlayer(Player.PLAYER);
        check(player.move(board[3][0], board[3][2], board, Player.PLAYER) == false);
    }

    public static void testMove5() {
        Player player = new Player();
        Hex hex1 = new Hex(Model.HEX_BOARD_LEN + 1, Model.HEX_BOARD_LEN + 1, Player.PLAYER);
        Hex hex2 = new Hex(1, 1, Player.NOT_PLAYER);
        Hex[][] board = new Hex[7][7];
        check(player.move(hex1, hex2, board, Player.PLAYER) == false);
    }

    public static void testMove6() {
        Player player = new Player();
        Hex hex1 = new Hex(0, 0, Player.PLAYER);
        Hex hex2 = new Hex(1, 1, Player.NOT_PLAYER);
        Hex[][] board = null;
        check(player.move(hex1, hex2, board, Player.PLAYER) == false);
    }

    public static void testMoveBot() {
        Bot bot = new Bot();
        Hex[][] board = null;
        check(bot.moveBot(board) == false);
    }


    public static void show() {
        if (failed == 0) {
            System.out.println("\nOK");
        } else {
            System.out.println(failed + "test of" + count + "failed.");
        }
    }

    public static void check(boolean b) {
        count++;
        if (!b) {
            failed++;
            throw new RuntimeException("Test Failed!");
        }
        System.out.print(".");
    }

    public static void runTest() {
        testMove1();
        testMove2();
        testMove3();
        testMove4();
        testMove5();
        testMove6();
        testMoveBot();
    }

    public static void main(String[] args) {
        runTest();
        show();
    }
}
