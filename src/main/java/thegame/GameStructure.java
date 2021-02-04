	package thegame;
	  
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Rectangle;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.concurrent.ThreadLocalRandom;


import javax.swing.JPanel;
	import javax.swing.Timer;

	public class GameStructure extends JPanel implements ActionListener, KeyListener {
	    private static final long serialVersionUID = 6260582674762246325L;

	    private boolean gameOver, started;
	    private Timer timer;
	    private List<Rectangle> pipes;
	    private Rectangle Birb;
		public int ticks, yFall;

	    public GameStructure(final int width, final int height) {
	        this.gameOver = false;
	        this.started = false;
	        this.pipes = new ArrayList<>();

	        for (int i = 0; i < 5; ++i) {
	            addPipes(width, height);
	        }

	        this.Birb = new Rectangle(20, width/2-15, 30, 20);

	        this.timer = new Timer(20, this);
	        this.timer.start();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        repaint(g);
	    }

	    private void addPipes(final int width, final int height) {
	        int a = ThreadLocalRandom.current().nextInt(110,140);
	        int y2 = -180 + a;
	    	int x = 400;
	        int y = 120 + a;
	        pipes.add(new Rectangle(x, y, 20, 230));
	        pipes.add(new Rectangle(x, y2, 20, 180));
	    }

	    private void repaint(Graphics g) {
	        final Dimension d = this.getSize();

	        if (gameOver) {
	            g.setColor(Color.red);
	            g.fillRect(0, 0, d.width, d.height);    
	            g.setColor(Color.black);
	            g.setFont(new Font("Arial", Font.BOLD, 48));
	            g.drawString("Game over!", 20, d.width/2-24);
	            return;
	        }

	        // fill the background
	        g.setColor(Color.cyan);
	        g.fillRect(0, 0, d.width, d.height);

	        // draw the aliens
	        for (Rectangle pipe : pipes) {
	            g.setColor(Color.red);
	            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
	        }

	        // draw the space ship
	        g.setColor(Color.black);
	        g.fillRect(Birb.x, Birb.y, Birb.width, Birb.height);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (gameOver) {
	            timer.stop();
	            return;
	        }
	        int speed = 2;
	        
	        ticks++; // gör att det bli varannan gång
	        
	        if (started)
	        {
	        	for (int i = 0; i < pipes.size(); i++) // bestämmer hur snabbt positionen på de gröna pellarna ska förflyttas
				{
					Rectangle column = pipes.get(i);

					column.x -= speed;
				}

	        	
				if (ticks % 2 == 0 && yFall < 15)
				{
					yFall += 2;
				}

	        	
	        	Birb.y += yFall;
	        	
	    
	        }
	        
	        final List<Rectangle> toRemove = new ArrayList<>();

	        for (Rectangle pipe : pipes) {
	        	pipe.translate(-1, 0);
	            if (pipe.x + pipe.width < 0) {
	                toRemove.add(pipe);
	            }

	            if (pipe.intersects(Birb)) {
	                gameOver = true;
	            }
	        }

	        pipes.removeAll(toRemove);

	        for (int i = 0; i < toRemove.size(); ++i) {
	            Dimension d = getSize();
	            addPipes(d.width, d.height);
	        }
	        

	        this.repaint();
	    }

	    /**
	     * If the player presses SPACE then it calls the function jump
	     */
	    @Override
	    public void keyReleased(KeyEvent e) {

	        final int kc = e.getKeyCode();

	        if (kc == KeyEvent.VK_SPACE) {
	            
	            	jump();
	        }

	    
	    }
	    
	    /**
	     * 
	     * This controls the jump effect with the interact function above.
	     */
	    public void jump()
		{
	    	
			if (!started)
			{
				started = true;
			}
			else if (!gameOver)
			{
				if (yFall > 0)
				{
					yFall = 0;
				}

				yFall -= 10;
			}
		}
	    

	    
	    @Override
	    public void keyTyped(KeyEvent e) {
	        // do nothing
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	        // do nothing
	    }
	}
	

