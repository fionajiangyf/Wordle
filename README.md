# Wordle





  1. 2D Arrays
  I used 2D Arrays to implement the board to store each letter and their corresponding states.
  This is an appropriate use of the concept because each position in the array is a Tile class object.
  Using 2D Arrays, I can accurately store the game board since it is in the form of a grid.

  2. Collections
  I used collections to store all the 6 letter english words and act as a word bank. This comes from the feedback
  I got after submitting my proposal. I used TreeSet to store the words because each word only occurs once and there
  are no identical words in a word bank. Every time when I want to choose a word in the word bank, it is easy
  to retrieve the word.

  3. File I/O
  I used File I/O in two places.
  First, I used it to read the txt file that stores all the 6-letter words. Because each word is right next
  to each other with a space in between, it is easy to iterate through the file with File I/O.
  Then, I used it to save and restore the game state. I stored all the current game states into a file called
  gamestate.txt. When the restore function is called, the program will read from the text file. It is an appropriate
  use of File I/O.

  4. JUnit Testable Component
  I designed the model to be independent from the GUI components so that it can run without the GUI interface.
  I individually designed functions to deal with checking valid word, picking word, assigning states to each time,
  and many other functions.
