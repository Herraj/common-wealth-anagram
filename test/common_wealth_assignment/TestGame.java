package common_wealth_assignment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class TestGame {

	Game game;
	ArrayList<Character> testWordKeys;

	@BeforeAll
	void setUp() throws Exception {
		String url = "https://cwcodetest.s3.ca-central-1.amazonaws.com/wordlist.txt";
		game = new Game(url);

		testWordKeys = new ArrayList<Character>() {
			{
				add('a'); add('b'); add('c'); add('d'); add('e'); add('f');
				add('g'); add('h'); add('i'); add('j'); add('k'); add('l');
				add('m'); add('n'); add('o'); add('p'); add('q'); add('r');
				add('s'); add('t'); add('u'); add('v'); add('w'); add('x');
				add('y'); add('z');
			}
		};
		game.loadData();
		game.initializeHighScores();
		game.setShuffledLetterSet();
	}

	@AfterAll
	void tearDown() throws Exception {
		game = null;
		testWordKeys = null;
	}

	@Test
	void test_loadData() throws Exception {
		ArrayList<Character> wordMapKeys = new ArrayList<Character>(game.getWordMap().keySet());

		Collections.sort(wordMapKeys);
		Collections.sort(testWordKeys);

		assertTrue(wordMapKeys.equals(testWordKeys), "it should have the same keys as the alphabets");
		assertEquals(wordMapKeys.size(), testWordKeys.size(), "it should have only 26 keys");
	}

	@Test
	void test_shuffleWord() {
		String word = "random";
		String shuffledWord = Game.shuffleWord(word);

		assertTrue(word.length() == shuffledWord.length(), "word length should equal shuffled length");
	}

	@Test
	void test_preValidateWordSubmission() {
		String word = "random";
		String letterset = Game.shuffleWord(word);
		String longerWord = "randoma";
		String tooManySameChars = "randoo";

		assertTrue(game.preValidateWordSubmission(letterset, word), "it should return true for correct word");
		assertFalse(game.preValidateWordSubmission(letterset, longerWord), "it should return false for longer word");
		assertFalse(game.preValidateWordSubmission(letterset, tooManySameChars),
				"it should return false for one too many similar chars");
	}

	@Test
	void test_wordExists() {
		String word = "abacus";
		String fakeWord = "azzzz";

		assertTrue(game.wordExists(word), "it should return true for a real word in the list");
		assertFalse(game.wordExists(fakeWord), "it should return false for a fake word");
	}

	@Test
	void test_updateHighScore() {
		ArrayList<WordEntry> dummyScores = new ArrayList<WordEntry>() {
			{
				add(new WordEntry("ottawa", 100)); add(new WordEntry("kingston", 90));
				add(new WordEntry("whitby", 80)); add(new WordEntry("ajax", 70));
				add(new WordEntry("calgary", 60)); add(new WordEntry("perth", 50));
				add(new WordEntry("montreal", 40)); add(new WordEntry("toronto", 30));
				add(new WordEntry("vancouver", 20)); add(new WordEntry("kelowna", 10));
			}
		};
		game.loadDummyHighScores(dummyScores);

		// replace bottom score
		game.updateHighScore("london", 15);
		assertEquals(game.getHighScoreEntry(9).getKey(), "london", "it should rank london at bottom");
		assertEquals(game.getHighScoreEntry(9).getValue(), 15, "it should rank 15 at bottom");

		// replace top score
		game.updateHighScore("tokyo", 120);
		assertEquals(game.getHighScoreEntry(0).getKey(), "tokyo", "it should rank tokyo top");
		assertEquals(game.getHighScoreEntry(0).getValue(), 120, "it should rank 120 at top");
		// vancouver should the bottom score now
		assertEquals(game.getHighScoreEntry(9).getKey(), "vancouver", "it should rank vancouver at bottom");
		assertEquals(game.getHighScoreEntry(9).getValue(), 20, "it should rank 20 at bottom");

		// reset dummy scores
		game.loadDummyHighScores(dummyScores);

		// replace 4th score
		game.updateHighScore("berlin", 75);
		assertEquals(game.getHighScoreEntry(3).getKey(), "berlin", "it should rank berlin at 4th");
		assertEquals(game.getHighScoreEntry(3).getValue(), 75, "it should rank 75 at 4th");
		assertEquals(game.getHighScoreEntry(2).getKey(), "whitby", "it should rank whitby at 3rd");
		assertEquals(game.getHighScoreEntry(9).getKey(), "vancouver", "it should rank vancouver at bottom");

		// reset dummy scores
		game.loadDummyHighScores(dummyScores);

		// replace 7th score
		game.updateHighScore("cairo", 45);
		assertEquals(game.getHighScoreEntry(6).getKey(), "cairo", "it should rank cairo at 7th");
		assertEquals(game.getHighScoreEntry(6).getValue(), 45, "it should rank 45 at 7th");
		assertEquals(game.getHighScoreEntry(5).getKey(), "perth", "it should rank perth at 6th");
		assertEquals(game.getHighScoreEntry(9).getKey(), "vancouver", "it should rank vancouver at bottom");

	}

	@Test
	void test_getWordScore() {
		assertEquals(game.getWordScore("random"), 6);
		assertNotEquals(game.getWordScore("random"), 2);
	}

}
