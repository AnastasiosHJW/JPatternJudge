package DesignPatternAnnotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Repeatable(value = Patterns.class)
public @interface DesignPattern {
	public String pattern() default "";

	public int patternID() default 0;

	public String role() default "";
}