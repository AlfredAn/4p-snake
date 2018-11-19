package snakegame;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuHelp extends Menu
{
    MenuButton buttonBack;
    
    public MenuHelp(Board board)
    {
        super(board);
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8,
                "Help", Color.BLACK, Board.fontTitle));
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 70 + 40*0,
                "You control a snake.", Color.BLACK, Board.fontInfoText));
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 70 + 40*1,
                "Try to pick up as many apples as possible.", Color.BLACK, Board.fontInfoText));
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 70 + 40*2,
                "Don't run into the walls or yourself!", Color.BLACK, Board.fontInfoText));
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 210,
                "Controls:", Color.BLACK, Board.fontButton));
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 260 + 40*0,
                "Player 1: Arrow Keys", Game.colPlayerText[0], Board.fontInfoText));
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 260 + 40*1,
                "Player 2: W, A, S and D", Game.colPlayerText[1], Board.fontInfoText));
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 260 + 40*2,
                "Player 3: Numpad 8, 4, 5 and 6", Game.colPlayerText[2], Board.fontInfoText));
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 260 + 40*3,
                "Player 4: I, J, K and L", Game.colPlayerText[3], Board.fontInfoText));
        
        buttonBack = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 430, 240, 60);
        add(buttonBack);
        buttonBack.createLabel("Back", Board.fontButton);
    }
    
    @Override
    public void menuEvent(MenuEvent me)
    {
        MenuItem source = me.getSource();
        
        if (source == buttonBack)
        {
            board.setMenu(new MenuMain(board));
        }
    }
    
    @Override
    public void keyPressed(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_ESCAPE)
        {
            board.setMenu(new MenuMain(board));
        }
    }
}