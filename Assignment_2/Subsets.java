

/*
 * Subsets.java
 *
 * Version: $Id: Subsets.java, v 1.1 2015/09/02 13:50:40 $
 *
 *
 * Revisions:
 *     Revision 1.1 Kapil 2015/09/03 23:34:31
 *     Initial version
 */

/**
 * This program finds all possible combinations,
 * when 'n' people are attending party. In short it will find
 * power set of 'n'.
 *
 * @author      Pratik Kulkarni
 */
class AllSubSets {

	/**
	 *  The main method.
	 *
	 *  @param    args    Total number of people attending party.
	 */

	public static void main(String[] args) {

		// Total number of people attending party.
		int partyNumber;

		// If the input is Number then proceed, else throw invalid message.
		if(args.length==1 && args[0].matches("\\d+")) {

			//Opening brace of the set.
			System.out.print("{ ");
			partyNumber=Integer.parseInt(args[0]);
			if(partyNumber!=0) {
				int[] people= new int[partyNumber];

				//Storing all people numbers in an array.
				for(int count=partyNumber-1;count>=0;count--) {
					people[count]=partyNumber-count;
				}

				/* Power set of the set, having 'n' elements will have,
				2^n elements. */
				int powerSet=(int) Math.pow(2, partyNumber);

				/* Finding binary representation of each possibility
				in the power set. */
				for(int counter=0;counter<powerSet;counter++) {
					String strBin= Integer.toBinaryString(counter);
					String strrev="";

					// Making sure each possibility will have 'n' bits.
					while(strBin.length()!=partyNumber) {
						strBin="0"+strBin;
					}

					// Finding all possible combinations of people.
					for(int counter2=0;counter2<partyNumber;counter2++) {
						if(Character.toString(strBin.charAt(counter2)).equals("1")) {
							strrev=strrev+people[counter2];
						}
					}

					// Printing each combination in correct order.
					System.out.print("{"+new StringBuffer(strrev).reverse());
					if(counter==powerSet-1) {
						System.out.print("} ");
					}
					else {
						System.out.print("}, ");
					}
				}
			}

			// Ending brace of the set.
			System.out.print("}");
		}

		// Throws an error if invalid input is entered.
		else {
			System.out.println("Invalid input, please try again..");
		}

	}

}
