package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeIsInterfaceOrAbstractClass extends TypeConstraint{

	public TypeIsInterfaceOrAbstractClass(TypeDeclaration typeToCheck) {
		super(typeToCheck, null);
	}

	@Override
	public String getErrorMessage() {
		String error = ": This type must be an interface or an abstract class.";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String negativeError = ": This type is either an interface or an abstract class.";
		return negativeError;
	}

	@Override
	public boolean check() {
		TypeIsInterface interfaceConstraint = new TypeIsInterface(typeToCheck);
		ClassIsAbstract abstractConstraint = new ClassIsAbstract(typeToCheck);
		
		boolean satisfied = interfaceConstraint.check() || abstractConstraint.check();
		return satisfied;
	}

}
