package top.zoyn.particlelib.pobject;

import org.bukkit.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Image extends ParticleObject {

    private final File imageFile;
    private final String imageUrl;
    private final double step;
    private final double scale;

    private boolean bypassGrayCheck = false;

    private BufferedImage bufferedImage;

    public Image(Location origin, File imageFile, int step, double scale) {
        setOrigin(origin);
        this.imageFile = imageFile;
        this.imageUrl = imageFile.getAbsolutePath();
        try {
            this.bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.step = step;
        this.scale = scale;
    }

    public boolean isBypassGrayCheck() {
        return bypassGrayCheck;
    }

    public Image setBypassGrayCheck(boolean bypassGrayCheck) {
        this.bypassGrayCheck = bypassGrayCheck;
        return this;
    }

    @Override
    public List<Location> calculateLocations() {
        return null;
    }

    @Override
    public void show() {
        double width = bufferedImage.getWidth();
        double height = bufferedImage.getHeight();
        // 高
        for (int j = 0; j < height; j += step) {
            // 宽
            for (int i = 0; i < width; i += step) {
                Color color = new Color(bufferedImage.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                if (bypassGrayCheck) {
                    double xi = (i - width / 2) / scale;
                    double yi = (j - height / 2) / scale;

                    Location spawnLocation = getOrigin().clone().add(xi, 0, yi);
                    spawnColorParticle(spawnLocation, r, g, b);
                } else {
                    // 灰度值判断
                    if (isBlack(r, g, b)) {
                        double xi = (i - width / 2) / 16;
                        double yi = (j - height / 2) / 16;

                        Location spawnLocation = getOrigin().clone().add(xi, 0, yi);
                        spawnColorParticle(spawnLocation, r, g, b);
                    }
                }
            }
        }
    }

    private boolean isBlack(int r, int g, int b) {
        double grayScale = 0.299 * r + 0.587 * g + 0.114 * b;
        return grayScale < 132;
    }
}
