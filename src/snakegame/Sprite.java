package snakegame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Sprite
{
    private BufferedImage image;
    
    public Sprite(String path)
    {
        URL url = getClass().getResource(path);
        
        try
        {
            image = ImageIO.read(url);
        }
        catch (IOException e)
        {
            System.out.println("File not found: " + path);
            System.exit(-1);
        }
    }
    
    public void draw(Graphics g, double x, double y)
    {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform tx = AffineTransform.getTranslateInstance(x, y);
        
        g2d.drawImage(image, tx, null);
    }
    
    public void drawRotated(Graphics g, double x, double y, double angle)
    {
        drawRotated(g, x, y, angle, (double)image.getWidth()/2, (double)image.getHeight()/2);
    }
    public void drawRotated(Graphics g, double x, double y, double angle, double xOrig, double yOrig)
    {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(angle),
                                x + xOrig, y + yOrig);
        tx.translate(x, y);
        
        g2d.drawImage(image, tx, null);
    }
    
    public Image getImage()
    {
        return image;
    }
}