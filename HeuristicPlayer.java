/*
 EVRIPIDIS BALTZIS 8196
 6980727893 evrimpal@ece.auth.gr
 *
 DIMITRIS ROPOUTIS 8233
 6986952463 dropoutis@ece.auth.gr
*/

package gr.auth.ee.dsproject.proximity.defplayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities; // import του package ProximityUtilities για τη χρήση των αντικειμένων του (πχ NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
import gr.auth.ee.dsproject.proximity.board.Tile;

/* κλάση που κληρονομεί την κλάση AbstractPlayer και
 * δημιουργεί νέους παίκτες
 * καθορίζει τις κινήσεις των παικτών
 * υπολογίζει το σκορ τους
 * χρησιμοποιείται από το κυρίως πρόγραμμα για τις παραπάνω λειτουργίες
 */
public class HeuristicPlayer implements AbstractPlayer {
	// χαρακτηριστικές μεταβλητές κάθε παίκτη
	int score;
	int id;
	String name;
	int numOfTiles;
    
	/* constructor με όρισμα την ταυτότητα (id) του παίκτη
	 * αντιστοιχεί την τιμή του ορίσματος με την χαρακτηριστική ταυτότητα
	 * του παίκτη που τον καλεί
	 */
	public HeuristicPlayer (Integer pid){
		id = pid;
		name = "Heuristic";
	}

	/* συνάρτηση που επιστρέφει τo όνομα (name) 
	 * του παίκτη που την καλεί
	 */
	public String getName (){
		return name;
	}

	/* συνάρτηση που επιστρέφει τον αριθμό των κελιών (numOfTiles)
	 * που ανήκουν στον παίκτη που την καλεί
	 */
	public int getNumOfTiles(){
		return numOfTiles;
	}

	/* συνάρτηση που θέτει το όρισμά της (plakidia)
	 * ως τον αριθμό των κελιών (numOfTiles)
	 * που ανήκουν στον παίκτη που την καλεί
	 */
	public void setNumOfTiles(int tiles){
		numOfTiles = tiles;
	}
    
	/* συνάρτηση που επιστρέφει την ταυτότητα (id)
	 * του παίκτη που την καλεί
	 */
	public int getId (){
		return id;
	}

	/* συνάρτηση που θέτει το όρισμά της (score) 
	 * ως το σκορ (score) του παίκτη που την καλεί
	 */
	public void setScore (int score){
		this.score = score;
	}

	/* συνάρτηση που επιστρέφει την το σκορ (score)
	 * του παίκτη που την καλεί
	 */
	public int getScore (){
		return score;
	}
    
	/* συνάρτηση που θέτει το όρισμά της (pid)
	 * ως την ταυτότητα (id) του παίκτη που την καλεί
	 */
	public void setId (int id){
		this.id = id;
	}

	/* συνάρτηση που θέτει το όρισμά της (onoma)
	 * ως το όνομα (name) του παίκτη που την καλεί
	 */	
	public void setName (String name){
		this.name = name;
	}
    
	/* συνάρτηση που δέχεται σαν ορίσματά της το ταμπλό (board)
	 * καθώς και το σκορ (randomNumber) του πλακιδίου που πρόκειται να χρησιμοποιηθεί από τον παίκτη
	 * ελέγχει κάθε άδεια θέση του ταμπλό
	 * καλέι τη συνάρτηση αξιολόγησης και αποθηκεύει
	 * σε ένα Hashmap πίνακα τις συντεταγμένες και το αποτέλεσμα της αξιολόγησης
	 * και στη συνέχεια επιστρέφει εκείνες τις συντεταγμένες για τις οποίες
	 * προέκυψε και η καλύτερη αξιολόγηση
	 * καθώς και το σκορ του πλακιδίου που θα χρησιμοποιηθεί
	 */
	public int[] getNextMove (Board board , int randomNumber){
		double eval; // μεταβλητή που δέχεται το αποτέλεσμα της συνάρτησης αξιολόγησης
		double max = 0; // μεταβλητή που κρατάει την καλύτερη των αξιολογήσεων 
		int[] nextMove = new int[3]; //επιστρεφόμενος πίνακας που περιέχει τις συντεταγμένες και το σκορ του πλακιδίου
		HashMap <Tile, Double> bestEvaluation = new HashMap <Tile, Double>(); // hashmap με πλακίδιο-αξιολόγηση ως κλειδί-τιμή
		//διπλή επανάληψη για έλεγψο όλων των διαθέσιμων θέσεων
		for(int x=0; x<ProximityUtilities.NUMBER_OF_COLUMNS; x++){
			for(int y=0; y<ProximityUtilities.NUMBER_OF_ROWS; y++){
				Tile tile = new Tile();
				tile = board.getTile(x, y); //δημιουργία ν'εου πλακιδίου
				if(tile.getColor() == 0){ // έλεγχος αν είναι άδειο το πλακίδιο
					eval = getEvaluation(board, randomNumber, tile); // κλήση της συνάρτησης αξιολόγησης
					bestEvaluation.put(tile, eval); // add του ζευγαριού στο hashmap
					if(bestEvaluation.get(tile)>max){ //σύγκριση για max
						max = bestEvaluation.get(tile);
						nextMove[0] = tile.getX();
						nextMove[1] = tile.getY();
						nextMove[2] = randomNumber;
					}
				}
			}
		}
		return nextMove; //επιστροφή της επόμενης κίνησης στο χρήστη
	}
	
