package constraints.TypeConstraints.Specific;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.ClassImplementsInterface;

public class AbstractFactoryProductInterfaceMatchingConstraint extends MatchingConstraint{

	public AbstractFactoryProductInterfaceMatchingConstraint(TypeDeclaration concreteProduct,
			ArrayList<TypeDeclaration> productInterfaces) {
		super(concreteProduct, productInterfaces);
	}

	@Override
	public boolean check() {
		for (TypeDeclaration productInterface:matchingTypes)
		{
			ClassImplementsInterface constraint1 = new ClassImplementsInterface(typeToCheck,productInterface);
			if(constraint1.check())
			{
				match = productInterface;
				return true;
			}
		}
		return false;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This Concrete Product does not implement a Product interface\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		// Should never be called
		return "";
	}

}
