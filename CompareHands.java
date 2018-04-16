import java.util.*;
import java.io.*;
class CompareHands {
  /* Compare two poker hands. You can enter the hands on
   * the command line in this form: Name: C1 C2 C3 C4 C5.
   * The hands need to be in the form ValueSuit, one letter for each.
   * Example: 3H is a 3 of Hearts, AC is Ace of Clubs.
   * You can also enter a file name containing lines of 2 poker hands.
   * Either way you do it, you need to enter 2 hands at a time.
   * Example: Joe: AH AC KD KH 6C Fred: 2D 3H 9S 10D 5C
   * */
  
  public static void main(String[] args) {
    // Need 2 players, each player has a name and 5 cards
    // Or a file containing rows of players/cards
    if(args.length != 12 && args.length != 1) {
      System.out.println("Incorrect input.");
      System.exit(0);
    }
    
    // the player names
    String player1;
    String player2;
    
    // the player Hands
    Hand hand1;
    Hand hand2;
    
    // Array to gather the Hands
    List<Card> gatherCards = new ArrayList<Card>();
    
    // use this to determine the outcome
    int outcome;
    
    // player hands entered on the command line
    if(args.length == 12) {
      player1 = args[0].substring(0,args[0].length()-1);
      player2 = args[6].substring(0,args[6].length()-1);
      // gather hand for player 1
      for(int i = 1; i < 6; i++) 
        gatherCards.add(new Card(args[i]));
      hand1 = new Hand(gatherCards);
      // clear Array, and gather for player 2
      gatherCards.clear();
      for(int i = 7; i < args.length; i++) 
        gatherCards.add(new Card(args[i]));
      hand2 = new Hand(gatherCards);
      outcome = hand1.compare(hand2);
      if(outcome == 1) {
        System.out.print(player1 + " wins - ");
        hand1.printHand();
      }
      else if(outcome == -1) {
        System.out.print(player2 + " wins - ");
        hand2.printHand();
      }
      else 
        System.out.println("Tie");
    }
    // player hands read from a file
    if(args.length == 1) {
      try {
        File file = new File(args[0]);
        FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);
        String line;
        while( (line = bReader.readLine()) != null) {
          gatherCards.clear();
          // turn line into array to access the arguments
          String[] input = line.split(" ");
          // player names
          player1 = input[0].substring(0,input[0].length()-1);
          player2 = input[6].substring(0,input[6].length()-1);
          // now do the process of gathering cards
          for(int i = 1; i < 6; i++) 
            gatherCards.add(new Card(input[i]));
          hand1 = new Hand(gatherCards);
          
          gatherCards.clear();
          for(int i = 7; i < input.length; i++) 
            gatherCards.add(new Card(input[i]));
          hand2 = new Hand(gatherCards);
          
          outcome = hand1.compare(hand2);
          if(outcome == 1) {
            System.out.print(player1 + " wins - ");
            hand1.printHand();
          }
          else if(outcome == -1) {
            System.out.print(player2 + " wins - ");
            hand2.printHand();
          }
          else 
            System.out.println("Tie");
        }
        reader.close();                       
      } catch(IOException e) {
         e.printStackTrace(); 
      }
    }
  }
}