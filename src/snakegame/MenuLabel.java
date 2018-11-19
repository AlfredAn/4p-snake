package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuLabel extends MenuItem
{
    public static final int A_LEFT = 0, A_CENTER = 1, A_RIGHT = 2,
            A_TOP = 0, A_MIDDLE = 1, A_BOTTOM = 2;
    
    String text;
    int xAlign, yAlign;
    Font font;
    Color color;
    
    public MenuLabel(Menu menu, int x, int y, String text)
    {
        this(menu, x, y, text, Color.BLACK);
    }
    public MenuLabel(Menu menu, int x, int y, String text, Color color)
    {
        this(menu, x, y, text, color, new Font("Verdana", Font.PLAIN, 12));
    }
    public MenuLabel(Menu menu, int x, int y, String text, Color color, Font font)
    {
        this(menu, x, y, text, color, font, A_CENTER, A_MIDDLE);
    }
    public MenuLabel(Menu menu, int x, int y, String text, Color color, Font font,
            int xAlign, int yAlign)
    {
        super(menu, x, y, 10, 10);
        
        this.text = text;
        this.color = color;
        this.font = font;
        this.xAlign = xAlign;
        this.yAlign = yAlign;
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.setFont(font);
        g.setColor(color);
        Util.drawStringAlign(g, text, x, y, xAlign, yAlign);
    }
}
