import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A simple panel with a space invaders "game" in it. This is just to
 * demonstrate the bare minimum of stuff than can be done drawing on
 * a panel. This is by no means very good code.
 * 
 */
public class StructuresOfBirb extends JPanel implements ActionListener {
    private static final long serialVersionUID = 6260582674762246325L;

    private boolean gameOver;
    private Timer timer;
    private List<Rectangle> pipes;
    private Rectangle bird;

    public StructuresOfBirb(final int width, final int height) {
        this.gameOver = false;
        this.pipes = new ArrayList<Rectangle>();

        for (int i = 0; i < 1; ++i) {
            addPipes(width, height);
        }

        this.bird = new Rectangle(500, width/2-15, 30, 20);

        this.timer = new Timer(20, this);
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint(g);
    }

    private void addPipes(final int width, final int height) {
        int x = 400;
        int a = ThreadLocalRandom.current().nextInt(110, 140);

        int y = 120 + a;
        int y2 = -180 + a;
        pipes.add(new Rectangle(x, y, 20, 230));
        pipes.add(new Rectangle(x, y2, 20, 180));
    }

    /**
     * @param g the graphics to paint on
     */
    private void repaint(Graphics g) {
        final Dimension d = this.getSize();
        // fill the background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, d.width, d.height);

        // draw the pipes
        for (Rectangle pipe : pipes) {
            g.setColor(Color.green);
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }
    }

    public void actionPerformed(ActionEvent e) {
        final List<Rectangle> toRemove = new ArrayList<Rectangle>();

        for (Rectangle pipe : pipes) {
            pipe.translate(-2, 0);
            if (pipe.x + pipe.width < 0) {
                //this list adds removed pipes 
                toRemove.add(pipe);
            }
            //if bird and pipe intersect gameOver is true
            if (pipe.intersects(bird)) {
                gameOver = true;
            }
        }

        pipes.removeAll(toRemove);
        // respawn new pipes when previous has passed the bird
        for (int i = 0; i < toRemove.size(); ++i) {
            Dimension d = getSize();
            addPipes(d.width, d.height);
        }
        this.repaint();
    }
}