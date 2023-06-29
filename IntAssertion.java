public class IntAssertion {
            
    private int i; //Private field.

    //Constructor
    public IntAssertion(int i) {
        this.i = i;
    }

    //Raises an exception if i != i2.
    public IntAssertion isEqualTo(int i2) {
        if(i != i2) {
            throw new UnsupportedOperationException("IntAssertion.isEqualTo(int i2) raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if i >= i2.
    public IntAssertion isLessThan(int i2) {
        if(i >= i2) {
            throw new UnsupportedOperationException("IntAssertion.isLessThan(int i2) raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if i <= i2.
    public IntAssertion isGreaterThan(int i2) {
        if(i <= i2) {
            throw new UnsupportedOperationException("IntAssertion.isGreaterThan(int i2) raises exception.");
        } else {
            return this;
        }
    }

}
