import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class BigTwoClient implements NetworkGame{

	private BigTwo game;
	private BigTwoGUI gui;
	private Socket sock;
	private ObjectOutputStream oos;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	
	/**
	 * BigTwoClient(BigTwo game, BigTwoGUI gui) ¡V a constructor for creating a Big Two
client. The first parameter is a reference to a BigTwo object associated with this client
and the second parameter is a reference to a BigTwoGUI object associated the BigTwo
object.
	 * 
	 * @param game - BigTwo game
	 * @param gui  - BigTwo gui
	 */
	public BigTwoClient(BigTwo game, BigTwoGUI gui) {
		this.game = game;
		this.gui = gui;
		this.gui.disableExceptChat();
		this.gui.client = this;
		setPlayerName(JOptionPane.showInputDialog(null, "BigTwo", "Please enter your player name"));
		connect();
	}
	
	@Override
	/**
	 * int getPlayerID() ¡V a method for getting the playerID (i.e., index) of the local player.
	 * 
	 * @return the player Id of this local player
	 * 
	 */
	public int getPlayerID() {
		return this.playerID;
	}

	@Override
	/**
	 * void setPlayerID(int playerID) ¡V a method for setting the playerID (i.e., index) of
the local player. This method will be called from the parseMessage() method when a
message of the type PLAYER_LIST is received from the game server. 
	 * 
	 * @param playerID - an integer representing the local player's player Id
	 * 
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		
	}

	@Override
	/**
	 * String getPlayerName() ¡V a method for getting the name of the local player.
	 * 
	 * @return playerName - a string representing the local player's name
	 * 
	 */
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	/**
	 * void setPlayerName(String playerName) ¡V a method for setting the name of the local
player. 
	 * 
	 * @param playerName - a string representing the local player's name
	 * 
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;	
	}

	@Override
	/**
	 * String getServerIP() ¡V a method for getting the IP address of the game server
	 * 
	 * @return ServerIP - a string representing the server's IP address
	 * 
	 */
	public String getServerIP() {
		return this.serverIP;
	}

	@Override
	/**
	 * void setServerIP(String serverIP) ¡V a method for setting the IP address of the game
server.
	 * 
	 * @param ServerIP - a string representing the server's IP address
	 * 
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
		
	}

	@Override
	/**
	 * int getServerPort() ¡V a method for getting the TCP port of the game server
	 * 
	 * @return ServerPort - an integer representing the server's TCP port
	 * 
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	@Override
	/**
	 * void setServerPort(int serverPort) ¡V a method for setting the TCP port of the game
server
	 * 
	 * @param ServerPort - an integer representing the server's TCP port
	 * 
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
		
	}

	@Override
	/**
	 * void connect () ¡V a method for making a socket connection with the game server.
Upon successful connection, this method will (i) create an ObjectOutputStream for sending
messages to the game server; (ii) create a new thread for receiving messages from the
game server
	 * 
	 * 
	 */
	public void connect() {
		// TODO Auto-generated method stub
		try {
			this.sock = new Socket("127.0.0.1", 2396);
			this.oos = new ObjectOutputStream(sock.getOutputStream());
			setServerPort(2396);
			setServerIP("127.0.0.1");
			this.gui.disableConnect();
			this.gui.printMsg("Connected to server at /" + getServerIP() + ":" + getServerPort() + "\n");
			Thread readerThread = new Thread(new ServerHandler(sock));
			readerThread.start();
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
	}

	@Override
	/**
	 * void parseMessage(GameMessage message) ¡V a method for parsing the messages
received from the game server. This method will be called from the thread
responsible for receiving messages from the game server. Based on the message type,
different actions will be carried out (please refer to the general behavior of the client
described in the previous section).
	 * 
	 * @param GameMessage message - the message received from the server
	 * 
	 */
	public void parseMessage(GameMessage message) {
		// TODO Auto-generated method stub
		switch (message.getType()) {
		case CardGameMessage.PLAYER_LIST:
			setPlayerID(message.getPlayerID());
			
			// update the player name
			int count = 0;
		    for(String i : (String[]) message.getData()) {
		    	if(i != null) {
		    		this.game.getPlayerList().get(count).setName(i);
		    	}
		    	count++;
		    }
		    
		    this.gui.repaint();
		    sendMessage(new CardGameMessage(1, -1, this.getPlayerName()));
			break;
		case CardGameMessage.JOIN:
			this.game.getPlayerList().get(message.getPlayerID()).setName((String) message.getData());
			this.gui.printMsg((String) message.getData() + " joins the game.\n");
			this.gui.repaint();
			if(message.getPlayerID() == this.getPlayerID()) {
				sendMessage(new CardGameMessage(4, -1, null));
			}
			break;
		case CardGameMessage.FULL:
			this.gui.printMsg("The game is full.\nCannot join the game");
			this.gui.disable();
			this.gui.repaint();
			break;
		case CardGameMessage.QUIT:
			this.game.getPlayerList().get(message.getPlayerID()).setName("");
			this.game.getHandsOnTable().clear();
			this.gui.reset();
			// stop the game by disable all the button except chat
			this.gui.disableExceptChat();
			this.gui.repaint();
			sendMessage(new CardGameMessage(4, -1, null));
			break;
		case CardGameMessage.READY:
			// marks the specified player as ready for a new game
			this.gui.printMsg(this.game.getPlayerList().get(message.getPlayerID()).getName() +  " is ready.\n");
			this.gui.repaint();
			break;
		case CardGameMessage.START:
			this.gui.reset();
			this.gui.printMsg("All players are ready. Game starts.\n");
			this.game.start((BigTwoDeck) message.getData());
			this.gui.disableExceptChat();
			if(this.getPlayerID() == this.game.getCurrentPlayerIdx()) {
				this.gui.enable();
			}
			break;
		case CardGameMessage.MOVE:
			// check and make the move
			this.game.checkMove(message.getPlayerID(), (int[]) message.getData());
			this.gui.disableExceptChat();
			if(this.getPlayerID() == this.game.getCurrentPlayerIdx()) {
				this.gui.enable();
			}
			this.gui.repaint();
			break;
		case CardGameMessage.MSG:
			// remove the local player's IP address and Port, then send to the chat area
			this.gui.printChatMsg(((String) message.getData()) + "\n");
			this.gui.repaint();
			break;
		default:
			// println("Wrong message type: " + message.getType());
			// invalid message
			break;
		}
		
	}

	@Override
	/**
	 * void sendMessage(GameMessage message) ¡V a method for sending the specified
message to the game server. This method will be called whenever the client wants to
communicate with the game server or other clients. 
	 * 
	 * @param GameMessage message - the message will be sent to the server
	 * 
	 */
	public void sendMessage(GameMessage message) {
		// TODO Auto-generated method stub
		try {
			oos.writeObject(message);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * class ServerHandler ¡V an inner class that implements the Runnable interface. this inner class implement the run() method from the Runnable interface and create a thread
with an instance of this class as its job in the connect() method from the NetworkGame
interface for receiving messages from the game server. Upon receiving a message, the
parseMessage() method from the NetworkGame interface should be called to parse the
messages accordingly
	 * 
	 * @version 1.0
	 * @author ken
	 *
	 */
	class ServerHandler implements Runnable{
		// private Socket socket; // socket connection to the client
		private ObjectInputStream oistream; // ObjectInputStream of the server

		/**
		 * Creates and returns an instance of the ServerHandler class.
		 * 
		 * @param socket
		 *            the socket connection to the server
		 */
		public ServerHandler(Socket socket) {
			// this.socket = socket;
			try {
				// creates an ObjectInputStream and chains it to the InputStream
				// of the client socket
				oistream = new ObjectInputStream(socket.getInputStream());
			} catch (Exception ex) {
				setServerIP(null);
				gui.enableConnect();
				ex.printStackTrace();
			}
		} // constructor

		// implementation of method from the Runnable interface
		/**
		 * public void run() - this method is the implementation of method from the Runnable interface, it receive and parse the message received from the game server
		 * 
		 * 
		 */
		public void run() {
			CardGameMessage message;
			try {
				// waits for messages from the server
				while ((message = (CardGameMessage) oistream.readObject()) != null) {
					parseMessage(message);
				} // close while
			} catch (Exception ex) {
				setServerIP(null);
				gui.enableConnect();
				ex.printStackTrace();			
			}
		} // run
	} // ServerHandler
		
		
	}

