package blackjacksolitaire;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Describes game model
* */
public class BlackjackSolitaire{
    
    private final Scanner sc;
    private final Card[] gameCells;
    private final Card[] discardedCards;
    private final int[][] gameDesk;
    
    public BlackjackSolitaire(){
             
        sc = new Scanner(System.in);
        gameCells = new Card[16];
        discardedCards = new Card[4];
        gameDesk = new int[5][];
    }
    
    /**
     * Describes the logics of the game
    * */
    public void play(){
        
        initializeGameDesk();   
               
        Deck deck = new Deck();
        deck.shuffle();
        
        displayGameDesk();
        displayDiscard();
        
        while(!gameCellsAreFilled()){
            
            Card currentCard = deck.getNextCardInOrder();
            System.out.println("Card from the deck: " + currentCard.toString());
            
            int currentCell = getCellNumberFromUser();
            setCardToCell(currentCard, currentCell); 
            
            displayGameDesk();
            displayDiscard();
                    
            scoring();           
        }  
       
        System.out.println("Game over. Thanks for participating.");
        sc.close();
    }
    
    /**
     * Scoring on the game desk
    **/
    private void scoring(){    
              
        int score = scoringInColumns() + scoringInLines();
       
        System.out.println("Score: " + score);
        System.out.println("========================================================");                
    }
    
    /**
     * Scoring at columns of the game desk
     * @return score in columns of the game desk
    **/
    private int scoringInColumns(){
        
        int scoreInColumns = 0;
        
        for (int i = 0; i < gameDesk.length; i++){   
            
            int points = 0;
            int countOfAces = 0;
            
            for (int j = 0; j < gameDesk[i].length; j++){
             
                int cellIndex = gameDesk[i][j] - 1;
                if(gameCells[cellIndex] != null){                                       
                    Card currentCard = gameCells[cellIndex];
                    countOfAces += currentCard.isItAce() ? 1 : 0;
                    points += currentCard.getRankValue();                   
                }                            
            }
                            
            while(points > 21 && countOfAces > 0){
                points -= 10;
                countOfAces -= 1;
            }
            
            scoreInColumns += convertPointsToScores(points, gameDesk[i].length);    
        }
        
        return scoreInColumns;
    }
    
    /**
     * Scoring at lines of the game desk
     * @return score at lines of the game desk
    **/
    private int scoringInLines(){
        
        int scoreInColumns = 0;
       
        for (int j = 0; j < 4; j++){
            
            int points = 0;
            int countOfAces = 0;
            
            for(int i = 0; i < gameDesk.length; i++){  
                
                if(j > gameDesk[i].length - 1){
                    continue;
                }
               
                int cellIndex = gameDesk[i][j] - 1;
                if(gameCells[cellIndex] != null){                   
                    Card currentCard = gameCells[cellIndex];
                    countOfAces += currentCard.isItAce() ? 1 : 0;
                    points += currentCard.getRankValue();                   
                }   
            }
            
            while(points > 21 && countOfAces > 0){
                points -= 10;
                countOfAces -= 1;
            }
            
            // in this case a cellCount doesn't metter
            scoreInColumns += convertPointsToScores(points, 0);   
        }
        
        return scoreInColumns;       
    }
    
    /**
     * Convert game points to scores
     * @return number of scores
    **/
    private int convertPointsToScores(int points, int cellCount){
        
        int scores = 0;
        
        if(points > 0 && points <= 16){
            scores = 1;
        }else if(points == 17){
            scores = 2;                
        }else if(points == 18){
            scores = 3;               
        }else if(points == 19){
            scores = 4;
        }else if(points == 20){
            scores = 5;
        }else if(points == 21 && cellCount == 2){
            scores = 10;
        }else if(points == 21){
            scores = 7;
        }
    
        return scores;
    }
    
    /**
     * Sets the card to the specified cell of game desk
     * @param card - playing card to be placed in the cell 
     * @param freeCellNumber - card free cell number
    * */
    private void setCardToCell(Card card, int freeCellNumber){   
        
        if(freeCellNumber <= gameCells.length){
            gameCells[freeCellNumber-1] = card;  
        }else{
            int cellNumber = freeCellNumber - gameCells.length - 1;
            discardedCards[cellNumber] = card;
        }
    }
    
    /**   
     * Gets a free cell number from the user 
     * @return cell number from user
    * */
    private int getCellNumberFromUser(){
        
        int cellNumber = 0;
        boolean isCorrectInput = false;
        String incorrectInputMessage = "Incorrect input. Try again to enter an integer from 1 to 20:";
        
        System.out.println("Please enter a free cell number"
                            + "(from 1 to 16 - numbers of game cells and from 17 to 20 - numbers of cells for discarded cards):");       
        
        while(!isCorrectInput){
                        
            try {
                cellNumber = sc.nextInt();               
            }catch (InputMismatchException ex){
                System.out.println(incorrectInputMessage);
                sc.nextLine();
                continue;
            }   
            
            if(cellNumber < 1 || cellNumber > 20 ){
                System.out.println(incorrectInputMessage);
                
            }else if(cellNumber <= gameCells.length && gameCells[cellNumber-1] != null){
                System.out.println("Cell number " + cellNumber + " is full. Enter the number of a free cell:");
                
            }else if(cellNumber > gameCells.length && discardedCards[cellNumber - gameCells.length - 1] != null){
                System.out.println("Trash cell number " + cellNumber + " is full. Enter the number of a free trash cell:");   
                    
            }else{
                isCorrectInput = true;
            }
        }
        
        return cellNumber;
    }
    
    /**
     * Checks the filling of game cells 
     * @return true if all cells are filled else false
    * */
    private boolean gameCellsAreFilled(){
        
        for(Card cell : gameCells){
            if(cell == null){
                return false;
            }                   
        }
        
        return true;
    }
    
    /**
     * Displays the state of the game desk on the console 
    * */
    private void displayGameDesk(){
        
        System.out.println("Game desk:");
                
        for (int j = 0; j < 4; j++){            
            for(int i = 0; i < gameDesk.length; i++){              

                if(j > gameDesk[i].length - 1){
                    System.out.print("\t");
                    continue;
                }
                
                int cellNumber = gameDesk[i][j];
                
                if(gameCells[cellNumber-1] != null){
                    System.out.print(gameCells[cellNumber-1].toString() + "\t");
                }else{
                    System.out.print(cellNumber + "\t");
                }
            }           
            System.out.println();
        }
    }
    
    /**
     * Displays the state of the card discard 
    * */
    private void displayDiscard(){
        
        System.out.println("Discard:");
                
        for(int i = 0; i < discardedCards.length; i++){ 
            
            if(discardedCards[i] != null){
                System.out.print(discardedCards[i] + "\t");
            }else{
                int cellNumber = (i + 1) + gameCells.length;
                System.out.print(cellNumber + "\t");
            }                       
        }
        System.out.println();
    }
    
    /**
     * Fills the array gameDesk[][]
    * */
    private void initializeGameDesk(){
             
        for(int i = 0; i < gameDesk.length; i++){           
            // if first one or last one element therefore 2 columns else 4
            int countCells = (i == 0 || i == gameDesk.length-1) ? 2 : 4;           
            gameDesk[i] = new int[countCells];            
        } 
        
        for (int i = 0; i < gameDesk.length; i++){            
           for (int j = 0; j < gameDesk[i].length; j++){
               
               if(j == 0){
                    gameDesk[i][j] = i+1;
               }else{
                    gameDesk[i][j] = (gameDesk[i][j-1] + gameDesk.length) - (j - 1);
               }             
            }
        }
    }

}