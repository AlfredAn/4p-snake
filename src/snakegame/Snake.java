package snakegame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

class Snake
{
    public final int id, keyRight, keyUp, keyLeft, keyDown;
    private int length, dir, prevDir, xPos, yPos, score;
    private boolean isKeyRight, isKeyUp, isKeyLeft, isKeyDown, isAlive;
    
    private int newX, newY;
    
    Game game;
    
    Snake(Game game, int id, int x, int y, int dir,
            int keyRight, int keyUp, int keyLeft, int keyDown)
    {
        this.game = game;
        
        this.id = id;
        length = Game.STARTLENGTH;
        this.dir = dir;
        prevDir = dir;
        xPos = x;
        yPos = y;
        score = 0;
        
        this.keyRight = keyRight;
        this.keyUp = keyUp;
        this.keyLeft = keyLeft;
        this.keyDown = keyDown;
        
        isKeyRight = false;
        isKeyUp = false;
        isKeyLeft = false;
        isKeyDown = false;
        
        isAlive = true;
    }
    
    public void update()
    {
        if (isAlive)
        {
            switch (dir)
            {
                case Game.DIR_RIGHT:
                    xPos++;
                    break;
                case Game.DIR_UP:
                    yPos--;
                    break;
                case Game.DIR_LEFT:
                    xPos--;
                    break;
                case Game.DIR_DOWN:
                    yPos++;
                    break;
                default:
                    System.out.println("Invalid direction id: " + dir);
                    System.exit(0);
                    break;
            }

            prevDir = dir;

            if (xPos < 0 || xPos >= Game.GRIDWIDTH
             || yPos < 0 || yPos >= Game.GRIDHEIGHT)
            {
                death();
            }
            else if (game.getGridId(xPos, yPos) >= 0)
            {
                death();
            }
            else
            {
                boolean food = game.getGridId(xPos, yPos) == Game.GRID_FOOD;

                game.setGridId(xPos, yPos, (byte)id);
                game.setGridTime(xPos, yPos, (short)length);

                if (food)
                {
                    SoundLoader.eat.play();
                    
                    score++;
                    length += Game.LENGTHINCREASE;

                    for (int y = 0; y < Game.GRIDHEIGHT; y++)
                    {
                        for (int x = 0; x < Game.GRIDWIDTH; x++)
                        {
                            if (game.getGridId(x, y) == id)
                            {
                                game.setGridTime(x, y,
                                        (short)(game.getGridTime(x, y)
                                        + Game.LENGTHINCREASE));
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void death()
    {
        isAlive = false;
        game.snakeDead(id);
    }
    
    public void drawSegment(Graphics g, int x, int y)
    {
        boolean[] c;
        int time = game.getGridTime(x, y);
        
        c = new boolean[4];
        c[0] = !checkPosition(x + 1, y, time);
        c[1] = !checkPosition(x, y - 1, time);
        c[2] = !checkPosition(x - 1, y, time);
        c[3] = !checkPosition(x, y + 1, time);
        
        for (int i = 0; i < 4; i++)
        {
            if (c[i])
            {
                if (c[(i+1)%4])
                {
                    GraphicsLoader.snakeTurn[id].drawRotated(g,
                            x * Game.CELLWIDTH, y * Game.CELLHEIGHT,
                            -i*90 - 90);
                }
                else if (c[(i+2)%4])
                {
                    GraphicsLoader.snakeBody[id].drawRotated(g,
                            x * Game.CELLWIDTH, y * Game.CELLHEIGHT,
                            -i*90);
                }
                else if (c[(i+3)%4])
                {
                    GraphicsLoader.snakeTurn[id].drawRotated(g,
                            x * Game.CELLWIDTH, y * Game.CELLHEIGHT,
                            -i*90);
                }
                else if (time == length - 1)
                {
                    GraphicsLoader.snakeHead[id].drawRotated(g,
                            x * Game.CELLWIDTH, y * Game.CELLHEIGHT,
                            -i*90);
                }
                else
                {
                    GraphicsLoader.snakeTail[id].drawRotated(g,
                            x * Game.CELLWIDTH, y * Game.CELLHEIGHT,
                            -i*90 + 180);
                }
                
                break;
            }
        }
    }
    
    private boolean checkPosition(int x, int y, int time)
    {
        boolean inBox = Util.isInBox(x, y, 0, 0, Game.GRIDWIDTH - 1, Game.GRIDHEIGHT - 1);
        
        if (inBox && game.getGridId(x, y) == id
                && Math.abs(game.getGridTime(x, y) - time) == 1)
        {
            if (game.getGridTime(x, y) == time - 1)
            {
                newX = x;
                newY = y;
            }
            return false;
        }
        
        return true;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public boolean isAlive()
    {
        return isAlive;
    }
    
    public void keyPressed(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (k == keyRight)
        {
            isKeyRight = true;
            
            if (prevDir != Game.DIR_LEFT)
            {
                dir = Game.DIR_RIGHT;
            }
        }
        else if (k == keyUp)
        {
            isKeyUp = true;
            
            if (prevDir != Game.DIR_DOWN)
            {
                dir = Game.DIR_UP;
            }
        }
        else if (k == keyLeft)
        {
            isKeyLeft = true;
            
            if (prevDir != Game.DIR_RIGHT)
            {
                dir = Game.DIR_LEFT;
            }
        }
        else if (k == keyDown)
        {
            isKeyDown = true;
            
            if (prevDir != Game.DIR_UP)
            {
                dir = Game.DIR_DOWN;
            }
        }
    }
    
    public void keyReleased(KeyEvent ke)
    {
        int k = ke.getKeyCode();
        
        if (k == keyRight)
        {
            isKeyRight = false;
        }
        else if (k == keyUp)
        {
            isKeyUp = false;
        }
        else if (k == keyLeft)
        {
            isKeyLeft = false;
        }
        else if (k == keyDown)
        {
            isKeyDown = false;
        }
    }
}




















