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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
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
	    private Rectangle test, upp, down;
	    private Rectangle Birb;
		public int ticks, yFall, score;
		public int highScore;
		private String difficulty;
		private int speed;

	    public GameStructure(final int width, final int height, String difficulty) {
	        this.gameOver = false;
	        this.started = false;
	        this.pipes = new ArrayList<>();
	        this.score = 0;	// making the round score == 0
	        this.difficulty = difficulty;

	        for (int i = 0; i < 1; ++i) {
	            addPipes(width, height);
	        }

	        this.Birb = new Rectangle(20, width/2-15, 30, 20);
	        test = new Rectangle(20, 1, 10, 20);
	        upp = new Rectangle(0, -10, 400, 5);
            down = new Rectangle(0, 420, 400, 5);
	        
            
	        this.timer = new Timer(20, this);
	        this.timer.start();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        try {
				repaint(g);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    private void addPipes(final int width, final int height) {
	        int a = ThreadLocalRandom.current().nextInt(40, 220);
	        int y2 = 0;
	    	int x = 400;
	        int y = 300;
	        if(this.difficulty.toLowerCase().equals("easy"))
	        {
	        	pipes.add(new Rectangle(x, y2, 20, a));
	        	pipes.add(new Rectangle(x, a+130, 20, 400));
	        }
	        else {
	        	
	        	pipes.add(new Rectangle(x, y2, 20, a));
	        	pipes.add(new Rectangle(x, a+100, 20, 400));
	        }
	
	        
	    }

	    private void repaint(Graphics g) throws IOException {
	        final Dimension d = this.getSize();

	        
	        if (gameOver) {
        	    
	        	String a = "src/main/java/thegame/test.txt";
	        	File le = new File(a);
	        	BufferedReader reader = new BufferedReader(new FileReader(le));
	        	String line = reader.readLine();
	        	reader.close();
	        	BufferedWriter writer;

	     	            	
	            	g.setColor(Color.red);
	            	g.fillRect(0, 0, d.width, d.height);    
	            	g.setColor(Color.black);
	            	g.setFont(new Font("Arial", Font.BOLD, 65));
	            	g.drawString("Game over!", 15, 120);
					
	            	System.out.println("kommer hit0");
	            	
	            	
	            	
	            	
	            	int test;
	            	
	            	g.setFont(new Font("Arial", Font.BOLD, 50));
	            	if(this.difficulty.toLowerCase().equals("easy")) {
	            		g.drawString("Score: " + String.valueOf(getScore()/10), 90, 200); // drawning final score of the round
	            		
	            			if((test = Integer.parseInt(line)) < getScore()/10)
	            			{
	            				writer = new BufferedWriter(new FileWriter(le));
	            				String testar =  String.valueOf(getScore()/10);
	            				writer.write(testar);
	            				writer.close();
	            			}
	            	}

	            	else if(this.difficulty.toLowerCase().equals("normal")) {
	            		g.drawString("Score: " + String.valueOf(getScore()/7), 90, 200); // drawning final score of the round
	            		System.out.println("kommer hit1");
	            		
	
	            		
	            		System.out.println(line);
	            		
	            		test = Integer.parseInt(line);
	            			
	            			if(test < getScore()/7)
	            			{
	            				writer = new BufferedWriter(new FileWriter(le));
	            				String testar =  String.valueOf(getScore()/7);
	            				writer.write(testar);
	            				writer.close();
	            			}
	            	}

	            	else if(this.difficulty.toLowerCase().equals("hard")) {
	            		g.drawString("Score: " + String.valueOf(getScore()/5), 90, 200); // drawning final score of the round
	            		
	            		
	            			if((test = Integer.parseInt(line)) < getScore()/5)
	            			{
	            				writer = new BufferedWriter(new FileWriter(le));
	            				String testar =  String.valueOf(getScore()/5);
	            				writer.write(testar);
	            				writer.close();
	            			}
	            		
	            	}


	            	
	            	reader = new BufferedReader(new FileReader(le));
	            	line = reader.readLine();
	            	reader.close();
	            	
	            	System.out.println("här: " + line);
	            	
	            	g.setFont(new Font("Arial", Font.BOLD, 50));
	            	g.drawString("Highscore: " + line, 45, 275); // drawning highscore
	            
	            
	            
	            g.setFont(new Font("Arial", Font.BOLD, 30));
	            g.drawString("Play again: r ", 90, 325); // drawning highscore
	                  
	          
	            
	            return;
	        }

	
	        g.setColor(Color.cyan);
	        g.fillRect(0, 0, d.width, d.height);
	        
	        g.setColor(Color.black);
	        g.setFont(new Font("Arial", Font.BOLD, 50));
	        
	        
	        if(this.difficulty.toLowerCase().equals("easy")) {
	        	 g.drawString(String.valueOf(getScore()/10), 175, 100); // updating score
            }
            else if(this.difficulty.toLowerCase().equals("normal")) {
            	 g.drawString(String.valueOf(getScore()/7), 175, 100); // updating score
            }
            else if(this.difficulty.toLowerCase().equals("hard")) {
            	 g.drawString(String.valueOf(getScore()/5), 175, 100); // updating score
            }
	        
	        

	
	        for (Rectangle pipe : pipes) {
	            
	        	g.setColor(Color.green);
	            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
 
	        }

	
	        
	        
	        g.setColor(Color.black);
	        g.fillRect(Birb.x, Birb.y, Birb.width, Birb.height);
	        
	       
	    }
	    	    
	    

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (gameOver) {
	            timer.stop();
	            return;
	        }
	        
	       
            if(this.difficulty.toLowerCase().equals("easy")) {
                speed = 3;
            }
            else if(this.difficulty.toLowerCase().equals("normal")) {
                speed = 4;
            }
            else if(this.difficulty.toLowerCase().equals("hard")) {
                speed = 6;
            }
            else {
                speed = 4;
            }
	        
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
					yFall += 1;
				}

	        	
	        	Birb.y += yFall;
	        	
	    
	        }
	        
	        if(test.intersects(pipes.get(0)))
	        {
	        	setScore();
	        }
	        
	        if(Birb.intersects(upp) || Birb.intersects(down))
	        {
	        	gameOver = true;
	        }
	        
	  
	        
	        final List<Rectangle> toRemove = new ArrayList<>();

	        for (Rectangle pipe : pipes) {
	            if (pipe.x + pipe.width < 0) {
	                toRemove.add(pipe);
	            }
	            
	            if (pipe.intersects(Birb)) {
	                gameOver = true;
	            }
	            
	        }

	        pipes.removeAll(toRemove);

	        for (int i = 0; i < toRemove.size()/2  ; ++i) {
	            
	            if(i==0) {
	            	Dimension d = getSize();
	            	addPipes(d.width, d.height);
	            	
	            }
	        }
	        
	        this.repaint();
	    }

	    /**
	     * If the player presses SPACE then it calls the function jump
	     */
	    @Override
	    public void keyReleased(KeyEvent e) {

	     
	    }
	    
	    /**
	     * 
	     * This controls the jump effect with the interact function above.
	     */
	    public void jump()
		{
	    	
	    	if (!started)
            {
                if (yFall > 0)
                {
                    yFall = 0;
                }

                yFall -= 9;
                started = true;
            }
            else if (!gameOver)
            {
            	yFall = 0;
                if (yFall > 0)
                {
                    yFall = 0;
                }

                yFall -= 9;
            }
		}
		
	    

	    public void setScore() {
	    	score++;
	    }
	    
	    public int getScore() {
			return score;
		}


		
		
		@Override
	    public void keyTyped(KeyEvent e) {
	        // do nothing
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	    	
	    	final int kc = e.getKeyCode();

            if (kc == KeyEvent.VK_SPACE) {

                    jump();
            }
            if(gameOver == true)
            {
            	final int rbutton = e.getKeyCode();
            	if(rbutton == KeyEvent.VK_R) {
            		
            		speed = 4;
            		yFall = 0;
            		this.gameOver = false;
            		this.started = false;
            		this.pipes = new ArrayList<>();
            		this.score = 0;    // making the round score == 0
            		
            		for (int i = 0; i < 1; ++i) {
            			addPipes(400, 400);
            		}
            		
            		this.Birb = new Rectangle(20, 400/2-15, 30, 20);
            		test = new Rectangle(20, 1, 10, 80);
            		
            		this.timer = new Timer(20, this);
            		this.timer.start();
            		
            	}
            }
            
            

	    }
	}
	
