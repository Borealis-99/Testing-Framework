import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class Unit {

    /*
    Return 6 types of methods and their names in the class specified by the given name
    Sort the 6 lists of methods names by alphabetical order
    Args: the name of class
    Returns: mapping: method type -> names of all methods of that type
    */
    private static Map<String, List<String>> methodTypeToNames(String className) {
        List<String> testMethodNames = new LinkedList<>(), //Names of Test methods(cases)
                     beforeClassMethodNames = new LinkedList<>(), //Names of BeforeClass methods
                     beforeMethodNames = new LinkedList<>(), //Names of Before methods
                     afterClassMethodNames = new LinkedList<>(), //Names of AfterClass methods
                     afterMethodNames = new LinkedList<>(), //Names of After methods
                     propertyMethodNames = new LinkedList<>(); //Names of Property methods(used in Part 2)

        Map<String, List<String>> result = new HashMap<>() {{
            put("TestMethod", testMethodNames);
            put("BeforeClassMethod", beforeClassMethodNames);
            put("BeforeMethod", beforeMethodNames);
            put("AfterClassMethod", afterClassMethodNames);
            put("AfterMethod", afterMethodNames);
            put("PropertyMethod", propertyMethodNames);
        }};

        //Get the Class object c associated with the class of the given string name
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch(ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new UnsupportedOperationException(e + ": The class cannot be located.");
        }

        //Iterate over all public methods of object c
        for(Method meth: c.getMethods()) {
            String methodName = meth.getName(); //The name of method meth
            int numAnnotations = meth.getAnnotations().length; //Number of annotations on method meth
            if(numAnnotations > 1) { //If method meth has more than one annotation
                throw new UnsupportedOperationException("Method " + methodName + " has more than one annotation.");
            }
            else if(numAnnotations < 1) {  //If method meth has no annotation
                continue;
            }
            else if(numAnnotations == 1) { //If method meth has only one annotation
                Annotation a = meth.getAnnotations()[0];
                if(a instanceof Test) { //If annotation a is Test
                    result.get("TestMethod").add(methodName);
                    continue;
                }
                else if(a instanceof BeforeClass) { //If annotation a is BeforeClass
                    if(!Modifier.isStatic(meth.getModifiers())) { //If BeforeClass annotation a doesn't appear on static method
                        throw new UnsupportedOperationException("BeforeClass method " + methodName + " doesn't appear on static method.");
                    }
                    else {result.get("BeforeClassMethod").add(methodName); continue;}
                }
                else if(a instanceof Before) { //If annotation a is Before
                    result.get("BeforeMethod").add(methodName);
                    continue;
                }
                else if(a instanceof AfterClass) { //If annotation a is AfterClass
                    if(!Modifier.isStatic(meth.getModifiers())) { //If AfterClass annotation a doesn't appear on static method
                        throw new UnsupportedOperationException("AfterClass method " + methodName + " doesn't appear on static method.");
                    }
                    else {result.get("AfterClassMethod").add(methodName); continue;}
                }
                else if(a instanceof After) { //If annotation a is After
                    result.get("AfterMethod").add(methodName);
                    continue;
                }
                else if(a instanceof Property) { //If annotation a is Property
                    result.get("PropertyMethod").add(methodName);
                }
            }
        }

        //Sort all 6 lists of methods names by alphabetical order
        for(String methodType: result.keySet()) {
            Collections.sort(result.get(methodType));
        }

        return result;
    }


    /*
    Implement this!
    Given a class name, this method runs all the test cases(methods) in that class.
    The return value is a map where the keys of the map are the test cases names,
    and the values are either the exception or error thrown by a test case
    (indicating that test case failed) or null for test cases that passed.
    */
    public static Map<String, Throwable> testClass(String name) {
        //mapping: test case(method) name -> Throwable
        Map<String, Throwable> result = new HashMap<>();
        //mapping: method type -> names of all methods of that type
        Map<String, List<String>> typeToNames = methodTypeToNames(name);
        //mapping: method name -> method
        Map<String, Method> nameToMethod = new HashMap<>();

        //Get the Class object c associated with the class of the given string name
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new UnsupportedOperationException(e + ": The class cannot be located.");
        }

        //Get all methods and their names of class c
        for(Method meth: c.getMethods()) {
            nameToMethod.put(meth.getName(), meth);
        }

        //Execute all BeforeClass methods in alphabetical order
        for(String bcMethodName: typeToNames.get("BeforeClassMethod")) { //Iterate over the list of BeforeClass method names
            //Get the relevant BeforeClass method bcMeth()
            Method bcMeth = nameToMethod.get(bcMethodName);
            try {
                bcMeth.invoke(c); //Invoke bcMeth() on class c
            } catch (IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new UnsupportedOperationException(e + ": BeforeClass method invocation exception.");
            }
        }

        //Iterate over all Test methods in alphabetical order
        for(String tMethodName: typeToNames.get("TestMethod")) { //Iterate over the list of Test method names
            //Get the constructor cons of class c
            Constructor<?> cons = null;
            try {
                cons = c.getConstructor();
            } catch (NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Construct an instance(object) o of class c
            Object o = null;
            try {
                o = cons.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Initialize return value with test case name and null(assume the test case will pass)
            result.put(tMethodName, null);

            //Execute all Before methods in alphabetical order
            for(String bMethodName: typeToNames.get("BeforeMethod")) { //Iterate over the list of Before method names
                //Get the relevant Before method bMeth()
                Method bMeth = nameToMethod.get(bMethodName);
                try {
                    bMeth.invoke(o); //Invoke bMeth() on instance o
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new UnsupportedOperationException(e + ": Before method invocation exception.");
                }
            }

            //Get the Test method tMeth()
            Method tMeth = nameToMethod.get(tMethodName);
            try {
                tMeth.invoke(o); //Invoke tMeth() on instance o
            } catch (IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //If exception emerges, then replace null with throwable
                //Unwrap exception! Unwrap an InvocationTargetException.
                result.put(tMethodName, e.getCause());
            }

            //Execute all After methods in alphabetical order
            for(String aMethodName: typeToNames.get("AfterMethod")) { //Iterate over the list of Before method names
                //Get the relevant After method aMeth()
                Method aMeth = nameToMethod.get(aMethodName);
                try {
                    aMeth.invoke(o); //Invoke aMeth() on instance o
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new UnsupportedOperationException(e + ": After method invocation exception.");
                }
            }
        }

        //Execute all AfterClass methods in alphabetical order
        for(String acMethodName: typeToNames.get("AfterClassMethod")) { //Iterate over the list of AfterClass method names
            //Get the relevant AfterClass method acMeth()
            Method acMeth = nameToMethod.get(acMethodName);
            try {
                acMeth.invoke(c); //Invoke acMeth() on class c
            } catch (IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new UnsupportedOperationException(e + ": AfterClass method invocation exception.");
            }
        }

        return result;
	//throw new UnsupportedOperationException();
    }


    /*
    Get the list of all possible values for the argument annotated with annotation a
    Args: annotation, mapping: method name -> method, instance(object)
    Returns: the list of all possible values for the argument annotated with annotation a
    */
    private static List valsForArgWithAnn(Annotation a, Map<String, Method> nameToMethod, Object o) {
        List valsForArg = new LinkedList<>();

        if (a instanceof IntRange) { //If annotation a is @IntRange. Equivalent to a.annotationType().equals(IntRange.class)
            //Downcast a to IntRange to get its fields min() and max()
            for (int i = ((IntRange) a).min(); i <= ((IntRange) a).max(); i++) { //Iterate over the range of integer
                valsForArg.add(i);
            }
        }
        else if (a instanceof StringSet) { //If annotation a is @StringSet
            valsForArg.addAll(Arrays.asList(((StringSet) a).strings())); //Add the array of strings to the list
        }
        else if (a instanceof ListLength) { //If annotation a is @ListLength
            for (int l = ((ListLength) a).min(); l <= ((ListLength) a).max(); l++) { //Iterate over the range of list length
                valsForArg.add(l);
            }
        }
        else if (a instanceof ForAll) { //If annotation a is @ForAll
            try {
                Method meth = nameToMethod.get(((ForAll) a).name()); //Get the instance method meth that will be called called from its name
                for (int t = 1; t <= ((ForAll) a).times(); t++) { //Iterate over the times count of method calls
                    Object obj = meth.invoke(o); //Call meth
                    valsForArg.add(obj); //Add the values of method call to the list
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new UnsupportedOperationException("@ForAll method calling raises exception: " + e);
            }
        }
        //If annotation a is not among @IntRange, @StringSet, @ListLength, and @ForAll
        else throw new UnsupportedOperationException("Unknown annotation type: " + a);

        return valsForArg;
    }
    

    /*
    Args: 
        length of list, list of all possible values for argument for the list
    Returns:
        List<List<Object>>: list of all possible combinations(lists) of arguments for the list of length listLength
    */
    private static List combosOfArgsForListOfLength(int listLength, List valsForArg) {
        List result = new LinkedList();

        //Base case
        if (listLength == 1) {
            for (Object o : valsForArg) {
                //Each List l1 is a List<Object> of length 1
                List l1= new LinkedList<>();
                l1.add(o);
                result.add(l1);
            }
        } 
        else { //If listLength > 1
            for (Object o : valsForArg) {
                //Recursion
                for (Object l : combosOfArgsForListOfLength(listLength - 1, valsForArg)) { 
                    //Downcasting. Each List lg1 is a list<Object> of length listLength - 1.
                    List lg1 = (List) l;
                    //Pad lg1 with each possible argument value
                    lg1.add(o);
                    result.add(lg1);
                }
            }
        }

        return result;
    }


    /*
    Args: 
        minimum list length, maximum list length, 
        list of all possible values for argument for the list
    Returns: 
        List<List<Object>>: list of all possible combinations(lists) of arguments 
                            for the list of length between range [minLength, maxLength]
    */
    private static List combosOfArgsForListBetweenRange(int minLength, int maxLength, List valsForArg) {
        List result = new LinkedList<>();

        for (int listLength = minLength; listLength <= maxLength; listLength++) { //Iterate over all possible list lengths
            if (listLength == 0) {
                List emptyList = new LinkedList<>();
                result.add(emptyList); //Add the empty list
            }
            else { //If listLength > 0
                //Add all possible combinations(lists) of arguments for the list of length listLength
                result.addAll(combosOfArgsForListOfLength(listLength, valsForArg)); 
            }
        }

        return result;
    }


    /*
    Get all possible combinations(arrays) of all arguments
    Args: 
        List<List>: list of all possible values for all arguments
    Returns: 
        list<Object[]>: list of all possible combinations(arrays) of all arguments
    */
    private static List<Object[]> combosOfArgs(List<List> valsForArgs) {
        List<Object[]> result = new LinkedList();
        int numArguments = valsForArgs.size(); //The number of all arguments

        //Base case
        if (numArguments == 1) { //If there's only one argument
            for (Object o : valsForArgs.get(0)) { //Iterate over all values for the first(and last) argument
                Object[] arrayOfArg = new Object[] {o}; //Each array arrayOfArg is of length 1 and initialized with value o
                result.add(arrayOfArg);
            }
        } 
        else { //If there are multiple arguments
            for (Object o : valsForArgs.get(numArguments - 1)) { //Iterate over all values for the last argument
                for (Object[] args : combosOfArgs(valsForArgs.subList(0, numArguments - 1))) { //Recursion
                    //Each array args is of length numArguments - 1
                    //Create a new array arrayOfArgs by adding o to the end of args
                    Object[] arrayOfArgs = new Object[args.length + 1];
                    for(int i = 0; i < args.length; i++) {
                        arrayOfArgs[i] = args[i];
                    }
                    arrayOfArgs[args.length] = o;

                    //Add arrayOfArgs to the result
                    result.add(arrayOfArgs);
                }
            }
        }

        return result;
    }


    /*
    Implement this!
    Args: name of class
    Returns:
        mapping: 1. name of (Property) method ->
                   the first array of arguments for which the Property method returned false or threw a Throwable
                 2. Otherwise, if the Property runs until termination with only true return values,
                    name of (Property) method -> null.
                    Then quickCheckClass will run the next property in the class.
    */
    public static Map<String, Object[]> quickCheckClass(String name) {
        //The result that will be returned
        Map<String, Object[]> result = new HashMap<>();
        //mapping: method type -> names of all methods of that type
        Map<String, List<String>> typeToNames = methodTypeToNames(name);
        //mapping: method name -> method
        Map<String, Method> nameToMethod = new HashMap<>();

        //Get the Class object c associated with the class of the given string name
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new UnsupportedOperationException(e + ": The class cannot be located.");
        }

        //Get all methods and their names of class c
        for(Method meth: c.getMethods()) {
            nameToMethod.put(meth.getName(), meth);
        }

        //Iterate over all Property method names in alphabetical order
        for(String pMethodName: typeToNames.get("PropertyMethod")) {

            //Get the constructor cons of class c
            Constructor<?> cons = null;
            try {
                cons = c.getConstructor();
            } catch (NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Construct an instance(object) o of class c
            Object o = null;
            try {
                o = cons.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Initialize return value with Property method name and null(assume the Property method will pass)
            result.put(pMethodName, null);
            //Get the Property method pMeth()
            Method pMeth = nameToMethod.get(pMethodName);
            //The list of all possible values for all arguments in pMeth()
            List<List> valsForArgs = new LinkedList<>();
            //Get the array of AnnotatedType objects representing the types of the formal parameters of Property method pMeth
            AnnotatedType[] aTypes = pMeth.getAnnotatedParameterTypes();

            //Get the list of all possible values for all arguments in pMeth()
            for(AnnotatedType aType: aTypes) { //Iterate over all types of parameters of pMeth
                Annotation a = aType.getAnnotations()[0]; //Get the first annotation on aType
                if(!(aType instanceof AnnotatedParameterizedType)) { //If AnnotatedType object aType is not an instance of AnnotatedParameterizedType
                    //Get the list of all possible values for the argument in pMeth() annotated with annotation a
                    List valsForArg = valsForArgWithAnn(a, nameToMethod, o);
                    //Add to the list of all possible values for all arguments in pMeth()
                    valsForArgs.add(valsForArg);
                }
                else { //If AnnotatedType object aType is an instance of AnnotatedParameterizedType
                    if(a instanceof ListLength) { //If the first annotation a on aType is @ListLength
                        //Downcast a to ListLength
                        ListLength l = (ListLength) a;
                        //Typecast AnnotatedType aType to AnnotatedParameterizedType apType
                        AnnotatedParameterizedType apType = (AnnotatedParameterizedType) aType;
                        //Return the potentially annotated actual type arguments of this parameterized type apType
                        AnnotatedType[] gTypes = apType.getAnnotatedActualTypeArguments(); //Generic types
                        AnnotatedType gType = gTypes[0];
                        Annotation ga = gType.getAnnotations()[0]; //Get the first annotation ga
                        //The list of all possible values for the argument in pMeth() annotated with annotation ga
                        List valsForArg = valsForArgWithAnn(ga, nameToMethod, o);
                        //The list of all possible combinations(lists) of arguments for the list of length between range [l.min(), l.max()]
                        List cArgsList = combosOfArgsForListBetweenRange(l.min(), l.max(), valsForArg);
                        valsForArgs.add(cArgsList); //addAll?
                    }
                }
            }

            //Get the list of all possible combinations(arrays) of all arguments
            List<Object[]> combos= combosOfArgs(valsForArgs);
            int executionCount = 1; //Initialize the times count of single Property method calls
            for(Object[] combo: combos) { //Iterate over all possible combinations(arrays) of arguments
                if(executionCount > 100) { //If pMeth() runs over 100 times
                    break; //Run the next Property
                }

                executionCount += 1;

                try {
                    Boolean rslt = (Boolean) pMeth.invoke(o, combo); //The result of calling o.pMeth(combo)
                    if(rslt == false) { //If Property method propM returns false
                        result.put(pMethodName, combo);
                        break; //Run the next Property
                    }
                } catch (IllegalAccessException | InvocationTargetException e) { //If Property method propM throws a Throwable
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    result.put(pMethodName, combo);
                    break; //Run the next Property
                }
            }

        }

        return result;
	//throw new UnsupportedOperationException();
    }
    
}