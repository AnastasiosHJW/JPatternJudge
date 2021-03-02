package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class MementoPattern extends DesignPattern{

	public MementoPattern()
	{
		patternName = "Memento";
		
		roleNames.add("Memento");
		roleNames.add("Originator");
		roleNames.add("Caretaker");

	}
	
	@Override
	public void populateConstraints() {
		TypeDeclaration memento = roles.get("Memento").get(0);
		TypeDeclaration originator = roles.get("Originator").get(0);
		TypeDeclaration caretaker = roles.get("Caretaker").get(0);
		
		constraintList.add(new ClassHasFieldCollectionOfType(caretaker,memento));
		constraintList.add(new ClassHasFieldOfType(caretaker, originator));
		
		constraintList.add(new ClassHasMethodWithReturnType(originator, memento));
		constraintList.add(new ClassInstantiatesObjectOfType(originator, memento));
		constraintList.add(new ClassHasMethodWithParameterOfType(originator, memento));

	}

	@Override
	public MementoPattern createPattern() {
		return new MementoPattern();
	}

}
