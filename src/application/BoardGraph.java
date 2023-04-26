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
                 //+ 1 * (i % 2)
                if(i != 0) {
                    if(j!=19)
                	    bordering[0] = hexMatrix[i-1][j + (i % 2)];
                    if (j==19)
                        if(i%2==0)
                            bordering[0] = hexMatrix[i-1][j];
                } 
                if(j != hexMatrix[0].length - 1) {
                	bordering[1] = hexMatrix[i][j+1];
                } 
                if(i != hexMatrix[0].length - 1) {
                    if (j!=19)
                	    bordering[2] = hexMatrix[i+1][j + (i % 2)];
                    if (j==19)
                        if(i%2==0)
                            bordering[2] = hexMatrix[i+1][j];
                } 
                if(i != hexMatrix[0].length - 1) {
                    if(j!=0)
                	    bordering[3] = hexMatrix[i+1][j - ((i+1) % 2)];
                    if (j==0)
                        if((i+1)%2==0)
                            bordering[3] = hexMatrix[i+1][j];
                } 
                if(j != 0) {
                	bordering[4] = hexMatrix[i][j-1];
                } 
                if(i != 0) {
                    if(j!=0)
                	    bordering[5] = hexMatrix[i-1][j - ((i+1) % 2)];
                    if (j==0)
                        if((i+1)%2==0)
                            bordering[5] = hexMatrix[i-1][j];
                } 
            }
        }
        //System.out.println(hexMatrix[1][0].getBordering(3).getTerrain());
        root = hexMatrix[0][0];
        //System.out.println(hexMatrix[1][0].getBordering(3).getTerrain());
        /*HexNode temp = root;
        for(int i = 0; i<19; i++){
            temp=temp.getBordering(1);
        }
        System.out.println(temp.getTerrain());
        for(int i = 0; i<6; i++){
            if(temp.getBordering(i)!=null)
                System.out.println(i + " " + temp.getBordering(i).getTerrain());
        }*/
        System.out.println(root.toString());
        if(root.getBordering(2).getBordering(3).equals(root)){
            System.out.println("OOPS");
        }
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









