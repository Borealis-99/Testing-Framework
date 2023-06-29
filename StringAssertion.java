public class StringAssertion {
    
    private String s; //Private field.

    //Constructor
    public StringAssertion(String s) {
        this.s = s;
    }

    /*
    Raise an exception (any exception) if s is null, otherwise return an object 
    such that more of the methods in this chain can be called.
    */
    public StringAssertion isNotNull() {
        if(s == null) {
            throw new UnsupportedOperationException("StringAssertion.isNotNull() raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if s is not null.
    public StringAssertion isNull() {
        if(s != null) {
            throw new UnsupportedOperationException("StringAssertion.isNull() raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if s is not .equals to o.
    public StringAssertion isEqualTo(Object o) {
        if(!s.equals(o)) {
            throw new UnsupportedOperationException("StringAssertion.isEqualTo(Object o) raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if s is .equals to o.
    public StringAssertion isNotEqualTo(Object o) {
        if(s.equals(o)) {
            throw new UnsupportedOperationException("StringAssertion.isNotEqualTo(Object o) raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if s does not start with s2.
    public StringAssertion startsWith(String s2) {
        if(!s.startsWith(s2)) {
            throw new UnsupportedOperationException("StringAssertion.startsWith(String s2) raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if s is not the empty string.
    public StringAssertion isEmpty() {
        if(!s.isEmpty()) {
            throw new UnsupportedOperationException("StringAssertion.isEmpty() raises exception.");
        } else {
            return this;
        }
    }

    //Raises an exception if s does not contain s2.
    public StringAssertion contains(String s2) {
        if(!s.contains(s2)) {
            throw new UnsupportedOperationException("StringAssertion.contains(String s2) raises exception.");
        } else {
            return this;
        }
    } 

}
