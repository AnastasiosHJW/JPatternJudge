package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class CompositePattern extends DesignPattern{

	public CompositePattern()
	{
		patternName = "Composite";
		
		roleNames.add("Component");
		roleNames.add("Composite");
		roleNames.add("Leaf");

	}
	
	
	@Override
	public void populateConstraints() {
		TypeDeclaration component = roles.get("Component").get(0);
		TypeDeclaration composite = roles.get("Composite").get(0);
		TypeDeclaration leaf = roles.get("Leaf").get(0);
		
		//constraintList.add(new TypeIsInterface(component));
		constraintList.add(new TypeIsInterfaceOrAbstractClass(component));
		
		constraintList.add(new ClassHasMethodWithParameterOfType(component,component));
		constraintList.add(new ClassHasMethodReturnsCollectionOfType(component, component));
		
		//constraintList.add(new ClassImplementsInterface(composite, component));	
		constraintList.add(new TypeInheritsFrom(composite, component));
		constraintList.add(new ClassHasFieldCollectionOfType(composite,component));
		
		//constraintList.add(new ClassImplementsInterface(leaf, component));
		constraintList.add(new TypeInheritsFrom(leaf, component));
	}

	@Override
	public CompositePattern createPattern() {
		return new CompositePattern();
	}

}
