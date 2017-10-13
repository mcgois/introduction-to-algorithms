package seamcarver;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private static final double MAX_ENERGY = 1000;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.energy = new double[picture.width()][picture.height()];
        computeEnergy();
    }

    private void computeEnergy() {
        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1) {
                    energy[x][y] = MAX_ENERGY;
                } else {
                    double deltaX = gradient(picture.get(x - 1, y), picture.get(x + 1, y));
                    double deltaY = gradient(picture.get(x, y - 1), picture.get(x, y + 1));
                    energy[x][y] = Math.sqrt(deltaX + deltaY);
                }
            }
        }
    }

    private double gradient(Color color1, Color color2) {
        double red = color1.getRed() - color2.getRed();
        double green = color1.getGreen() - color2.getGreen();
        double blue = color1.getBlue() - color2.getBlue();
        return red * red + green * green + blue * blue;
    }

    public Picture picture() {
        return this.picture;
    }

    public int height() {
        return picture.height();
    }

    public int width() {
        return picture.width();
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IllegalArgumentException();
        }
        return energy[x][y];
    }

    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        return null;
    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {

    }

}
