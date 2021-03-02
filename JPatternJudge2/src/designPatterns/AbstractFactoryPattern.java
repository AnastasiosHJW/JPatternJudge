package designPatterns;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;
import constraints.TypeConstraints.Specific.AbstractFactoryInstantiationMatching;
import constraints.TypeConstraints.Specific.AbstractFactoryProductInterfaceMatchingConstraint;

public class AbstractFactoryPattern extends DesignPattern{
	
	public AbstractFactoryPattern()
	{
		patternName = "Abstract Factory";
		
		roleNames.add("Abstract Factory");
		roleNames.add("Concrete Factory");
		roleNames.add("Product");
		roleNames.add("Concrete Product");

	}

	@Override
	public void populateConstraints() {
		TypeDeclaration abstractFactory = roles.get("Abstract Factory").get(0);
		
		constraintList.add(new TypeIsInterface(abstractFactory));
		
		for (TypeDeclaration product: roles.get("Product"))
		{
			constraintList.add(new TypeIsInterface(product));
			constraintList.add(new ClassHasMethodWithReturnType(abstractFactory,product));
		}
		
		HashMap<TypeDeclaration, ArrayList<TypeDeclaration>> productMap = new HashMap<TypeDeclaration, ArrayList<TypeDeclaration>>();
		for (TypeDeclaration concreteProduct:roles.get("Concrete Product"))
		{
			AbstractFactoryProductInterfaceMatchingConstraint c = new AbstractFactoryProductInterfaceMatchingConstraint(concreteProduct,roles.get("Product"));
			constraintList.add(c);
			c.check();
			if (c.getMatch()!=null && !productMap.keySet().contains(c.getMatch()))
			{
				productMap.put(c.getMatch(), new ArrayList<TypeDeclaration>());
			}
			productMap.get(c.getMatch()).add(concreteProduct);
		}
		for (TypeDeclaration concreteFactory:roles.get("Concrete Factory"))
		{
			constraintList.add(new ClassImplementsInterface(concreteFactory,abstractFactory));
			for (TypeDeclaration product:productMap.keySet())
			{
				constraintList.add(new AbstractFactoryInstantiationMatching(concreteFactory,productMap.get(product)));
			}
		}		
		
	}

	@Override
	public AbstractFactoryPattern createPattern() {
		return new AbstractFactoryPattern();
	}

}
