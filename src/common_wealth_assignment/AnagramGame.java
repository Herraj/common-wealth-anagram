package common_wealth_assignment;

public interface AnagramGame {

	/**
	 * Construct an instance of the game.
	 *
	 * @parameter letterset The set of letters the player can use to construct words
	 * @parameter wordlist URL for the word list to check against
	 */
	void AnagrameGame(String letterset, String wordlist);
	
	/**
	 * Submit a word. A word is accepted if its letters are
	 * contained in the letterset submitted in the constructor, and if it
	 * is in the word list at the URL supplied in the constructor.
	 * If the word is accepted and its score is high enough, the submission
	 * should be added to the high score list. If there are multiple submissions
	 * with the same score, all are accepted, but the first submission with that
	 * score will rank higher.
	 *
	 * @parameter word The submission. All submissions may be assumed to be
	 * lowercase and containing no whitespace or special
	 * characters.
	 */
	void submitWord(String word);
	
	/**
	 * Display the current top 10 high score list (word and score).
	 */
	void showHighScoreList();
	

}
