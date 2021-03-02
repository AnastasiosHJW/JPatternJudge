package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class ObserverPattern extends DesignPattern{

	public ObserverPattern()
	{
		patternName = "Observer";
		
		roleNames.add("Subject");
		roleNames.add("Observer");
		roleNames.add("Concrete Observer");
		
	}
	@Override
	public void populateConstraints() {
		TypeDeclaration subject = roles.get("Subject").get(0);
		TypeDeclaration observer = roles.get("Observer").get(0);
		
		constraintList.add(new TypeIsInterface(observer));
		
		constraintList.add(new ClassHasFieldCollectionOfType(subject,observer));
		constraintList.add(new ClassHasMethodWithParameterOfType(subject,observer));
		constraintList.add(new ClassInvokesMethodOfClass(subject,observer));
		
		for (TypeDeclaration concreteObserver:roles.get("Observer"))
		{
			constraintList.add(new ClassImplementsInterface(concreteObserver, observer));
		}

	}

	@Override
	public ObserverPattern createPattern() {
		return new ObserverPattern();
	}

}
