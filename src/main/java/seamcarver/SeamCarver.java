package seamcarver;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private static final double MAX_ENERGY = 1000;
    private double[][] distTo;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.energy = new double[picture.width()][picture.height()];
        this.distTo = new double[picture.width()][picture.height()];
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
        int[][] steps = new int[picture.width()][picture.height()];
        double[][] sumEnergy = new double[picture.width()][picture.height()];
        for (int y = 0; y < picture.height(); y++) {
            this.dfsX(0, y, steps, sumEnergy);
        }

        int[] result = new int[picture.width()];
        double bestEnergy = Double.MAX_VALUE;
        for (int y = 0; y < picture.height(); y++) {
            if (sumEnergy[0][y] < bestEnergy) {
                bestEnergy = sumEnergy[0][y];
                result[0] = y;
            }
        }

        for (int x = 1; x < picture.width() - 1; x++) {
            result[x] = steps[x-1][result[x - 1]];
        }

        return result;
    }

    public int[] findVerticalSeam() {
        int[][] steps = new int[picture.width()][picture.height()];
        double[][] sumEnergy = new double[picture.width()][picture.height()];
        for (int x = 0; x < picture.width(); x++) {
            this.dfsY(x, 0, steps, sumEnergy);
        }

        int[] result = new int[picture.height()];
        double bestEnergy = Double.MAX_VALUE;
        for (int x = 0; x < picture.width(); x++) {
            if (sumEnergy[x][0] < bestEnergy) {
                bestEnergy = sumEnergy[x][0];
                result[0] = x;
            }
        }

        for (int y = 1; y < picture.height(); y++) {
            result[y] = steps[result[y - 1]][y - 1];
        }

        return result;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != picture.width()) {
            throw new IllegalArgumentException();
        }

        Picture copy = new Picture(picture.width(), picture.height() - 1);
        for (int x = 0; x < picture.width(); x++) {
            int k = 0;
            for (int y = 0; y < picture.height(); y++) {
                if (y != seam[x]) {
                    copy.set(x, k, picture.get(x, y));
                    k++;
                }
            }
        }

        this.picture = copy;
        this.computeEnergy();
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != picture.height()) {
            throw new IllegalArgumentException();
        }

        Picture copy = new Picture(picture.width() - 1, picture.height());
        for (int y = 0; y < picture.height(); y++) {
            int k = 0;
            for (int x = 0; x < picture.width(); x++) {
                if (x != seam[y]) {
                    copy.set(k, y, picture.get(x, y));
                    k++;
                }
            }
        }

        this.picture = copy;
        this.computeEnergy();
    }

    private void dfsY(int x, int y, int[][] steps, double[][] sumEnergy) {

        if (y == picture.height() - 1) {
            sumEnergy[x][y] = energy[x][y];
            steps[x][y] = -1;
            return;
        }

        double minPath = Double.MAX_VALUE;
        int bestMove = 0;

        for (int mv = -1; mv <= 1; mv++) {
            int py = y + 1;
            int px = x + mv;

            if (px >= picture.width() || px < 0) {
                continue;
            }


            if (steps[px][py] == 0) {
                // nao visitado
                dfsY(px, py, steps, sumEnergy);
            }

            if (sumEnergy[px][py] < minPath) {
                minPath = sumEnergy[px][py];
                bestMove = px;
            }
        }

        steps[x][y] = bestMove;
        sumEnergy[x][y] = energy[x][y] + minPath;
    }

    private void dfsX(int x, int y, int[][] steps, double[][] sumEnergy) {

        if (x == picture.width() - 1) {
            sumEnergy[x][y] = energy[x][y];
            steps[x][y] = -1;
            return;
        }

        double minPath = Double.MAX_VALUE;
        int bestMove = 0;

        for (int mv = -1; mv <= 1; mv++) {
            int py = y + mv;
            int px = x + 1;

            if (py >= picture.height() || py < 0) {
                continue;
            }


            if (steps[px][py] == 0) {
                // nao visitado
                dfsX(px, py, steps, sumEnergy);
            }

            if (sumEnergy[px][py] < minPath) {
                minPath = sumEnergy[px][py];
                bestMove = py;
            }
        }

        steps[x][y] = bestMove;
        sumEnergy[x][y] = energy[x][y] + minPath;
    }

}
