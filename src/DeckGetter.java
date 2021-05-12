import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.*;

/**
 * Talks to DeckofCards API to get and deal decks
 */
public class DeckGetter {
    
    /**
     * Gets and returns a card deck from the API
     * @return an array of Card objects representing the deck
     */
    static public Card[] getDeck() {
        System.out.println("Deck getter called");
        String deckId = "";
        JSONObject deck;
        Card[] cards = new Card[52];
        //get a new deck from the API, we will now have its deck_id
        try {
            URL url = new URL("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1");
            deck = getRequest(url);
            //get deck_id from JSON
            deckId = (String)deck.get("deck_id");
        } catch (Exception e){
            System.out.println("DeckofCards API NOT WORKING");
        }
        
        //with the deck ID, we can retrieve all 52 cards from this deck
        try {
            URL url = new URL("https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=52");
            deck = getRequest(url);
            //with array of 52 cards, add them into our Card array
            JSONArray temp = (JSONArray)deck.get("cards");
            for (int i = 0; i < 52; i++){
                //temp variable for current card
                JSONObject currCard = (JSONObject)temp.get(i);
                //getting value and image from the API
                cards[i] = new Card((String)currCard.get("value"),(String)currCard.get("image"));
            }
        } catch (Exception e){
            System.out.println("DeckofCards API NOT WORKING");
        }
        return cards;
    }
    
    /**
     * Helper function for sending requests to API
     * @param url: the URL to send a request to
     * @return the JSON response from the API, throws exception if no response
     */
    static private JSONObject getRequest(URL url) throws IOException {
        JSONObject deck;
        try {
            //get HTTP connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            
            //reads input stream
            InputStream ip = con.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(ip));
            
            //build a response string
            StringBuilder response = new StringBuilder();
            String responseSingle = null;
            while ((responseSingle = br1.readLine()) != null) 
            {
                response.append(responseSingle);
            }
            String xx = response.toString();
            //close buffered reader
            br1.close();
            
            //parse string response as JSON and return
            return (JSONObject)JSONValue.parse(xx);
            
        } catch (Exception e){
            System.out.println("DeckofCards API NOT WORKING");
            throw e;
        }
    }
    
    
}
