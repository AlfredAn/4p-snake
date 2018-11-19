package snakegame;

public abstract class SoundLoader
{
    public static Sound eat, gameOver;
    
    public static void load()
    {
        eat = new Sound("/sound/eat.wav", -12);
        gameOver = new Sound("/sound/gameOver.wav", -18);
    }
}