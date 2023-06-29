import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
    /*
    Public instance methods that take no arguments.
    Should be run before each execution of a test method. 
    Multiple @Before methods should be run in alphabetical order.
    */
}