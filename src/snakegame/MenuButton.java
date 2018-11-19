package snakegame;

import java.awt.Color;
import java.awt.Graphics;

public class MenuButton extends MenuItem
{
    Color color, colorHover, colorPress;
    
    public static final int ST_NORMAL = 0, ST_HOVER = 1, ST_PRESS = 2, ST_DRAG = 3, ST_DRAGOTHER = 4;
    
    private boolean active = true;
    boolean selected = false;
    int state = ST_NORMAL;
    
    public MenuButton(Menu menu, int x, int y, int width, int height)
    {
        this(menu, x, y, width, height, Color.getHSBColor(0, 0, .5f),
                Color.getHSBColor(0, 0, .625f), Color.getHSBColor(0, 0, .4375f));
    }
    public MenuButton(Menu menu, int x, int y, int width, int height,
            Color color, Color colorHover, Color colorPress)
    {
        super(menu, x, y, width, height);
        
        this.color = color;
        this.colorHover = colorHover;
        this.colorPress = colorPress;
    }
    
    @Override
    public void update()
    {
        boolean hover = Util.isInBox(board.getMouseX(), board.getMouseY(), x, y, width, height);
        
        if (active)
        {
            switch (state)
            {
                case ST_NORMAL:
                    if (hover)
                    {
                        state = ST_HOVER;
                    }
                    else if (board.getMouseLeft())
                    {
                        state = ST_DRAGOTHER;
                    }
                    break;
                case ST_HOVER:
                    if (!hover)
                    {
                        state = ST_NORMAL;
                    }
                    if (board.getMouseLeft())
                    {
                        state = ST_PRESS;
                    }
                    break;
                case ST_PRESS:
                    if (board.getMouseLeft())
                    {
                        if (!hover)
                        {
                            state = ST_DRAG;
                        }
                    }
                    else
                    {
                        state = ST_NORMAL;
                        if (hover)
                        {
                            menu.menuEvent(new MenuEvent(this, MenuEvent.ME_BUTTONPRESS));
                        }
                    }
                    break;
                case ST_DRAG:
                    if (board.getMouseLeft())
                    {
                        if (hover)
                        {
                            state = ST_PRESS;
                        }
                    }
                    else
                    {
                        state = ST_NORMAL;
                    }
                    break;
                case ST_DRAGOTHER:
                    if (!board.getMouseLeft())
                    {
                        if (hover)
                        {
                            state = ST_HOVER;
                        }
                        else
                        {
                            state = ST_NORMAL;
                        }
                    }
                    break;
            }
        }
        else
        {
            state = ST_NORMAL;
        }
    }
    
    @Override
    public void draw(Graphics g)
    {
        switch (state)
        {
            case ST_HOVER:
                g.setColor(colorHover);
                break;
            case ST_PRESS:
                g.setColor(colorPress);
                break;
            default:
                g.setColor(color);
                break;
        }
        
        g.fillRect(x, y, width, height);
        
        if (selected)
        {
            g.setColor(new Color(.875f, .875f, 1f, .5f));
            g.fillRect(x, y, width, height);
        }
        
        label.draw(g);
        
        if (!active)
        {
            g.setColor(new Color(.75f, .75f, .75f, .5f));
            g.fillRect(x, y, width, height);
        }
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
    
    public int getState()
    {
        return state;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
}