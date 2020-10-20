package common_wealth_assignment;

import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.net.*;
import java.io.*;

public class GameRunner {

	// loading word data from the URL
	public static Map<Character, List<String>> loadData() throws Exception {
		URL wordListURL = new URL("https://cwcodetest.s3.ca-central-1.amazonaws.com/wordlist.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(wordListURL.openStream()));
		Map<Character, List<String>> wordMap = new HashMap<Character, List<String>>();

		wordMap = in.lines().map(word -> word.toLowerCase()).collect(Collectors.groupingBy(word -> word.charAt(0)));

		return wordMap;
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
	public static boolean preValidateWordSubmission(String letterset, String word) {
		List<String> letterSet = Arrays.asList(letterset.split(""));
		List<String> submissionWord = Arrays.asList(word.split(""));

		if (submissionWord.size() > letterSet.size())
			return false;
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
	public static boolean wordExists(Map<Character, List<String>> wordMap, String word) {
		return wordMap.get(word.charAt(0)).contains(word);
	}

	// High score update
	public static void updateHighScore(WordEntry[] highScores, String userWord, int wordScore) {
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
		if((wordScore > highScores[lastIdx].getValue()) && (wordScore < highScores[secondLastIdx].getValue())) {
			// replace bottom word and bottom score
			highScores[lastIdx].setKey(userWord);
			highScores[lastIdx].setValue(wordScore);
		}
		if((wordScore > highScores[lastIdx].getValue()) && (wordScore < highScores[0].getValue())) {
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

	// get a random shuffled word from the word map
	public static String getRandomWord(Map<Character, List<String>> wordMap) {
		Random ran = new Random();
		char randomLetterKey = (char) ('a' + ran.nextInt(26));

		List<String> wordList = wordMap.get(randomLetterKey);
		int randomIndex = ran.nextInt(wordList.size());

		return shuffleWord(wordList.get(randomIndex));
	}

	// calculate word score
	public static int getWordScore(String word) {
		return word.length();
	}

	public static void main(String[] args) throws Exception {

		// shuffle word -> DONE
		// methods to pre-validate submitted word -> DONE
		// method to check submitted word in the wordmap -> DONE
		// method to generate next shuffled word -> DONE
		// method to calculate score for word - goes into submitWord() -> DONE

		// methods to update highscore - goes into submitWord()
		// method to show highscore

		// start game in a while loop
		Map wordList = loadData();

		System.out.println(getRandomWord(wordList));

	}

}
