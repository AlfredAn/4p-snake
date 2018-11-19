package snakegame;

public abstract class GraphicsLoader
{
    public static Sprite[] snakeHead, snakeBody, snakeTurn, snakeTail;
    public static Sprite icon, apple;
    
    public static void load()
    {
        icon = new Sprite("/graphics/icon.png");
        
        snakeHead = new Sprite[4];
        snakeBody = new Sprite[4];
        snakeTurn = new Sprite[4];
        snakeTail = new Sprite[4];
        
        for (int i = 0; i < 4; i++)
        {
            snakeHead[i] = new Sprite("/graphics/head" + Integer.toString(i+1) + ".png");
            snakeBody[i] = new Sprite("/graphics/body" + Integer.toString(i+1) + ".png");
            snakeTurn[i] = new Sprite("/graphics/turn" + Integer.toString(i+1) + ".png");
            snakeTail[i] = new Sprite("/graphics/tail" + Integer.toString(i+1) + ".png");
        }
        
        apple = new Sprite("/graphics/apple.png");
    }
}