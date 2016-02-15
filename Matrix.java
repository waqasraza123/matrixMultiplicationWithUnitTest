package lab1_matrix_multiplication;

import java.util.Random;

public class Matrix {
	
	//define variables used
	public int numberOfRows=2, numberOfColumns=2, MAX=100, MIN=0, dataContainer[][];
	
	//main thread starts here
		public static void main(String[] args){
			Matrix doubleObj = new Matrix(2,2);
			Matrix doubleObj2 = new Matrix(2,2);
			
			doubleObj.printResults();
			System.out.println("\n");
			
			
			doubleObj2.printResults();
			System.out.println("\n");
			
			Matrix mulOld = doubleObj.multiply_Old(doubleObj2);
			mulOld.printResults();
			
			Matrix doubleObj3 = new Matrix(2,2);
			doubleObj3 = doubleObj3.multiply_Strassen(doubleObj.dataContainer, doubleObj2.dataContainer);
			doubleObj3.printResults();
			
		}
	//default constructor
	public Matrix(){		
		dataContainer = new int[numberOfRows][numberOfColumns];	
	}
	
	//single argument constructor
	public Matrix(int[][] someArray){		
		dataContainer = someArray;	
	}
	
	//double argument constructor
	public Matrix(int r, int c){
		numberOfRows = r;
		numberOfColumns = c;
		
		dataContainer = new int[numberOfRows][numberOfColumns];

		
		//fill the array with rabdom numbers
		for(int i=0; i<numberOfRows; i++){
			for(int j=0; j<numberOfColumns; j++){
				dataContainer[i][j] = randomNum();
			}
		}	
	}
	
	
	//fill the array with zeros
	public void zeroInitialize(){
		dataContainer = new int[numberOfRows][numberOfColumns];

		for(int i=0; i<numberOfRows; i++){
			for(int j=0; j<numberOfColumns; j++){
				dataContainer[i][j] = 0;
			}
		}
	}
	
	
	//generate the random numbers
	public int randomNum(){
		Random rand = new Random();
		int randomNum = rand.nextInt((MAX-MIN)+1)+ MIN;
		return randomNum;
	}
	
	public Matrix multiply_Old(Matrix other){
		int n = numberOfRows, m = numberOfColumns, p = other.numberOfColumns;
		Matrix result = new Matrix(m,p);
		result.zeroInitialize();
		
		for(int i=0; i<n; i++){
			for(int j=0; j<m; j++){
				for(int k=0; k<m; k++){
					result.dataContainer[i][j] += dataContainer[i][k]*other.dataContainer[k][j];
				}
			}
		}
		
		return result;
	}
	
	public Matrix multiply_Strassen(int[][] doubleObj, int[][] doubleObj2)
    {        
        int n = doubleObj.length;
        int[][] R = new int[n][n];
        
        if (n == 1)
            R[0][0] = doubleObj[0][0] * doubleObj2[0][0];
        else
        {
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];
 
            /** Dividing matrix doubleObj into 4 halves **/
            split(doubleObj, A11, 0 , 0);
            split(doubleObj, A12, 0 , n/2);
            split(doubleObj, A21, n/2, 0);
            split(doubleObj, A22, n/2, n/2);
            
            
            /** Dividing matrix doubleObj2 into 4 halves **/
            split(doubleObj2, B11, 0 , 0);
            split(doubleObj2, B12, 0 , n/2);
            split(doubleObj2, B21, n/2, 0);
            split(doubleObj2, B22, n/2, n/2);
 
            int [][] M1 = multiply_Strassen(add(A11, A22), add(B11, B22)).dataContainer;
            int [][] M2 = multiply_Strassen(add(A21, A22), B11).dataContainer;
            int [][] M3 = multiply_Strassen(A11, sub(B12, B22)).dataContainer;
            int [][] M4 = multiply_Strassen(A22, sub(B21, B11)).dataContainer;
            int [][] M5 = multiply_Strassen(add(A11, A12), B22).dataContainer;
            int [][] M6 = multiply_Strassen(sub(A21, A11), add(B11, B12)).dataContainer;
            int [][] M7 = multiply_Strassen(sub(A12, A22), add(B21, B22)).dataContainer;
 
            int [][] C11 = add(sub(add(M1, M4), M5), M7);
            int [][] C12 = add(M3, M5);
            int [][] C21 = add(M2, M4);
            int [][] C22 = add(sub(add(M1, M3), M2), M6);
 
            join(C11, R, 0 , 0);
            join(C12, R, 0 , n/2);
            join(C21, R, n/2, 0);
            join(C22, R, n/2, n/2);
        }
    
        Matrix result = new Matrix(R);
        return result;
    }

    public int[][] sub(int[][] doubleObj, int[][] doubleObj2)
    {
        int n = doubleObj.length;
        int[][] mulOld = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                mulOld[i][j] = doubleObj[i][j] - doubleObj2[i][j];
        return mulOld;
    }

    public int[][] add(int[][] doubleObj, int[][] doubleObj2)
    {
        int n = doubleObj.length;
        int[][] mulOld = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                mulOld[i][j] = doubleObj[i][j] + doubleObj2[i][j];
        return mulOld;
    }
   
    public void split(int[][] P, int[][] mulOld, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < mulOld.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < mulOld.length; j1++, j2++)
                mulOld[i1][j1] = P[i2][j2];
    }
    
    public void join(int[][] mulOld, int[][] P, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < mulOld.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < mulOld.length; j1++, j2++)
                P[i2][j2] = mulOld[i1][j1];
    } 
   
	
    
    //now print the results on screen
	public void printResults(){
		System.out.println("[");
		for(int i=0; i<numberOfRows; i++){
			for(int j=0; j<numberOfColumns; j++){
				System.out.print(dataContainer[i][j] + " ");
			}
			System.out.println("\n");
		}
		System.out.println("]");
	}
	
}//END

