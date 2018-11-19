package snakegame;

public class MenuEvent
{
    public static final int ME_BUTTONPRESS = 1;
    
    private final MenuItem source;
    private final int type;
    
    public MenuEvent(MenuItem source, int type)
    {
        this.source = source;
        this.type = type;
    }
    
    public MenuItem getSource()
    {
        return source;
    }
    
    public int getType()
    {
        return type;
    }
}
