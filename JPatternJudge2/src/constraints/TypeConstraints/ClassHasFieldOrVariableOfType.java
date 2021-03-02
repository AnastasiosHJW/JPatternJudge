package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassHasFieldOrVariableOfType extends TypeConstraint{

	public ClassHasFieldOrVariableOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a field or variable of type " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String negativeError = ": This class has a field or variable of type " + targetType.getName().getIdentifier();
		return negativeError;
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		ClassHasFieldOfType fieldCheck = new ClassHasFieldOfType(typeToCheck, targetType);
		ClassHasVariableOfType variableCheck = new ClassHasVariableOfType(typeToCheck, targetType);
		
		satisfied = fieldCheck.check() || variableCheck.check();
		return satisfied;
	}

}
