package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodHasParameterOfType;

public class ClassHasMethodWithParameterOfType extends TypeConstraint{

	public ClassHasMethodWithParameterOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		MethodDeclaration[] methods = typeToCheck.getMethods();
		for (MethodDeclaration method:methods)
		{
			MethodHasParameterOfType methodConstraint = new MethodHasParameterOfType(method,targetTypeName);
			if (methodConstraint.check())
			{
				satisfied = true;
			}
		}
		
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a method with a parameter of type " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class has a method with a parameter of type " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
