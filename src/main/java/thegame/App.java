package thegame;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import thegame.GameStructure;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a very small "game" just to show the absolute basics of
 * how to draw on a surface in a frame using Swing/AWT.
 * 
 */
public class App {

	public static void main(String[] args) throws IOException {
        JFrame main = new JFrame("Flappy Birb");
        ImageIcon ic = new ImageIcon("images/backgroundimagebirb.png");
        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(ic);

        String difficulty = JOptionPane.showInputDialog(main, "Enter difficulty: easy, normal, hard");
        
        GameStructure gs = new GameStructure(410, 410, difficulty, ic);
        
        main.setSize(410, 410);
        main.setResizable(true);
        main.add(gs);
        main.addKeyListener(gs);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setVisible(true);
        main.toFront();
    }
}