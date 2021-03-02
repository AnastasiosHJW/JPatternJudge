package DesignPatternAnnotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Patterns {
	DesignPattern[] value();

}
