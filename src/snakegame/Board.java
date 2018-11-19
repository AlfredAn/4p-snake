package snakegame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Board extends Applet implements Runnable, KeyListener,
        MouseListener, MouseMotionListener
{
    public static final Font fontTitle = new Font("Verdana", Font.BOLD, 48),
            fontButton = new Font("Verdana", Font.PLAIN, 36),
            fontScore = new Font("Verdana", Font.PLAIN, 30),
            fontInfoText = new Font("Verdana", Font.PLAIN, 24);
    
    private boolean active = false;
    
    private Game game;
    private Menu menu;
    
    private boolean mouseLeft, mouseRight;
    private int mouseX, mouseY;
    
    public int lastPlayerAmount = -1, lastGameSpeed = -1;
    
    long time;
    
    @Override
    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void init()
    {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        requestFocus();
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            mouseLeft = false;
            mouseRight = false;
            mouseX = -1;
            mouseY = -1;
            
            menu = new MenuMain(this);
            
            active = true;
            
            time = System.nanoTime();
            long frameTime = (long)(1000000000/Game.FRAMERATE);
            
            while (true)
            {
                if (game != null)
                {
                    game.update();
                }
                
                if (menu != null)
                {
                    menu.update();
                }
                
                repaint();
                
                long delta = 0;
                
                while (delta < frameTime)
                {
                    long newTime = System.nanoTime();
                    delta = newTime - time;
                    
                    if (frameTime - delta > 1100000)
                    {
                        sleepNoAdjust((frameTime - delta - 100000)/1000000);
                    }
                }
                time += frameTime;
            }
        }
    }
    
    protected void startGame(int playerAmount, int gameSpeed)
    {
        menu = null;
        game = new Game(this, playerAmount, gameSpeed);
    }
    
    protected void restart()
    {
        restart(new MenuMain(this));
    }
    protected void restart(Menu menu)
    {
        game = null;
        this.menu = menu;
    }
    
    protected void setMenu(Menu menu)
    {
        this.menu = menu;
    }
    
    private void sleepNoAdjust(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {}
    }
    public void sleep(long time)
    {
        sleepNoAdjust(time);
        this.time += time * 1000000;
    }
    
    @Override
    public void update(Graphics g)
    {
        paint(g);
    }
    
    @Override
    public void paint(Graphics g)
    {
        if (active)
        {
            Image img = createImage(Game.GAMEWIDTH, Game.GAMEHEIGHT);
            Graphics g2 = img.getGraphics();
            
            g2.setColor(Color.lightGray);
            g2.fillRect(0, 0, Game.GAMEWIDTH, Game.GAMEHEIGHT);
            
            if (game != null)
            {
                game.draw(g2);
            }
            
            if (menu != null)
            {
                menu.draw(g2);
            }

            g.drawImage(img, 0, 0, null);
        }
    }
    
    public boolean getMouseLeft()
    {
        return mouseLeft;
    }
    
    public boolean getMouseRight()
    {
        return mouseRight;
    }
    
    public int getMouseX()
    {
        return mouseX;
    }
    
    public int getMouseY()
    {
        return mouseY;
    }
    
    @Override
    public void keyPressed(KeyEvent ke)
    {
        if (menu != null)
        {
            menu.keyPressed(ke);
        }
        if (game != null)
        {
            game.keyPressed(ke);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke)
    {
        if (menu != null)
        {
            menu.keyReleased(ke);
        }
        if (game != null)
        {
            game.keyReleased(ke);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == 1)
        {
            mouseLeft = true;
        }
        else if (e.getButton() == 2)
        {
            mouseRight = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == 1)
        {
            mouseLeft = false;
        }
        else if (e.getButton() == 2)
        {
            mouseRight = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}