	/* συνάρτησηπου δέχεται σαν ορίσματα το ταμπλό (board),
	 * το σκορ (randomNumber) του πλακιδίου που πρόκειται να χρησιμοποιηθεί από τον παίκτη
	 * καθώς και ένα πλακίδιο (tile) 
	 * εφαρμόζει έναν αλγόριθμο αξιολόγησης και
	 * επιστρέφει τελικά έναν αριθμό που αντιστοιχί στο αποτέλεσμα της αξιολόγησης για την
	 * αντίστοιχη θέση στο ταμπλό
	 */
	double getEvaluation(Board board, int randomNumber, Tile tile){
		int neighborsOutOfBounds = 0; // πόσα εφαπτομενικά πλακίδια είναι εκτός ορίων ταμπλό
		int neighborsHeuristic = 0; // πόσα εφαπτομενικά πλακίδια ανήκουν ήδη στον παίκτη
		int opponentTilesWon = 0; // πόσα αντίπαλα εφαπτομενικά πλακίδια κερδίζει ο παίκτης αν βάλει το πλακίδιο του στη συγκεκριμένη θέση
		int opponentPointsWon = 0; // πόσους πόντους του αντιπάλου αφαίρεσε (κέρδισε) ο παίκτης
		int totalPointsWon = 0; // σύνολο κερδισμένων από την κίνηση πόντων
		int availableNeighbors = 0; // άδεια εφαπτομενικά πλακίδια
		int numOfNeighbors; // αριθμός συνολικών γειτόνων = 6 - εφαπτομενικά εκτός ταμπλό
        float evaluationNeighbors; // αξιολόγηση για γείτονες
        float evaluationTotalPointsWon; // αξιολόγηση για σύνολο κκερδισμένων πόντων
        float evaluationOpponentPointsLost; // αξιολόγηση για σύνολο κερδισμένων από τον αντίπαλο πόντων
        double evaluation; // επιστρεφόμενη τιμή
        
        // συνάρτηση που υπολογίζει τις μεταβλητές αυτής της συνάρτησης
		for(int i=0; i<6; i++){
			if(ProximityUtilities.getNeighbors(tile.getX(), tile.getY(), board)[i] == null){ //εκτός ορίων εφαπτομενικό πλακίδιο
				neighborsOutOfBounds++;
			}
			else if(ProximityUtilities.getNeighbors(tile.getX(), tile.getY(), board)[i].getColor() == this.id){ //εφαπτομενικό πλακίδιο που ανήκει ήδη στον παίκτη
				neighborsHeuristic++;
			}
			else if(ProximityUtilities.getNeighbors(tile.getX(), tile.getY(), board)[i].getColor() == 0){ //άδειο εφαπτομενικό πλακίδιο
				availableNeighbors++;
			}
			else{
				if(randomNumber > ProximityUtilities.getNeighbors(tile.getX(), tile.getY(), board)[i].getScore()){ // κερδισμένο από τον αντίπαλο εφαπτομενικό πλακίδιο
					opponentTilesWon++;
					opponentPointsWon += ProximityUtilities.getNeighbors(tile.getX(), tile.getY(), board)[i].getScore();
				}
			}
		}
		numOfNeighbors = 6 - neighborsOutOfBounds;
		evaluationNeighbors = (float) ((1 - availableNeighbors / numOfNeighbors)*15);
		totalPointsWon = neighborsHeuristic + opponentPointsWon + randomNumber;
		
		evaluationTotalPointsWon = (float) ((totalPointsWon / 131)*40); // 131 μέγιστο πιθανό σύνολο κερδισμένων πόντων, αναγωγή στη μονάδα και πολλαπλασιασμός με το συντελεστή βαρύτητας 40
		if(totalPointsWon > 60){ //αν σύνολο κερδισμένων πόντων μεγαλύτερο από 60
			evaluationTotalPointsWon += (15/40)*evaluationTotalPointsWon; // αύξηση βαρύτητας κατα 15% 
		}
		evaluationOpponentPointsLost = (float) ((opponentPointsWon / 111)*30); // 111 μέγιστο πιθανό σύνολο κερδισμένων από τον αντίπαλο πόντων, αναγωγή στη μονάδα και πολλαπλασιασμός με το συντελεστή βαρύτητας 30
		if(opponentPointsWon > 40){ // αν σύνολο κερδισμένων από τον αντίπαλο πόντων μεγαλύτερο του 40
			evaluationOpponentPointsLost += (1/3)*evaluationOpponentPointsLost; //αύξηση βαρύτητας κατά 10%
		}

		evaluation = evaluationNeighbors + evaluationTotalPointsWon + evaluationOpponentPointsLost; // άθροισμα των 3 βαθμίδων αξιολόγησης σε ενιαίο τελικό αποτέλεσμα αξιολόγησης
		return evaluation; //επιστροφή της αξιολόγησης
	}

}
