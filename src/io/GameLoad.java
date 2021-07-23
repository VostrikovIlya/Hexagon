package io;

import model.Hex;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameLoad {
    public static Hex[][] loadGame() {
        try (ObjectInputStream ous = new ObjectInputStream(new FileInputStream("game.dat"))) {
            return (Hex[][]) ous.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.fillInStackTrace();
        }
        return null;
    }
}
