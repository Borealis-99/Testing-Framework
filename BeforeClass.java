import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeClass {
    /*
    Can only appear on static methods. 
    If they appear on an instance method, testClass should throw an exception (any exception).
    Should be executed once before any tests in the class are run. 
    If there are multiple @BeforeClass methods, they are executed in alphabetical order.
    Should be run even if there are no @Test methods in the class.
    */
}