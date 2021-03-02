package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.Specific.SingletonConstraint;

public class SingletonPattern extends DesignPattern{
	
	public SingletonPattern()
	{
		patternName = "Singleton";
		
		roleNames.add("Singleton");
		
	}

	@Override
	public void populateConstraints() {
		TypeDeclaration singleton = roles.get("Singleton").get(0);
		
		constraintList.add(new SingletonConstraint(singleton));

	}

	@Override
	public SingletonPattern createPattern() {
		return new SingletonPattern();
	}

}
