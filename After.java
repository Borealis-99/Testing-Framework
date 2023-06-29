import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface After {
    /*
    Public instance methods that take no arguments.
    Should be run after each execution of a test method. 
    Multiple @After methods should be run in alphabetical order.
    */
}