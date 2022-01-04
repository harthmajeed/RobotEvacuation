import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Robot {
	
	private int x;
	private int y;
	private int radius;
	private Color status;
	
	private int TopLeft;
	private int Top;
	private int TopRight;
	private int Left;
	private int Right;
	private int BottomLeft;
	private int Bottom;
	private int BottomRight;
	
	boolean exitFound = false;
	boolean wallFound = false;
	boolean evacuated = false;
	boolean directionSet = false;
	
	public int exitX;
	public int exitY;
	
	private int directionY;
	private int directionX;
	
	private int lastPositionX;
	private int lastPositionY;
	
	private int terminateX;
	private int terminateY;
	
	private Random random = new Random();
	
	private BufferedImage image;
	
	private int STEPS = 0;
	
	
	public Robot(int xCoor, int yCoor, int r, int termX, int termY) {
		this.x = xCoor; 
		this.y = yCoor;
		radius = r;
		status = Color.RED;
		terminateX = termX;
		terminateY = termY;
		chooseDirection();
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(status);
		g.fillOval((x-radius), (y-radius), radius, radius);
	}
	
	public int getRadius()
	{	return radius;	}
	
	public void setYellow()
	{	status = Color.ORANGE;	}
	
	public void setGreen()
	{	status = Color.GREEN;	}
	
	public void setPosition(int x, int y)
	{
		if(directionSet == true)
		{
			lastPositionX = this.x;
			lastPositionY = this.y;
		}
		this.x = x;
		this.y = y;
		STEPS++;
		System.out.println("Steps: "+STEPS);
	}
	
	public int getSteps()
	{	return STEPS;	}
	
	public boolean isExitFound()
	{	return exitFound;	}
	
	private void chooseDirection()
	{
		int r1 = -2;
		int r2 = -2;
		boolean good = false;
		while(good != true)
		{
			r1 = random.nextInt(3);
			r2 = random.nextInt(3);
			if(!(r1 == 1 && r2 == 1))
				good = true;
		}
		if(r1 == 0)
			r1 = -1;
		if(r1 == 1)
			r1 = 0;
		if(r1 == 2)
			r1 = 1;
		
		if(r2 == 0)
			r2 = -1;
		if(r2 == 1)
			r2 = 0;
		if(r2 == 2)
			r2 = 1;
		
		directionX = r1;
		directionY = r2;
		System.out.println(r1);
		System.out.println(r2);
		System.out.println();
	}
	
	public void setExitFound(boolean t, int x, int y)
	{	
		exitFound = t;	
		exitX = x;
		exitY = y;
	}
	
	private void wallFound()
	{	wallFound = true;	}
	
	private void evacuated()
	{	evacuated = true;	}
	
	public void terminate()
	{
		setExitFound(true,x,y);
		setPosition(terminateX,terminateY);
		status = Color.MAGENTA;
		evacuated();
	}
	
	private void scan()
	{
		//System.out.println("scan start ***********");
		TopLeft = image.getRGB((x-1), (y-1));		//System.out.println(TopLeft);
		Top = image.getRGB(x, (y-1));				//System.out.println(Top);
		TopRight = image.getRGB((x+1), (y-1));		//System.out.println(TopRight);
		Left = image.getRGB((x-1), y);				//System.out.println(Left);
		Right = image.getRGB((x+1), y);				//System.out.println(Right);
		BottomLeft = image.getRGB((x-1), (y+1));	//System.out.println(BottomLeft);
		Bottom = image.getRGB(x, (y+1));			//System.out.println(Bottom);
		BottomRight = image.getRGB((x+1), (y+1));	//System.out.println(BottomRight);
		//System.out.println("scan end ***********");	
	}
	
	private void setDirection()
	{
		int found = 0;
		scan();
		if(TopLeft == -8421505)
		{
			if(found == 1)
			{
				lastPositionX += -1;
				lastPositionY += -1;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(Top == -8421505)
		{
			if(found == 1)
			{
				//lastPositionX += 0;
				lastPositionY += -1;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(TopRight == -8421505)
		{
			if(found == 1)
			{
				lastPositionX += 1;
				lastPositionY += -1;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(Left == -8421505)
		{
			if(found == 1)
			{
				lastPositionX += -1;
				//lastPositionY += 0;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(Right == -8421505)
		{
			if(found == 1)
			{
				lastPositionX += 1;
				//lastPositionY += 0;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(BottomLeft == -8421505)
		{
			if(found == 1)
			{
				lastPositionX += -1;
				lastPositionY += 1;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(Bottom == -8421505)
		{
			if(found == 1)
			{
				//lastPositionX += 0;
				lastPositionY += 1;
				return;
			}
			else if(found == 0)
				found++;
		}
		if(BottomRight == -8421505)
		{
			if(found == 1)
			{
				lastPositionX += 1;
				lastPositionY += 1;
				return;
			}
			else if(found == 0)
				found++;
		}
		directionSet = true;
	}
	
	private boolean checkExit()
	{
		if(TopLeft == -32985 || 
			Top == -32985 || 
			TopRight == -32985 ||
			Left == -32985 || 
			Right == -32985 ||
			BottomLeft == -32985 ||
			Bottom == -32985 ||
			BottomRight == -32985)
			{
				System.out.println("Found Exit: terminate()");
				terminate();
				return true;
			}
		else
			return false;
	}
	
	private void walk()
	{
			scan();
			if(checkExit() == true)
			{
				return;
			}	
			if(TopLeft == -8421505 && ((x-1) != lastPositionX && (y-1) != lastPositionY))
			{
				System.out.println("TopLeft");
				setPosition((x-1), (y-1));
				return;
			}
			if(Top == -8421505 && (x != lastPositionX && (y-1) != lastPositionY))
			{
				System.out.println("Top");
				setPosition(x, (y-1));
				return;
			}
			if(TopRight == -8421505 && ((x+1) != lastPositionX && (y-1) != lastPositionY))
			{
				System.out.println("TopRight");
				setPosition((x+1), (y-1));
				return;
			}
			if(Left == -8421505 && ((x-1) != lastPositionX && y != lastPositionY))
			{
				System.out.println("Left");
				setPosition((x-1), y);
				return;
			}
			if(Right == -8421505 && ((x+1) != lastPositionX && y != lastPositionY))
			{
				System.out.println("Right");
				setPosition((x+1), y);
				return;
			}
			if(BottomLeft == -8421505 && ((x-1) != lastPositionX && (y+1) != lastPositionY))
			{
				System.out.println("BottomLeft");
				setPosition((x-1), (y+1));
				return;
			}
			if(Bottom == -8421505 && (x != lastPositionX && (y+1) != lastPositionY))
			{
				System.out.println("Bottom");
				setPosition(x, (y+1));
				return;
			}
			if(BottomRight == -8421505 && ((x+1) != lastPositionX && (y+1) != lastPositionY))
			{
				System.out.println("BottomRight");
				setPosition((x+1), (y+1));
				return;
			}
	}
	
	private void findWall()
	{
		if(wallFound == true)
		{
			return;
		}
		if(image.getRGB(x, y) == -8421505)
		{
			setYellow();
			wallFound();
			return;
		}
		int nextX = x + directionX;
		int nextY = y + directionY; 
		System.out.println("Direction: "+directionX+" "+directionY);
		System.out.println("Current: "+x+" "+y);
		System.out.println("Next: "+nextX+" "+nextY);
		if((image.getRGB(nextX, nextY) == -8421505) && (wallFound == false))
		{
			setDirection();
			setYellow();
			wallFound();
			setPosition(nextX, nextY); 
			System.out.println(image.getRGB(nextX, nextY));
		}	
		System.out.println("Current color: "+image.getRGB(x, y));
		System.out.println("Next color: "+image.getRGB(nextX, nextY));
		if((image.getRGB(x, y) == -2824) && (image.getRGB(nextX, nextY) == -16777216) && (wallFound == false))
		{
			if(directionX == -1 && directionY == -1)
			{
				setPosition(x, nextY);
				System.out.println("Detected! -1 -1");
				return;
			}	
			if(directionX == 1 && directionY == -1)
			{
				setPosition(x, nextY);
				System.out.println("Detected! 1 -1");
				return;
			}	
			if(directionX == 1 && directionY == 1)
			{
				setPosition(x, nextY);
				System.out.println("Detected! 1 1");
				return;
			}
			if(directionX == -1 && directionY == 1)
			{
				setPosition(x, nextY);
				System.out.println("Detected! -1 1");
				return;
			}	
		}
		if((image.getRGB(nextX, nextY) != -8421505) && (wallFound == false))
		{
			setPosition(nextX, nextY);
		}
	}
	
	public void gotoExit()
	{
		if(x == exitX && y == exitY)
		{
			terminate();
			return;
		}
			
		if(x != exitX)
		{
			if(exitX > x)
				x++;
			if(exitX < x)
				x--;
		}
		if(y != exitY)
		{
			if(exitY > y)
				y++;
			if(exitY < y)
				y--;
		}
		setPosition(x,y);
		scan();
		checkExit();
	}
	
	public void simulate()
	{
		System.out.println("\n"+System.identityHashCode(this));
		if(evacuated == true)
		{
			System.out.println("evacuated == true");
			return;
		}
		if(exitFound == true)
		{
			System.out.println("gotoExit()");
			gotoExit();
		}	
		if(wallFound == true && exitFound == false)
		{
			System.out.println("walk()");
			walk();
		}
		if(wallFound == false)
		{
			System.out.println("findWall()");
			findWall();
		}
	}
	
}

/*
 * interior :	-2824
 * exit		: 	-32985
 * exterior :	-1968641
 * wall		:	-16777216
 * path		:	-8421505
 */

