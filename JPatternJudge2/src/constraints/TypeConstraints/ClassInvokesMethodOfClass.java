package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodInvokesMethod;

public class ClassInvokesMethodOfClass extends TypeConstraint{

	public ClassInvokesMethodOfClass(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		MethodDeclaration[] methodsToCheck = typeToCheck.getMethods();
		MethodDeclaration[] targetMethods = targetType.getMethods();
		
		for (MethodDeclaration methodToCheck:methodsToCheck)
		{
			for (MethodDeclaration targetMethod:targetMethods)
			{
				MethodInvokesMethod methodConstraint = new MethodInvokesMethod(methodToCheck, targetMethod.getName().getIdentifier());
				if (methodConstraint.check())
				{
					satisfied = true;
				}
			}
		}
		return satisfied;	
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not invoke a method of class " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class invokes a method of class " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
