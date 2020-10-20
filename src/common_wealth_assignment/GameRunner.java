package common_wealth_assignment;

public class GameRunner {

	public static void main(String[] args) throws Exception {

		//create game instance and run
		Game game = new Game("https://cwcodetest.s3.ca-central-1.amazonaws.com/wordlist.txt");
		game.run();

	}

}
