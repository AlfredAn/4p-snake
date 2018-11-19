package snakegame;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuResults extends Menu
{
    MenuButton buttonRestart, buttonBack;
    int gameSpeed;
    
    public MenuResults(Board board, int score, int gameSpeed)
    {
        super(board);
        
        this.gameSpeed = gameSpeed;
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8,
                "Game Over", Color.black, Board.fontTitle));
        
        int i = -1;
        String speedText = "";
        switch (gameSpeed)
        {
            case 5:
                i = 0;
                speedText = "Slow";
                break;
            case 3:
                i = 1;
                speedText = "Medium";
                break;
            case 2:
                i = 2;
                speedText = "Fast";
                break;
            default:
                System.out.println("Invalid gameSpeed " + gameSpeed);
                System.exit(0);
                break;
        }
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 80,
                "Game speed: " + speedText, Color.black, Board.fontButton));
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 130,
                "Final score: " + Integer.toString(score), Color.black, Board.fontButton));
        
        String s;
        if (score > SaveManager.maxScore[i])
        {
            s = "New record!";
            SaveManager.maxScore[i] = score;
            SaveManager.save();
        }
        else
        {
            s = "Your record on this speed: " + SaveManager.maxScore[i];
        }
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 180,
                s, Color.black, Board.fontButton));
        
        buttonRestart = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 230,
                240, 60);
        add(buttonRestart);
        buttonRestart.createLabel("Play Again", Board.fontButton);
        
        buttonBack = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 310,
                240, 60);
        add(buttonBack);
        buttonBack.createLabel("Back", Board.fontButton);
    }
    
    @Override
    public void menuEvent(MenuEvent me)
    {
        MenuItem source = me.getSource();
        
        if (source == buttonRestart)
        {
            board.startGame(1, gameSpeed);
        }
        else if (source == buttonBack)
        {
            board.setMenu(new MenuMain(board));
        }
    }
    
    @Override
    public void keyPressed(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
        {
            board.startGame(1, gameSpeed);
        }
        else if (k == KeyEvent.VK_ESCAPE)
        {
            board.setMenu(new MenuMain(board));
        }
    }
}