import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;


public class GraphicsDemo extends JFrame{
	
		// constructor
	public GraphicsDemo()
	{
		setTitle("GraphicsDemo with Kilobolt");
		setSize(800, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(0,  0,  800,  480);
		g.setColor(Color.BLUE);
		g.drawRect(60, 200, 100, 250);
		g.setColor(Color.BLACK);
		g.drawString("My name is Nate",  200, 400);
	}

	public static void main(String[] args) {
		GraphicsDemo graphicsDemo = new GraphicsDemo();
		

	}

}
