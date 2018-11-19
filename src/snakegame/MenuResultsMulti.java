package snakegame;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuResultsMulti extends Menu
{
    MenuButton buttonRestart, buttonBack;
    int playerAmount, gameSpeed;
    
    public MenuResultsMulti(Board board, int winner, int[] score, int gameSpeed)
    {
        super(board);
        
        playerAmount = score.length;
        this.gameSpeed = gameSpeed;
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8,
                "Player " + Integer.toString(winner+1) + " wins!", Color.black, Board.fontTitle));
        
        String speedText = "";
        switch (gameSpeed)
        {
            case 5:
                speedText = "Slow";
                break;
            case 3:
                speedText = "Medium";
                break;
            case 2:
                speedText = "Fast";
                break;
            default:
                System.out.println("Invalid gameSpeed " + gameSpeed);
                System.exit(0);
                break;
        }
        
        add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 70,
                "Game speed: " + speedText, Color.black, Board.fontButton));
        
        for (int i = 0; i < playerAmount; i++)
        {
            add(new MenuLabel(this, Game.GAMEWIDTH/2, Game.GAMEHEIGHT/8 + 120 + i*50,
                "Player " + Integer.toString(i+1) + ": " + Integer.toString(score[i]), Game.colPlayerText[i], Board.fontButton));
        }
        
        buttonRestart = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 120 + playerAmount*50,
                240, 60);
        add(buttonRestart);
        buttonRestart.createLabel("Play Again", Board.fontButton);
        
        buttonBack = new MenuButton(this,
                Game.GAMEWIDTH/2 - 120, Game.GAMEHEIGHT/8 + 200 + playerAmount*50,
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
            board.startGame(playerAmount, gameSpeed);
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
            board.startGame(playerAmount, gameSpeed);
        }
        else if (k == KeyEvent.VK_ESCAPE)
        {
            board.setMenu(new MenuMain(board));
        }
    }
}