package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeIsInterface extends TypeConstraint{

	public TypeIsInterface(TypeDeclaration typeToCheck) {
		super(typeToCheck, null);
	}

	@Override
	public boolean check() {
		return typeToCheck.isInterface();
	}

	@Override
	public String getErrorMessage() {
		String error = ": This type is not an interface";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String error = ": This type is an interface";
		return error;
	}

}
