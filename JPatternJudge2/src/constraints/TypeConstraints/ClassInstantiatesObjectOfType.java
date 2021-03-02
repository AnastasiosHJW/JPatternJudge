package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodInstantiatesObjectOfType;

public class ClassInstantiatesObjectOfType extends TypeConstraint{

	public ClassInstantiatesObjectOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		MethodDeclaration[] methods = typeToCheck.getMethods();
		for (MethodDeclaration method:methods)
		{
			MethodInstantiatesObjectOfType methodConstraint = new MethodInstantiatesObjectOfType(method, targetTypeName);
			if (methodConstraint.check())
			{
				satisfied = true;
			}
		}
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not instntiate an object of class " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class instantiates an object of class " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
