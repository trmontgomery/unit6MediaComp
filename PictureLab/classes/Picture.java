import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0 ; col < width / 2; col++)
      {
        leftPixel = pixels[row][width - 1 - col];
        rightPixel = pixels[row][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
    
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int height = pixels.length;
    for (int row = 0; row < height / 2; row++)
    {
      for (int col = 0 ; col < pixels[0].length; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[height - 1 - row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontalBotToTop()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int height = pixels.length;
    for (int row = 0; row < height / 2; row++)
    {
      for (int col = 0 ; col < pixels[0].length; col++)
      {
        topPixel = pixels[height - 1 - row][col];
        bottomPixel = pixels[row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
    }
    
  public void mirrorDiagonal()
  {
      Pixel[][] pixels = this.getPixels2D();
      Pixel topTriangle = null;
      Pixel botTriangle = null;
      int height = pixels.length;
      
      for (int row = 0; row < height; row++)
      {
          for (int col = 0; col < pixels.length; col++)
          {
              topTriangle = pixels[row][col];
              botTriangle = pixels[col][row];
              topTriangle.setColor(botTriangle.getColor());
            }
        }
    }
    
  public void mirrorArms()
  {
      int mirrorPoint = 245;
      Pixel topPixel = null;
      Pixel botPixel = null;
      int count = 0;
      Pixel[][] pixels = this.getPixels2D();

      // loop through the rows
      for (int row = 158; row < mirrorPoint; row++)
      {
        // loop from 13 to just before the mirror point
        for (int col = 103; col < 173; col++)
        {
            topPixel = pixels[row][col];
            botPixel = pixels[mirrorPoint - row + mirrorPoint][col];
            botPixel.setColor(topPixel.getColor());
        }
      }
           
  }
  
  public void mirrorGull()
  {
      int mirrorPoint = 350;
      Pixel leftPixel = null;
      Pixel rightPixel = null;
      int count = 0;
      Pixel [][] pixels = this.getPixels2D();
      
      for (int row = 232; row < 325; row++)
      {
          for (int col = 230; col < mirrorPoint; col++)
          {
              leftPixel = pixels[row][col];
              rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
              rightPixel.setColor(leftPixel.getColor());
            }
        }
    }
  
  public void keepOnlyBlue()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      for (Pixel[] rowArray : pixels)
      {
          for (Pixel pixelObj : rowArray)
          {
              pixelObj.setRed(0);
              pixelObj.setGreen(0);
          }
      }
  }
  
  public void keepOnlyRed()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      for (Pixel[] rowArray : pixels)
      {
          for (Pixel pixelObj : rowArray)
          {
              pixelObj.setBlue(0);
              pixelObj.setGreen(0);
          }
      }
  }
  
  public void keepOnlyGreen()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      for (Pixel[] rowArray : pixels)
      {
          for (Pixel pixelObj : rowArray)
          {
              pixelObj.setRed(0);
              pixelObj.setBlue(0);
          }
      }
  }
  
  public void negate()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      for (Pixel[] rowArray : pixels)
      {
          for (Pixel pixelObj : rowArray)
          {
              pixelObj.setRed(255 - pixelObj.getRed());
              pixelObj.setGreen(255 - pixelObj.getGreen());
              pixelObj.setBlue(255 - pixelObj.getBlue());
          }
      }
    }
    
  public void grayscale()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      for (Pixel[] rowArray : pixels)
      {
          for (Pixel pixelObj : rowArray)
          {
              int avg = 0;
              int red = pixelObj.getRed();
              int blue = pixelObj.getBlue();
              int green = pixelObj.getGreen();
              avg = (red + blue + green) / 3;
              pixelObj.setRed(avg);
              pixelObj.setRed(avg);
              pixelObj.setRed(avg);
          }
      }
    } 
    
  public void fixUnderWater()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      for (Pixel[] rowArray : pixels)
      {
          for (Pixel pixelObj : rowArray)
          {
              pixelObj.setBlue(0);
          }
      }
    }   
   
  
  public void cropAndCopy(Picture fromPic, int startRow, int endRow,
  int startCol, int endCol, 
  int startDesRow, int startDesCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = startRow, toRow = startDesRow; 
         fromRow < endRow &&
         toRow < (startDesRow + (endRow-startRow) ); 
         fromRow++, toRow++)
    {
      for (int fromCol = startCol, toCol = startDesCol; 
           fromCol < endCol &&
           toCol < (startDesCol + (endCol-startCol) );  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  
  public Picture scaleByHalf()
  {
      Pixel[][] source = this.getPixels2D();
      int halfWidth = source.length / 2;
      int halfHeight = source[0].length / 2;
      Picture halfSize = new Picture(halfWidth, halfHeight);
      Pixel[][] half = halfSize.getPixels2D();
      Pixel sourcePixel = null;
      Pixel halfPixel = null;
      
      for (int row = 0, halfRow = 0; 
           row < source.length && halfRow < half.length; 
           row = row + 2, halfRow++)
      {
          for (int col = 0, halfCol = 0; 
               col < source[0].length && halfCol < half[0].length;
               col = col + 2, halfCol++)
          {
              sourcePixel = source[row][col];
              halfPixel = half[halfRow][halfCol];
              halfPixel.setColor(sourcePixel.getColor());
            }
        }
        
      return halfSize;  
  }
  
  public Picture scaleBy(int scaleFactor)
  {
      Pixel[][] source = this.getPixels2D();
      int scaledWidth = source.length / scaleFactor;
      int scaledHeight = source[0].length / scaleFactor;
      Picture reSize = new Picture(scaledWidth, scaledHeight);
      Pixel[][] scaled = reSize.getPixels2D();
      Pixel sourcePixel = null;
      Pixel scalePixel = null;
      
      for (int row = 0, scaledRow = 0; 
           row < source.length && scaledRow < scaled.length; 
           row = row + scaleFactor, scaledRow++)
      {
          for (int col = 0, scaledCol = 0; 
               col < source[0].length && scaledCol < scaled[0].length;
               col = col + scaleFactor, scaledCol++)
          {
              sourcePixel = source[row][col];
              scalePixel = scaled[scaledRow][scaledCol];
              scalePixel.setColor(sourcePixel.getColor());
            }
        }
        
      return reSize;  
  }
 
  
  public static void myCollage()
  {
      //initialize pictures and store them in an array
      
      Picture a = new Picture("final.jpg");
      a.negate();
     
      Picture b = new Picture("final.jpg");
      b.zeroBlue();
      
      Picture c = new Picture("final.jpg");
      c.keepOnlyBlue();
      
      Picture d = new Picture("final.jpg");
      d.keepOnlyRed();
      
      Picture e = new Picture("final.jpg");
      e.keepOnlyGreen();
      
      Picture f = new Picture("final.jpg");
      f.mirrorHorizontal();
      
      Picture g = new Picture("final.jpg");
      g.mirrorHorizontalBotToTop();
      
      Picture h = new Picture("final.jpg");
      h.mirrorVertical();
      
      Picture[][] pictures = {
                                {a,h,d,b},
                                {g,e,f,c}
                            };
      
      //scale all pictures to 1/3 size and then add them to collage
      int canvasWidth = (a.getWidth()/3)*4;
      int canvasHeight = (a.getHeight()/3)*2 ;
      Picture blankCanvas = new Picture(canvasHeight, canvasWidth);
      for (int row = 0; row < pictures.length; row++)
      {
          for (int col = 0; col < pictures[0].length; col++)
          {
              Picture x = pictures[row][col].scaleBy(3);
              int height = x.getHeight();
              int width = x.getWidth();
              blankCanvas.cropAndCopy(x,0,height,0,width,
                                0 + (row*height) ,0 + (col*width));
            }
        }
      
          
      blankCanvas.explore();
      blankCanvas.write("MyCollage.jpg");
    }
   /* Main method for testing - each class in Java can have a main 
    * method 
    */
  public static void main(String[] args) 
  {
    
    Picture b = new Picture("face.jpg");
    b.explore();
    b.mirrorVerticalRightToLeft();
    b.explore();
    Picture c = new Picture("face.jpg");
    c.mirrorVertical();
    c.explore();
      
  }
  
} // this } is the end of class Picture, put all new methods before this
