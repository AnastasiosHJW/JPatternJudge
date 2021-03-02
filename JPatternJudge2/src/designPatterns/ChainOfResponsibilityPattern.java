package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class ChainOfResponsibilityPattern extends DesignPattern{

	public ChainOfResponsibilityPattern()
	{
		patternName = "Chain Of Responibility";
		
		roleNames.add("Handler");
		roleNames.add("Concrete Handler");
		
	}
	
	@Override
	public void populateConstraints() {
		TypeDeclaration handler = roles.get("Handler").get(0);
		
		constraintList.add(new TypeIsInterface(handler));
		constraintList.add(new ClassHasMethodWithParameterOfType(handler, handler));
		
		for (TypeDeclaration concreteHandler:roles.get("ConcreteHandler"))
		{
			constraintList.add(new ClassImplementsInterface(concreteHandler, handler));
			constraintList.add(new ClassHasFieldOfType(concreteHandler, handler));
		}
		
	}

	@Override
	public ChainOfResponsibilityPattern createPattern() {
		return new ChainOfResponsibilityPattern();
	}

}
