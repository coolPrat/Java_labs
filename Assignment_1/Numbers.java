
/*
 * Numbers.java
 *
 * Version: $Id: Numbers.java, v 1.1 2015/08/27 21:08:40 $
 *
 *
 * Revisions:
 *     Revision 1.1 Kapil dole Pratik kulkarni  2015/08/28 23:40:30
 *     Initial version
 */

/**
 * This program finds all numbers between 2 and 100000,
 * which satisfies following 3 conditions
 * 1) Number 'n' is 'kth' primary number.(n=73, k=21)
 * 2) Its mirror 'm' is also primary and
 * it index is also mirror of index of n. (m=37, k'=12)
 * 3)binary representation of 'n' is palindrome.
 *
 * @author      Pratik Kulkarni
 */

public class Numbers{
    /**
     *  The main program.
     *
     *
     *  @param    args    command line arguments (ignored)
     */

	//Array for checking prime number and finding its index.
	public static int[][] javaNumbers= new int[100000][2];

	public static void main(String[] args){

		int mirrorIndex;  //index for mirror of number.

		String[] str = new String[6]; // used for comparing two strings.

	    //Calling function for listing prime numbers and their indexes.
		primeList();

		System.out.println("java Numbers");


		for(int k=0;k<100000;k++){
			// Checking if the given number is prime.
			if(javaNumbers[k][0]==1) {
				str[0]=String.valueOf(k);
				str[1]= new StringBuffer(str[0]).reverse().toString();
				mirrorIndex=Integer.parseInt(str[1]);

				//Checking if the mirror image of number is prime.
				if(javaNumbers[mirrorIndex][0]==1) {
					str[2]=String.valueOf(javaNumbers[k][1]);
					str[3]= new StringBuffer(
							String.valueOf(javaNumbers[mirrorIndex][1]))
							.reverse().toString();
					//Checking if index of prime number and
					//its reverse are mirror images.
					if(str[3].equals(str[2])) {
						str[4]=Integer.toBinaryString(k);
						str[5]=new StringBuffer(str[4]).reverse().toString();
						//Checking if the binary representation
						//of the number is palindrome.
						if(str[4].equals(str[5])) {
							System.out.println(k);
						}
					}
				}
			}
		}
	}

	/**
	 * This function will list out all primary numbers between 2 and 100000,.
	 * Also list out their index.
	 *
	 */
	public static void primeList(){

		//Checking whether number is prime or not, and if it is prime, storing its index.
		int j,counter, primeIndex=1;
			for(int i=2;i<=100000;i++) {
				counter=0;
				for(j=2;j<=i;j++){
					if(i%j==0){
					counter++;
				}
			}
			if(counter==1)
			{
				javaNumbers[j-1][0]=1;
				javaNumbers[j-1][1]=primeIndex;
				primeIndex++;
			}
		}
	}
}
