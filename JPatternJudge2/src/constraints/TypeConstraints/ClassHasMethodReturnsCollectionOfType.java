package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodReturnsCollectionOfType;

public class ClassHasMethodReturnsCollectionOfType extends TypeConstraint{

	public ClassHasMethodReturnsCollectionOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		MethodDeclaration[] methods = typeToCheck.getMethods();
		for (MethodDeclaration method:methods)
		{
			MethodReturnsCollectionOfType methodConstraint = new MethodReturnsCollectionOfType(method, targetTypeName);
			if (methodConstraint.check())
			{
				satisfied = true;
			}
		}
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a method that returns a collection of " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class has a method that returns a collection of " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
}
