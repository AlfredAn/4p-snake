package snakegame;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
---Save file format---
Version 0:

int version;
int maxScore[0];
int maxScore[1];
int maxScore[2];
*/

public class SaveManager
{
    public static final String basePath = System.getenv("AppData");
    public static final String dirPath = basePath + "/Alfred Andersson/SnakeGame";
    public static final String filePath = dirPath + "/save.dat";
    private static final int currentVersion = 0;
    
    public static int[] maxScore;
    
    public static void init()
    {
        if (!load())
        {
            saveDefault();
        }
    }
    
    private static boolean load()
    {
        FileInputStream in = null;
        BufferedInputStream bin = null;
        DataInputStream din = null;
        boolean success = false;
        
        try
        {
            in = new FileInputStream(filePath);
            bin = new BufferedInputStream(in);
            din = new DataInputStream(bin);
            
            int version = din.readInt();
            
            maxScore = new int[3];
            
            if (version == 0)
            {
                maxScore[0] = din.readInt();
                maxScore[1] = din.readInt();
                maxScore[2] = din.readInt();
                success = true;
            }
        }
        catch (IOException e) {}
        finally
        {
            try
            {
                if (din != null)
                {
                    din.close();
                }
                if (bin != null)
                {
                    bin.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException e) {}
        }
        
        return success;
    }
    
    public static boolean save()
    {
        FileOutputStream out = null;
        BufferedOutputStream bout = null;
        DataOutputStream dout = null;
        boolean success = false;
        
        try
        {
            File dir = new File(dirPath);
            File file = new File(filePath);
            
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            if (!file.exists())
            {
                file.createNewFile();
            }
            
            out = new FileOutputStream(file);
            bout = new BufferedOutputStream(out);
            dout = new DataOutputStream(bout);
            
            dout.writeInt(currentVersion);
            dout.writeInt(maxScore[0]);
            dout.writeInt(maxScore[1]);
            dout.writeInt(maxScore[2]);
            
            success = true;
        }
        catch (IOException e) {}
        finally
        {
            try
            {
                if (dout != null)
                {
                    dout.close();
                }
                if (bout != null)
                {
                    bout.close();
                }
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e) {}
        }
        
        return success;
    }
    
    private static boolean saveDefault()
    {
        maxScore = new int[3];
        maxScore[0] = 0;
        maxScore[1] = 0;
        maxScore[2] = 0;
        return save();
    }
}
