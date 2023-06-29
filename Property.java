import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Property {
    /*
    Should be run in alphabetical order. You can assume, without checking, 
    that no method is annotated as both @Test and @Property.
    You can assume all property methods are instance methods that return boolean.
    All properties are run at most 100 times. They may run fewer times depending on limits of the search.
    */
}