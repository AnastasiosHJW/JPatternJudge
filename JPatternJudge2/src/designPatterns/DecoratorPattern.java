package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class DecoratorPattern extends DesignPattern{

	public DecoratorPattern()
	{
		patternName = "Decorator";
		
		roleNames.add("Component");
		roleNames.add("Concrete Component");
		roleNames.add("Decorator");
		//roleNames.add("Concrete Decorator");

	}
	
	@Override
	public void populateConstraints() {
		TypeDeclaration component = roles.get("Component").get(0);
		TypeDeclaration decorator = roles.get("Decorator").get(0);
		
		//constraintList.add(new TypeIsInterface(component));
		constraintList.add(new TypeIsInterfaceOrAbstractClass(component));
		
		for (TypeDeclaration concreteComponent:roles.get("Concrete Component"))
		{
			//constraintList.add(new ClassImplementsInterface(concreteComponent, component));
			constraintList.add(new TypeInheritsFrom(concreteComponent, component));
		}
		
		//constraintList.add(new ClassIsAbstract(decorator));
		//constraintList.add(new ClassImplementsInterface(decorator, component));
		constraintList.add(new TypeInheritsFrom(decorator, component));
		constraintList.add(new ClassHasFieldOfType(decorator,component));
		
		//for (TypeDeclaration concreteDecorator:roles.get("Concrete Decorator"))
		//{
		//	constraintList.add(new ClassExtendsSuperclass(concreteDecorator, decorator));
		//}
	}

	@Override
	public DecoratorPattern createPattern() {
		return new DecoratorPattern();
	}

}
