package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class VisitorPattern extends DesignPattern{

	public VisitorPattern()
	{
		patternName = "Visitor";
		
		roleNames.add("Visitor");
		roleNames.add("Concrete Visitor");
		roleNames.add("Element");
		roleNames.add("Concrete Element");
	}
	
	@Override
	public void populateConstraints() {
		TypeDeclaration visitor = roles.get("Visitor").get(0);
		TypeDeclaration element = roles.get("Element").get(0);
		
		constraintList.add(new TypeIsInterface(visitor));
		constraintList.add(new TypeIsInterface(element));
		constraintList.add(new ClassHasMethodWithParameterOfType(element,visitor));
		
		for (TypeDeclaration concreteVisitor:roles.get("Concrete Visitor"))
		{
			constraintList.add(new ClassImplementsInterface(concreteVisitor,visitor));
		}
		
		for (TypeDeclaration concreteElement:roles.get("Concrete Element"))
		{
			constraintList.add(new ClassHasMethodWithParameterOfType(visitor, concreteElement));
			constraintList.add(new ClassImplementsInterface(concreteElement, element));
			constraintList.add(new ClassInvokesMethodOfClass(concreteElement,visitor));
		}

	}

	@Override
	public VisitorPattern createPattern() {
		return new VisitorPattern();
	}

}
