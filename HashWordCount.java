import java.io.*;
import javax.swing.*;

/**
 * HashWordCount scans the file and saves it in a string, then split the string at delimiters and saves all words into 
 * a string array after converting all words to lower case. Then insert all words into the hash table, and rehash
 * when lambda is more than 45%. The file is then printed.
 * @author Jiaxin He
 */
public class HashWordCount {
  
  private int numElement;
  public HashNode[] hashTable;
  private double collisions;
  
  /**
   * Constructor with no inputs. Create a hashTable of of size 2.
   */
  public HashWordCount () {
    numElement = 0;
    hashTable = new HashNode[2];
  }
  
  /**
   * Constructor with an input size to set the size of the hashTable.
   * @param size The size of the hashtable
   */
  public HashWordCount (int size) {
    numElement = 0;
    hashTable = new HashNode[size];
  }
  
  /**
   * Scans the file and saves it into a string
   * @param fileName The file name being scanned
   * @return The string representation of the content of the file
   */
  public String scanner (String fileName) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    try {
      StringBuilder builder = new StringBuilder();
      String everyLine = reader.readLine();
      
      while (everyLine != null) {
        builder.append(everyLine + "\n");
        everyLine = reader.readLine();
      }
      return builder.toString();
    }
    finally {
      reader.close();
    }
  }
  
  /**
   * Split the word at delimiters and saves it into a string array
   * @param text The string being slide into an array
   * @return The string array containing all the words of the text
   */
  public String[] splitWords(String text) {
    text = text.toLowerCase();
    return text.split("\\W");
  }
  
  /**
   * Creates a new hashtable.
   * @param wordArray input an array of the words of the textfile.
   */
  public void createHash (String[] wordArray) {
    
    for (int i = 0; i < wordArray.length; i++) {
      if (wordArray[i] != null && wordArray[i].length() != 0) {
        insertHash(new HashNode(wordArray[i]));
      }
    }
  }
  
  /**
   * Insert the words into the hashtable, rehash when needed
   * @param word The word being inserted into the hash table
   */
  public void insertHash (HashNode word){
    
    double lambda = lambda();
    
    if (lambda >= 45.00) {
      reHash();
    }
    
    int key = Math.abs((word.getElement().hashCode()) % hashTable.length);
    
    //case empty
    if (hashTable[key] == null){
      hashTable[key] = word;
      incNumElement();
    }
    
    //case with only 1 element
    else if (hashTable[key].equals(word.getElement())) {
      hashTable[key].incCount();
    }
    
    //case more than 1 element
    else {
      HashNode trav = hashTable[key];
      boolean didAdd = false;
      
      //all elements in list but the last not null element.
      while (trav.getNext() != null) {
        if (trav.getElement().equals(word.getElement())) {
          trav.incCount();
          didAdd = true;
        }
        trav = trav.getNext();
      }
      //the last element.
      if (trav.getElement().equals(word.getElement())) {
        trav.incCount();
        didAdd = true;
      }
      //add new node if searched the linkedlist and didnt find the word.
      if (didAdd == false) {
        trav.setNext(word);
        incNumElement();
        collisions++;
      }
    }  
  }
  
  /**
   * Rehash the hashtable when lamba is more than 45%
   */
  public void reHash () {
    int length = hashTable.length;
    
    HashWordCount newHashTable = new HashWordCount(length*2);
    
    for (int i = 0; i < length; i++) {
      HashNode trav = hashTable[i];
      while (trav != null) {
        newHashTable.insertHash(new HashNode(trav.getElement(), trav.getCount()));
        trav = trav.getNext(); 
      }
    }
    hashTable = newHashTable.hashTable;
  }
  
  /**
   * Calculates lambda
   * @return Calculates lambda and returns as a double
   */
  public double lambda () {
    return (((double)numElement/hashTable.length) * 100);
  }
  
  /**
   * Increases the count of number of elements in the hashtable.
   */
  public void incNumElement() {
    this.numElement++;
  }
  
  /**
   * This method sums up all previous methods. Scans file, saves it into a string. Split word and save into String array, then createHash
   * @param input_file The string of the input file
   */
  public void wordCount (String input_file) throws IOException {
    String text = scanner (input_file);
    String[] wordArray = splitWords(text);
    createHash(wordArray);
  }
  
  /**
   * Prints the hashtable out onto the outputName into the same folder as the .java file.
   * @param outputName The desired file name of the output file.
   */
  public void printTable(String outputName) throws IOException {
    PrintStream outputFile = new PrintStream(outputName);
    
    HashNode node;
    
    for (int i = 0; i < hashTable.length; i++){
      
      if (hashTable[i] != null) {
        node = hashTable[i];
        String output = "(" + node.getElement() + ":" + node.getCount() + ")";
        outputFile.print(output);
        outputFile.print("\n");                        
        
        while (node.getNext() != null) {
          String output2 = "(" + node.getNext().getElement() + ":" + node.getNext().getCount() + ")";
          outputFile.print(output2);
          outputFile.print("\n");
          node = node.getNext();
        }
      }
    }
    outputFile.print("Average Collision is: " + (collisions/hashTable.length) + "\n");
  }
  
  /**
   * Main method to execute all code. Including scan, creating hashtable, and printing the hashtable. 
   * @param args[] The arguement for the main method.
   */
  public static void main (String args[]) throws IOException {
    
    String inputFileName = "Sherlock Holmes.txt";
    String outputFileName = "Sherlock is Awesome";
    
    HashWordCount hWCount= new HashWordCount();
    hWCount.wordCount(inputFileName);
    hWCount.printTable(outputFileName);
  }
}
