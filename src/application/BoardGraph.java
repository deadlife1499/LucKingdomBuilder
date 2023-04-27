package application;

public class BoardGraph {
	private String[][] mapMatrix;
    private HexNode[][] hexMatrix;
    private HexNode root;
    
    public BoardGraph(String[][] matrix) {
    	mapMatrix = matrix;
    	hexMatrix = new HexNode[mapMatrix.length][mapMatrix[0].length];
    	
    	buildGraph();
    }
    
    public HexNode[][] getMatrix(){
        return hexMatrix;
    }
    
    public HexNode getRoot() {
    	return root;
    }
    
    public void buildGraph() {
    	for(int i = 0; i < mapMatrix.length; i++){
            for(int j = 0; j < mapMatrix[0].length; j++){
                hexMatrix[i][j] = new HexNode(mapMatrix[i][j]);
            }
    	}
    	
        for(int i = 0; i < mapMatrix.length; i++){
            for(int j = 0; j < mapMatrix[0].length; j++){                
                HexNode[] bordering = hexMatrix[i][j].getBordering();
                
                if(i != 0 && j < 19) {
                	bordering[0] = hexMatrix[i - 1][j + i % 2];
                } 
                if(j != hexMatrix[0].length - 1) {
                	bordering[1] = hexMatrix[i][j + 1];
                } 
                if(i != hexMatrix[0].length - 1 && j < 19) {
                	bordering[2] = hexMatrix[i + 1][j + i % 2];
                } 
                if(i != hexMatrix[0].length - 1 && j != 0) {
                	bordering[3] = hexMatrix[i + 1][j - 1 + i % 2];
                } 
                if(j != 0) {
                	bordering[4] = hexMatrix[i][j - 1];
                } 
                if(i != 0 && j != 0) {
                	bordering[5] = hexMatrix[i - 1][j - 1 + i % 2];
                } 
            }
        }
        
        root = hexMatrix[0][0];
    }
    
    public String toString() {
    	if(hexMatrix == null) {
    		return "";
    	}
    	
    	String str = "";
    	for(HexNode[] arr : hexMatrix) {
    		for(HexNode hex : arr) {
    			str += hex + " ";
    		}
    		str += "\n";
    	}
    	return str;
    }
}









