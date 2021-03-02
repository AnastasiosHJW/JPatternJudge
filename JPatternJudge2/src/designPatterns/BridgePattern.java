package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class BridgePattern extends DesignPattern{
	
	public BridgePattern()
	{
		patternName = "Bridge";
		
		roleNames.add("Abstraction");
		roleNames.add("Refined Abstraction");
		roleNames.add("Implementor");
		roleNames.add("Concrete Implementor");

	}
	
	
	@Override
	public void populateConstraints() {
		TypeDeclaration abstraction = roles.get("Abstraction").get(0);
		TypeDeclaration implementor = roles.get("Implementor").get(0);
		
		constraintList.add(new ClassIsAbstract(abstraction));
		constraintList.add(new ClassHasFieldOfType(abstraction, implementor));
		
		constraintList.add(new TypeIsInterface(implementor));
		
		for (TypeDeclaration refinedAbstraction:roles.get("Refined Abstraction"))
		{
			constraintList.add(new ClassExtendsSuperclass(refinedAbstraction,abstraction));
			constraintList.add(new ClassInvokesMethodOfClass(refinedAbstraction, implementor));
		}
		
		for (TypeDeclaration concreteImplementor:roles.get("Concrete Implementor"))
		{
			constraintList.add(new ClassImplementsInterface(concreteImplementor,implementor));
		}
		
	}

	@Override
	public BridgePattern createPattern() {
		return new BridgePattern();
	}

}
