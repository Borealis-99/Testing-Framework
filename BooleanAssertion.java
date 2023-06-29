public class BooleanAssertion {
        
    private boolean b; //Private field.

    //Constructor
    public BooleanAssertion(boolean b) {
        this.b = b;
    }

    //Raises an exception if b != b2.
    public BooleanAssertion isEqualTo(boolean b2) {
        if(b != b2) {
            throw new UnsupportedOperationException("BooleanAssertion.isEqualTo(boolean b2) raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if b is false.
    public BooleanAssertion isTrue() {
        if(b == false) {
            throw new UnsupportedOperationException("BooleanAssertion.isTrue() raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if b is true.
    public BooleanAssertion isFalse() {
        if(b == true) {
            throw new UnsupportedOperationException("BooleanAssertion.isFalse() raises exception.");
        } else {
            return this;
        }
    }

}
