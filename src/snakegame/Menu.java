package snakegame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Menu
{
    private ArrayList<MenuItem> item;
    Board board;
    
    public Menu(Board board)
    {
        item = new ArrayList<>();
        this.board = board;
    }
    
    public void add(MenuItem mi)
    {
        item.add(mi);
    }
    
    public void update()
    {
        for (int i = 0; i < item.size(); i++)
        {
            item.get(i).update();
        }
    }
    
    public void draw(Graphics g)
    {
        for (int i = 0; i < item.size(); i++)
        {
            item.get(i).draw(g);
        }
    }
    
    public abstract void menuEvent(MenuEvent me);
    
    public void keyPressed(KeyEvent ke) {}
    public void keyReleased(KeyEvent ke) {}
    
    public Board getBoard()
    {
        return board;
    }
}
