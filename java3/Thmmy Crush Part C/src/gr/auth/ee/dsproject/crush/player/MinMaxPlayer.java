package gr.auth.ee.dsproject.crush.player;

import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.defplayers.AbstractPlayer;
import gr.auth.ee.dsproject.crush.node.Node;

import java.util.ArrayList;

public class MinMaxPlayer implements AbstractPlayer
{
  // TODO Fill the class code.

  int score;
  int id;
  String name;

  public MinMaxPlayer (Integer pid)
  {
    id = pid;
    score = 0;
  }

  public String getName ()
  {
	  return "MinMax";
  }

  public int getId ()
  {
	  return id;
  }

  public void setScore (int score)
  {
	  this.score = score;
  }

  public int getScore ()
  {
      return score;
  }

  public void setId (int id)
  {
	  this.id = id;
  }

  public void setName (String name)
  {
	  this.name = name;
  }

  public int[] getNextMove (ArrayList<int[]> availableMoves, Board board)
  {
    // TODO Fill the code

    int indexBest = 0;
    
    Board boardNow = CrushUtilities.cloneBoard(board);
    Node root = new Node(boardNow);
    createMySubTree(root, 1);
    //createOpponentSubTree??
    indexBest = chooseMove(root);
    
    int[] bestMove = availableMoves.get(indexBest);
    
    return CrushUtilities.calculateNextMove(bestMove);
  }

  private void createMySubTree (Node parent, int depth)
  {	
	  ArrayList<int[]> availableMoves = CrushUtilities.getAvailableMoves(parent.getNodeBoard());
	  ArrayList<Node> children = new ArrayList<Node>();
	  for(int i=0; i<availableMoves.size(); i++){
		  Board boardNow = CrushUtilities.boardAfterFullMove(parent.getNodeBoard(), availableMoves.get(i));
		  Node child = new Node(parent, boardNow, availableMoves.get(i));
		  children.add(child);
		  double x = child.evaluate();  
	  }
	  parent.setChildren(children);
    // TODO Fill the code
  }

  private void createOpponentSubTree (Node parent, int depth)
  {
	  Board boardNow = CrushUtilities.boardAfterFullMove(parent.getNodeBoard(), parent.getNodeMove());
	  ArrayList<int[]> availableMoves = CrushUtilities.getAvailableMoves(boardNow);
	  ArrayList<Node> children = new ArrayList<Node>();
	  
	  for(int i=0; i<availableMoves.size(); i++){
		  Board boardMove = CrushUtilities.boardAfterFullMove(boardNow, availableMoves.get(i));
		  Node child = new Node(parent, boardMove, availableMoves.get(i));
		  children.add(child);
		  double x = parent.nodeEvaluation - child.evaluate();
	  }
	  
	  parent.setChildren(children);
  }

  private int chooseMove (Node root)
  {		
	  int v;
	  int i;
	  int a=-101;
	  int b=101;
	  
	  
	  if(root.getNodeDepth()==0 || root.getChildren().isEmpty()){
		  v = (int) root.evaluate();
		  return v;                      // LATHOS EPITHDES
	  }
	  
	  if(root.getNodeDepth()%2==1){
		  v = -101;
		  
		  for(i=0; i<root.getChildren().size(); i++){
			  if(chooseMove(root.getChildren().get(i))>v){
				  v = chooseMove(root.getChildren().get(i));
			  }
			  if(v>a){
				  a = v;
			  }
			  if(b<=a){
				  break;
			  }
		  }
		  
		  return v;
	  }
	  else{
		  v=101;
		  
		  for(i=0; i<root.getChildren().size(); i++){
			  if(chooseMove(root.getChildren().get(i))<v){
				  v = chooseMove(root.getChildren().get(i));
			  }
			  if(v<b){
				  b = v;
			  }
			  if(b<=a){
				  break;
			  }
		  }
		  
		  return v;
	  } 
  }

}
