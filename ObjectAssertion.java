public class ObjectAssertion {

    private Object o; //Private field.

    //Constructor
    public ObjectAssertion(Object o) {
        this.o = o;
    }

    /*
    Raise an exception (any exception) if o is null, otherwise return an object 
    such that more of the methods in this chain can be called.
    */
    public ObjectAssertion isNotNull() {
        if(o == null) {
            throw new UnsupportedOperationException("ObjectAssertion.isNotNull() raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if o is not null.
    public ObjectAssertion isNull() {
        if(o != null) {
            throw new UnsupportedOperationException("ObjectAssertion.isNull() raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if o is not .equals to o2.
    public ObjectAssertion isEqualTo(Object o2) {
        if(!o.equals(o2)) {
            throw new UnsupportedOperationException("ObjectAssertion.isEqualTo(Object o2) raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if o is .equals to o2.
    public ObjectAssertion isNotEqualTo(Object o2) {
        if(o.equals(o2)) {
            throw new UnsupportedOperationException("ObjectAssertion.isNotEqualTo(Object o2) raises exception.");
        } else {
            return this;
        }
    }

    //Raise exception if o is not an instance of Class c
    public ObjectAssertion isInstanceOf(Class c) {
        if(!c.isInstance(o)) {
            throw new UnsupportedOperationException("ObjectAssertion.isInstanceOf(Class c) raises exception.");
        } else {
            return this;
        }
    }

}
