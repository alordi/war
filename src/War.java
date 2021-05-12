
import java.util.ArrayList;

/**
 * Class where most of the game logic occurs
 */
public class War {
    
    //represents each players hands
    private ArrayList<Card> hand1 = new ArrayList<Card>();
    private ArrayList<Card> hand2 = new ArrayList<Card>();
    
    //represents pile in the middle
    private ArrayList<Card> pile = new ArrayList<Card>();
    
    //represents the cards drawn from each player that will be compared
    private Card card1;
    private Card card2;
    
    //a msg/indicator telling what happened in the previous turn
    private String msg;
    
    //title and description of the panel which shows between games
    private String title;
    private String description;
    
    //whether easyMode(win every war) is turned on, default is false
    private Boolean easyMode = false;
    
    /**
     * Constructor for War Class, gets a new deck and deals cards
     */
    public War(){
        this.msg = "";
        Card[] deck = DeckGetter.getDeck();
        System.out.println("deck received");
        this.dealCards(deck);
    }
    
    /**
     * Deals cards from the deck to each player, alternating cards between them
     * Each player receives 26 cards
     * @param deck: an array of Card objects which represents the total deck
     */
    private void dealCards(Card[] deck) {
        for (int i = 0; i < 52; i+=2) {
            hand1.add(deck[i]);
            hand2.add(deck[i+1]);
        }
        System.out.println("deck dealt");
    }
    
    /**
     * Represents a normal move, each player draws the top card from their hand 
     * and the player with the higher # card gets both cards
     * @return int: an integer representing the outcome of the play
     *          1: normal move, 2: war, 3: game ended
     */
    public int go() {
       
        //clear pile before each move
        pile.clear();
        
        // get the top card from each hand and remove them
        card1 = hand1.get(0);
        card2 = hand2.get(0);   
        hand1.remove(0);
        hand2.remove(0);
        
        // add these cards to the middle pile
        pile.add(card1);
        pile.add(card2);
        
        //player 1 wins
        if (card1.getValue() > card2.getValue()) {
            //wins whats in the pile
            hand1.addAll(pile);
            msg = "Player 1 wins";
            //if player 2 has 0 cards, the game is over
            if (hand2.size() == 0) {
                gameOver(true, true);
                return 3;
            }
            return 1;
        }
        //player 2 wins
        else if (card1.getValue() < card2.getValue()) {
            //wins whats in the pile
            hand2.addAll(pile);
            msg = "Player 2 wins";
            //if player 1 has 0 cards the game is over
            if (hand1.size() == 0) {
                gameOver(false, true);
                return 3;
            }
            return 1;
        }
        //tie, war then starts
        else {
            System.out.println("WAR!");
            msg = "It's a tie!";
            return 2;
        }
    }
    
    /**
     * Represents a war event, each player draws 3 face down cards from their hand 
     * and places them in the pile, the players then draw another card whichever highest of
     * these cards wins the whole pile
     * @return int: an integer representing the outcome of the play
     *          1: normal move, 2: war, 3: game ended
     */
    public int war() {
        
        //make sure each player has enough cards to fulfill the war, if not the game ends
        if (hand1.size() < 4) {
            gameOver(false, false);
            return 3;
        }
        if (hand2.size() < 4) {
            gameOver(true, false);
            return 3;
        }
        
        //smaller piles representing the 3 cards each player places down
        ArrayList<Card> pile1 = new ArrayList<Card>();
        ArrayList<Card> pile2 = new ArrayList<Card>();
        //populate smaller piles and remove them from hands
        for (int i = 0; i < 3; i++) {
            pile1.add(hand1.get(0));
            pile2.add(hand2.get(0));
            hand1.remove(0);
            hand2.remove(0);
        }
        
        //get the fourth card for comparison, add to piles and remove from hand
        card1 = hand1.get(0);
        card2 = hand2.get(0);
        hand1.remove(0);
        hand2.remove(0);
        pile1.add(card1);
        pile2.add(card2);
        
        //add both smaller piles to the main pile
        pile.addAll(pile1);
        pile.addAll(pile2);
        
        //player 1 wins
        if (card1.getValue() > card2.getValue() || easyMode) {
            //wins whats in the pile
            hand1.addAll(pile);
            msg = "Player 1 wins the war!";
            //if player 2 has 0 cards, the game is over
            if (hand2.size() == 0) {
                gameOver(true, true);
                return 3;
            }
            return 1;
        }
        //player 2 wins
        else if (card1.getValue() < card2.getValue() && !easyMode) {
            //wins whats in the pile
            hand2.addAll(pile);
            msg = "Player 2 wins the war!";
            //if player 1 has 0 cards, the game is over
            if (hand1.size() == 0) {
                gameOver(false, true);
                return 3;
            }
            return 1;
        }
        //tie, war continues
        else {
            System.out.println("WAR!");
            msg = "It's a tie(again)!";
            return 2;
        }
        
    }
    
    /**
     * Called when a game ends, updates the title and description accordingly
     * @param win: boolean representing who won,
     *              true->player 1 won
     *              false->player 2 won
     * @param zero: boolean representing win condition
     *              true->ran out of cards
     *              false->not enough cards to finish war
     */
    public void gameOver(Boolean win, Boolean zero) {
        //depending on params, set up corresponding titles and descriptions
        //player 1 won
        if (win) {
            title = "You Win!";
            if (zero) {
                description = "Player 2 ran out of cards.";
            }
            else {
                description = "Player 2 doesn't have enough cards to finish the war.";
            }
        }
        //player 2 won
        else {
            title = "You Lose!";
            if (zero) {
                description = "You ran out of cards.";
            }
            else {
                description = "You don't have enough cards to finish the war.";
            }
        }
    }
    
    /**
     * Getter for hand sizes
     * @param  hand: the hand to get the size of
     * @return int: the size of the hand, -1 if hand isn't 1 or 2
     */
    public int getHandSize(int hand) {
        if (hand == 1) {
            return hand1.size();
        }
        else if (hand == 2) {
            return hand2.size();
        }
        else {
            return -1;
        }
    }
    
    /**
     * Getter for image urls
     * @param  hand: the hand to get the image url of
     * @return int: the image url of the hand, N/A if hand isn't 1 or 2
     */
    public String getCardUrl(int hand) {
        if (hand == 1) {
            return card1.getImgUrl();
        }
        else if (hand == 2) {
            return card2.getImgUrl();
        }
        else {
            return "N/A";
        }
    }
    
    /**
     * Getter for msg
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
    /**
     * Getter for description
     * @return the description
     */
    public String getDesc() {
        return description;
    }
    
    /**
     * Getter for title
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Setter for easyMode
     * @param check: a boolean indicating what to set easyMode to
     */
    public void setEasyMode(Boolean check) {
        easyMode = check;
    }
}
