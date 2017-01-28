// this implementation implements the methods,
// but the methods are null methods;
public class StringStackOld implements StackInterfaceOld {
    /**
     * This class is used to understant cooncepts related to interfaces.
     *
     *
     * @author      Pratik kulkarni
     */

    public void push( Object item )	{	       }
    public Object pop() 		{ return null; }
    public Object peek() 		{ return "hi"; }
    public boolean isEmpty() 		{ return true; }

    public static void main(String args[])	{
	StackInterfaceOld aStackInterfaceOld = new StringStackOld();
	aStackInterfaceOld.push("hello");	// why is here no warning?
	/* We are passing String type of object reference to the Object type,
	 * as Object class is super class of all classes, String will be
	 * implicitly type casted to type Object(this is called up casting)
	 * Compiler is OK with up casting, as there will be no loss of data
	 * thats why we are not getting any warning. */

	String aString = (String) aStackInterfaceOld.pop();

	/* Here we are getting compiler error because, pop function is returning
	 * null object and we are trying to assign it to String object (this is
	 * down casting) Compiler is not OK with down casting as we are trying to
	 * assign parent class reference to child class, which will result into
	 * loss of data. to remove the compilation error we have to type cast
	 * it to string object.
	 */
    }
/*
javac StringStackOld.java			// explain this error
StringStackOld.java:11: incompatible types	// explain what a cast would do
found   : java.lang.Object			// regarding possible compiler error detection
required: java.lang.String
	String aString = aStackInterfaceOld.pop();
	                                       ^
1 error

*/

}
