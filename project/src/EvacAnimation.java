import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class EvacAnimation extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage[] evacExitLocs = new BufferedImage[15];
	public Robot r1;
	public Robot r2;
	private int imageNum = 0;
	private Random random = new Random();
	
	public EvacAnimation() throws IOException
	{
		for (int i=0; i < 15; i++)
			evacExitLocs[i] = ImageIO.read(getClass().getResource("evac"+i+".png"));
		r1 = new Robot(190, 150, 15, 45, 20);
		r2 = new Robot(190, 150, 15, 35, 50);
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(evacExitLocs[imageNum], 0, 0, null);
		r1.draw(g);
		r2.draw(g);
	}
	
	public int getRGB(int x, int y) {
		return evacExitLocs[imageNum].getRGB(x, y);
	}
	
	public void setRandomImage()
	{
		imageNum = random.nextInt(15);
		r1.setImage(evacExitLocs[imageNum]);
		r2.setImage(evacExitLocs[imageNum]);
	}
		
	public void simulateAnimation() 
	{
		r1.simulate();
		r2.simulate();
	}
		
}


/*
 * interior :	-2824
 * exit		: 	-32985
 * exterior :	-1968641
 * wall		:	-16777216
 * path		:	-8421505
 */
