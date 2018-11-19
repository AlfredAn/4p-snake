package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Game
{
    public static final int GAMEWIDTH = 768, GAMEHEIGHT = 768,
            GRIDWIDTH = 48, GRIDHEIGHT = 48, CELLWIDTH = 16, CELLHEIGHT = 16,
            DIR_RIGHT = 0, DIR_UP = 1, DIR_LEFT = 2, DIR_DOWN = 3,
            STARTLENGTH = 9, LENGTHINCREASE = 8;
    
    public static final double FRAMERATE = 60;
    
    public static final byte GRID_EMPTY = -1, GRID_FOOD = -2;
    
    public static Color[] colPlayer, colPlayerText;
    
    public final int playerAmount;
    public final int gameSpeed;
    
    private byte[][] gridId;
    private short[][] gridTime;
    
    private Snake[] snake;
    
    private Menu menu;
    
    private int[] deathOrder;
    
    private int frame = 0;
    private boolean paused = false, justPaused = false;
    
    Board board;
    
    public Game(Board board, int playerAmount, int gameSpeed)
    {
        this.board = board;
        this.playerAmount = playerAmount;
        this.gameSpeed = gameSpeed;
        
        initLevel();
        initPlayers();
    }
    
    protected void update()
    {
        if (frame % gameSpeed == 0 && !paused)
        {
            updateSnakes();
            updateGrid();
            updateGameState();
        }
        
        if (menu != null)
        {
            menu.update();
        }
        
        justPaused = false;
        
        frame++;
    }
    
    public void draw(Graphics g)
    {
        drawGrid(g);
        
        drawGui(g);
        
        if (menu != null)
        {
            menu.draw(g);
        }
    }
    
    private void updateSnakes()
    {
        for (int i = 0; i < playerAmount; i++)
        {
            snake[i].update();
        }
    }
    
    protected void snakeDead(int id)
    {
        int j = -1;
        
        for (int i = 0; i < playerAmount; i++)
        {
            if (!snake[i].isAlive())
            {
                j++;
            }
        }
        
        deathOrder[j] = id;
    }
    
    private void updateGrid()
    {
        int foodAmount = 0;
        
        for (int y = 0; y < GRIDHEIGHT; y++)
        {
            for (int x = 0; x < GRIDWIDTH; x++)
            {
                if (gridId[x][y] >= 0 && snake[gridId[x][y]].isAlive())
                {
                    gridTime[x][y]--;
                    if (gridTime[x][y] == 0)
                    {
                        gridId[x][y] = GRID_EMPTY;
                    }
                }
                else if (gridId[x][y] == GRID_FOOD)
                {
                    foodAmount++;
                }
            }
        }
        
        if (playerAmount == 1)
        {
            if (foodAmount == 0)
            {
                placeFood();
            }
        }
        else
        {
            while (foodAmount < 2)
            {
                placeFood();
                foodAmount++;
            }
        }
    }
    
    private void updateGameState()
    {
        int snakesAlive = 0, maxScore = -1;
        
        for (int i = 0; i < playerAmount; i++)
        {
            if (snake[i].isAlive())
            {
                snakesAlive++;
            }
            if (snake[i].getScore() > maxScore)
            {
                maxScore = snake[i].getScore();
            }
        }
        
        if (snakesAlive == 0 || (playerAmount > 1 && snakesAlive == 1))
        {
            if (playerAmount == 1)
            {
                SoundLoader.gameOver.play();
                board.sleep(500);
                board.restart(new MenuResults(board, snake[0].getScore(), gameSpeed));
            }
            else
            {
                int winner = -1;
                for (int i = 0; i < playerAmount; i++)
                {
                    if (deathOrder[i] != -1 && snake[deathOrder[i]].getScore() == maxScore)
                    {
                        winner = deathOrder[i];
                    }
                }
                for (int i = 0; i < playerAmount; i++)
                {
                    if (snake[i].isAlive() && snake[i].getScore() == maxScore)
                    {
                        winner = i;
                    }
                }
                
                if (winner != -1 && (snake[winner].isAlive() || snakesAlive == 0))
                {
                    int[] score = new int[playerAmount];
                    for (int i = 0; i < playerAmount; i++)
                    {
                        score[i] = snake[i].getScore();
                    }
                    
                    SoundLoader.gameOver.play();
                    board.sleep(500);
                    board.restart(new MenuResultsMulti(board, winner, score, gameSpeed));
                }
            }
        }
    }
    
    private void drawGrid(Graphics g)
    {
        for (int y = 0; y < GRIDHEIGHT; y++)
        {
            for (int x = 0; x < GRIDWIDTH; x++)
            {
                byte id = gridId[x][y];
                
                if (id == GRID_FOOD)
                {
                    GraphicsLoader.apple.draw(g, x * CELLWIDTH, y * CELLHEIGHT);
                }
                else if (id >= 0)
                {
                    snake[id].drawSegment(g, x, y);
                }
                else if (id != GRID_EMPTY)
                {
                    System.out.println("Invalid cell id " + id
                            + " at pos (" + x + ", " + y + ")");
                    System.exit(0);
                }
            }
        }
    }
    
    private void drawGui(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(Board.fontScore);
        
        if (playerAmount >= 1)
        {
            g.setColor(colPlayerText[0]);
                Util.drawStringAlign(g, Integer.toString(snake[0].getScore()),
                        Game.GAMEWIDTH/16, Game.GAMEHEIGHT/16,
                        MenuLabel.A_CENTER, MenuLabel.A_MIDDLE);
        }
        if (playerAmount >= 2)
        {
            g.setColor(colPlayerText[1]);
                Util.drawStringAlign(g, Integer.toString(snake[1].getScore()),
                        15*Game.GAMEWIDTH/16, Game.GAMEHEIGHT/16,
                        MenuLabel.A_CENTER, MenuLabel.A_MIDDLE);
        }
        if (playerAmount >= 3)
        {
            g.setColor(colPlayerText[2]);
                Util.drawStringAlign(g, Integer.toString(snake[2].getScore()),
                        Game.GAMEWIDTH/16, 15*Game.GAMEHEIGHT/16,
                        MenuLabel.A_CENTER, MenuLabel.A_MIDDLE);
        }
        if (playerAmount >= 4)
        {
            g.setColor(colPlayerText[3]);
                Util.drawStringAlign(g, Integer.toString(snake[3].getScore()),
                        15*Game.GAMEWIDTH/16, 15*Game.GAMEHEIGHT/16,
                        MenuLabel.A_CENTER, MenuLabel.A_MIDDLE);
        }
    }
    
    private void placeFood()
    {
        while (true)
        {
            int x = (int)(Math.random() * GRIDWIDTH);
            int y = (int)(Math.random() * GRIDHEIGHT);
            
            if (gridId[x][y] < 0)
            {
                gridId[x][y] = GRID_FOOD;
                break;
            }
        }
    }
    
    public static void initColors()
    {
        colPlayer = new Color[4];
        colPlayer[0] = Color.getHSBColor(2f/3, .75f, 1f);
        colPlayer[1] = Color.getHSBColor(0, .75f, 1f);
        colPlayer[2] = Color.getHSBColor(1f/3, .75f, 1f);
        colPlayer[3] = Color.getHSBColor(1f/6, .75f, 1f);
        
        colPlayerText = new Color[4];
        colPlayerText[0] = Color.getHSBColor(2f/3, 1f, 1f);
        colPlayerText[1] = Color.getHSBColor(0, 1f, 1f);
        colPlayerText[2] = Color.getHSBColor(1f/3, 1f, 1f);
        colPlayerText[3] = Color.getHSBColor(1f/6, 1f, 1f);
    }
    
    private void initLevel()
    {
        gridId = new byte[GRIDWIDTH][GRIDHEIGHT];
        gridTime = new short[GRIDWIDTH][GRIDHEIGHT];
        
        for (int y = 0; y < GRIDHEIGHT; y++)
        {
            for (int x = 0; x < GRIDWIDTH; x++)
            {
                gridId[x][y] = GRID_EMPTY;
                gridTime[x][y] = 0;
            }
        }
    }
    private void initPlayers()
    {
        snake = new Snake[playerAmount];
        
        switch (playerAmount)
        {
            case 1:
                createPlayer(0, 24, 24, DIR_RIGHT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);
                break;
            case 2:
                createPlayer(0, 12, 24, DIR_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);
                createPlayer(1, 36, 24, DIR_UP, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S);
                break;
            case 3:
                createPlayer(0, 12, 12, DIR_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);
                createPlayer(1, 24, 36, DIR_UP, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S);
                createPlayer(2, 36, 12, DIR_DOWN, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5);
                break;
            case 4:
                createPlayer(0, 12, 12, DIR_RIGHT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);
                createPlayer(1, 36, 12, DIR_DOWN, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S);
                createPlayer(2, 12, 36, DIR_UP, KeyEvent.VK_6, KeyEvent.VK_8, KeyEvent.VK_4, KeyEvent.VK_5);
                createPlayer(3, 36, 36, DIR_LEFT, KeyEvent.VK_L, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K);
                break;
        }
        
        deathOrder = new int[playerAmount];
        for (int i = 0; i < playerAmount; i++)
        {
            deathOrder[i] = -1;
        }
    }
    private void createPlayer(int id, int x, int y, int dir,
            int keyRight, int keyUp, int keyLeft, int keyDown)
    {
        gridId[x][y] = (byte)id;
        gridTime[x][y] = STARTLENGTH - 1;
        
        snake[id] = new Snake(this, id, x, y, dir,
                keyRight, keyUp, keyLeft, keyDown);
    }
    
    protected byte getGridId(int x, int y)
    {
        return gridId[x][y];
    }
    protected short getGridTime(int x, int y)
    {
        return gridTime[x][y];
    }
    
    protected void setGridId(int x, int y, byte val)
    {
        gridId[x][y] = val;
    }
    protected void setGridTime(int x, int y, short val)
    {
        gridTime[x][y] = val;
    }
    
    protected void setPaused(boolean paused)
    {
        if (paused != this.paused && !justPaused)
        {
            this.paused = paused;
            
            if (paused)
            {
                menu = new MenuPause(board, this);
            }
            else
            {
                menu = null;
            }
            
            justPaused = true;
        }
    }
    
    protected void keyPressed(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (menu != null)
        {
            menu.keyPressed(ke);
        }
        
        if (k == KeyEvent.VK_ESCAPE)
        {
            setPaused(true);
        }
        
        for (int i = 0; i < playerAmount; i++)
        {
            snake[i].keyPressed(ke);
        }
    }
    
    protected void keyReleased(KeyEvent ke)
    {
        if (menu != null)
        {
            menu.keyReleased(ke);
        }
        
        for (int i = 0; i < playerAmount; i++)
        {
            snake[i].keyReleased(ke);
        }
    }
}
