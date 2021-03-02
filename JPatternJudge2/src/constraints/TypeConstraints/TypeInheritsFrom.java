package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeInheritsFrom extends TypeConstraint{

	public TypeInheritsFrom(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public String getErrorMessage() {
		String error = ": This type does not inherit from type " + targetType.getName().getIdentifier();
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String negativeError = ": This type inherits from " + targetType.getName().getIdentifier();
		return negativeError;
	}

	@Override
	public boolean check() {

		boolean satisfied = false;
		
		//ClassIsAbstract classCheck = new ClassIsAbstract(targetType);
		TypeIsInterface interfaceCheck = new TypeIsInterface(targetType);
		
		
		if (interfaceCheck.check())
		{
			ClassImplementsInterface classImplements = new ClassImplementsInterface(typeToCheck, targetType);
			if (classImplements.check())
			{
				satisfied = true;
			}
		}
		else 
		{
			ClassExtendsSuperclass classExtends = new ClassExtendsSuperclass(typeToCheck, targetType);
			if (classExtends.check())
			{
				satisfied = true;
			}
		}
		
		return satisfied;
	}

}
