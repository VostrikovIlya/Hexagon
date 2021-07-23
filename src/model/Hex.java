package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Hex extends Polygon implements Cloneable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public transient static final int HEXSIZE = 40;

    private final int x;
    private final int y;
    private int player;
    private transient boolean click = false;
    private transient boolean illumination = false;

    public Hex(int x, int y, int player) {
        this.player = player;
        this.x = x;
        this.y = y;
        double[] centres = calculatePixelHexCentre(x, y);
        Double[] edges = calculateHexEdges(centres[0], centres[1], HEXSIZE - 1);
        this.getPoints().addAll(edges);

        if (player == Player.PLAYER)
            setFill(Color.ORANGE);
        if (player == Bot.BOT )
            setFill(Color.LIGHTSKYBLUE);
        if (player == Player.NOT_PLAYER)
            setFill(Color.LAVENDER);
    }

    public static int[] correctionPoint(int x, int y) {
        int[] ar = new int[2];
        if (y < 2)
            x--;
        if (y > 3) {
            x++;
            if (y > 5)
                x++;
        }
        ar[0] = x;
        ar[1] = y;
        return ar;
    }

    public void setClick(boolean click) {
        this.click = click;
        if (click)
            setFill(Color.SEAGREEN);
        else if (player == Player.PLAYER){
            setFill(Color.ORANGE);
        }
        else if (player == Bot.BOT){
            setFill(Color.LIGHTSKYBLUE);
        }
    }

    public void setIllumination(boolean illumination) {
        this.illumination = illumination;
        if (illumination)
            setFill(Color.LIGHTSEAGREEN);
        else
            setFill(Color.LAVENDER);
    }

    public void setPlayer(int pl) {
        player = pl;
        if (player == Player.PLAYER)
            setFill(Color.ORANGE);
        if (player == Bot.BOT)
            setFill(Color.LIGHTSKYBLUE);
        if (player == Player.NOT_PLAYER)
            setFill(Color.LAVENDER);
    }

    public boolean getClick() {
        return click;
    }

    public boolean getIllumination() {
        return illumination;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayer() {
        return player;
    }

    public static int hexDistance(final Hex a, final Hex b) {
        return (Math.abs(a.x - b.x) + Math.abs(a.x + a.y - b.x - b.y) + Math.abs(a.y - b.y)) / 2;
    }

    public static double[] calculatePixelHexCentre(int ax, int ay) {
        double h;
        double w;
        double[] newcoords = new double[2];
        int[] correctPoint = correctionPoint(ax, ay);
        ax = correctPoint[0];
        ay = correctPoint[1];
        h = HEXSIZE * 2; // height
        w = Math.sqrt(3) / 2 * h; // width
        newcoords[0] = w + ax * w; // horizontal distance
        newcoords[1] = h + ay * ((3.0 / 4.0) * h); // vertical distance
        if (ay % 2 == 1) { // this checks for odd/even: if odd, offset 2nd, 4th,...
            newcoords[0] = newcoords[0] + (w / 2);
        }
        return newcoords;
    }

    public Double[] calculateHexEdges(double centrex, double centrey, int size) {
        double[] centre = {centrex, centrey};
        Double[] edges = new Double[12];
        int count = 0;
        for (int corner = 0; corner < 6; corner++) {
            double angle_degree;
            angle_degree = 60 * corner + 30;
            double angle_radius = Math.PI / 180 * angle_degree;
            edges[count] = (centre[0] + size * Math.cos(angle_radius));
            edges[++count] = (centre[1] + size * Math.sin(angle_radius));
            count++;
        }
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return x == hex.x && y == hex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public Hex clone() throws CloneNotSupportedException {
        return new Hex(x, y, player);
    }

}

