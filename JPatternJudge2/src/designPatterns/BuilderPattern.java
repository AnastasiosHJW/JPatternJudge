package designPatterns;

import java.util.ArrayList;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;
import constraints.TypeConstraints.Specific.BuilderProductMatchingConstraint;

public class BuilderPattern extends DesignPattern{
	
	ArrayList<TypeDeclaration> productCopyList;
	
	public BuilderPattern()
	{
		patternName = "Builder";
		
		roleNames.add("Director");
		roleNames.add("Builder");
		roleNames.add("Concrete Builder");
		roleNames.add("Product");
		
		productCopyList = new ArrayList<TypeDeclaration>();

	}

	@Override
	public void populateConstraints() {
		TypeDeclaration director = roles.get("Director").get(0);
		TypeDeclaration builder = roles.get("Builder").get(0);
		
		constraintList.add(new TypeIsInterface(builder));
		
		constraintList.add(new ClassHasFieldOfType(director,builder));
		
		
		for (TypeDeclaration product:roles.get("Product"))
		{
			productCopyList.add(product);
		}
		
		//HashMap<TypeDeclaration,TypeDeclaration> builderProductMap = new HashMap<TypeDeclaration,TypeDeclaration>();
		for (TypeDeclaration concreteBuilder:roles.get("Concrete Builder"))
		{
			constraintList.add(new ClassImplementsInterface(concreteBuilder,builder));
			constraintList.add(new BuilderProductMatchingConstraint(concreteBuilder,productCopyList));
		}
		
	}
	

	@Override
	public BuilderPattern createPattern() {
		return new BuilderPattern();
	}

}
