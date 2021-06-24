package blackjacksolitaire;

/**
  * Describes a playing card
*/

public class Card{

    private final Rank rank;
    private final Suit suit;
    
    /**
     * Set of card's suits
    */ 
    public enum Suit{       
        HEARTS('H'), 
        SPADES('S'), 
        DIAMONDS('D'), 
        CLUBS('C'); 
    
        private final char title;
        
        Suit(char title){
            this.title = title;
        }    
    }
    
    /**
     * Set of playing card's ranks
    */ 
    public enum Rank{  
        TWO(2), THREE(3), FOUR(4), 
        FIVE(5), SIX(6), SEVEN(7), 
        EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), АСЕ(11);
        
        private final int value;
        
        Rank(int value){
            this.value = value;
        }
     }
       
    /**
     * Creates a playing card
     * @param rank - a card's rank 
     * @param suit - a card's suit
    */ 
    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
   }
    
    /**
     * Forms the string representation of the playing card
     * @return the string representation of the playing card
    */
    @Override
    public String toString(){       
        Rank currentRank = this.rank;       
        String str = getStringSymbolOfRank(currentRank);
        
        return str + this.suit.title;
    }
    
    /**
      * Get the rank value of the playing card
      * @return int value of the playing card
     */
    public int getRankValue(){       
        Rank currentRank = this.rank;               
        return currentRank.value;
    }
    
    /**
      * Checks if the playing card is an ace
      * @return true if the playing card is an ace else false
     */
    public boolean isItAce(){       
        return (this.rank == Rank.АСЕ);
    }
    
    /**
     * Forms a string representation of card rank
     * @return string representation of card rank
    */
    private String getStringSymbolOfRank(Rank cardRank){
        
        String strSymbolOfRank;
        
        if(cardRank == Rank.JACK || cardRank == Rank.QUEEN 
                || cardRank == Rank.KING || cardRank == Rank.АСЕ){
            char firstLetter = cardRank.name().charAt(0);
            strSymbolOfRank = String.valueOf(firstLetter);
        } else{
            strSymbolOfRank = String.valueOf(cardRank.value);
        }        
        
        return strSymbolOfRank;
    }
}
