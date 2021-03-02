package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class StatePattern extends DesignPattern{
	
	public StatePattern()
	{
		patternName = "State";
		
		roleNames.add("State");
		roleNames.add("Concrete State");
		roleNames.add("Context");
	}

	@Override
	public void populateConstraints() {
		TypeDeclaration state = roles.get("State").get(0);
		TypeDeclaration context = roles.get("Context").get(0);
		
		constraintList.add(new TypeIsInterface(state));
		constraintList.add(new ClassHasMethodWithParameterOfType(state,context));
		
		constraintList.add(new ClassHasFieldOfType(context, state));
		constraintList.add(new ClassInvokesMethodOfClass(context,state));
		
		for (TypeDeclaration concreteState:roles.get("State"))
		{
			constraintList.add(new ClassImplementsInterface(concreteState,state));
		}
	}

	@Override
	public StatePattern createPattern() {
		return new StatePattern();
	}

}
