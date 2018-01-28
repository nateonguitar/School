// Boolean Matrix Class
public class BMat
{
  // Instance variables
  public int M[][];
  public int SIZE;

  // Boolean matrix constructors

  public BMat(int s)
  {
    SIZE = s;
    M = new int[SIZE][SIZE];
    // Fill M with zeros
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        M[r][c] = 0;
      }
    }
  } 

  public BMat(int[][] B)
  {
    SIZE = B.length;
    M = new int[SIZE][SIZE];
    // Copy matrix B values into M
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        if(B[r][c] == 0)
          M[r][c] = 0;
        else
          M[r][c] = 1;
      }
    }
  } 

  // Boolean matrix methods

  public void show()
  {
    // Display matrix
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        System.out.print("  " + M[r][c]);
      }
      System.out.println();
    }
    return;
  }

  public boolean isEqual(BMat M2)
  {
    // Check if current matrix equals matrix M2
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        if(this.M[r][c] != M2.M[r][c])
          return false;
      }
    }
    return true;
  }

  public int trace()
  {
    // Sum of main diagonal values
    int diag = 0;
    for(int r = 0; r < SIZE; r++)
      diag = diag + M[r][r];
    return diag;
  }

  public int arrows()
  {
    // No. of 1's in matrix
    int narrows = 0;
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        narrows = narrows + M[r][c];
      }
    }
    return narrows;
  }

  public int indegree(int K)
  {
    // Number of arrows INTO node K of digraph
    // Nodes are numbered 0,1,2,...,SIZE-1
    int colsum = 0;
    for(int r = 0; r < SIZE; r++)
      colsum = colsum + M[r][K];
    return colsum;
  }

  public BMat complement()
  {
    // Logical NOT of current matrix
    BMat W1 = new BMat(SIZE);
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        if(this.M[r][c] == 0)
          W1.M[r][c] = 1;
        else
          W1.M[r][c] = 0;
      }
    }
    return W1;
  }

  public BMat join(BMat M2)
  {
    // Logical OR of current matrix with matrix M2
    BMat W1 = new BMat(SIZE);
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        W1.M[r][c] = this.M[r][c] | M2.M[r][c];
      }
    }
    return W1;
  }

  public BMat power(int N)
  {
    // Raise current matrix to Boolean Nth power (N >= 1)
    BMat W1 = new BMat(this.M);
    BMat W2 = new BMat(this.M);
    for(int k = 2; k <= N; k++){
      W1 = W1.product(W2);
    }
    return W1;
  }

  public BMat rclosure()
  {
    // Reflexive closure of current matrix
    BMat W1 = new BMat(this.M);
    // Put 1's on main diagonal
    for(int r = 0; r < SIZE; r++)
      W1.M[r][r] = 1;
    return W1;
  }

  public BMat sclosure()
  {
    // Symmetric closure of current matrix
    BMat W1 = new BMat(this.M);
    BMat W2 = W1.transpose();
    W1 = W1.join(W2);
    return W1;
  }

   // *********************************
   // Project #4 functions to add
   // *********************************
   
   public int outdegree(int K)
   {
   	// Number of arrows FROM node K of digraph
   	// Nodes are numbered 0,1,2,...,SIZE-1
   	int rowsum = 0;
   	for(int r = 0; r < SIZE; r++)
   		rowsum = rowsum + M[K][r];
   	return rowsum;
   }
   
   public BMat meet(BMat M2)
   {
   	BMat temp = new BMat(SIZE);
   
   	// join the member variable M with M2
   	for(int i=0; i<SIZE; i++)
   	{
   		for(int j=0; j<SIZE; j++)
   		{
   			if(this.M[i][j] == 1 && M2.M[i][j] == 1)
   			{
   				temp.M[i][j] = 1;
   			}
   			else
   			{
   				temp.M[i][j] = 0;
   			}
   		}
   	}
   	return temp;
   }
   
   public BMat transpose()
   { 
   	BMat transposed = new BMat(SIZE);
   
   	for(int i=0; i<SIZE; i++)
   	{
   		for(int j=0; j<SIZE; j++)
   		{            
   			transposed.M[i][j] = this.M[j][i];
   		}
   	}
   
   	return transposed;
   }
   
   public BMat product(BMat M2)
   {
   	// logical AND
   	BMat BMatProduct = new BMat(SIZE);
   	for(int i=0; i<SIZE; i++)
   	{
   		for(int j=0; j<SIZE; j++)
   		{
   			BMatProduct.M[i][j] = this.M[i][j] * M2.M[j][i];
   		}
   	}
   	return BMatProduct;
   }
   
   public BMat tclosure()
   {
   	BMat Work = new BMat(this.M);
   	BMat T = new BMat(SIZE);
   
   	int[] row = new int[SIZE];
   	int[] col = new int[SIZE];
   
   	for(int i=0; i<SIZE; i++)
   	{
   		// set row and columns to work with
   		for(int j=0; j<SIZE; j++)
   		{
   			row[j] = Work.M[i][j];
   		}
   		for(int j=0; j<SIZE; j++)
   		{
   			col[j] = Work.M[j][i];
   		}
   
   		// build T.M
   		for(int k=0; k<SIZE; k++)
   		{
   			for(int m=0; m<SIZE; m++)
   			{
   			T.M[m][k] = row[k] * col[m];
   			}
   		}
   
   		// W = W v T
   		// join the member variable M with M2
   		for(int p=0; p<SIZE; p++)
   		{
   			for(int q=0; q<SIZE; q++)
   			{
   				if(T.M[p][q] == 1 || Work.M[p][q] == 1)
   				{
   					Work.M[p][q] = 1;
   				}
   				else
   				{
   					Work.M[p][q] = 0;
   				}
   			}
   		}
   	}
   	return Work;
   }
   
   public int rootnode()
   {
   	int[] col = new int[SIZE];
   	int index = 0;
   	int count = 0;
   	boolean colZero;
   
   	for(int i=0; i<SIZE; i++)
   	{
   		colZero = true;
   
   		// fill col array with i M collumn
   		for(int j=0; j<SIZE; j++)
   		{
   			col[j] = M[j][i];
   		}
   
   		// check for any 1s
   		for(int j=0; j<SIZE; j++)
   		{
   			if(col[j] != 0)
   			{
   				colZero = false;
   			}
   		}
   
   		// if it didn't find any 1s
   		if(colZero == true)
   		{
   			index = i;
   			count++;
   		}
   	}
   
   	// if it found more than one root candidate
   	if(count != 1)
   	{
   		return -1;
   	}
   	else
   	{
   		return index;
   	}
   }
} // end class