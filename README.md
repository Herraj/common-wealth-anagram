# Anagaram game
### A simple anagagram game that asks you to create anagram words from a random shuffled word. This game has no UI and runs in the console for now.
### This game uses the word list from: https://cwcodetest.s3.ca-central-1.amazonaws.com/wordlist.txt

#### How to run:
Assuming you have java installed on your machine.
1. Clone MAIN branch on your machine
2. using console/terminal navigate to src/common-wealth-assignment (can also run through your IDE)
3. type 'javac GameRunner.java' and hit enter

#### To run tests:
1. Clone MAIN branch on your machine
2. using console/terminal navigate to test/common-wealth-assignment (can also run through your IDE)
3. type 'javac TestGame.java' and hit enter

#### Notes:
* Although I am using a Java Interface that is implemented my Game class, the example interface that was provided didn't fit in my design. So I have omitted/modified the 'AnagramGame' method/constructor. 
* In the example interface, it wanted to construct a game instance by providing a URL and a Letterset. My design generates a random letterset from the world list loaded from URL. 
* There is still plenty of room to make improvements here. Defensive programming could be improved, adding try-catches and checking for edge cases for user inputs and other method arguments
