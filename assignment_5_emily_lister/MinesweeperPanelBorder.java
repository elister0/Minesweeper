import java.awt.*;
import javax.swing.*;

//The panel border that allows for there to be a display of the amount of mines remaining
// and the win status if the game is lost or won
// The MinesweeperPanel is placed in the center of the PanelBorder
public class MinesweeperPanelBorder extends JPanel {

    //Takes in the frame so that it can ask the frame questions about the model and panel
    MinesweeperFrame frame;
    JLabel minesRemaining;
    int SIZE;

    //The constructor call that sets everything up and takes in the frame to store
    public MinesweeperPanelBorder (MinesweeperFrame f, int size)
    {
        SIZE = size;
        setLayout(new BorderLayout());
        
        frame = f;
        //Creates and adds the game display panel to the center of the outside panel
        MinesweeperPanel panel1 = new MinesweeperPanel(f, SIZE);
        add(panel1, BorderLayout.CENTER);
        //Creates the mines remaining label at the top of the screen
        minesRemaining = new JLabel("Remaining Mines: " + frame.getModel().numMines);
        minesRemaining.setFont(new Font("Serif", Font.PLAIN, 20));
        add(minesRemaining, BorderLayout.PAGE_START);

        

    }

    //Updates the mine count depending on how many flags the user has placed
    public void updateMineCount(){
        int MineCount = frame.getModel().numMines;
        //Runs through each tile and subtracts the flags from the total (40)
        for (int i = 0; i< SIZE; i++)
        {
            for (int j = 0; j<SIZE; j++)
            {
                int squareStatus = frame.getModel().returnStatus(i,j);
                if(squareStatus == 6 || squareStatus == 7 || squareStatus == 8)
                {
                    MineCount--;
                }
            }
        }

        minesRemaining.setText("Mines Remaining: " + MineCount);
    }

    //If the game is lost, displays to the user the proper information 
    //(Shows "You Lost" in the bottom of the screen)
    public void YouLose ()
    {
        JLabel loseMessage = new JLabel("You Lost!");
        loseMessage.setFont(new Font("Serif", Font.PLAIN, 40));
        loseMessage.setForeground(Color.RED);
        add(loseMessage, BorderLayout.PAGE_END);
    }

    //If the game is won, displays to the user the proper information 
    //(Shows "You Win" in the bottom of the screen)
    public void YouWin ()
    {
        JLabel winMessage = new JLabel("You Win!");
        winMessage.setFont(new Font("Serif", Font.PLAIN, 40));
        winMessage.setForeground(Color.GREEN);
        add(winMessage, BorderLayout.PAGE_END);
    }
}