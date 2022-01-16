import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The BigTwoGUI class implements the CardGameUI interface. It is used to build a GUI for
the Big Two card game and handle all user actions.
 * @author ken
 * @version 1.0
 *
*/
public class BigTwoGUI implements CardGameUI{
	
	private BigTwo game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel[] bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField chatInput;
	private JButton sendButton;
	private JMenuItem connect;
	/**
	 * The client of the BigTwo gui
	 * 
	 */
	public BigTwoClient client;
	
	
	/**
	 * BigTwoGUI(BigTwo game) ¡V a constructor for creating a BigTwoGUI. The parameter
game is a reference to a Big Two card game associates with this GUI.
     *
	 * @param game (the game using this BigTwoGui)
	 * 
	 */
	BigTwoGUI(BigTwo game){
		this.game = game;
		boolean[] selected1 = {false, false, false, false, false, false, false, false, false, false, false, false, false};
		selected = selected1;
		go();
		setActivePlayer(game.getCurrentPlayerIdx());
	}
	
	/**
	 * void setActivePlayer(int activePlayer) ¡V a method for setting the index of the
active player (i.e., the player having control of the GUI). 
	 * 
	 * @param int activePlayer - the current player for this turn
	 * 
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * void repaint() ¡V a method for repainting the GUI. 
	 * 
	 */
	public void repaint() {
		boolean[] selected1 = {false, false, false, false, false, false, false, false, false, false, false, false, false};
		selected = selected1;
		frame.repaint();
	}
	
	/**
	 * void printMsg(String msg) ¡V a method for printing the specified string to the message
area of the GUI.
     *
	 * @param String msg - the message to be displayed to the message area
	 * 
	 */
    public void printMsg(String msg) {
    	msgArea.append(msg);
    };

    /**
	 * void printChatMsg(String msg) ¡V a method for printing the specified string to the chat
area of the GUI.
     *
	 * @param String msg - the message to be displayed to the chat area
	 * 
	 */
    public void printChatMsg(String msg) {
    	chatArea.append(msg);
    };
    
	/**
	 * void clearMsgArea() ¡V a method for clearing the message area of the GUI. 
	 * 
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	};

	/**
	 * void disableConnect() - a method to disbale connect
	 * 
	 */
	public void disableConnect() {
		connect.setEnabled(false);
	}
	
	/**
	 * void enableConnect() - a method to enbale connect
	 * 
	 */
	public void enableConnect() {
		connect.setEnabled(true);
	}
	
	/**
	 * void reset() ¡V a method for resetting the GUI. This method will (i) reset the list of selected
cards; (ii) clear the message area; and (iii) enable user interactions
	 * 
	 */
	public void reset() {
		clearMsgArea();
		repaint();
		enable();
	};

