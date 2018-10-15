import java.util.Random;
 
public class project2
{
    public static void main(String[] args) {
        int[] nValues={4,8,16,32,64,128,256,512,1024,2048,4096};
        Random RNG=new Random();
        project2 implementation=new project2();
    
        for (int index=0; index<nValues.length; index++) {
            int n=nValues[index];

            int[][] arrayA=new int[n][n];
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++)
                    arrayA[i][j]=RNG.nextInt(1); //all "random" values are 0 for experimental purposes, can increase argument value to verify the matrices are being multiplied correctly
            int[][] arrayB=new int[n][n];
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++)
                    arrayB[i][j]=RNG.nextInt(1);
            
            long startTimer=System.nanoTime();
            int[][] arrayC=implementation.multiply(arrayA,arrayB); //strassen's divide and conquer method
            long endTimer=System.nanoTime();
            long duration=endTimer-startTimer;
        
            /*for (int i=0; i<n; i++) { //prints array, commented out to save space
                for (int j=0; j<n; j++)
                    System.out.print(arrayC[i][j]+" ");
                System.out.println();
            }*/
        
            System.out.println("Array Sizes: "+nValues[index]+"x"+nValues[index]);
            System.out.println("Time: "+duration+" ns");
            System.out.println();
        }
    }
    
    public int[][] multiply(int[][] A,int[][] B) {        
        int n=A.length;
        int[][] product=new int[n][n];
        if (n==1)
            product[0][0]=A[0][0]*B[0][0];
        else {
            int[][] a=new int[n/2][n/2];
            int[][] b=new int[n/2][n/2];
            int[][] c=new int[n/2][n/2];
            int[][] d=new int[n/2][n/2];
            int[][] e=new int[n/2][n/2];
            int[][] f=new int[n/2][n/2];
            int[][] g=new int[n/2][n/2];
            int[][] h=new int[n/2][n/2];
 
            split(A,a,0,0);
            split(A,b,0,n/2);
            split(A,c,n/2,0);
            split(A,d,n/2,n/2);
            split(B,e,0,0);
            split(B,f,0,n/2);
            split(B,g,n/2,0);
            split(B,h,n/2,n/2);
 
            int[][] operation1=multiply(a,subtract(f,h));
            int[][] operation2=multiply(add(a,b),h);
            int[][] operation3=multiply(add(c,d),e);
            int[][] operation4=multiply(d,subtract(g,e));
            int[][] operation5=multiply(add(a,d),add(e,h));
            int[][] operation6=multiply(subtract(b,d),add(g,h));
            int[][] operation7=multiply(subtract(a,c),add(e,f));

            int[][] C1=add(subtract(add(operation5,operation4),operation2),operation6);
            int[][] C2=add(operation1,operation2);
            int[][] C3=add(operation3,operation4);
            int[][] C4=add(subtract(add(operation5,operation1),operation3),operation7);
 
            join(C1,product,0,0);
            join(C2,product,0,n/2);
            join(C3,product,n/2,0);
            join(C4,product,n/2,n/2);
        } 
        return product;
    }
    
    public void split(int[][] P,int[][] C,int iB,int jB) {
        for(int i1=0,i2=iB; i1<C.length; i1++,i2++)
            for(int j1=0,j2=jB; j1<C.length; j1++,j2++)
                C[i1][j1]=P[i2][j2];
    }
    public int[][] subtract(int[][] A,int[][] B) {
        int n=A.length;
        int[][] C=new int[n][n];
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                C[i][j]=A[i][j]-B[i][j];
        return C;
    }
    public int[][] add(int[][] A,int[][] B) {
        int n=A.length;
        int[][] C=new int[n][n];
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                C[i][j]=A[i][j]+B[i][j];
        return C;
    }
    public void join(int[][] C,int[][] P,int iB,int jB) {
        for(int i1=0,i2=iB; i1<C.length; i1++,i2++)
            for(int j1=0,j2=jB; j1<C.length; j1++,j2++)
                P[i2][j2]=C[i1][j1];
    }    
}