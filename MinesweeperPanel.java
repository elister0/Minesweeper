import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//The panel that displays the information given by the model to the user-- Concerned with GUI, not how the game actually works
public class MinesweeperPanel extends JPanel{

    //Stores the frame so that the user can ask the model questions
    MinesweeperFrame frame;
    //Stores if the game is still running-- 1 for running, 0 if the user has won or lost the game meaning inputs will no longer
    //be registered
    public int runningStatus;
    private final int SIZE;

    //The constructor of the panel takes in the frame so that it can store it to ask the model questions
    public MinesweeperPanel (MinesweeperFrame f, int size)
    {
        //Creates a grid layout that's SIZE by SIZE and contains buttons for the user to interact with
        super(new GridLayout(size,size));
        SIZE = size;
        frame = f;
        //Adds the buttons and gives them a mouse listener. Minesweeper buttons are implemented below and
        //take in a position and the default blank string that means the user hasn't clicked on them yet
        //TileHandler is also implemented below
        for(int i = 0; i< SIZE * SIZE; i++){
            JButton temp = new MineSweeperButton(i, "");
            temp.addMouseListener(new TileHandler());
            
            this.add(temp);
            
        }
        runningStatus = 1;
        
    }

    //An inner class that creates MineSweeper Buttons
    private class MineSweeperButton extends JButton{
        
        //Instance variables of x position (row) and y position (column)
        private int xPos;
        private int yPos;

        //The constructor that takes in the position of the button and the string message to be displayed
        public MineSweeperButton(int i, String s)
        {
            super (s);
            xPos = i/SIZE;
            yPos = i%SIZE;
            this.setOpaque(true);
            this.setBorderPainted(false);
            //Creates a checkerboard pattern that makes it easy for the user to comprehend where the buttons end and begin
            if ((i%SIZE + i/SIZE)%2 == 1)
            {
                this.setBackground(Color.LIGHT_GRAY);
            }
            
        }

        //A method that can be asked of the button to return the xPosition of it
        public int getXPos()
        {
            return xPos;
        }

         //A method that can be asked of the button to return the yPosition of it
        public int getYPos()
        {
            return yPos;
        }

    
    }

    //TileHandlers are mouse adapters that only look at if a button has been pressed by a mouse-- either a right click 
    //or a left click and tells the model to update the game accordingly
    private class TileHandler extends MouseAdapter {
        public void mousePressed (MouseEvent e)
        {
            //Checks if the game is running (Hasn't been won or lost)
            if(runningStatus == 1){
                if (e.getButton() == MouseEvent.BUTTON1) //Button one is left click
                {
                    frame.getModel().updateSquare(((MineSweeperButton)e.getSource()).getXPos(), ((MineSweeperButton)e.getSource()).getYPos(), "l");
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON3) //Button three is right click
                {
                    frame.getModel().updateSquare(((MineSweeperButton)e.getSource()).getXPos(), ((MineSweeperButton)e.getSource()).getYPos(), "r");
                    frame.getBorder().updateMineCount();
                    repaint();
                }
            }
            

        } 
    }  

