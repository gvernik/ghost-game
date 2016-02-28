To run:
javac ghost/*.java
java ghost.Ghost

The word set comes from:
https://raw.githubusercontent.com/first20hours/google-10000-english/master/20k.txt
It is smaller and easier to play.
Also I am excluding words less than 3 characters (it is configurable programmatically) to make it more interesting to play.

TODO:
Unit tests
Strategy: Currently the computer plays naive strategy: either bluff (at random with the given probability) or gives the 
first character which doesn't complete the word (if possible). The better approach would be to try to trick the player by 
picking the letter with only one character remaining to complete the word.
 