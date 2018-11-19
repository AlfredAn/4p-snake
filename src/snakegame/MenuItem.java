package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class MenuItem
{
    int x, y, width, height;
    Menu menu;
    Board board;
    MenuLabel label;
    
    public MenuItem(Menu menu, int x, int y, int width, int height)
    {
        this.menu = menu;
        board = menu.getBoard();
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update() {}
    
    public void draw(Graphics g)
    {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        
        label.draw(g);
    }
    
    public MenuLabel createLabel(String text, Font font)
    {
        return createLabel(text, font, Color.BLACK);
    }
    public MenuLabel createLabel(String text, Font font, Color color)
    {
        label = new MenuLabel(menu, x + width/2, y + height/2, text, color, font,
                MenuLabel.A_CENTER, MenuLabel.A_MIDDLE);
        return label;
    }
}