    //Paints what the model contains in a way that is easy to understand by the user
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);

        //runs through every tile in the grid
        for (int row = 0; row<SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                //Asks the model for the status to be translated
                int status = frame.getModel().returnStatus(row,col);
                int xpos = col ;
                int ypos = row;
                //The position of the tile as a single int to be understoof by the getComponent method
                int position = ypos * SIZE + xpos;
                JButton location = (JButton)this.getComponent(position);
                
                int fontSize = 20;
                if (SIZE == 9)
                {
                    fontSize = 25;
                } else if (SIZE == 20)
                {
                    fontSize = 15;
                }
                location.setFont(new Font("Serif", Font.PLAIN, fontSize));

                //Sets the color text and display based on the status and the mine count
                if (status == 0)
                {
                    location.setText("");
                }
                else if (status == 1)
                {
                    location.setForeground(Color.BLACK);
                    location.setText("");
                } else if (status == 2)
                {
                    location.setForeground(Color.BLACK);
                    location.setText("");
                }
                else if( status == 3)
                {   
                    location.setText(" ");
                    location.setBackground(Color.DARK_GRAY);
                } 
                else if (status == 4)
                {
                    location.setBackground(Color.DARK_GRAY);
                    //Check surrounding is implemented below. Returns the mine count surrounding the tile
                    int surroundingBombs = checkSurrounding(row,col);
                    if (surroundingBombs == 1)
                    {
                        location.setForeground(Color.CYAN);
                    } else if (surroundingBombs == 2)
                    {
                        location.setForeground(Color.GREEN);
                    } else if (surroundingBombs == 3)
                    {
                        location.setForeground(Color.YELLOW);
                    } else if (surroundingBombs == 4)
                    {
                        location.setForeground(Color.ORANGE);
                    } else if (surroundingBombs == 5)
                    {
                        location.setForeground(Color.RED);
                    }  else if (surroundingBombs == 6)
                    {
                        location.setForeground(Color.PINK);
                    }  else if (surroundingBombs == 7)
                    {
                        location.setForeground(Color.BLUE);
                    } else if (surroundingBombs == 8)
                    {
                        location.setForeground(Color.MAGENTA);
                    }

                    location.setText("" + surroundingBombs);
                } else if (status == 5)
                {
                    //The game was lost because a mine was clicked
                    location.setBackground(Color.RED);
                    //Tells the frame border to display the lose message and sets running status to 0
                    frame.getBorder().YouLose();
                    runningStatus = 0;
                    
                } else if (status == 6 || status == 7 || status == 8)
                {
                    location.setForeground(Color.RED);
                    location.setText("F");
                }
            }
        }

        //Checks if the game has been won. Defaults to true unless proven otherwise (A tile next to a mine hasn't been clicked
        // or  has no data and hasn't been clicked)
        boolean winStatus = true;
        for (int r = 0; r< SIZE; r++)
        {
            for (int c = 0; c<SIZE; c++)
            {
                if (frame.getModel().returnStatus(r,c) == 0 || frame.getModel().returnStatus(r,c) == 2)
                {
                    winStatus = false;
                }
            }
        }

        //If the game is won, tells the frame border to display the win message and sets the running status to 0 
        //(no more user input)
        if(winStatus)
        {
            runningStatus = 0;
            frame.getBorder().YouWin();
        }

    }


    //Checks the surrounding [r][c] for mines and returns the amount of mines surroinding the tile
    public int checkSurrounding(int r, int c)
    {
        int count = 0;
        //Checks all the non-normal tiles first that would cause out of bounds errors otherwise

        //The top left corner that would cause an exception if done the "normal" way.
        if (r == 0 && c == 0)
        {
            if (frame.getModel().returnStatus(0,1) == 1 || frame.getModel().returnStatus(0,1) == 6 || frame.getModel().returnStatus(0,1) == 5)
            {
                count ++;
            }
            if (frame.getModel().returnStatus(1,1)== 1|| frame.getModel().returnStatus(1,1) == 6 || frame.getModel().returnStatus(1,1) == 5)
            {
                count++;
            }
            if (frame.getModel().returnStatus(1,0) == 1|| frame.getModel().returnStatus(1,0) == 6 || frame.getModel().returnStatus(1,0) == 5)
            {
                count++;
            }
        } else if (r == 0 && c == SIZE-1) //The top right corner that would cause an exception if done the "normal" way.
        {
            if (frame.getModel().returnStatus(0,SIZE-2) == 1 || frame.getModel().returnStatus(0,SIZE-2) == 6 || frame.getModel().returnStatus(0,SIZE-2) == 5)
            {
                count ++;
            }
            if (frame.getModel().returnStatus(1,SIZE-2)== 1 || frame.getModel().returnStatus(1,SIZE-2) == 6 || frame.getModel().returnStatus(1,SIZE-2) == 5)
            {
                count++;
            }
            if (frame.getModel().returnStatus(1,SIZE-1) == 1 || frame.getModel().returnStatus(1,SIZE-1) == 6 || frame.getModel().returnStatus(1,SIZE-1) == 5)
            {
                count++;
            }
        } else if (r == SIZE-1 && c == SIZE-1) //The bottom right corner that would cause an exception if done the "normal" way.
        {
            if (frame.getModel().returnStatus(SIZE-2,SIZE-1) == 1 || frame.getModel().returnStatus(SIZE-2,SIZE-1) == 6 || frame.getModel().returnStatus(SIZE-2,SIZE-1) == 5)
            {
                count ++;
            }
            if (frame.getModel().returnStatus(SIZE-2,SIZE-2)== 1 || frame.getModel().returnStatus(SIZE-2,SIZE-2) == 6 || frame.getModel().returnStatus(SIZE-2,SIZE-2) == 5)
            {
                count++;
            }
            if (frame.getModel().returnStatus(SIZE-1,SIZE-2) == 1 || frame.getModel().returnStatus(SIZE-1,SIZE-2) == 6 || frame.getModel().returnStatus(SIZE-1,SIZE-2) == 5)
            {
                count++;
            }
        } else if (r == SIZE-1 && c == 0) //The bottom left corner that would cause an exception if done the "normal" way.
        {
            if (frame.getModel().returnStatus(SIZE-2,0) == 1 || frame.getModel().returnStatus(SIZE-2,0) == 6 || frame.getModel().returnStatus(SIZE-2,0) == 5)
            {
                count ++;
            }
            if (frame.getModel().returnStatus(SIZE-2,1)== 1 || frame.getModel().returnStatus(SIZE-2,1) == 6 || frame.getModel().returnStatus(SIZE-2,1) == 5)
            {
                count++;
            }
            if (frame.getModel().returnStatus(SIZE-1,1) == 1 || frame.getModel().returnStatus(SIZE-1,1) == 6 || frame.getModel().returnStatus(SIZE-1,1) == 5)
            {
                count++;
            }
        } else if (r == 0){ //The top row that would cause an exception if done the "normal" way.
            for (int i = r; i<=r+1;i++)
            {
                for (int j = c-1; j<=c+1;j++)
                {
                    if ((frame.getModel().returnStatus(i,j) == 1|| frame.getModel().returnStatus(i,j) == 6 || frame.getModel().returnStatus(i,j) == 5) && !(i==r && j==c))
                    {
                        count++;
                    }
                }
            } 
        } else if (r == SIZE-1){ //The bottom row that would cause an exception if done the "normal" way.
            for (int i = r-1; i<=r;i++)
            {
                for (int j = c-1; j<=c+1;j++)
                {
                    if ((frame.getModel().returnStatus(i,j) == 1 || frame.getModel().returnStatus(i,j) == 6 || frame.getModel().returnStatus(i,j) == 5) && !(i==r && j==c))
                    {
                        count++;
                    }
                }
            }
        } else if (c == 0){ //The left column that would cause an exception if done the "normal" way.
            for (int i = r-1; i<=r+1;i++)
            {
                for (int j = c; j<=c+1;j++)
                {
                    if ((frame.getModel().returnStatus(i,j) == 1|| frame.getModel().returnStatus(i,j) == 6 || frame.getModel().returnStatus(i,j) == 5) && !(i==r && j==c))
                    {
                        count++;
                    }
                }
            }
        } else if (c == SIZE-1){ //The right column that would cause an exception if done the "normal" way.
            for (int i = r-1; i<=r+1;i++)
            {
                for (int j = c-1; j<=c;j++)
                {
                    if ((frame.getModel().returnStatus(i,j) == 1|| frame.getModel().returnStatus(i,j) == 6 || frame.getModel().returnStatus(i,j) == 5) && !(i==r && j==c))
                    {
                        count++;
                    }
                }
            }
        } else {
            //The standard way
        
            for (int i = r-1; i<= r+1; i++)
            {
                for (int j = c - 1; j<= c+1; j++)
                {
                    if ((frame.getModel().returnStatus(i,j) == 1|| frame.getModel().returnStatus(i,j) == 6 || frame.getModel().returnStatus(i,j) == 5) && !(i==r && j==c))
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}