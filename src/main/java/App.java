import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import thegame.GameStructure;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.ImageIcon;


public class App {
    public static void main(String[] args) {
        JFrame main = new JFrame("Jumpy Birb");

        String difficulty = JOptionPane.showInputDialog(main, "Enter difficulty: easy, normal, hard");
        ImageIcon backgroundImage = new ImageIcon("images/enviromentBirb.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(backgroundImage);
        
        GameStructure gs = new GameStructure(410, 420, difficulty, backgroundImage, main);

        main.setSize(410, 420);
        main.setResizable(false);
        main.add(gs);
        main.addKeyListener(gs);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setVisible(true);
    }
}
