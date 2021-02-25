import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import thegame.GameStructure;

class TestGameStructure {

	GameStructure newStructureGame = new GameStructure(0, 0, "normal", null, null);
	
	@Test
	public void testSetAndGetScore() {
		int totalScore = 0;
		int exeptedScore = 1;
		for(int i = 0; i<10; i++) {
			newStructureGame.setScore();
		}
		totalScore = newStructureGame.getScore("easy");
		
		assertEquals(exeptedScore, totalScore);
	}
	
	@Test
	public void testFailSetAndGetScore() {
		int totalScore = 0;
		int exeptedScore = 10;
		for(int i = 0; i<10; i++) {
			newStructureGame.setScore();
		}
		totalScore = newStructureGame.getScore("easy");
		
		assertNotEquals(exeptedScore, totalScore);
	}
	

    @Test
    public void testingPipeCreation() {
        int totalPipes = newStructureGame.testingIflistOfPipesCreates();
        assertEquals(2, totalPipes);
    }
    @Test
    public void testingIfSpeedIsCorrect() {
        GameStructure easy = new GameStructure(0, 0, "easy", null, null);
        GameStructure normal = new GameStructure(0, 0, "normal", null, null);
        GameStructure hard = new GameStructure(0, 0, "hard", null, null);
        int easyspeed = easy.checkDifficulty();
        int normalspeed = normal.checkDifficulty();
        int hardspeed = hard.checkDifficulty();
        assertEquals(3, easyspeed);
        assertEquals(4, normalspeed);
        assertEquals(6, hardspeed);	
    }

    @Test
    void testCorrectJumpSpeed() {
        newStructureGame.jump();
        int yFallAfterJump = newStructureGame.getyFall();
        assertEquals(yFallAfterJump, -9);
    }
	

}
