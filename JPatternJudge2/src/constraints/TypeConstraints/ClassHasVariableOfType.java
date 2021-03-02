package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodHasVariableOfType;

public class ClassHasVariableOfType extends TypeConstraint{

	public ClassHasVariableOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		MethodDeclaration[] classMethods = typeToCheck.getMethods();
		boolean satisfied = false;
		for (MethodDeclaration method:classMethods)
		{
			MethodHasVariableOfType methodConstraint = new MethodHasVariableOfType(method, targetTypeName);
			if (methodConstraint.check())
			{
				satisfied = true;
			}
		}
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a variable of type " + targetTypeName;
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class has a variable of type " + targetTypeName;
		return error;
	}

}
