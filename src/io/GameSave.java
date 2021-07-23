package io;

import model.Hex;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameSave {
    public static void saveGame(Hex[][] board) {
        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("game.dat"))) {
            ois.writeObject(board);
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }
    }
}
