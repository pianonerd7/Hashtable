/**
 * HashNode class are the nodes of the list that is used in the linkedList when there are collisions in the hashTable
 * used in the HashWordCount class.
 * @Jiaxin He
 */
public class HashNode {
  
  String element;
  int count;
  HashNode next;
  
  /**
   * Constructor with just a word input.
   * @param element The element of the hashnode
   */
  public HashNode (String element) {
    this.element = element;
    count = 1;
    next = null;
  }
  
  /**
   * Constructor with word and number of frequency input. used in rehashing.
   * @param element The element of the hasnode
   * @param count The number of counts 
   */
  public HashNode (String element, int count) {
    this.element = element;
    this.count = count;
    next = null;
  }

  /**
   * Sets the element of node
   * @param element The element of the node being set
   */
  public void setElement(String element) {
    this.element = element;
  }
  
  /**
   * Returns the element of the node
   * @returns the element of the node
   */
  public String getElement() {
    return element;
  }
  
  /**
   * increases count by 1.
   */
  public void incCount() {
    count++;
  }
  
  /**
   * Returns the count of node
   * @return the count of the node
   */
  public int getCount() {
    return count;
  }
  
  /**
   * Sets the next node
   * @param next The next node being set
   */
  public void setNext(HashNode next) {
    this.next = next;
  }
  
  /**
   * Return the next node
   * @return The next HashNode
   */
  public HashNode getNext() {
    return next;
  }
}