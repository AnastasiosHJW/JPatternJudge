package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class StrategyPattern extends DesignPattern{
	
	public StrategyPattern()
	{
		patternName = "Strategy";
		
		roleNames.add("Strategy");
		roleNames.add("Concrete Strategy");
		roleNames.add("Context");
	}

	@Override
	public void populateConstraints() {
		TypeDeclaration strategy = roles.get("Strategy").get(0);
		TypeDeclaration context = roles.get("Context").get(0);
		
		constraintList.add(new TypeIsInterface(strategy));
		constraintList.add(new ClassHasFieldOfType(context, strategy));
		constraintList.add(new ClassInvokesMethodOfClass(context, strategy));
		
		for (TypeDeclaration concreteStrategy:roles.get("Concrete Strategy"))
		{
			constraintList.add(new ClassImplementsInterface(concreteStrategy, strategy));
		}
	}

	@Override
	public StrategyPattern createPattern() {
		return new StrategyPattern();
	}

}
