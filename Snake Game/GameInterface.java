import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class GameInterface extends JPanel implements ActionListener
{
//Screen size
static final int ScreenX = 500;
static final int ScreenY = 500;
// declares the size of the objects in the game
static final int ObjectSize = 25;
//calculates the amount of objects we can fit on the screen
static final int GameSize = ( ScreenX*ScreenY)/ObjectSize;
//Coordinates for the snakes body 
final int x[] = new int[GameSize];
final int y[] = new int[GameSize];

//The amount of body parts the snake has at the start of the game
int SnakeBody = 5;
//The x and y coordinate for where the apple is going to located in the game  
int AppleX;
int AppleY;
//The count for how much of the food the snake is eaten
int FoodEaten;
//This declares the direction the snake will move to at the start of the game, in this case its going to move right 
char movement = 'R';
boolean running = false;

Random random;



GameInterface()
{
   random = new Random();
   this.setPreferredSize(new Dimension(ScreenX,ScreenY));
   this.setBackground(Color.black);
   this.setFocusable(true);
   this.addKeyListener(new MyKeyAdapter());
   //This will start the game 
   GameSetUp(); 
}

 public void GameSetUp()
    {
        //This will add an apple to the screen and run the program
        addFood();
        running = true;
        
    }
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      
    }
    public void draw(Graphics g)
    {
        if(running)
        {
        //This will make a grid on the screen 
        for(int i = 0; i < ScreenX/ObjectSize; i++)
        {
            g.drawLine(i*ObjectSize, 0,i*ObjectSize, ScreenY);
            g.drawLine(0,i*ObjectSize,ScreenY,i*ObjectSize);

        }
        //Sets the colour and the size of the food 
        g.setColor(Color.blue);
        g.fillOval(AppleX,AppleY,ObjectSize,ObjectSize);

        for(int i = 0; i < SnakeBody; i++)
        {
            if(i == 0)
            {
                //set the colour and size of the snake
                g.setColor(Color.blue);
                g.fillRect(x[i],y[i],ObjectSize, ObjectSize);
            }
            else{
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i],y[i],ObjectSize,ObjectSize);
            }
        }
    }
    else 
    {
        GameFinished(g);
    }
}
    public void Motion()
    {
        //The loop will go through all the body parts of the snake
      for(int i = SnakeBody; i > 0; i--)
      {
        //This will shift the body parts of the snake by 1
          x[i] = x[i-1];
          y[i] = y[i-1];
      }
      // This will help change the direction if where the snake will go
      switch(movement)
      {
        case 'U':
        y[0]= y[0] - ObjectSize;
        break;
        case 'D':
        y[0]= y[0] - ObjectSize;
        break;
        case 'L':
        x[0]= x[0] - ObjectSize;
        break;
      }
    }
    
    public void addFood()
    {
        //This method helps generate a new apple on the screen
       AppleX = random.nextInt(ScreenX/ObjectSize)*(ObjectSize);
       AppleY = random.nextInt(ScreenY/ObjectSize)*(ObjectSize);

    }
    public void Food()
    {
        //This condition is for when the snake eates its food
        if((x[0] == AppleX) && (y[0] == AppleY))
        {
            //snakes body increases by 1
            SnakeBody++;
            FoodEaten++;
            //this generates new food
            addFood();
        }
    }
    public void checkCollisions()
    {
        // This condition is for if the head collides with the body
       for(int i = SnakeBody; i > 0; i--)
       {
           if((x[0] == x[i] && y[0] == y[i]))
           {
               running = false;
           }
       }
       // This condition checks to see if the snakes head touches the edges of the screen
       if (x[0] < 0)
       {
           running = false;
       }
       if(x[0] > ScreenX){
           running = false;
       }
       if(y[0] < 0){
           running = false;
       }
       if(y[0] > ScreenY){
           running = false;
       }
       
    }
    public void GameFinished(Graphics g)
    {
        //Here we are putting text in the middle of the screen saying "Game over" when the game is finished
      g.setColor(Color.white);
      g.setFont(new Font("Ink Free",Font.BOLD,75));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("Game Over", (ScreenX - metrics.stringWidth("Game Over"))/2,ScreenY/2);
    }






    public void actionperformed(ActionEvent e)
    {
        //Here this calls for the snake to move, checks the collisions from the wall and adds new apples 
      if(running)
      {
          Motion();
          checkCollisions();
          addFood();

      }
    }
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e)
        {
            //This helps move the snake with the arrow keys 
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                // This limits the snake to only making 90 degree turns, so it doesnt hit itself
                if(movement != 'R')
                {
                    movement = 'L';
                }
                break;
            
            case KeyEvent.VK_RIGHT:
            if(movement != 'L'){
            {
                movement = 'R';
            }
            break;
        }
        case KeyEvent.VK_UP:
        if(movement != 'D'){
        {
            movement = 'U';
        }
        break;
    }
        case KeyEvent.VK_DOWN:
        if(movement != 'U'){
        {
            movement = 'D';
        }
        break;
    }
        }
    }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
