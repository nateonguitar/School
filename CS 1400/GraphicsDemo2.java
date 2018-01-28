/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* Messing around with pictures
*******************************************/

   // Graphics, Image, Color
   // Graphics2D, RadialGradientPaint, AlphaComposite, Font
   
import java.awt.*;  // for Graphics, Image, and Color classes
import javax.swing.JApplet;

public class GraphicsDemo2 extends JApplet // This extends JApplet appended to the class heading 
                                          // allows this class to "borrow" the getImage method  
                                          // from the already defined Java API JApplet class
{
   public void paint (Graphics g)
   {
   Image image = this.getImage(getDocumentBase(),"lotro-the-shire.jpg");
   
   Graphics2D g2 = (Graphics2D) g.create();
   float centerX = 180;
   float centerY = 135;
   float radius = 140;
   float[] gradientRange = {0.5f, 1.0f};  // times radius
   
      // Color components are: red, green, blue, and opaqueness
      // in the range between 0.0f and 1.0f
   Color insideColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
   Color outsideColor = Color.BLACK;
   Color[] colors = {insideColor, outsideColor};
   
   g.drawImage(image, 0, 0, 680, 480,     // destination topL, botR
      0, 0, 2306, 1625, this);            // source topL, botR
      
      // for tinted pane
   g2.setPaint(new RadialGradientPaint(centerX, centerY, radius, gradientRange, colors));
   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
   g2.fillRect(0, 0, getWidth(), getHeight());
   
      // for text
   g.setColor(Color.WHITE);
   g.setFont(new Font(Font.SERIF, Font.ITALIC, 30));
   g.drawString("Lotro", 300, 300);   
   } // end of paint
} // end of class GraphicsDemo2