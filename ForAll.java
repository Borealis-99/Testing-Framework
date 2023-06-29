import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface ForAll {
    public String name() default ""; //Public field.
    public int times() default 3; //Public field.
}