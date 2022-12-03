import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

//The interfaces are for activating the arrow keys of the keyboard to make the snake move
public class Gameplay extends JPanel implements KeyListener, ActionListener
{
	private ImageIcon title;
	//ImageIcon creates an image by reading the array of bytes from the image that we give
	
	
	//arrays for the position of snake inside the JPannel
	/*The 1st position of these arrays contain the head of the snake
	 * the others contain the length
	 */
	private int[] lengthx = new int[750];
	private int[] lengthy = new int[750];
	
	//variables to track the movements of the snake
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	//variables to store the images of the snake
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon snakeimage;
	private ImageIcon downmouth;
	private ImageIcon food;
	
	//The default length of snake
	private int snake_length=3;
	private int [] foodx = {25,75,100,125,150,175,200,225,250,275,300,325,350,
			375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750};
	private int [] foody = {75,100,125,150,175,200,225,250,275,300,325,350,
			375,400,425,450,475,500,525};
	
	private Random random = new Random();
	
	private int xpos = random.nextInt(29);//total no. of x positions for food that we have decided
	private int ypos = random.nextInt(19);//total y food positions
	
	private int score = 0;
	//For the movement of snake, create a timer class 
	//that will manage the speed of the snake, we use a built-in class Timer
	private Timer timer;
	private int delay=100; //for the speed of snake
	
	private int moves =0;
	
	public Gameplay()
	{
		//To set the default position of the snake
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
		
	}
	
	public void paint(Graphics g)
	{
		//This is a built in method used to draw everything in the JPanel
		
		
		if(moves == 0)
		{
			//if the game has just started
			lengthx[2]=50;
			lengthx[1]=75;
			lengthx[0]=100;
			
			lengthy[2]=100;
			lengthy[1]=100;
			lengthy[0]=100;
		}
		//Draw the border for title image
		g.setColor(Color.white);
		g.drawRect(24,10,851,55);
		
		//Draw the title image
		title = new ImageIcon("title.png");
		title.paintIcon(this,g,24,11);
		//paintIcon actually paints the image,the last 2 arguments are the coordinates of the top-left position of the image
		
		//Draw the border for the playing area
		/*g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);*/
		
		//Draw background border for playing area
		g.setColor(Color.black);
		g.fillRect(25,75,850,575);
		
		//drawing the score
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,14));
		g.drawString("scores: "+score, 775, 30);
		
		//drawing the length of the snake
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN ,14));
		g.drawString("snake length: "+snake_length, 775,50); 
		
		//drawing the snake
		//drawing the the default position of snake during start at the top right position of the playing area
		rightmouth = new ImageIcon("upmouth.png");
		rightmouth.paintIcon(this, g, lengthx[0],lengthy[0]);
		
		
		//inside the loop the direction of the snake is detected
		for(int i=0;i< snake_length;i++)
		{
			if(i==0 && right)
			{
				rightmouth = new ImageIcon("upmouth.png");
				rightmouth.paintIcon(this, g, lengthx[i],lengthy[i]);
			}
			if(i==0 && left)
			{
				leftmouth = new ImageIcon("upmouth.png");
				leftmouth.paintIcon(this, g, lengthx[i],lengthy[i]);
			}
			if(i==0 && up)
			{
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, lengthx[i],lengthy[i]);
			}
			if(i==0 && down)
			{
				downmouth = new ImageIcon("upmouth.png");
				downmouth.paintIcon(this, g, lengthx[i],lengthy[i]);
			}
			if(i!=0)//means its not the snakes face(since lengthx[0] and lengthy[0] denotes the face
			{
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, g, lengthx[i],lengthy[i]);				
			}
		}
		food = new ImageIcon("food.png");
		//detect if the head is colliding with the food
		if(foodx[xpos] == lengthx[0] && foody[ypos] == lengthy[0])
		{
			score++;
			//increment snake length
			snake_length++;
			//regenerate the food somewhere else
			xpos = random.nextInt(29);
			ypos = random.nextInt(19);
		}
		
		food.paintIcon(this, g, foodx[xpos], foody[ypos]);
		
		//check the collision with itself, if it does then game is over
		for(int i=1;i<snake_length;i++)
		{
			if(lengthx[i]==lengthx[0] && lengthy[i]==lengthy[0])
			{
				left=right=up=down = false;
				g.setColor(Color.white);
				g.setFont(new Font("arial",Font.BOLD, 50));
				g.drawString("GAME OVER!!",300,300);
				
				g.setFont(new Font("arial",Font.BOLD,20));
				g.drawString("Enter space to restart..", 350, 340);
			}
		}
		
		g.dispose();
		//this method makes the os to destroy and clean up the JFrame
	}

	public void actionPerformed(ActionEvent e)
	{
		timer.start();
		if(right)
		{
			for(int i = snake_length-1;i>=0;i--)
			{
				/*since the head leads the way and it has index 0,all other parts
				 * should follow it,i.e higher index should have the same y length as the index below it
				 */
				lengthy[i+1]=lengthy[i];
			}
			for(int i=snake_length;i>=0;i--)
			{
				if(i==0)
					lengthx[i]+=25;
				else
					lengthx[i] = lengthx[i-1];
				if(lengthx[i]>850)
					lengthx[i]=25;
			}
			repaint();//it calls the paint method;
		}
		if(left)
		{
			for(int i=snake_length-1;i>=0;i--)
				lengthy[i+1]=lengthy[i];
			for(int i=snake_length;i>=0;i--)
			{
				if(i==0)
					lengthx[i]-=25;
				else
					lengthx[i] = lengthx[i-1];
				if(lengthx[i] <25)
					lengthx[i] = 850;
			}
			repaint();
		}
		if(up)
		{
			for(int i=snake_length-1;i>=0;i--)
				lengthx[i+1]=lengthx[i];
			for(int i=snake_length;i>=0;i--)
			{
				if(i==0)
					lengthy[i]-=25;
				else
					lengthy[i] = lengthy[i-1];
				if(lengthy[i]<75)
					lengthy[i] = 625;
			}
			repaint();
		}
		if(down)
		{
			for(int i=snake_length-1;i>=0;i--)
				lengthx[i+1] = lengthx[i];
			for(int i=snake_length;i>=0;i--)
			{
				if(i==0)
					lengthy[i]+=25;
				else
					lengthy[i] = lengthy[i-1];
				if(lengthy[i]>625)
					lengthy[i] = 75;
			}
			repaint();		
					
		}
}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()== KeyEvent.VK_SPACE)
		{
			//if space bar is pressed, the game will be reset
			moves = 0;
			score=0;
			snake_length = 3;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moves++;//so that the position changes
			right = true;
			/*we should make sure that when snake id moving in one direction it should 
			not be pressed in the opposite direction*/
			
			if(!left)
			{
				right = true;
				/*i.e if snake is not in left direction(i.e its moving up or down)and 
				 * the right arrow is pressed, then change direction to right
			}
			else 
			{
				right = false;
				left = true;
				/*if snake is moving in left direction and the right arrow key is pressed
				 * then keep it moving in left itself*/
			}
			up = false;
			down = false;	
		}
		//the same with all other keys
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moves++;
			left = true;
			
			if(!right)
				left = true;
			else 
			{
				right = true;
				left = false;
			}
			up = false;
			down = false;	
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			moves++;
			up= true;
			
			if(!down)
				up = true;
			else 
			{
				down = true;
				up = false;
			}
			right = false;
			left= false;	
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moves++;
			down= true;
			
			if(!up)
				down = true;
			else 
			{
				down = false;
				up = true;
			}
			right = false;
			left= false;	
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
