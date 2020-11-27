
/**
 * This class is designed to draw the pictures being used. The class also determines the rotation of the ship. 
 * Each orienantation of the ship is stored in an image file like a film real. 
 * This way the image file is loaded into the computer once. The right oriantation is determined by 
 * trimming the image in the y-axis so that only on cell/frame is shown.
 * 
 * @author Sheldon Maschmeyer
 * @version 30_05_06
 */

import java.awt.*;
import java.applet.*;
import java.util.*;

public class Drawing
{
    // declaring an image variable
    Image img;
    // declaring an applet called "parent"
    static Applet parent;
    // "size" is a point object
    Point size=new Point();
    Point position;
    int cellCount;
    int currentCell;
    
    // constructor "Drawing" creates drawing objects 
    Drawing (String fileName, int cells) {
        img = parent.getImage(parent.getDocumentBase(), fileName);
        // don't leave the loop untill the width and height do not equal -1 & I do not use an image observer which is a parameter 
        // required by the method therefore it is set to null (do not use) 
        // exit the loop when it has the width and the height of image
        while (img.getWidth(null) == -1 || img.getHeight(null) == -1);
        cellCount = cells;
        size.x=img.getWidth(null);
        size.y=img.getHeight(null);
        size.y=size.y/cellCount;
        position = new Point();
    }
    /** This method calculates the orientation of the ship and draws the images.*/
    public void paint (Graphics g) {
        // variables for actual images files (no rotation/frame/cell determinations.
        int imgLeft = position.x;
        int imgRight = imgLeft+size.x;
        int imgTop = position.y;
        int imgBottom = imgTop+size.y;
        
        // variables to calculate which frame/cell (rotation of the image of the ship) to display. 
        int frameLeft = 0;
        int frameRight = frameLeft+size.x;
        int frameTop = currentCell * size.y;
        int frameBottom = frameTop+size.y;
        
        // draws the ship images based on the variable values defined
        g.drawImage(img, imgLeft,imgTop, imgRight, imgBottom, frameLeft, frameTop, frameRight, frameBottom, null);
    }
    
    public static void setApplet(Applet a) {
        // applet variable "parent" equal to applet variable "a"
        parent = a;
    }
}
