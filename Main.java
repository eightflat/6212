package pkg2.pkg3;
 
import java.util.Random;
 
public class Main
{
    public int[][] multiply(int[][] A,int[][] B) {        
        int n=A.length;
        int[][] product=new int[n][n];
        if (n==1)
            product[0][0]=A[0][0]*B[0][0];
        else {
            int[][] A11=new int[n/2][n/2];
            int[][] A12=new int[n/2][n/2];
            int[][] A21=new int[n/2][n/2];
            int[][] A22=new int[n/2][n/2];
            int[][] B11=new int[n/2][n/2];
            int[][] B12=new int[n/2][n/2];
            int[][] B21=new int[n/2][n/2];
            int[][] B22=new int[n/2][n/2];
 
            split(A,A11,0,0);
            split(A,A12,0,n/2);
            split(A,A21,n/2,0);
            split(A,A22,n/2,n/2);

            split(B,B11,0,0);
            split(B,B12,0,n/2);
            split(B,B21,n/2,0);
            split(B,B22,n/2,n/2);
 
            int [][] M1=multiply(add(A11,A22),add(B11,B22));
            int [][] M2=multiply(add(A21,A22),B11);
            int [][] M3=multiply(A11,subtract(B12,B22));
            int [][] M4=multiply(A22,subtract(B21,B11));
            int [][] M5=multiply(add(A11,A12),B22);
            int [][] M6=multiply(subtract(A21,A11),add(B11,B12));
            int [][] M7=multiply(subtract(A12,A22),add(B21,B22));
 
            int [][] C11=add(subtract(add(M1,M4),M5),M7);
            int [][] C12=add(M3,M5);
            int [][] C21=add(M2,M4);
            int [][] C22=add(subtract(add(M1,M3),M2),M6);
 
            join(C11,product,0,0);
            join(C12,product,0,n/2);
            join(C21,product,n/2,0);
            join(C22,product,n/2,n/2);
        } 
        return product;
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
    public void split(int[][] P,int[][] C,int iB,int jB) {
        for(int i1=0,i2=iB; i1<C.length; i1++,i2++)
            for(int j1=0,j2=jB; j1<C.length; j1++,j2++)
                C[i1][j1]=P[i2][j2];
    }
    public void join(int[][] C,int[][] P,int iB,int jB) {
        for(int i1=0,i2=iB; i1<C.length; i1++,i2++)
            for(int j1=0,j2=jB; j1<C.length; j1++,j2++)
                P[i2][j2]=C[i1][j1];
    }    
    
    public static void main (String[] args) {
        int testValues[]={4,8,16,32,64,128,256,512,1024,2048,4096};
        Random RNG=new Random();
        Main test=new Main();
    
        for (int index=0; index<testValues.length; index++) {
            int n=testValues[index];

            int[][] A=new int[n][n];
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++)
                    A[i][j]=RNG.nextInt(1);
            int[][] B=new int[n][n];
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++)
                    B[i][j]=RNG.nextInt(1);
            double startTimer=System.nanoTime();
            int[][] C=test.multiply(A,B);
            double endTimer=System.nanoTime();
            double duration=endTimer-startTimer;
        
            /*for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++)
                System.out.print(C[i][j]+" ");
            System.out.println();
            }*/
        
            System.out.println("Array Size: "+testValues[index]);
            System.out.println("Time: "+duration);
            System.out.println();
        }
    }
}