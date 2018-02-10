package assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageClass {
    int hieght;
    int width;
    int[][] imagePixels;
    String outPath;
    //ImageNewName imgNewName = new ImageNewName();
//    public static void main(String[] args) {
//
//        int[][] pixels = ImageClass.readImage("E:\\FCI\\Third year\\First term\\Multimedia\\readWriteImage\\ReadWriteImageClass\\cameraMan.jpg");
//
//        ImageClass.writeImage(pixels, "E:\\FCI\\Third year\\First term\\Multimedia\\readWriteImage\\ReadWriteImageClass\\cameraMan_out.jpg");
//
//    }

    public int[][] readImage(String path) {
       // imgNewName.setPath(path);
        outPath = handleCompName(path);

       // imgNewName.setOutPath(outPath);
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
            hieght = img.getHeight();
            width = img.getWidth();
            imagePixels = new int[hieght][width];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < hieght; y++) {
                    int pixel = img.getRGB(x, y);
                    int red = (pixel & 0x00ff0000) >> 16;
                    int grean = (pixel & 0x0000ff00) >> 8;
                    int blue = pixel & 0x000000ff;
                    int alpha = (pixel & 0xff000000) >> 24;
                    imagePixels[y][x] = red;
                }
            }
            return imagePixels;
        } catch (IOException e) {
            return null;
        }


    }

    private String handleCompName(String path) {
        int exeIndex = path.lastIndexOf(".");
        String exe = path.substring(exeIndex, path.length());
        path = path.replace(exe, "Comp");
        path += exe;
        return path;
    }

    public void writeImage(int[][] imagePixels) {
        BufferedImage image = new BufferedImage(imagePixels[0].length, imagePixels.length, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imagePixels.length; y++) {
            for (int x = 0; x < imagePixels[y].length; x++) {
                int value = -1 << 24;
                value = 0xff000000 | (imagePixels[y][x] << 16) | (imagePixels[y][x] << 8) | (imagePixels[y][x]);
                image.setRGB(x, y, value);
            }
        }
        File ImageFile = new File("E:\\FCI\\Third year\\First term\\Multimedia\\readWriteImage\\ReadWriteImageClass\\cameraMan15.jpg");
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHieght() {
        return hieght;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHieght(int hieght) {
        this.hieght = hieght;
    }

}
