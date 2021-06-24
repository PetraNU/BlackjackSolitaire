package blackjacksolitaire;

import blackjacksolitaire.Card.Suit;
import blackjacksolitaire.Card.Rank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Describes deck of 52 play cards
*/
public class Deck{
    
    private final List<Card> cards;
     
    public Deck(){
        
        cards = new ArrayList<>();
        Suit[] suits = Suit.values();
        Rank[] ranks = Rank.values();
        
        for (Suit s : suits){
            for (Rank r : ranks){
                cards.add(new Card(r, s));
            }    
        }       
    }
    
    /**
     * Shuffles deck: arranges cards in random order 
    */ 
    public void shuffle(){
        Collections.shuffle(cards);
    }
    
    /**
     * Returns next in order card of the deck and deletes this card from the deck 
     * @return next card in order from the deck
    */ 
    public Card getNextCardInOrder(){
        
        if(this.cards.isEmpty()){
            return null;
        }
        
        Card nextCard = this.cards.get(0);
        this.cards.remove(0);
                
        return nextCard;
    }
}
