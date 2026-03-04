import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MinesweeperFrame extends JFrame{

    public MinesweeperModel model;
    public MinesweeperPanelBorder border;
    private MinesweeperFrame frame;
    int SIZE;
    

    //Sets up the frame for the user to play in and adds the panels
    public MinesweeperFrame(int size){
        setTitle("Minesweeper");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.height+100,screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SIZE = size;
        frame = this;

        model = new MinesweeperModel(SIZE);
        border = new MinesweeperPanelBorder(this, SIZE);
        
        add(border);

        JMenuBar dropdown = new JMenuBar();
        JMenu options = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenu create = new JMenu("New");
        JMenuItem easy = new JMenuItem("Easy");
        JMenuItem intermediate = new JMenuItem("Intermediate");
        JMenuItem hard = new JMenuItem("Hard");
        JMenuItem quit = new JMenuItem("Quit");

        save.addActionListener(new MenuActionListener());
        load.addActionListener(new MenuActionListener());
        easy.addActionListener(new MenuActionListener());
        intermediate.addActionListener(new MenuActionListener());
        hard.addActionListener(new MenuActionListener());
        quit.addActionListener(new MenuActionListener());

        create.add(easy);
        create.add(intermediate);
        create.add(hard);
        
        options.add(save);
        options.add(load);
        options.add(create);
        options.add(quit);
        
        dropdown.add(options);
        
        this.setJMenuBar(dropdown);

        setVisible(true);
    }

    public class MenuActionListener implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            if (command.equals("Quit")) {
                System.exit(0);
            }
            if (command.equals("Easy")) {
                createNew(9);
            }
            if (command.equals("Intermediate")) {
                createNew(16);
            }
            if (command.equals("Hard")) {
                createNew(20);
            }
            if (command.equals("Save"))
            {
                
                MinesweeperFileChooser j = new MinesweeperFileChooser(frame);
                j.saveModelWithFileChooser();
            }
            if (command.equals("Load"))
            {
                MinesweeperFileChooser j = new MinesweeperFileChooser(frame);
                
                getContentPane().removeAll();
                
                MinesweeperModel testModel = j.loadModelWithFileChooser();
                // Re-initialize model and border with new size
                
                if(testModel != null)
                {
                    model = testModel;
                    border = new MinesweeperPanelBorder(frame, model.SIZE);
                }
                
                
                
                // Add updated border to the content pane
                getContentPane().add(border);

                // Revalidate and repaint to refresh the frame
                revalidate();
                repaint();
            }
        }

        public void createNew (int size)
        {
            
            getContentPane().removeAll();
                
            // Re-initialize model and border with new size
            model = new MinesweeperModel(size);
            border = new MinesweeperPanelBorder(frame, size);
            
            // Add updated border to the content pane
            getContentPane().add(border);

            // Revalidate and repaint to refresh the frame
            revalidate();
            repaint();
        }
    }

    
    //The Model that can be accessed by all classes that take in the frame
    public MinesweeperModel getModel()
    {
        return model;
    }
    
    //The external border that can be accessed by all classes that take in the frame
    public MinesweeperPanelBorder getBorder()
    {
        return border;
    }
}
