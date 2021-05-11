import java.util.*;

/**
 * Card class representing a card element
 */
public class Card {
    
    //elements of a card, its numeric value (1-14), and its image url
    private int value;
    private String url;
    
    /**
     * Constructor
     */
    public Card(String value, String url) {
        this.value = this.findValue(value);
        this.url = url;
    }
    
    /**
     * Getter for value
     * @return the value of the card
     */
    public int getValue(){
        return this.value;
    }
    
    /**
     * Getter for image url
     * @return the image url of the card
     */
    public String getImgUrl() {
        return this.url;
    }
    
    /**
     * Determines numeric value of the card
     * @param card: the string value of the card
     * @return the numeric value of the card (int)
     */
    public int findValue(String card) {
        if (card.equals("ACE")) {
            return 14;
	}
	else if (card.equals("KING")) {
            return 13;
	}
	else if (card.equals("QUEEN")) {
            return 12;
	}
	else if (card.equals("JACK")) {
            return 11;
	}
	else {
            return Integer.parseInt(card);
	}
    }
}
