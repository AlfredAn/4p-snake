package snakegame;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class Util
{
    public static boolean isInBox(int x, int y, int x0, int y0, int width, int height)
    {
        return (x >= x0 && x <= x0 + width
             && y >= y0 && y <= y0 + height);
    }
    
    public static void drawStringAlign(Graphics g, String str, int x, int y, int xAlign, int yAlign)
    {
        FontMetrics fm = g.getFontMetrics();
        int x0 = x, y0 = y;
        
        switch (xAlign)
        {
            case MenuLabel.A_LEFT:
                x0 = x;
                break;
            case MenuLabel.A_CENTER:
                x0 = x - fm.stringWidth(str) / 2;
                break;
            case MenuLabel.A_RIGHT:
                x0 = x - fm.stringWidth(str);
                break;
        }
        
        switch (yAlign)
        {
            case MenuLabel.A_TOP:
                y0 = y + fm.getAscent();
                break;
            case MenuLabel.A_MIDDLE:
                y0 = y + (fm.getAscent() - fm.getDescent()) / 2;
                break;
            case MenuLabel.A_BOTTOM:
                y0 = y - fm.getDescent();
                break;
        }
        
        g.drawString(str, x0, y0);
    }
}