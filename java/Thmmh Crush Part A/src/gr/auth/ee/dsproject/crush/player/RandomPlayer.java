/* ΔΟΜΕΣ ΔΕΔΟΜΕΝΩΝ 2016-17
 * DS - CANDY CRUSH
 * 
 * ΕΥΡΙΠΙΔΗΣ ΜΠΑΛΤΖΗΣ
 * ΑΕΜ: 8196
 * ΤΗΛ: 6980727893
 * e-mail; evrimpal@ece.auth.gr
 * 
 * ΒΑΣΙΛΗΣ ΜΠΕΛΛΟΣ
 * ΑΕΜ: 8715
 * ΤΗΛ: 6947998401
 * e-mail: v8.bellos@gmail.com
 */
package gr.auth.ee.dsproject.crush.player;

import gr.auth.ee.dsproject.crush.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;

import java.util.ArrayList;
import java.util.Random; //κλάση για την παραγωγή ψευδοτυχαίων αριθμών

/* κλάση που προσδιορίζει ένα τυχαίο παίκτη και περιέχει μεθόδους
 * δημιουργίας του παίκτη, get και set των παραμέτρων της κλάσης,
 * όπως αυτές περιγράφονται παρακάτω, καθώς και μια συνάρτηση για τον
 * καθόρισμο της επόμενης κίνησης του παίκτη
 */
public class RandomPlayer implements AbstractPlayer
{
  private int id;							 // ταυτότητα παίκτη
  private String name; 						 // όνομα παίκτη
  private int score;						 // σκορ παίκτη
  
  /* συνάρτηση-constructor της κλάσης, η οποία δέχεται σαν όρισμα ένα
   * αντικείμενο της κλάσης Integer και το περνά στην αντίστοιχη τιμή
   * της ταυτότητας του παίκτη (int id)
   */
  public RandomPlayer(Integer pid){
  	id = pid;
  }
  
  // συνάρτηση που επιστρέφει την τιμή της ταυτότητας του παίκτη
  public int getId(){
  	return id;
  }
  
  /* συνάρτηση που δέχεται ως όρισμα μια τιμή και την περνά στην αντίστοιχη
   * μεταβλητή της ταυτότητας του παίκτη
   */
  public void setId(int idUser){
  	id = idUser;
  }
  
  // συνάρτηση που επιστρέφει το όνομα του παίκτη
  public String getName(){
  	return name;
  }
  
  /* συνάρτηση που δέχεται ως όρισμα ένα αντικείμενο της κλάσης String και
   * περνά την τιμή του στην αντίστοιχη μεταβλητή του ονόματος του παίκτη
   */
  public void setName(String nameUser){
  	name = nameUser; 
  }
  
  // συνάρτηση που επιστρέφει το σκορ του παίκτη
  public int getScore(){
  	return score;
  }
  
  /* συνάρτηση που δέχεται ως όρισμα μια τιμή και την περνά στην αντίστοιχη 
   * μεταβλητή του σκορ του παίκτη
   */
  public void setScore(int scoreUser){
  	score = scoreUser;
  }

