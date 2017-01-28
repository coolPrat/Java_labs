/*
 * OnceOrMany.java
 *
 * Version: $Id: OnceOrMany.java, v 1.1 2015/09/07 19:50:40 $
 *
 *
 * Revisions:
 *     Revision 1.1 Kapil 2015/09/07 23:34:31
 *     Initial version
 */

/**
 * This program is for understanding String related concepts.
 * each of the string expression below is explained with
 * the comment above.
 *
 * @author      Pratik Kulkarni
 */

class OnceOrMany {

    /**
     * This method will compare references of two string objects..
     *
     * @param       literal	     First String.
     * @param       aNewString	 Second String.
     *
     * @return                 	 Boolean true if references of strings are same
     * 							 or else false.
     */
	public static boolean singelton(String literal, String aNewString)	{
		return ( literal == aNewString );
	}

	/**
	 *  The main method.
	 *
	 *  @param       args        command line arguments (ignored)
	 */

	public static void main( String args[] ) {

		// Creates new string literal stored in String pool.

		String aString = "xyz";

		/* Java evaluates expression from left to right, so in this case,
		 * it will append first two strings i.e. (1.	xyz == aString:	xyz) and
		 * then compare its reference with aString.
		 * So, output here will be "false". */

		System.out.println("1.	xyz == aString:	" +     "xyz" == aString   );

		/* In java, precedence of parenthesis is highest, so it will first
		 * evaluate second expression i.e. ("xyz" == aString), which returns
		 * us "true" and finally it will append it to first string.
		 * So, output here will be "2.	xyz == aString:	true". */

		System.out.println("2.	xyz == aString:	" +   ( "xyz" == aString ) );

		// Creates new instance of string "xyz".

		String newString = new String("xyz");

		/* In Java, when we create any string object with new keyword,
		 * we are asking to create new instance of the string, even if
		 * the particular string is present in the string pool, it will
		 * not assign same object reference to the string created with new
		 * keyword. So, output here will be,  "xyz == new String(xyz)
		 *												false" */

		System.out.println("xyz == new String(xyz)\n	" + ("xyz" == newString) );

		/* In Java, when we create multiple string literals having same values,
		 * only one object will be created for all of them, so all of them will
		 * be sharing same object reference. This is possible for String class
		 * specially only because, string are immutable.
		 * So, output here will be "1: true".  */

		System.out.println("1: " + singelton("xyz", "xyz"));

		/* Here, we are comparing string literal and new instance of the same
		 * string, even though their values are same, but their object references
		 * will be different,as discussed above.
		 * So, output will be "2: false". */

		System.out.println("2: " + singelton("xyz", new String("xyz") ));

		/* In below code, java compiler first optimizes the right hand side
		 * expression before comparing. so, both of the string literals refer
		 * to the same object and have same reference.
		 * So, output will be "3: true". */

		System.out.println("3: " + singelton("xyz", "xy" + "z"));

		/* In below expression, similar to above, compiler will first optimizes
		 * expression on left. and thus both the string refer to the same object.
		 * So, output here will be "4: true".*/

		System.out.println("4: " + singelton("x" + "y" + "z", "xyz"));

		/* Below, on left hand side, we are creating new instance of string "z"
		 * so after optimizing the left hand side, new string will not have same
		 * object reference as that of the string in String pool.
		 * So, output here will be, "5: false". */

		System.out.println("5: " + singelton("x" + "y" + new String("z"), "xyz"));

		/* In below code, while optimizing the expression on left hand side
		 * first precedence will be given to the ones in the parenthesis.
		 * but after evaluating the expression, at the end it will refer to
		 * the same object in the string pool.
		 * So, output here will be, "6: true". */
		System.out.println("6: " + singelton("x" + ( "y" + "z"), "xyz"));

		/* Below expression is same as that of above except we are appending
		 * character to the string. here, result will be implicitly type casted
		 * to the string. ultimately it will refer to the same string in the pool.
		 * So, output here will be "7: true". */

		System.out.println("7: " + singelton('x' + ( "y" + "z"), "xyz"));

	}
}
