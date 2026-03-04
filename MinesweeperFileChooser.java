import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

public class MinesweeperFileChooser {
    MinesweeperFrame frame;
    public MinesweeperFileChooser(MinesweeperFrame f)
    {
        frame = f;
    }
    public void saveModelWithFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Set the file chooser to only accept .dat files by default
        fileChooser.setFileFilter(new FileNameExtensionFilter("Minesweeper Save Files (*.dat)", "dat"));
        
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Ensure the file has the correct .dat extension
            if (!filePath.endsWith(".dat")) {
                filePath += ".dat";
            }
    

            // Save the model to the chosen file
            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

                objectOut.writeObject(frame.getModel());
                

            } catch (IOException e) {
                System.out.println("Error saving Minesweeper model: " + e.getMessage());
            }
        }
    }

    public MinesweeperModel loadModelWithFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Set the file chooser to only accept .dat files
        fileChooser.setFileFilter(new FileNameExtensionFilter("Minesweeper Save Files (*.dat)", "dat"));
        
        // Show open dialog
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file path
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Load the model from the file
            try (FileInputStream fileIn = new FileInputStream(filePath);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                // Read the object from the file and cast it to MinesweeperModel
                MinesweeperModel model = (MinesweeperModel) objectIn.readObject();
                
                return model;

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading Minesweeper model: " + e.getMessage());
            }
        } 

        return null; 
    }
}