	/**
	 * void enable() ¡V a method for enabling user interactions with the GUI. This method will (i)
enable the ¡§Play¡¨ button and ¡§Pass¡¨ button (i.e., making them clickable); (ii) enable the
chat input; and (iii) enable the BigTwoPanel for selection of cards through mouse
clicks
     *
     *
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		chatInput.setEnabled(true);
		sendButton.setEnabled(true);
		for (JPanel i : bigTwoPanel) {
			i.setEnabled(true);
		}
		
	};

	/**
	 * void disable() ¡V a method for disabling user interactions with the GUI. This method will (i)
disable the ¡§Play¡¨ button and ¡§Pass¡¨ button (i.e., making them not clickable); (ii)
disable the chat input; and (ii) disable the BigTwoPanel for selection of cards through
mouse clicks.
	 * 
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		chatInput.setEnabled(false);
		sendButton.setEnabled(false);
		for (JPanel i : bigTwoPanel) {
			i.setEnabled(false);
		}	
	};

	/**
	 * void disableExceptChat() ¡V a method for disabling user interactions with the GUI. This method will (i)
disable the ¡§Play¡¨ button and ¡§Pass¡¨ button (i.e., making them not clickable); and (ii) disable the BigTwoPanel for selection of cards through
mouse clicks.
	 * 
	 */
	public void disableExceptChat() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		chatInput.setEnabled(true);
		sendButton.setEnabled(true);
		for (JPanel i : bigTwoPanel) {
			i.setEnabled(false);
		}	
	};
	
	/**
	 * void promptActivePlayer() ¡V a method for prompting the active player to select cards
and make his/her move. A message will be displayed in the message area showing it
is the active player¡¦s turn.
     *
	 */
	public void promptActivePlayer() {
		// activePlayer = -1 means game end
		if (activePlayer != -1) {
			printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: \n");
		}
	};
	
	
	private Image cardToImage(Card card) {
		char[] suit = {'d', 'c','h','s'};
		char[] rank = {'a', '2', '3', '4', '5', '6' ,'7' ,'8', '9', 't', 'j', 'q', 'k'};
		String path = "src\\pictrue\\" + rank[card.getRank()] + suit[card.getSuit()] + ".gif" ;
		Image image = new ImageIcon(path).getImage();
		return image;
	}
	
	private int[] getSelected() {
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}
	
	private void go() {
    	this.frame = new JFrame("Big Two");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.GREEN.darker().darker().darker()); //set the background color
		frame.setLayout(new GridBagLayout()); 
		ImageIcon img = new ImageIcon("src//pictrue//bigTwoIcon.gif"); 
		frame.setIconImage(img.getImage()); //set a iconbitmap
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("Run");
		menuBar.add(menu);
		this.connect = new JMenuItem("Connect");
		menu.add(connect);
		JMenuItem quit = new JMenuItem("Quit");
		menu.add(quit);
		connect.addActionListener(new ConnectMenuItemListener());
		quit.addActionListener(new QuitMenuItemListener());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1; // default value
		c.gridheight = 1; // default value
		c.weightx = 0.0; // default value
		c.weighty = 0.0; // default value
		c.anchor = GridBagConstraints.NORTH; // default value
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0); // default value
		c.ipadx = 0; // default value
		c.ipady = 0; // default value
		
		BigTwoPanel player1Panel = new BigTwoPanel(0);
		JLabel player1 = new JLabel(this.game.getPlayerList().get(0).getName());
		player1Panel.add(player1);
		player1Panel.setPreferredSize(new Dimension(500,130) );
		player1Panel.setMinimumSize(new Dimension(500, 130));
		frame.add(player1Panel, c);
		
		BigTwoPanel player2Panel = new BigTwoPanel(1);
		JLabel player2 = new JLabel(this.game.getPlayerList().get(1).getName());
		player2Panel.add(player2);
		player2Panel.setPreferredSize(new Dimension(500,130) );
		player2Panel.setMinimumSize(new Dimension(500, 130));
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		frame.add(player2Panel, c);
		
		BigTwoPanel player3Panel = new BigTwoPanel(2);
		JLabel player3 = new JLabel(this.game.getPlayerList().get(2).getName());
		player3Panel.add(player3);
		player3Panel.setPreferredSize(new Dimension(500,130) );
		player3Panel.setMinimumSize(new Dimension(500, 130));
		c.gridy = 2;
		c.insets = new Insets(10, 0, 0, 0);
		frame.add(player3Panel, c);
		
		BigTwoPanel player4Panel = new BigTwoPanel(3);
		JLabel player4 = new JLabel(this.game.getPlayerList().get(3).getName());
		player4Panel.add(player4);
		player4Panel.setPreferredSize(new Dimension(500,130) );
		player4Panel.setMinimumSize(new Dimension(500, 130));
		c.gridy = 3;
		c.insets = new Insets(10, 0, 0, 0);
		frame.add(player4Panel, c);
		
		BigTwoPanel[] panel = {player1Panel, player2Panel, player3Panel, player4Panel};
		this.bigTwoPanel = panel;
		
		Table table = new Table();
		JLabel tableLabel = new JLabel("table");
		table.add(tableLabel);
		table.setPreferredSize(new Dimension(500,130) );
		table.setMinimumSize(new Dimension(500, 130));
		c.gridy = 4;
		c.insets = new Insets(10, 0, 0, 0);
		frame.add(table, c);
		
		// JPanel msgAreaContainer = new JPanel();
		
		this.msgArea = new JTextArea();
		msgArea.setLineWrap(true);
		// this.msgArea.setPreferredSize(new Dimension(400, 270));
		// this.msgArea.setMinimumSize(new Dimension(400, 260));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	    this.msgArea.setBorder(BorderFactory.createCompoundBorder(border,
	    BorderFactory.createEmptyBorder(0, 0, 1, 1)));
	    DefaultCaret caret = (DefaultCaret)msgArea.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    msgArea.setEditable(false);
	    JScrollPane scroller = new JScrollPane(msgArea);
	    scroller.setVerticalScrollBarPolicy(
	             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroller.setHorizontalScrollBarPolicy(
	             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scroller.setMinimumSize(new Dimension(400, 270));
	    scroller.setPreferredSize(new Dimension(400, 270));
        // JScrollPane scrollPane = new JScrollPane(msgArea);
	    c.gridy = 0;
		c.gridx = 1;
		c.gridheight = 3;
		c.insets = new Insets(0, 0, 0, 0);
		// frame.add(this.msgArea, c);
		//frame.add(scrollPane, c);
		frame.add(scroller, c);
		
		this.chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		// chatArea.setPreferredSize(new Dimension(400, 270));
		// chatArea.setMinimumSize(new Dimension(400, 260));
	    chatArea.setBorder(BorderFactory.createCompoundBorder(border,
	    BorderFactory.createEmptyBorder(0, 0, 1, 1)));
	    DefaultCaret chat_caret = (DefaultCaret)chatArea.getCaret();
	    chat_caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    chatArea.setEditable(false);
	    JScrollPane chat_scroller = new JScrollPane(chatArea);
	    chat_scroller.setVerticalScrollBarPolicy(
	             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    chat_scroller.setHorizontalScrollBarPolicy(
	             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    chat_scroller.setMinimumSize(new Dimension(400, 270));
	    chat_scroller.setPreferredSize(new Dimension(400, 270));
		c.gridy = 2;
		c.gridheight = 3;
		c.insets = new Insets(10, 0, 0, 0);
		frame.add(chat_scroller, c);
		
		JPanel b = new JPanel();
		b.setLayout(new GridBagLayout());
		
		GridBagConstraints x = new GridBagConstraints();
		x.gridx = 0;
		x.gridy = 0;
		x.gridwidth = 1; // default value
		x.gridheight = 1; // default value
		x.weightx = 0.0; // default value
		x.weighty = 0.0; // default value
		x.anchor = GridBagConstraints.NORTH; // default value
		x.fill = GridBagConstraints.HORIZONTAL;
		x.insets = new Insets(0, 0, 0, 0); // default value
		x.ipadx = 0; // default value
		x.ipady = 0; // default value
		
		this.playButton = new JButton("Play");
		this.playButton.addActionListener(new PlayButtonListener());
		// playButton.setPreferredSize(new Dimension(125, 30));
		this.passButton = new JButton("Pass");
		this.passButton.addActionListener(new PassButtonListener());
		this.chatInput = new JTextField(30);
		JLabel chatLabel = new JLabel("message: ");
		sendButton = new JButton("Send");
		sendButton.addActionListener(new SendMessageListener());
		// this.chatInput.setMinimumSize(new Dimension(30, 260));
		
		// b.setMinimumSize(new Dimension(200, 260));
		// b.setMaximumSize(new Dimension(200, 260));
		x.anchor = GridBagConstraints.CENTER;
		x.fill = GridBagConstraints.NONE;
		b.add(chatLabel, x);
		x.fill = GridBagConstraints.HORIZONTAL;
		x.gridx = 1;
		x.gridwidth = 2;
		b.add(this.chatInput, x);
		x.anchor = GridBagConstraints.NORTH;
		x.fill = GridBagConstraints.HORIZONTAL;
		x.gridx = 3;
		x.gridwidth = 1;
		b.add(sendButton, x);
		// x.fill = GridBagConstraints.HORIZONTAL;
		x.gridwidth = 4;
		// x.weightx = 3;
		x.gridy = 1;
		x.gridx = 0;
		x.insets = new Insets(30, 0, 0, 0);
		b.add(this.playButton, x);
		x.gridy = 2;
		x.gridx = 0;
		// x.fill = GridBagConstraints.NONE;
		x.insets = new Insets(10, 0, 0, 0);
		b.add(this.passButton, x);
		
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridheight = 1;
		c.ipady = 10;
		c.insets = new Insets(10, 0, 0, 0);
		this.frame.add(b, c);
		
		frame.getRootPane().setDefaultButton(sendButton);
		
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	/**
	 * class BigTwoPanel ¡V an inner class that extends the JPanel class and implements the
MouseListener interface. Overrides the paintComponent() method inherited from the
JPanel class to draw the card game table. Implements the mouseReleased() method
from the MouseListener interface to handle mouse click events. 
	 * 
	 * 
	 * @author ken
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		int playerId;
		
		/**
		 * public BigTwoPanel(int playerId) - a public constructor for creating a BigTwoPanel. The parameter
playerId is representing the player for this Panels.
		 * 
		 * @param playerId - the player who this panel belongs to 
		 * @version 1.0
		 * 
		 */
		public BigTwoPanel(int playerId) {
			this.playerId = playerId;
			addMouseListener(this);
		}
		
		/**
		 * public void paintComponent(Graphics g) - this method draw the player icon, player name and their card
		 * 
		 * @param g the Graphics object to protect
		 *  
		 */
		public void paintComponent(Graphics g) {	
			g.setColor(Color.GREEN.darker().darker());
			g.fillRect(0, 0, 500, 130);	
			g.setColor(Color.WHITE);
			if(playerId == client.getPlayerID()) {
				g.drawString("You", 5 , 15); 
			}
			else {
				g.drawString(game.getPlayerList().get(playerId).getName(), 5, 15); 
			}
			// check if player exists
			if(game.getPlayerList().get(playerId).getName() != "") {
				Image image = new ImageIcon("src\\pictrue\\" + String.valueOf(playerId + 1) + ".png").getImage();
				g.drawImage(image, 0, 20, this);
			}		
			//check if show the back of card
			if (playerId == client.getPlayerID()) {
				for(int i = 0; i < game.getPlayerList().get(playerId).getNumOfCards();i++) {
					Image cardImage = cardToImage(game.getPlayerList().get(playerId).getCardsInHand().getCard(i));
					if (selected[i] == true) {
						g.drawImage(cardImage, 130 + 20 * i, 10, this); //raise the card if it is selected
					}
					else {
						g.drawImage(cardImage, 130 + 20 * i, 30, this);
					}		
				}
			} else {
				Image backImage = new ImageIcon("src\\pictrue\\b.gif").getImage();
				for(int i = 0; i < game.getPlayerList().get(playerId).getNumOfCards();i++) {
					g.drawImage(backImage, 130 + 20 * i, 30, this); //each card is overlapped
				}
			}
	    }
		

		/**
		 * public void mouseClicked(MouseEvent e) - this method judge which card is selected or unselected y the mouse click
		 * 
		 * @param e the event to be processed
		 *  
		 */
		public void mouseClicked(MouseEvent e) {
			if(this.playerId == client.getPlayerID() && client.getPlayerID() == activePlayer) {
				int x_coordinate = e.getX();
				int y_coordinate = e.getY();
				int num_of_card = game.getPlayerList().get(this.playerId).getNumOfCards();
				// handle the first card(no overlapped case)
				if(selected[num_of_card - 1] == false) {
					if(x_coordinate >= 130 + 20 * (num_of_card - 1) && (x_coordinate <= 130 + 20 * (num_of_card - 1) + 73) && (y_coordinate >= 30) && (y_coordinate <= 127)) {
						selected[num_of_card - 1] = true;
						frame.repaint();
						return;
					}
				}else {
					if(x_coordinate >= 130 + 20 * (num_of_card - 1) && (x_coordinate <= 130 + 20 * (num_of_card - 1) + 73) && (y_coordinate >= 10) && (y_coordinate <= 107)) {
						selected[num_of_card - 1] = false;
						frame.repaint();
						return;
				}
					}
				
				// handle the rest overlapped case if any. 
				// There are four cases because the card can be selected or not selected and the card overlapping it can also be selected or not selected. 2 * 2 = 4 cases
				if (num_of_card > 1) {
					for(int i = num_of_card - 2; i >= 0;i--) {
						if(selected[i + 1] == false) {
							if(selected[i] == false) {
								if(x_coordinate >= 130 + 20 * (i) && (x_coordinate <= 130 + 20 * (i+1)) && (y_coordinate >= 30) && (y_coordinate <= 127)) {
									selected[i] = true;
									frame.repaint();
									return;
							}
						}else {
							if(x_coordinate >= 130 + 20 * (i) && (x_coordinate <= 130 + 20 * (i+1)) && (y_coordinate >= 10) && (y_coordinate <= 107)) {
								selected[i] = false;
								frame.repaint();
								return;
						}
					}
				}else {
					if(selected[i] == false) {
						if((x_coordinate >= 130 + 20 * (i) && (x_coordinate <= 130 + 20 * (i+1)) && (y_coordinate >= 30) && (y_coordinate <= 127)) || ((x_coordinate <= 130 + 20 * (i) + 73 && (x_coordinate >= 130 + 20 * (i+1)) && (y_coordinate >= 107) && (y_coordinate <= 127)))) {
							selected[i] = true;
							frame.repaint();
							return;
					}
					}else {
						if(x_coordinate >= 130 + 20 * (i) && (x_coordinate <= 130 + 20 * (i+1)) && (y_coordinate >= 10) && (y_coordinate <= 107)) {
							selected[i] = false;
							frame.repaint();
							return;
					}
					}
				}
				
				}	
		}
		}
		}
		
		public void mousePressed(MouseEvent e) {
		}

		
		public void mouseReleased(MouseEvent e) {
		}

		
		public void mouseEntered(MouseEvent e) {
		}

		
		public void mouseExited(MouseEvent e) {
		}
	} // close BigTwoPanel class
	
	
	/**
	 * class Table ¡V an inner class that extends the JPanel class. Overrides the paintComponent() method inherited from the
JPanel class to draw the card game table.
	 * 
	 * @author ken
	 * @version 1.0
	 * 
	 *
	 */
	class Table extends JPanel{
		
		/**
		 * public void paintComponent(Graphics g) - this method draw card on the table
		 * 
		 * @param g the Graphics object to protect
		 *  
		 */
		public void paintComponent(Graphics g) {
			g.setColor(Color.GREEN.darker().darker());
			g.fillRect(0, 0, 500, 130);	
			g.setColor(Color.WHITE);
			if(game.getHandsOnTable().size() != 0) {
				Hand lastHandOnTable = (game.getHandsOnTable().get(game.getHandsOnTable().size() - 1));
				g.drawString("Last Hand played by: " + lastHandOnTable.getPlayer().getName(), 5, 15); 
				for (int i = 0; i < lastHandOnTable.size(); i++) {
					Image cardImage = cardToImage(lastHandOnTable.getCard(i));
					g.drawImage(cardImage, 78 * i, 30, this);
				}
			}else {
				g.drawString("No Hand played yet", 5, 15); 
			}
		}
		
	} // close Table class
	
	/**
	 * class PlayButtonListener ¡V an inner class that implements the ActionListener
interface. Implements the actionPerformed() method from the ActionListener interface
to handle button-click events for the ¡§Play¡¨ button. When the ¡§Play¡¨ button is clicked,
makeMove() method of your BigTwo object is called to make a move
     *
	 * @author ken
	 * @version 1.0
	 *
	 */
	class PlayButtonListener implements ActionListener{

		@Override
		/**
		 * public void actionPerformed(ActionEvent e) - a method to make the move of the player when the play button is clicked
		 * 
		 * @param e the event to be processed
		 */
		public void actionPerformed(ActionEvent e) {
			if(client.getPlayerID() == activePlayer) {
				int[] selectedCardList = getSelected();
				boolean[] selected1 = {false, false, false, false, false, false, false, false, false, false, false, false, false};
				selected = selected1;
				if (selectedCardList != null) {
					game.makeMove(activePlayer, selectedCardList);
					// client.sendMessage(new CardGameMessage(6, activePlayer, selectedCardList));
				}	
			}		
		}

	} // close PlayButtonListener class
	
    /**
     * class PassButtonListener ¡V an inner class that implements the ActionListener
interface. Implements the actionPerformed() method from the ActionListener interface
to handle button-click events for the ¡§Pass¡¨ button. When the ¡§Pass¡¨ button is clicked,
makeMove() method of BigTwo object is called to make a move.
     * 
     * 
     * @author ken
     * @version 1.0
     *
     */
	class PassButtonListener implements ActionListener{

		@Override
		/**
		 * public void actionPerformed(ActionEvent e) - a method to make the move of the player when the pass button is clicked
		 * 
		 * @param e the event to be processed
		 */
		public void actionPerformed(ActionEvent e) {
			// printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: ");
			if(client.getPlayerID() == activePlayer) {
				int[] cardIdx = null;
				boolean[] selected1 = {false, false, false, false, false, false, false, false, false, false, false, false, false};
				selected = selected1;
				game.makeMove(activePlayer, cardIdx);
				//client.sendMessage(new CardGameMessage(6, activePlayer, cardIdx));
			}
			
		}
		
	} // close PlayButtonListener class
	
	/**
	 * class ConnectMenuItemListener ¡V an inner class that implements the ActionListener
interface. Implements the actionPerformed() method from the ActionListener interface
to handle menu-item-click events for the ¡§Connect¡¨ menu item. When the ¡§Restart¡¨
menu item is selected, the client should make a connection to the server.
	 * 
	 * 
	 * @version 1.0
	 * @author ken
	 *
	 */
	class ConnectMenuItemListener implements ActionListener{

		@Override
		/**
		 * public void actionPerformed(ActionEvent e) - a method to restart the game when restart menu item is clicked
		 * 
		 * @param e the event to be processed
		 */
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// check if connected or not
			if((client.getServerIP() == null )) {
				client.connect();
			}	
		}
		
	} // close RestartMenuItemListener class
	
	/**
	 * class QuitMenuItemListener ¡V an inner class that implements the ActionListener
interface. Implements the actionPerformed() method from the ActionListener interface
to handle menu-item-click events for the ¡§Quit¡¨ menu item. When the ¡§Quit¡¨ menu
item is selected, this method will terminate the application. 
	 * 
	 * @version 1.0
	 * @author ken
	 * 
	 */
	class QuitMenuItemListener implements ActionListener{

		@Override
		/**
		 * public void actionPerformed(ActionEvent e) - a method to quit the game when quit menu item is clicked
		 * 
		 * @param e the event to be processed
		 */
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);	
		}
		
	} // close QuitMenuItemListener class
	
	/**
	 * class SendMessageListener ¡V an inner class that implements the ActionListener
interface. Implements the actionPerformed() method from the ActionListener interface
to handle chat message. When it is clicked the message in the chatInput will be sent to the chatArea. 
	 * 
	 * @version 1.0
	 * @author ken
	 * 
	 */
	class SendMessageListener implements ActionListener{

		@Override
		/**
		 * public void actionPerformed(ActionEvent e) - a method to send the message in the chatInput when send button is clicked
		 * 
		 * @param e the event to be processed
		 */
		public void actionPerformed(ActionEvent e) {
			String text = chatInput.getText();
			chatInput.setText("");
			//chatArea.append(game.getPlayerList().get(game.getCurrentPlayerIdx()).getName() + ": " + text);
			//chatArea.append("\n");
			client.sendMessage(new CardGameMessage(7, -1, text));
		}
		
	}
	
	
}
