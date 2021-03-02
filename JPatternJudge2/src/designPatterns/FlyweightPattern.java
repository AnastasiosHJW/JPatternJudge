package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;
import constraints.TypeConstraints.Specific.FlyweightFactoryConstraint;

public class FlyweightPattern extends DesignPattern{
	
	public FlyweightPattern()
	{
		patternName = "Flyweight";
		
		roleNames.add("Client");
		roleNames.add("Flyweight");
		roleNames.add("Concrete Flyweight");
		roleNames.add("Flyweight Factory");

	}

	@Override
	public void populateConstraints() {
		TypeDeclaration client = roles.get("Client").get(0);
		TypeDeclaration flyweight = roles.get("Flyweight").get(0);
		TypeDeclaration factory = roles.get("Flyweight Factory").get(0);
		
		constraintList.add(new TypeIsInterface(flyweight));
		constraintList.add(new ClassHasFieldOfType(client, flyweight));
		constraintList.add(new ClassInvokesMethodOfClass(client, factory));
		constraintList.add(new FlyweightFactoryConstraint(factory,flyweight));
		
		for (TypeDeclaration cocreteFlyweight:roles.get("Concrete Flyweight"))
		{
			constraintList.add(new ClassImplementsInterface(cocreteFlyweight, flyweight));
		}

	}

	@Override
	public FlyweightPattern createPattern() {
		return new FlyweightPattern();
	}

}
