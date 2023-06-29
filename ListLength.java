import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface ListLength {
    public int min() default 0; //Public field.
    public int max() default 3; //Public field.
}