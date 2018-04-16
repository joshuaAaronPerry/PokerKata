import java.util.*;
class Hand {
 /* A class for Hand objects. A Hand contains 5 cards.
  * It uses bestHand() to find the type of hand the player has.
  * compare() is used to compare 2 hands, and the handRankings List is
  * used in case of a tie to determine the winner. The only public interface 
  * methods are compare() and printHand(); the rest are just helper functions.
  * */
 private List<Card> cards;
 private Map<Integer,Integer> cardValues;
 private List<Integer> handRankings;
 
 /* These variables will be used to store the values
  * that are in the hand multiple times, and how many times
  for each. For example: the player has FullHouse with 3 Kings, 2 10s.
  So the first value is King and there are 3, second value is 10. */
 int firstValue, secondValue;
 int firstAmount, secondAmount;
 
 int bestHand;
 
 public Hand(List<Card> cards) {
   this.cards = cards;
   // this is a frequency table for the number of each card value
   cardValues = new HashMap<Integer,Integer>();
   for(Card c : cards) {
     Integer freq = cardValues.get(c.getValue());
     cardValues.put(c.getValue(), (freq == null) ? 1 : freq+1);
   }
   // this is to rank the individual cards, in case of a tie
   handRankings = new ArrayList<Integer>();
   // intialize these values
   firstValue = 0; secondValue = 0;
   firstAmount = 0; secondAmount = 0;
   initializeVals();
   // establish the ranking for this hand, used in case of tie
   bestHand = bestHand();
   establishRank(bestHand);
 }
 public int compare(Hand otherHand) {
   // return 1 if current hand wins, -1 if otherHand wins, 0 for tie
   int score1 = this.bestHand;
   int score2 = otherHand.bestHand;
   if(score1 > score2) return 1;
   else if(score1 < score2) return -1;
   else {
     int possibleTie = resolveTie(otherHand);
     return possibleTie;
   }
 }
 public void printHand() {
   // print the best hand that this hand makes
   String result;
   int score = bestHand;
   switch(score) {
     case 1:
       result = "Pair of " + valString(firstValue) + "s";
       break;
     case 2:
       result = "Two Pair: " + valString(firstValue) + "s and " + valString(secondValue) + "s";
       break;
     case 3:
       result = "Three of a kind: " + valString(firstValue);
       break;
     case 4:
       result = "Straight: " + valString(handRankings.get(0)) + " high";
       break;
     case 5:
       result = "Flush";
       break;
     case 6:
       result = "Full House: " + valString(firstValue) + " over " + valString(secondValue);
       break;
     case 7:
       result = "Four of a Kind: " + valString(firstValue);
       break;
     case 8:
       result = "Straight Flush: " + valString(handRankings.get(0)) + " high";
       break;
     default: // default is HighCard
       result = "High Card: " + valString(handRankings.get(0));
       break;
   }
   System.out.println(result);
 }
 private void establishRank(int score) {
   // to break ties, rank the cards based off the specific type of hand
   ArrayList<Integer> copy = new ArrayList<>();
   int val;
   // get the card values
   for(Card c : cards)
     copy.add(c.getValue());
   Collections.sort(copy, Collections.reverseOrder());
   switch(score) {
     case 1:
       handRankings.add(firstValue);
       copy.remove(Arrays.asList(firstValue));
       handRankings.addAll(copy);
       break;
     case 2:
       copy.remove(Arrays.asList(firstValue,secondValue));
       int small = Math.min(firstValue,secondValue);
       int big = Math.max(firstValue,secondValue);
       handRankings.add(big);
       handRankings.add(small);
       handRankings.addAll(copy);
       break;
     case 3:
       handRankings.add(firstValue);
       break;
     case 4:
       val = copy.get(1); // handle the special case of Ace low
       handRankings.add( val==5 ? 5 : copy.get(0)); 
       break;
     case 5:
       handRankings = copy;
       break;
     case 6:
       handRankings.add(firstValue);
       break;
     case 7:
       handRankings.add(firstValue);
       break;
     case 8:
       val = copy.get(1); //  Ace low again
       handRankings.add( val==5 ? 5 : copy.get(0));
       break;
     default: // default is HighCard
       handRankings = copy;
       break;
   }
 }
 private int resolveTie(Hand other) {
  // players had same hand, resolve by searching through handRankings 
  // again, return 1 for this player, -1 for the other, 0 for tie 
   for(int i = 0; i < handRankings.size(); i++) {
     if(handRankings.get(i) > other.handRankings.get(i))
       return 1;
     else if(handRankings.get(i) < other.handRankings.get(i))
       return -1;
   }
   return 0;
 }
 private int bestHand() {
   // return 0 for HighCard, 1 for Pair, ... 8 for StraightFlush
   int result = 0;
   if(firstAmount == 2 && secondAmount == 1)
     result = 1;
   if(firstAmount == 2 && secondAmount == 2)
     result = 2;
   if(firstAmount == 3 && secondAmount != 2)
     result = 3;
   if(findStraight())
     result = 4;
   if(findFlush())
     result = 5;;
   if(firstAmount == 3 && secondAmount == 2)
     result = 6;
   if(firstAmount == 4)
     result = 7;
   if(findFlush() && findStraight())
     result = 8;
   return result;
 }
 private boolean findFlush() {
    // check if the hand has a Flush
   String suit = cards.get(0).getSuit();
   for(Card c : cards) {
     // if there is more than one suit, return false
     String s = c.getSuit();
     if( !(s.equals(suit)) )
       return false;
   }
   return true;
 }
 private boolean findStraight() {
   // check if the hand has a Straight
   int[] values = new int[5];
   boolean straight = true;
   // gather the values and sort them
   for(int i = 0; i < values.length; i++) 
     values[i] = cards.get(i).getValue();
   Arrays.sort(values);
   for(int i = 0; i < values.length-1; i++) 
     // if values are not sequential, its not a Straight
      if( !(values[i] == values[i+1]-1) )
        straight = false;
   
   // special case: Ace Low
   boolean aceLow = cardValues.containsKey(2) && cardValues.containsKey(3) && 
                    cardValues.containsKey(4) && cardValues.containsKey(5) &&
                    cardValues.containsKey(15); // Ace is treated as value 15
     
   if(aceLow)
     straight = true;
   return straight;
 }
 private void initializeVals() {
   // this simply inializes the value/amount variables
   for(Integer val : cardValues.keySet()) {
     if( cardValues.get(val) > firstAmount ) {
       // the most frequent value becomes 2nd most frequent
       secondValue = firstValue;
       secondAmount = firstAmount;
       
       firstValue = val;
       firstAmount = cardValues.get(val);
     }
     else if( cardValues.get(val) > secondAmount ) {
        secondValue = val;
        secondAmount = cardValues.get(val);
     }
   }
 }
 private String valString(int val) {
  // note: this uses constants defined in Card
  String result;
  if(val == Card.A)
    result = "Ace";
  else if(val == Card.K) 
    result = "King";
  else if(val == Card.Q)
    result = "Queen";
  else if(val == Card.J)
    result = "Jack";
  else
    result = Integer.toString(val);
  return result;
 }
}