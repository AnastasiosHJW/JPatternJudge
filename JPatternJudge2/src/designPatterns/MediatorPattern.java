package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class MediatorPattern extends DesignPattern{
	
	public MediatorPattern()
	{
		patternName = "Mediator";
		
		roleNames.add("Mediator");
		roleNames.add("Concrete Mediator");
		roleNames.add("Colleague");
		roleNames.add("Concrete Colleague");

	}

	@Override
	public void populateConstraints() {
		TypeDeclaration mediator = roles.get("Mediator").get(0);
		
		constraintList.add(new TypeIsInterface(mediator));
		
		for (TypeDeclaration concreteMediator:roles.get("Mediator"))
		{
			constraintList.add(new ClassImplementsInterface(concreteMediator, mediator));
			for (TypeDeclaration concreteColleague:roles.get("Concrete Colleague"))
			{
				constraintList.add(new ClassHasFieldOfType(concreteMediator,concreteColleague));
			}
			
		}
		
		TypeDeclaration colleague = roles.get("Colleague").get(0);
		
		constraintList.add(new ClassIsAbstract(colleague));
		constraintList.add(new ClassHasFieldOfType(colleague,mediator));
		
		for (TypeDeclaration concreteColleague:roles.get("Concrete Colleague"))
		{
			constraintList.add(new ClassExtendsSuperclass(concreteColleague, colleague));
		}

	}

	@Override
	public MediatorPattern createPattern() {
		return new MediatorPattern();
	}

}
