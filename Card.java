class Card {
  /* A class for Card objects.
   * It contains constants to represent the values Jack - Ace.
   * It contains two constuctors. one takes a suit and value. The 
   * other takes a 2-letter string, for example: 2S would be 2 of Spades.
   * */
  private String suit;
  private int value;
  
  public final static int J = 11, Q = 12, K = 13, A = 15;
  
  public Card(String suit, int value) {
    this.suit = suit;
    this.value = value;
  }
  public Card(String card) {
    // this constructor takes a string, example: 2S for 2 of Spades
    // special case: suit = 10
    if(card.length() == 3) {
      value = 10;
      this.suit = card.substring(2,3);
    }
    else {
      this.value = convertToInt(card.substring(0,1));
      this.suit = card.substring(1,2);
    }
  }
  private int convertToInt(String stringValue) {
    // convert the string representation of the card value to an integer
    int value;
    switch(stringValue.toUpperCase()) {
      case "J":
        value = J;
        break;
      case "Q":
        value = Q;
        break;
      case "K":
        value = K;
        break;
      case "A":
        value = A;
        break;
      default:
        value = Integer.parseInt(stringValue);
        break;
    }
    return value;
      
  }
  public String getSuit() {
    return suit;
  }
  public int getValue() {
    return value;
  }
}