  /* συνάρτηση που δέχεται ως ορίσματα ένα αντικείμενο της κλάσης ArrayList
   * και ένα αντικείμενο της κλάσης Board, υπολογίζει και επιστρέφει ένα
   * διάνυσμα 1x4 που περιέχει τις τιμές των τετμημένων x και τεταγμένων y
   * των πλακιδίων που θα αλλάξουν κατά την επόμενη κίνηση του παίκτη
   */
  public int[] getNextMove (ArrayList<int[]> availableMoves, Board board){
   	Random random = new Random();                                      // δημιουργία νέου αντικειμένου της κλάσης Random
   	int index= random.nextInt(availableMoves.size());				   // ορισμός στη μεταβλητή index ενός ψευδοτυχαίου ακεραίου στο διάστημα [0, μέγεθος πίνακα διαθέσιμων κιλησεων)
   	int[] nextMove = new int[4];                                       // δημιουργία νέου πίνακα-διανύσματος 1x4 που επιστρέφεται από την παρούσα συνάρτηση
   	int[] move = CrushUtilities.getRandomMove(availableMoves, index);  // διάνυσμα 1x3 που δέχεται τις τιμές του από τη συνάρτηση getRandomMove της κλάσης CrushUtilities
   	
   	/* Η συνάρτηση getRandomMove δέχεται ως ορίσματα έναν πίνακα-διάνυσμα 
   	 * ακεραίων καθώς και μια μταβλητή ακεραίων και επιστρέφει ένα διάνυσμα
   	 * 1x3 με τις συντεταγμένες x,y του ενός πλακιδίου που θα αλλάξει θέση
   	 * και μια ακέραια τιμή που προσδιορίζει τη θέση του άλλου πλακιδίου ως
   	 * προς τη θέση του πρώτου πάνω στο ταμπλό. Οι επιστρεφόμενες τιμές προκύπτουν
   	 * από την τιμή του πίνακα availableMoves[index] και ορίζουν την επόμενη
   	 * κίνηση του παίκτη
   	 */
   	
   	nextMove[0]=move[0];                                               // τετμημένη x του πλακιδίου 1
   	nextMove[1]=move[1];											   // τεταγμένη x του πλακιδίου 1
   	
   	boolean flag=false;												   // λογική μεταβλητή που χρησιμέυει στον έλεγχο της ορθότητας της επιλεγμένης κίνησης
   	while(flag==false){												   // όσο συνθήκη τερματισμού επανάληψης = ΨΕΥΔΗΣ
   		if(move[2]==0){												   // θέση πλακιδίου 2 = ΑΡΙΣΤΕΡΑ
   			if(move[0]!=0){											   // έλεγχος ορθότητας κίνησης
   				nextMove[2]=move[0]-1;								   // τετμημένη πλακιδίου 2
   				nextMove[3]=move[1];								   // τεταγμένη πλακιδίου 2
   				flag=true;											   // συνθήκη τερματισμού επανάληψης = ΑΛΗΘΗΣ
   			}
   			else{													   // περίπτωση μη έγκυρης επιλογής επόμενης κίνησης
   				index= random.nextInt(availableMoves.size());		   // επιλογή νέου δείκτη index
   				move = CrushUtilities.getRandomMove(availableMoves, index);    // επιλογή νέας κίνησης
   				System.out.print("OUT OF BOUNDS\n");				   // εμφάνιση μηνύματος λάθους
   			}
   		}
   		else if(move[2]==2){									 	   // θέση πλακιδίου 2 = ΔΕΞΙΑ
   			if(move[0]!=CrushUtilities.NUMBER_OF_COLUMNS-1){		   // έλεγχος ορθότητας κίνησης 
   				nextMove[2]=move[0]+1;								   // τετμημένη πλακιδίου 2
   				nextMove[3]=move[1];								   // τεταγμένη πλακιδίου 2
   				flag=true;											   // συνθήκη τερματισμού επανάληψης = ΑΛΗΘΗΣ
   			}
   			else{													   // περίπτωση μη έγκυρης επιλογής επόμενης κίνησης
   				index= random.nextInt(availableMoves.size());		   // επιλογή νέου δείκτη index
   				move = CrushUtilities.getRandomMove(availableMoves, index);    // επιλογή νέας κίνησης
   				System.out.print("OUT OF BOUNDS\n");				   // εμφάνιση μηνύματος λάθους
   			}
   		}
   		else if(move[2]==1){										   // θέση πλακιδίου 2 = ΚΑΤΩ
   			if(move[1]!=CrushUtilities.NUMBER_OF_ROWS-1){			   // έλεγχος ορθότητας κίνησης
   				nextMove[2]=move[0]; 								   // τετμημένη πλακιδίου 2
   				nextMove[3]=move[1]-1;								   // τεταγμένη πλακιδίου 2
   				flag=true;											   // συνθήκη τερματισμού επανάληψης = ΑΛΗΘΗΣ
   			}
   			else{													   // περίπτωση μη έγκυρης επιλογής επόμενης κίνησης
   				index= random.nextInt(availableMoves.size());		   // επιλογή νέου δείκτη index
   				move = CrushUtilities.getRandomMove(availableMoves, index);    // επιλογή νέας κίνησης
   				System.out.print("OUT OF BOUNDS\n");				   // εμφάνιση μηνύματος λάθους
   			}
   		}
   		else if(move[2]==3){										   // θέση πλακιδίου 2 = ΠΑΝΩ
   			if(move[1]!=0){											   // έλεγχος ορθότητας κίνησης
   				nextMove[2]=move[0];								   // τετμημένη πλακιδίου 2
   				nextMove[3]=move[1]+1; 								   // τεταγμένη πλακιδίου 2
   				flag=true;											   // συνθήκη τερματισμού επανάληψης = ΑΛΗΘΗΣ
   			}
   			else{													   // περίπτωση μη έγκυρης επιλογής επόμενης κίνησης
   				index= random.nextInt(availableMoves.size());		   // επιλογή νέου δείκτη index
   				move = CrushUtilities.getRandomMove(availableMoves, index);    // επιλογή νέας κίνησης
   				System.out.print("OUT OF BOUNDS\n");				   // εμφάνιση μηνύματος λάθους
   			}
   		}
   	}
   	return nextMove;												   // επιστροφή 
  }

}
