package snakegame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class SnakeGame
{
    public static void main(String[] args)
    {
        GraphicsLoader.load();
        SoundLoader.load();
        Game.initColors();
        SaveManager.init();
        
        Board board = new Board();
        board.init();
        
        JFrame frame = new JFrame("Snake");
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(GraphicsLoader.icon.getImage());
        frame.setIgnoreRepaint(true);
        
        setSizeWithoutInsets(frame, Game.GAMEWIDTH, Game.GAMEHEIGHT);
        
        frame.add(board);
        frame.setVisible(true);
        
        board.start();
    }
    
    private static void setSizeWithoutInsets(Frame frame, int width, int height)
    {
        frame.setSize(width, height);
        frame.pack();
        
        Insets insets = frame.getInsets();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = width + insets.left + insets.right;
        int frameHeight = height + insets.top + insets.bottom;
        
        frame.setBounds(screenSize.width/2 - frameWidth/2,
                screenSize.height/2 - frameHeight/2, frameWidth, frameHeight);
    }
}