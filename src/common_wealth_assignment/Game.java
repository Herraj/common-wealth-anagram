package common_wealth_assignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Game implements AnagramGame {

	private URL wordListURL;
	private WordEntry[] highScores;
	private Map<Character, List<String>> wordMap;
	private String currentLetterSet;

	public Game(String url) throws Exception {
		wordListURL = new URL(url);
		highScores = new WordEntry[10];
		wordMap = new HashMap<Character, List<String>>();
		currentLetterSet = "";
	}

	@Override
	public void submitWord(String word) {
		if((preValidateWordSubmission(currentLetterSet, word)) && (wordExists(word))) {
			showWordScore(word);
			updateHighScore(word, getWordScore(word));
		}
		else {
			System.out.println(word + ", is NOT acceptable");
		}
	}

	@Override
	public void showHighScoreList() {
		System.out.println(highScores.toString());
	}

	// initialize high score array with empty strings and zero scores
	public void initializeHighScores() {
		for (int i = 0; i < highScores.length; i++) {
			highScores[i] = new WordEntry("", 0);
		}
	}

	// loading word data from the URL
	public void loadData() throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(wordListURL.openStream()));

		wordMap = in.lines().map(word -> word.toLowerCase()).collect(Collectors.groupingBy(word -> word.charAt(0)));
	}

	// shuffle a word for user to solve
	public static String shuffleWord(String word) {
		List<String> letters = Arrays.asList(word.split(""));
		Collections.shuffle(letters);
		StringBuilder builder = new StringBuilder();

		for (String letter : letters) {
			builder.append(letter);
		}

		return builder.toString();
	}

	// Pre-validate user submitted word before checking it exists in the word list
	public boolean preValidateWordSubmission(String letterset, String word) {
		List<String> letterSet = Arrays.asList(letterset.split(""));
		List<String> submissionWord = Arrays.asList(word.split(""));

		if (submissionWord.size() > letterSet.size()) {
			return false;
		}
		else {
			for (String currentletter : submissionWord) {
				if (Collections.frequency(submissionWord, currentletter) > Collections.frequency(letterSet,
						currentletter))
					return false;
			}
		}
		return true;
	}

	// Check if submitted word exists in the word list
	public boolean wordExists(String word) {
		return wordMap.get(word.charAt(0)).contains(word);
	}

	// High score update
	public void updateHighScore(String userWord, int wordScore) {
		int currTopScore = highScores[0].getValue();
		int lastIdx = highScores.length - 1;
		int secondLastIdx = highScores.length - 2;
		int tempIdx = -1;
		WordEntry tempEntry = null;

		if (wordScore > currTopScore) {
			// replace Top word and top score
			highScores[0].setKey(userWord);
			highScores[0].setValue(wordScore);
		}
		if ((wordScore > highScores[lastIdx].getValue()) && (wordScore < highScores[secondLastIdx].getValue())) {
			// replace bottom word and bottom score
			highScores[lastIdx].setKey(userWord);
			highScores[lastIdx].setValue(wordScore);
		}
		if ((wordScore >= highScores[lastIdx].getValue()) || (wordScore <= highScores[0].getValue())) {
			// find item to be replaced between 2nd and 2nd last
			for (int i = 1; i < secondLastIdx; i++) {
				if (wordScore > highScores[i].getValue()) {
					tempIdx = i;
					tempEntry = highScores[i];

					highScores[i].setKey(userWord);
					highScores[i].setValue(wordScore);

					break;
				}
			}

			// shift high scores to right by 1
			if ((tempIdx > 0) && (tempEntry != null)) {
				for (int i = lastIdx; i > tempIdx + 1; i--) {
					highScores[i] = highScores[i - 1];
				}

				highScores[tempIdx] = tempEntry;
			}

		}

	}

	// set currentLetterSet to a random shuffled word from the word map
	public void setShuffledLetterSet() {
		Random ran = new Random();
		char randomLetterKey = (char) ('a' + ran.nextInt(26));

		List<String> wordList = wordMap.get(randomLetterKey);
		int randomIndex = ran.nextInt(wordList.size());

		currentLetterSet = shuffleWord(wordList.get(randomIndex));
	}

	// get current shuffled letter set
	public String getCurrentLetterSet() {
		return currentLetterSet;
	}
	
	// calculate word score
	public int getWordScore(String word) {
		return word.length();
	}
	
	// show score for user submitted word
	public void showWordScore(String word) {
		System.out.println("Word: " + word + ", score: " + getWordScore(word));
	}
	
	// get WordMap (used for unit test only)
	public Map<Character, List<String>> getWordMap(){
		return this.wordMap;
	}
	
	//load dummy high scores (used for unit test only)
	public void loadDummyHighScores(ArrayList<WordEntry> dummyScores) {
		if(dummyScores.size() != highScores.length) {
			System.out.println("invalid dummy array");
		}
		else {
			for(int i = 0; i < dummyScores.size(); i++) {
				highScores[i] = dummyScores.get(i);
			}
		}
	}

	//get high score entry (used for unit test only)
	public WordEntry getHighScoreEntry(int idx) {
		return highScores[idx];
	}
	
	//Run game 
	public void run() throws Exception {
		//while
		
		loadData();
		initializeHighScores();
		setShuffledLetterSet();
		
	}

}
