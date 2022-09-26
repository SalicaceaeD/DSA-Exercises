import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    /**
     * create a seam carver object based on the given picture.
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throwIllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException();
    }

    /**
     * current picture.
     */
    public Picture picture() {
        return new Picture(picture);
    }

    /**
     * width of current picture.
     */
    public int width() {
        return picture.width();
    }

    /**
     * height of current picture.
     */
    public int height() {
        return picture.height();
    }

    private double sqr(int x) {
        return (double) x * x;
    }

    private boolean isInvalidColumn(int x) {
        return x < 0 || x >= width();
    }

    private boolean isInvalidRow(int y) {
        return y < 0 || y >= height();
    }

    /**
     * energy of pixel at column x and row y.
     */
    public double energy(int x, int y) {
        if (isInvalidColumn(x) || isInvalidRow(y)) {
            throwIllegalArgumentException();
        }

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }

        double energy = 0;

        Color upColor = picture.get(x - 1, y);
        Color downColor = picture.get(x + 1, y);
        int red = upColor.getRed() - downColor.getRed();
        int green = upColor.getGreen() - downColor.getGreen();
        int blue = upColor.getBlue() - downColor.getBlue();
        energy += sqr(red) + sqr(green) + sqr(blue);

        Color leftColor = picture.get(x, y - 1);
        Color rightColor = picture.get(x, y + 1);
        red = leftColor.getRed() - rightColor.getRed();
        green = leftColor.getGreen() - rightColor.getGreen();
        blue = leftColor.getBlue() - rightColor.getBlue();
        energy += sqr(red) + sqr(green) + sqr(blue);

        return Math.sqrt(energy);
    }

    private double[][] calculateEnergy() {
        int width = width();
        int height = height();
        double[][] energy = new double[width][height];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                energy[x][y] = energy(x, y);
            }
        }
        return energy;
    }

    /**
     * sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        int width = width();
        int height = height();
        double[][] energy = calculateEnergy();
        double[][] dp = new double[width][height];
        int[][] trace = new int[width][height];
        int[] to = {-1, 0, 1};
        for (int y = 0; y < height; ++y) {
            dp[0][y] = energy[0][y];
        }
        for (int x = 1; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                dp[x][y] = Double.POSITIVE_INFINITY;
            }
            for (int y = 0; y < height; ++y) {
                for (int i = 0; i < to.length; ++i) {
                    int ny = y + to[i];
                    if (isInvalidRow(ny)) {
                        continue;
                    }
                    if (dp[x][y] > dp[x - 1][ny] + energy[x][y]) {
                        dp[x][y] = dp[x - 1][ny] + energy[x][y];
                        trace[x][y] = ny;
                    }
                }
            }
        }
        int current = 0;
        double minEnergy = Double.POSITIVE_INFINITY;
        for (int y = 0; y < height; ++y) {
            if (minEnergy > dp[width - 1][y]) {
                minEnergy = dp[width - 1][y];
                current = y;
            }
        }
        int[] horizontalSeam = new int[width];
        for (int x = width - 1; x >= 0; --x) {
            horizontalSeam[x] = current;
            current = trace[x][current];
        }
        return horizontalSeam;
    }

    /**
     * sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        int width = width();
        int height = height();
        double[][] energy = calculateEnergy();
        double[][] dp = new double[height][width];
        int[][] trace = new int[height][width];
        int[] to = {-1, 0, 1};
        for (int x = 0; x < width; ++x) {
            dp[0][x] = energy[x][0];
        }
        for (int y = 1; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                dp[y][x] = Double.POSITIVE_INFINITY;
            }
            for (int x = 0; x < width; ++x) {
                for (int i = 0; i < to.length; ++i) {
                    int nx = x + to[i];
                    if (isInvalidColumn(nx)) {
                        continue;
                    }
                    if (dp[y][x] > dp[y-1][nx] + energy[x][y]) {
                        dp[y][x] = dp[y-1][nx] + energy[x][y];
                        trace[y][x] = nx;
                    }
                }
            }
        }
        int current = 0;
        double minEnergy = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width; ++x) {
            if (minEnergy > dp[height - 1][x]) {
                minEnergy = dp[height - 1][x];
                current = x;
            }
        }
        int[] verticalSeam = new int[height];
        for (int y = height - 1; y >= 0; --y) {
            verticalSeam[y] = current;
            current = trace[y][current];
        }
        return verticalSeam;
    }

    /**
     * remove horizontal seam from current picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        int width = width();
        int height = height();
        if (seam == null || seam.length != width || height <= 1) {
            throwIllegalArgumentException();
        }
        for (int x = 0; x < width; ++x) {
            if (isInvalidRow(seam[x])) {
                throwIllegalArgumentException();
            }
            if (x > 0 && Math.abs(seam[x] - seam[x - 1]) > 1) {
                throwIllegalArgumentException();
            }
        }

        Picture newPicture = new Picture(width, height - 1);
        for (int x = 0; x < width; ++x) {
            int ny = 0;
            for (int y = 0; y < height; ++y) {
                if (y != seam[x]) {
                    newPicture.set(x, ny, picture.get(x, y));
                    ++ny;
                }
            }
        }
        picture = newPicture;
    }

    /**
     * remove vertical seam from current picture.
     */
    public void removeVerticalSeam(int[] seam) {
        int width = width();
        int height = height();
        if (seam == null || seam.length != height || width <= 1) {
            throwIllegalArgumentException();
        }
        for (int y = 0; y < height; ++y) {
            if (isInvalidColumn(seam[y])) {
                throwIllegalArgumentException();
            }
            if (y > 0 && Math.abs(seam[y] - seam[y - 1]) > 1) {
                throwIllegalArgumentException();
            }
        }

        Picture newPicture = new Picture(width - 1, height);
        for (int y = 0; y < height; ++y) {
            int nx = 0;
            for (int x = 0; x < width; ++x) {
                if (x != seam[y]) {
                    newPicture.set(nx, y, picture.get(x, y));
                    ++nx;
                }
            }
        }
        picture = newPicture;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        // empty
    }

}