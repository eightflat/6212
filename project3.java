import java.util.Random;

class project3 { 
	
    static void longestCommonSubsequence(String a,String b,int n1,int n2) {
        int[][] length=new int[n1+1][n2+1]; 
         
        for (int i=0;i<=n1;i++) { // integer length of the longest common subsequence
            for (int j=0;j<=n2;j++) { 
                if (i == 0 || j == 0) // end of string reached
                    length[i][j]=0;
                else if (a.charAt(i-1) == b.charAt(j-1)) // character pairs match
                    length[i][j]=length[i-1][j-1]+1; 
                else // character pairs don't match
                    length[i][j]=Math.max(length[i-1][j],length[i][j-1]); 
            } 
        } 
       
        int index=length[n1][n2];
        int placeholder=index; // used for detailed output
        char[] output=new char[index+1];
        
        int i=n1,j=n2; // initialize output with length of strings
        
        while (i>0 && j>0) { // string output of the longest common subsequence
            if (a.charAt(i-1) == b.charAt(j-1)) { // character pairs match
                output[index-1]=a.charAt(i-1);
                i--;
                j--; 
                index--;	 
            } 
            else if (length[i-1][j] > length[i][j-1]) // characters pairs don't match
                i--; 
            else
                j--; 
        } 
        
        /* detailed output, commented out to reduce overhead
        System.out.println("String: "+a);
        System.out.println("String: "+b);
        System.out.print("Longest Common Subsequence: ");
        for(int k=0;k<=placeholder;k++) 
            System.out.print(output[k]); 
        System.out.println();
        System.out.println("Length: "+length[n1][n2]);
        */
    } 
    public static void main (String[] args) { 
        int stringBegin = 97; 
        int stringEnd = 122;
        
        int[] nValues={4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384};
        for (int index=0;index<nValues.length;index++) {
            int length = nValues[index];
            Random random = new Random();

            StringBuilder aGenerator = new StringBuilder(length); // generates first character string using "a-z" at random
            for (int i=0;i<length;i++) {
                int randomLimitedInt=stringBegin+(int)(random.nextFloat()*(stringEnd-stringBegin+1));
                aGenerator.append((char) randomLimitedInt);
            }
            String first = aGenerator.toString();

            StringBuilder bGenerator = new StringBuilder(length); // generates second character string using "a-z" at random
            for (int i=0;i<length;i++) {
                int randomLimitedInt=stringBegin+(int)(random.nextFloat()*(stringEnd-stringBegin+1));
                bGenerator.append((char) randomLimitedInt);
            }
            String second = bGenerator.toString();

            String a=first;
            String b=second;
            int n1 = a.length(); 
            int n2 = b.length(); 

            long startTimer=System.nanoTime();
            longestCommonSubsequence(a,b,n1,n2); // dynamic programming solution to be timed
            long endTimer=System.nanoTime();
            long duration=endTimer-startTimer;
            System.out.println("n1n2: "+length*length);
            System.out.println("Time: "+duration+" ns");
            System.out.println();
        }
    } 
    
} 
