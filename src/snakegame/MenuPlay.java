package snakegame;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuPlay extends Menu
{
    MenuButton[] buttonPlayer, buttonSpeed;
    MenuButton buttonStart, buttonBack;
    
    public MenuPlay(Board board)
    {
        super(board);
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8,
                "Start Game", Color.BLACK, Board.fontTitle));
        
        buttonPlayer = new MenuButton[4];
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 70,
                "Player Amount:", Color.BLACK, Board.fontButton));
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 200,
                "Game Speed:", Color.BLACK, Board.fontButton));
        
        for (int i = 0; i < 4; i++)
        {
            buttonPlayer[i] = new MenuButton(this,
                    Game.GAMEWIDTH/2 - 150 + i*80, Game.GAMEHEIGHT/8 + 100,
                    60, 60);
            add(buttonPlayer[i]);
            buttonPlayer[i].createLabel(Integer.toString(i+1), Board.fontButton);
        }
        
        buttonSpeed = new MenuButton[3];
        
        buttonSpeed[0] = new MenuButton(this,
                Game.GAMEWIDTH/2 - 290, Game.GAMEHEIGHT/8 + 230,
                180, 60);
        add(buttonSpeed[0]);
        buttonSpeed[0].createLabel("Slow", Board.fontButton);
        
        buttonSpeed[1] = new MenuButton(this,
                Game.GAMEWIDTH/2 - 90, Game.GAMEHEIGHT/8 + 230,
                180, 60);
        add(buttonSpeed[1]);
        buttonSpeed[1].createLabel("Medium", Board.fontButton);
        
        buttonSpeed[2] = new MenuButton(this,
                Game.GAMEWIDTH/2 + 110, Game.GAMEHEIGHT/8 + 230,
                180, 60);
        add(buttonSpeed[2]);
        buttonSpeed[2].createLabel("Fast", Board.fontButton);
        
        buttonStart = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 50 + 300,
                240, 60);
        add(buttonStart);
        buttonStart.createLabel("Start Game", Board.fontButton);
        buttonStart.setActive(false);
        
        buttonBack = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 50 + 380,
                240, 60);
        add(buttonBack);
        buttonBack.createLabel("Back", Board.fontButton);
        
        if (board.lastPlayerAmount != -1)
        {
            buttonPlayer[board.lastPlayerAmount - 1].selected = true;
        }
        if (board.lastGameSpeed != -1)
        {
            int i = -1;
            switch (board.lastGameSpeed)
            {
                case 5:
                    i = 0;
                    break;
                case 3:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                default:
                    System.out.println("Invalid gameSpeed " + board.lastGameSpeed);
                    System.exit(0);
                    break;
            }
            
            if (board.lastPlayerAmount != -1)
            {
                buttonSpeed[i].selected = true;
            }
        }
        
        if (board.lastPlayerAmount != -1 && board.lastGameSpeed != -1)
        {
            buttonStart.setActive(true);
        }
    }
    
    @Override
    public void menuEvent(MenuEvent me)
    {
        MenuItem source = me.getSource();
        
        for (int i = 0; i < 4; i++)
        {
            if (source == buttonPlayer[i])
            {
                for (int j = 0; j < 4; j++)
                {
                    buttonPlayer[j].selected = false;
                }
                buttonPlayer[i].selected = true;
                
                board.lastPlayerAmount = i + 1;
                
                if (board.lastGameSpeed != -1)
                {
                    buttonStart.setActive(true);
                }
            }
            
            if (i < 3 && source == buttonSpeed[i])
            {
                for (int j = 0; j < 3; j++)
                {
                    buttonSpeed[j].selected = false;
                }
                buttonSpeed[i].selected = true;
                
                switch (i)
                {
                    case 0:
                        board.lastGameSpeed = 5;
                        break;
                    case 1:
                        board.lastGameSpeed = 3;
                        break;
                    case 2:
                        board.lastGameSpeed = 2;
                        break;
                }
                
                if (board.lastPlayerAmount != -1)
                {
                    buttonStart.setActive(true);
                }
            }
        }
        
        if (source == buttonStart && board.lastPlayerAmount != -1 && board.lastGameSpeed != -1)
        {
            board.startGame(board.lastPlayerAmount, board.lastGameSpeed);
        }
        
        if (source == buttonBack)
        {
            board.setMenu(new MenuMain(board));
        }
    }
    
    @Override
    public void keyPressed(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER && board.lastPlayerAmount != -1 && board.lastGameSpeed != -1)
        {
            board.startGame(board.lastPlayerAmount, board.lastGameSpeed);
        }
        if (k == KeyEvent.VK_ESCAPE)
        {
            board.setMenu(new MenuMain(board));
        }
    }
}