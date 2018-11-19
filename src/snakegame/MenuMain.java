package snakegame;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuMain extends Menu
{
    MenuButton buttonStart, buttonHelp, buttonExit;
    
    public MenuMain(Board board)
    {
        super(board);
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8,
                "Snake", Color.black, Board.fontTitle));
        
        buttonStart = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 50, 240, 60);
        add(buttonStart);
        buttonStart.createLabel("Start Game", Board.fontButton);
        
        buttonHelp = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 130, 240, 60);
        add(buttonHelp);
        buttonHelp.createLabel("Help", Board.fontButton);
        
        buttonExit = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 210, 240, 60);
        add(buttonExit);
        buttonExit.createLabel("Exit", Board.fontButton);
    }
    
    @Override
    public void menuEvent(MenuEvent me)
    {
        MenuItem source = me.getSource();
        
        if (source == buttonStart)
        {
            board.setMenu(new MenuPlay(board));
        }
        else if (source == buttonHelp)
        {
            board.setMenu(new MenuHelp(board));
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
            board.setMenu(new MenuPlay(board));
        }
        else if (k == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
}