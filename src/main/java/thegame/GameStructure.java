package thegame;
<<<<<<< HEAD

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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameStructure extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 6260582674762246325L;

	private boolean gameOver, started;
	private Timer timer;
	private List<Rectangle> listOfPipes;
	private Rectangle scoreRectangle, upperBorder, bottomBorder;
	private Rectangle Birb;
	private int ticks, yFall, score;
	private String difficulty;
	private int speed;
	private boolean showTopTenHighscores;
	private Map<Integer, String> mapForHighscores;
	private ImageIcon image;
	private JFrame mainFrame;
	private boolean keyHold;

	/**
	 * GameStructure is an constructor that is setting the starting values
	 * and starting the game!
	 * 
	 * 
	 * @param width width of game area
	 * @param height height of game area
	 * @param difficulty type of difficulty
	 * @param image image for background
	 * @param mainFrame application window
	 */
	public GameStructure(final int width, final int height, String difficulty, ImageIcon image, JFrame mainFrame) {
		this.gameOver = false;
		this.started = false;
		this.listOfPipes = new ArrayList<>();
		this.score = 0;
		this.difficulty = difficulty;
		this.image = image;
		this.mainFrame = mainFrame;
		showTopTenHighscores = false;
		this.keyHold = true;

		for (int i = 0; i < 1; ++i) {
			addlistOfPipes();
		}

		this.Birb = new Rectangle(20, width / 2 - 15, 30, 20);
		scoreRectangle = new Rectangle(20, 1, 10, 20);
		upperBorder = new Rectangle(0, -10, 400, 5);
		bottomBorder = new Rectangle(0, 420, 400, 5);

		this.timer = new Timer(20, this);
		this.timer.start();
	}

	/**
	 * method that paints the game.
	 * 
	 * @param g containing graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.paintIcon(this, g, 0, 0);
		repaint(g);
		
	}

	/**
	 * A method that creates new pipes that the birb interacts with.
	 * 
	 * 
	 */
	private void addlistOfPipes() {
		int randomHeightNumber = ThreadLocalRandom.current().nextInt(40, 220);
		int y = 0;
		int x = 400;
		if (this.difficulty.toLowerCase().equals("easy")) {
			listOfPipes.add(new Rectangle(x, y, 20, randomHeightNumber));
			listOfPipes.add(new Rectangle(x, randomHeightNumber + 130, 20, 400));
		} else {

			listOfPipes.add(new Rectangle(x, y, 20, randomHeightNumber));
			listOfPipes.add(new Rectangle(x, randomHeightNumber + 100, 20, 400));
		}

	}

	/**
	 * repaint is a method that continuously paints the rectangles, scores and gameover screen
	 * 
	 * @param g g contains graphics from the game
	 */
	private void repaint(Graphics g){
		final Dimension d = this.getSize();

		if (gameOver) {

			if (showTopTenHighscores) {

				g.setColor(Color.red);
				g.fillRect(0, 0, 400, 400);
				g.setColor(Color.black);

				int numberRanking = 1;
				int moveTheDifferentScores = 40;
				for (Map.Entry<Integer, String> entry : mapForHighscores.entrySet()) {

					g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString(numberRanking + ". " + entry.getKey() + " - " + entry.getValue(), 35,
							moveTheDifferentScores);
					numberRanking++;
					moveTheDifferentScores += 30;

				}

				g.setFont(new Font("Arial", Font.BOLD, 20));
				g.drawString("Play again: 'space' ", 100, 345);
				showTopTenHighscores = false;
				return;

			}

			g.setColor(Color.red);
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, 65));
			g.drawString("Game over!", 15, 120);

			getHighscore();

			g.setFont(new Font("Arial", Font.BOLD, 50));
			if (this.difficulty.toLowerCase().equals("easy")) {
				g.drawString("Score: " + String.valueOf(getScore("easy")), 90, 200); // drawing final score of the
																						// round
			} else if (this.difficulty.toLowerCase().equals("normal")) {
				g.drawString("Score: " + String.valueOf(getScore("normal")), 90, 200); // drawing final score of the
																						// round
			} else if (this.difficulty.toLowerCase().equals("hard")) {
				g.drawString("Score: " + String.valueOf(getScore("hard")), 90, 200); // drawing final score of the
																						// round

			}

			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Play again: 'space' ", 100, 325);
			g.drawString("Top 10 highscore: 'H' ", 100, 345);
			

			return;
		}

		

		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 50));

		if (this.difficulty.toLowerCase().equals("easy")) {
			g.drawString(String.valueOf(getScore("easy")), 175, 100); // updating score
		} else if (this.difficulty.toLowerCase().equals("normal")) {
			g.drawString(String.valueOf(getScore("normal")), 175, 100); // updating score
		} else if (this.difficulty.toLowerCase().equals("hard")) {
			g.drawString(String.valueOf(getScore("hard")), 175, 100); // updating score
		}

		for (Rectangle pipe : listOfPipes) {

			g.setColor(Color.green);
			g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);

		}

		g.setColor(Color.yellow);
		g.fillRect(Birb.x, Birb.y, Birb.width, Birb.height);

		g.setColor(Color.yellow);
		g.fillRect(Birb.x + 20, Birb.y - 6, Birb.width / 2, Birb.height - 7);

		g.setColor(Color.orange);
		g.fillRect(Birb.x + 35, Birb.y - 3, Birb.width / 4, Birb.height / 3);

		g.setColor(Color.black);
		g.fillRect(Birb.x + 25, Birb.y - 4, Birb.width / 5, Birb.height / 4);

		g.setColor(Color.black);
		g.fillRect(Birb.x + 5, Birb.y + 20, Birb.width / 7, Birb.height / 3);

		g.setColor(Color.black);
		g.fillRect(Birb.x + 10, Birb.y + 20, Birb.width / 7, Birb.height / 3);

		if (yFall == 0) {
			g.setColor(new Color(247, 176, 60));
			g.fillRect(Birb.x + 2, Birb.y + 5, Birb.width / 2, Birb.height - 15);
		}

		if (yFall < 0 && yFall > -2) {
			g.setColor(new Color(247, 176, 60));
			g.fillRect(Birb.x + 2, Birb.y + 5, Birb.width / 2, Birb.height - 10); // Going up animation
		}

		if (yFall < -2) {
			g.setColor(new Color(247, 176, 60));
			g.fillRect(Birb.x + 2, Birb.y + 5, Birb.width / 2, Birb.height - 5);
		}

		if (yFall > 0 && yFall < 2) {
			g.setColor(new Color(247, 176, 60));
			g.fillRect(Birb.x + 2, Birb.y + 3, Birb.width / 2, Birb.height - 10); // Falling animation
		}

		if (yFall > 2) {
			g.setColor(new Color(247, 176, 60));
			g.fillRect(Birb.x + 2, Birb.y - 6, Birb.width / 2, Birb.height - 5);
		}

	}

	/**
	 * checkIfNewHighscore is a method that keeps track if the users latest score is an highscore or not
	 * 
	 * 
	 * @param difficultyDivideNumber contains which difficulty the game runs in
	 */
	public void checkIfNewHighscore(String difficultyDivideNumber) {

		int tempNumberForCheckingMap = 0;
		for (Map.Entry<Integer, String> entry : mapForHighscores.entrySet()) {

			File nameOfTheFile = new File("highscore/highscore.txt");
			if (entry.getKey() == getScore(difficulty) && nameOfTheFile.length() != 0) {
				String highscoreUserName = JOptionPane.showInputDialog(mainFrame,
						"New top 10 Highscore! Enter username: ");
				mapForHighscores.put(getScore(difficultyDivideNumber), entry.getValue() + ", " + highscoreUserName);
				tempNumberForCheckingMap++;
				break;
			} else if (entry.getKey() < getScore(difficultyDivideNumber)) {

				setToptenHighscoreUsername(difficultyDivideNumber);
				tempNumberForCheckingMap++;
				break;


		}
		}   
		if (mapForHighscores.size() < 10 && mapForHighscores.size() > 0 && tempNumberForCheckingMap != 1) {
			
			setToptenHighscoreUsername(difficultyDivideNumber);
		}
		setHigscore();

	}

	/**
	 * setToptenHighscoreUsername is a method that makes the player type in a name upon new highscore
	 * 
	 * @param difficultyDivideNumber contains which difficulty the game runs in
	 */
	public void setToptenHighscoreUsername(String difficultyDivideNumber) {

		String highscoreUserName = JOptionPane.showInputDialog(mainFrame, "New top 10 Highscore! Enter username: ");
		mapForHighscores.put(getScore(difficultyDivideNumber), highscoreUserName);

	}

	/**
	 * setHighscore is a method that writes the list of highscores into a text file
	 */
	public void setHigscore() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore/highscore.txt"));) {

			while (mapForHighscores.size() > 10) {
				mapForHighscores.remove(((TreeMap<Integer, String>) mapForHighscores).lastKey());
			}

			for (Map.Entry<Integer, String> entry : mapForHighscores.entrySet()) {
				writer.write(entry.getKey() + " - " + entry.getValue() + "\n");

			}

		}catch (IOException ex) {
			System.err.println("Ett fel intr√§ffade!" + ex.getMessage());
		}

	}

	/**
	 * getHighscore is a method that reads the text file containing the 10 highest scores and splitting
	 * and adding them to a map.
	 */
	public void getHighscore() {

		String readHighscoreLine = "";
		try (BufferedReader reader = new BufferedReader(new FileReader("highscore/highscore.txt"));) {
			mapForHighscores = new TreeMap<>(Collections.reverseOrder());

			while ((readHighscoreLine = reader.readLine()) != null) {
				String[] tempArrayForSplittingString = new String[2];
				tempArrayForSplittingString = readHighscoreLine.split(" - ");

				mapForHighscores.put(Integer.parseInt(tempArrayForSplittingString[0]), tempArrayForSplittingString[1]);

			}

		} catch (FileNotFoundException ex) {
			System.err.println("Kunde inte hitta filen: " + ex.getMessage());
		}catch (IOException ex) {
			System.err.println("Ett fel intr√§ffade!" + ex.getMessage());
		}

	}

	/**
	 * actionPerformed is controlling the state of the game
	 * 
	 * @param e contains an interaction with the user
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (gameOver) {
			timer.stop();
			File nameOfTheFile = new File("highscore/highscore.txt");
			if (nameOfTheFile.length() == 0) {
				setToptenHighscoreUsername(difficulty);
			} else if (this.difficulty.toLowerCase().equals("easy")) {
				checkIfNewHighscore("easy");

			} else if (this.difficulty.toLowerCase().equals("normal")) {
				checkIfNewHighscore("normal");
			} else if (this.difficulty.toLowerCase().equals("hard")) {
				checkIfNewHighscore("hard");
			}

			setHigscore();

			return;
		}

		checkDifficulty();
		
		ticks++;

		if (started) {
			for (int i = 0; i < listOfPipes.size(); i++)
			{
				Rectangle column = listOfPipes.get(i);
				column.x -= speed;
			}

			if (ticks % 2 == 0 && yFall < 15) {
				yFall += 1;
			}

			Birb.y += yFall;

		}

		if (scoreRectangle.intersects(listOfPipes.get(0))) {
			setScore();
		}

		if (Birb.intersects(upperBorder) || Birb.intersects(bottomBorder)) {
			gameOver = true;
		}

		final List<Rectangle> toRemove = new ArrayList<>();

		for (Rectangle pipe : listOfPipes) {
			if (pipe.x + pipe.width < 0) {
				toRemove.add(pipe);
			}

			if (pipe.intersects(Birb)) {
				gameOver = true;
			}

		}

		listOfPipes.removeAll(toRemove);

		for (int i = 0; i < toRemove.size() / 2; ++i) {

			if (i == 0) {
				addlistOfPipes();

			}
		}

		this.repaint();
	}

	/**
	 * keyReleased is a function that runs upon the release of a key
	 * 
	 * @param e contains a users interaction with keyboard
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (gameOver == true) {
			final int spacebutton = e.getKeyCode();

			if (spacebutton == KeyEvent.VK_SPACE) {
				
				speed = 4;
				yFall = 0;
				this.gameOver = false;
				this.started = false;
				this.listOfPipes = new ArrayList<>();
				this.score = 0;

				for (int i = 0; i < 1; ++i) {
					addlistOfPipes();
				}

				this.Birb = new Rectangle(20, 400 / 2 - 15, 30, 20);
				scoreRectangle = new Rectangle(20, 1, 10, 80);

				this.timer = new Timer(20, this);
				this.timer.start();

			}
		}
		
			int kc = e.getKeyCode();
		if (kc == KeyEvent.VK_SPACE) {
			keyHold = true;
		}
		
	}
	
	/**
	 * testingIflistOfPipesCreates is a method that helps with testing
	 * 
	 * @return returns the number of objects in listofpipes
	 */
	public int testingIflistOfPipesCreates() {
        this.listOfPipes = new ArrayList<>();
            addlistOfPipes();
            return this.listOfPipes.size();
    }

	/**
	 * checkDifficulty is a function that returns the speed depending on what difficulty
	 * 
	 * @return returns the speed
	 */
    public int checkDifficulty() {
        if (this.difficulty.toLowerCase().equals("easy")) {
            return speed = 3;
        } else if (this.difficulty.toLowerCase().equals("normal")) {
            return speed = 4;
        } else if (this.difficulty.toLowerCase().equals("hard")) {
            return speed = 6;
        } else {
            this.difficulty = "normal";
            return speed = 4;
        }
    }
	
    /**
     * @return returns yFall
     */
    public int getyFall() {
    	return yFall;
    }

	/**
	 * jump is a method that controls the y-movement of the birb
	 * 
	 */
	public void jump() {

		if (!started) {
			if (yFall > 0) {
				yFall = 0;
			}

			yFall -= 9;
			started = true;
		} else if (!gameOver) {
			yFall = 0;
			if (yFall > 0) {
				yFall = 0;
			}

			yFall -= 9;
		}
	}

	public void setScore() {
		score++;
	}

	/**
	 * getScore, a method that returns score
	 * 
	 * @param difficulty contains the difficulty the game is running in
	 * @return returns the score depending on the difficulty
	 */
	public int getScore(String difficulty) {

		if (difficulty.equals("easy")) {
			return score / 10;
		} else if (difficulty.equals("normal")) {
			return score / 7;
		} else {
			return score / 5;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	/**
	 * keyPressed is a function that runs upon the press of a key
	 * 
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		final int kc = e.getKeyCode();

		if (kc == KeyEvent.VK_SPACE && keyHold == true) {
			keyHold = false;
			jump();
		}
		if (gameOver && kc == KeyEvent.VK_H) {

			showTopTenHighscores = true;
			repaint();

		}

	}
}
=======
	  

	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Image;
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

import javax.swing.ImageIcon;
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
		private ImageIcon image;

	    public GameStructure(final int width, final int height, String difficulty, ImageIcon image) {
	        this.gameOver = false;
	        this.started = false;
	        this.pipes = new ArrayList<>();
	        this.score = 0;	// making the round score == 0
	        this.difficulty = difficulty;
	        this.image = image;

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
	        image.paintIcon(this, g, 0, 0);
	        try {
				repaint(g);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    private void addPipes(final int width, final int height) {
	        int a = ThreadLocalRandom.current().nextInt(40, 220);
	        int y = 0;
	    	int x = 400;
	        if(this.difficulty.toLowerCase().equals("easy"))
	        {
	        	pipes.add(new Rectangle(x, y, 20, a));
	        	pipes.add(new Rectangle(x, a+130, 20, 400));
	        }
	        else {	
	        	pipes.add(new Rectangle(x, y, 20, a));
	        	pipes.add(new Rectangle(x, a+100, 20, 400));
	        }
	    }

	    private void repaint(Graphics g) throws IOException {
	        final Dimension d = this.getSize();

	        if (gameOver) {
	            	g.setColor(Color.red);
	            	g.fillRect(0, 0, d.width, d.height);    
	            	g.setColor(Color.black);
	            	g.setFont(new Font("Arial", Font.BOLD, 65));
	            	g.drawString("Game over!", 15, 120);
					
	            	System.out.println("kommer hit0");
	                   	
	            	String line = getHighscore();
	            	int test;
	            	
	            	g.setFont(new Font("Arial", Font.BOLD, 50));
	            	if(this.difficulty.toLowerCase().equals("easy")) {
	            		g.drawString("Score: " + String.valueOf(getScore()/10), 90, 200); // drawning final score of the round
	            		
	            			if((test = Integer.parseInt(line)) < getScore()/10)
	            			{
	            				String testar =  String.valueOf(getScore()/10);
	            				setHigscore(testar);
	            			}
	            	}

	            	else if(this.difficulty.toLowerCase().equals("normal")) {
	            		g.drawString("Score: " + String.valueOf(getScore()/7), 90, 200); // drawning final score of the round
	            		System.out.println("kommer hit1");
	            		
	            		System.out.println(line);
	            		
	            		test = Integer.parseInt(line);
	            			
	            			if(test < getScore()/7)
	            			{
	            				String testar =  String.valueOf(getScore()/7);
	            				setHigscore(testar);
	            			}
	            	}

	            	else if(this.difficulty.toLowerCase().equals("hard")) {
	            		g.drawString("Score: " + String.valueOf(getScore()/5), 90, 200); // drawning final score of the round
	            			            		
	            			if((test = Integer.parseInt(line)) < getScore()/5)
	            			{
	            				String testar =  String.valueOf(getScore()/5);
	            				setHigscore(testar);
	            			}
	            		
	            	}

	            	line = getHighscore();

	            	System.out.println("h‰r: " + line);
	            	
	            	g.setFont(new Font("Arial", Font.BOLD, 50));
	            	g.drawString("Highscore: " + line, 45, 275); // drawning highscore
	            
	            g.setFont(new Font("Arial", Font.BOLD, 30));
	            g.drawString("Play again: r ", 90, 325); // drawning highscore
	                  	            
	            return;
	        }
	        
	        image.paintIcon(this, g, 0, 0);
	        
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
	    
	    public void setHigscore(String score) {
	    	String a = "highscore/highscore.txt";
	    	File le = new File(a);
	    	try(BufferedWriter writer = new BufferedWriter(new FileWriter(le));)
	    	{
				writer.write(score);

			} 
	    	catch(Exception e)
	    	{
				
			}
	    }
	    
	    public String getHighscore() {
	    	
	    	String a = "highscore/highscore.txt";
	    	File le = new File(a);
	    	String line = "";
	    	try(BufferedReader reader = new BufferedReader(new FileReader(le));) {
				
	    		line = reader.readLine();
			} 
	    	catch (Exception e) {
				
			}
			return line;
	    	
        	
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
	        
	        ticks++; // gˆr att det bli varannan gÂng
	        
	        if (started)
	        {
	        	for (int i = 0; i < pipes.size(); i++) // best‰mmer hur snabbt positionen pÂ de grˆna pellarna ska fˆrflyttas
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
>>>>>>> 122095f3ccf23d927d878784589a6816096cd18e
