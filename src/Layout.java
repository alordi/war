import java.awt.*;  
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;
import javax.imageio.*;

/**
 * Organizes the front-end layout of the GUI application
 */
public class Layout extends JFrame{
    //War class instance we use to play games
    War game;
    
    //panel that appears between games and its components
    JPanel panel = new JPanel();
    JButton playButton = new JButton("Play!");
    JLabel title = new JLabel();
    JLabel description = new JLabel();
    JCheckBox easyCheck = new JCheckBox("Easy Mode");
    
    //components present when the game is being played
    JButton goButton = new JButton("Go!");
    JButton warButton = new JButton("War!");
    JLabel player2 = new JLabel();
    JLabel player1 = new JLabel();
    JLabel player2score = new JLabel();
    JLabel player1score = new JLabel();
    JLabel deck2 = new JLabel();
    JLabel deck1 = new JLabel();
    JLabel card2 = new JLabel();
    JLabel card1 = new JLabel();
    ImageIcon card2Icon = new ImageIcon();
    ImageIcon card1Icon = new ImageIcon();
    JLabel indicator = new JLabel();
    
    //initialize the frontend, sets up all visual elements
    public void init() {
        
        //set up panel and background
        setUpPanel();
        setUpBackground();
        
        //add everything to our main frame
        setLayout(null);
        add(panel);
        add(goButton); add(warButton);
        add(player2); add(player1);
        add(player2score); add(player1score);
        add(deck2); add(deck1);
        add(card2); add(card1);
        add(indicator);
        setTitle("Austin's War Game");
        setSize(900,600);
        getContentPane().setBackground(new java.awt.Color(20,110,40));
        //setForeground(new java.awt.Color(20, 90, 40));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    //sets up elements of the panel
    private void setUpPanel(){
        //size of panel
        panel.setBounds(0,0,900,600);    
        panel.setLayout(null);
        
        //title and description
        title.setText("Austin's War Game");
        title.setBounds(250,100,400,100);
        title.setFont(new Font("Arial",0,44));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        description.setText("Would You Like To Play?");
        description.setBounds(150,250,600,100);
        description.setFont(new Font("Arial",0,24));
        description.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title); 
        panel.add(description);
        
        //button that starts the game
        playButton.setBounds(390,400,120,40);    
        playButton.setBackground(Color.green);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                goButton.setVisible(true);
                warButton.setVisible(true);
                goButton.setEnabled(true);
                game = new War();
                game.setEasyMode(easyCheck.isSelected());
            }
        });
        panel.add(playButton);
        
        //checkbox to turn easyMode on/off
        easyCheck.setBounds(400,500,120,40);
        panel.add(easyCheck);
    }
    
    //sets of elements of the game
    private void setUpBackground() {
        //button that triggers "go" events (normal plays)
        goButton.setBounds(330,475,100,40);    
        goButton.setBackground(Color.blue);
        goButton.setForeground(Color.white);
        goButton.setEnabled(false);
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //call go() and see what it returns
                int check = game.go();
                //normal event, just update scores and images
                if(check == 1){
                    player1score.setText(String.valueOf(game.getHandSize(1)));
                    player2score.setText(String.valueOf(game.getHandSize(2)));
                    updateCardImages();
                }
                //war event, disable goButton, enable warButton, update scores and images
                else if (check == 2) {
                    goButton.setEnabled(false);
                    warButton.setEnabled(true);
                    player1score.setText(String.valueOf(game.getHandSize(1)));
                    player2score.setText(String.valueOf(game.getHandSize(2)));
                    updateCardImages();
                }
                //game over, show panel and reset game elements
                else if (check == 3) {
                    panel.setVisible(true);
                    goButton.setEnabled(false);
                    warButton.setEnabled(false);
                    goButton.setVisible(false);
                    warButton.setVisible(false);
                    playButton.setText("Play again?");
                    title.setText(game.getTitle());
                    description.setText(game.getDesc());
                    resetGame();
                }
            }
        });
        
        //button that triggers "war" events
        warButton.setBounds(470,475,100,40);    
        warButton.setBackground(Color.red);
        warButton.setForeground(Color.white);
        warButton.setEnabled(false);
        warButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //call war and see what it returns
                int check = game.war();
                //normal event, enable goButton, and disable warButton, update scores and images
                if(check == 1){
                    goButton.setEnabled(true);
                    warButton.setEnabled(false);
                    player1score.setText(String.valueOf(game.getHandSize(1)));
                    player2score.setText(String.valueOf(game.getHandSize(2)));
                    updateCardImages();
                }
                //war event, we are already at war so just update scores and images
                else if (check == 2) {
                    player1score.setText(String.valueOf(game.getHandSize(1)));
                    player2score.setText(String.valueOf(game.getHandSize(2)));
                    updateCardImages();
                }
                //game over, show panel and reset game elements
                else if (check == 3) {
                    panel.setVisible(true);
                    goButton.setEnabled(false);
                    warButton.setEnabled(false);
                    goButton.setVisible(false);
                    warButton.setVisible(false);
                    playButton.setText("Play again?");
                    title.setText(game.getTitle());
                    description.setText(game.getDesc());
                    resetGame();
                }
            }
        });
        
        //set up labels indicating scores(# of cards each player has)
        player2.setText("Player 2:");
        player2.setBounds(100,100,100,50);
        player2.setFont(new Font("Arial", 0, 22));
        player2.setForeground(Color.white);
        player2.setHorizontalAlignment(SwingConstants.CENTER);
        
        player1.setText("Player 1 (You):");
        player1.setBounds(80,280,150,50);
        player1.setFont(new Font("Arial", 0, 22));
        player1.setForeground(Color.white);
        player1.setHorizontalAlignment(SwingConstants.CENTER);
        
        player2score.setText("26");
        player2score.setBounds(100,150,100,50);
        player2score.setFont(new Font("Arial", 0, 22));
        player2score.setForeground(Color.white);
        player2score.setHorizontalAlignment(SwingConstants.CENTER);
        
        player1score.setText("26");
        player1score.setBounds(100,330,100,50);
        player1score.setFont(new Font("Arial", 0, 22));
        player1score.setForeground(Color.white);
        player1score.setHorizontalAlignment(SwingConstants.CENTER);
        
        //images for the deck
        deck2.setBounds(310,80,110,155);
        deck1.setBounds(310,260,110,155);
        //load image from file
        ImageIcon deckIcon = new ImageIcon(getClass().getResource("/images/cardback.png"));
        //scale image
        Image image = deckIcon.getImage();
        Image newimg = image.getScaledInstance(110, 155,  java.awt.Image.SCALE_SMOOTH);
        deckIcon = new ImageIcon(newimg);
        
        deck2.setIcon(deckIcon);
        deck1.setIcon(deckIcon);
        
        //set up for images of the card
        card2.setBounds(480,80,110,155);
        card1.setBounds(480,260,110,155);
        
        //indicator telling what happened on the previous turn
        indicator.setText("");
        indicator.setBounds(610,240,200,60);
        indicator.setFont(new Font("Arial", 0, 18));
        indicator.setForeground(Color.white);
        indicator.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    //update the card images for the most recently drawn cards
    private void updateCardImages(){
        try {
            //load images from URL
            URL url2 = new URL(game.getCardUrl(2));
            BufferedImage img2 = ImageIO.read(url2);
            URL url1 = new URL(game.getCardUrl(1));
            BufferedImage img1 = ImageIO.read(url1);
            card2Icon = new ImageIcon(img2);
            card1Icon = new ImageIcon(img1);
            //scale images
            Image image2 = card2Icon.getImage();
            Image image1 = card1Icon.getImage();
            Image newimg2 = image2.getScaledInstance(110, 155,  java.awt.Image.SCALE_SMOOTH);
            Image newimg1 = image1.getScaledInstance(110, 155,  java.awt.Image.SCALE_SMOOTH);
            card2Icon = new ImageIcon(newimg2);
            card1Icon = new ImageIcon(newimg1);
            //set images
            card2.setIcon(card2Icon);
            card1.setIcon(card1Icon);
        } catch (Exception e){
            System.out.println("Could not receive card pictures");
        }
        //update indicator message
        indicator.setText(game.getMsg());
    }
    
    //reset elements of the game when a new one starts
    private void resetGame() {
        indicator.setText("");
        player2score.setText("26");
        player1score.setText("26");
        card2.setIcon(null);
        card1.setIcon(null);
    }
}
