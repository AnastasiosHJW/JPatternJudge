package constraints.TypeConstraints.Specific;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.ClassInstantiatesObjectOfType;

public class AbstractFactoryInstantiationMatching extends MatchingConstraint{

	public AbstractFactoryInstantiationMatching(TypeDeclaration typeToCheck, ArrayList<TypeDeclaration> concreteProducts) {
		super(typeToCheck, concreteProducts);
	}

	@Override
	public boolean check() {

		for (TypeDeclaration product:matchingTypes)
		{
			ClassInstantiatesObjectOfType constraint = new ClassInstantiatesObjectOfType(typeToCheck,product);
			if (constraint.check())
			{
				match = product;
				return true;
			}
		}
		return false;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This factory class is not matched with all necessary products\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		//should never be called
		return "";
	}

}
