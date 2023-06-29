public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    /* Create other classes to represent the result of calling the different assertThat methods. */
    
    static ObjectAssertion assertThat(Object o) {
        return new ObjectAssertion(o);
	//throw new UnsupportedOperationException();
    }
    static StringAssertion assertThat(String s) {
        return new StringAssertion(s);
	//throw new UnsupportedOperationException();
    }
    static BooleanAssertion assertThat(boolean b) {
        return new BooleanAssertion(b);
	//throw new UnsupportedOperationException();
    }
    static IntAssertion assertThat(int i) {
        return new IntAssertion(i);
	//throw new UnsupportedOperationException();
    }
}