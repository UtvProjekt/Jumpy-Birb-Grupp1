import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import thegame.GameStructure;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.ImageIcon;

/**
 * This is a very small "game" just to show the absolute basics of
 * how to draw on a surface in a frame using Swing/AWT.
 * 
 */
public class App {
    public static void main(String[] args) {
        JFrame main = new JFrame("Jumpy Birb");

        String difficulty = JOptionPane.showInputDialog(main, "Enter difficulty: easy, normal, hard");
        ImageIcon backgroundImage = new ImageIcon("images/enviromentBirb.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(backgroundImage);
        
        GameStructure gs = new GameStructure(410, 420, difficulty, backgroundImage);

        main.setSize(410, 420);
        main.setResizable(false);
        main.add(gs);
        main.addKeyListener(gs);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setVisible(true);
    }
}