package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class MenuPause extends Menu
{
    Game game;
    MenuButton buttonResume, buttonBack, buttonExit;
    
    public MenuPause(Board board, Game game)
    {
        super(board);
        
        this.game = game;
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8,
                "Paused", Color.black, Board.fontTitle));
        
        buttonResume = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 50, 240, 60);
        add(buttonResume);
        buttonResume.createLabel("Resume", Board.fontButton);
        
        buttonBack = new MenuButton(this,
                Game.GAMEWIDTH/2 - 150, Game.GAMEHEIGHT/8 + 130, 300, 60);
        add(buttonBack);
        buttonBack.createLabel("Back to menu", Board.fontButton);
        
        buttonExit = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 210, 240, 60);
        add(buttonExit);
        buttonExit.createLabel("Exit Game", Board.fontButton);
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.setColor(new Color(0, 0, 0, .5f));
        g.fillRect(0, 0, Game.GAMEWIDTH, Game.GAMEHEIGHT);
        
        super.draw(g);
    }
    
    @Override
    public void menuEvent(MenuEvent me)
    {
        MenuItem source = me.getSource();
        
        if (source == buttonResume)
        {
            game.setPaused(false);
        }
        else if (source == buttonBack)
        {
            board.restart();
        }
        else if (source == buttonExit)
        {
            System.exit(0);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
        {
            game.setPaused(false);
        }
        else if (k == KeyEvent.VK_ESCAPE)
        {
            game.setPaused(false);
        }
    }
}