package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassIsAbstract extends TypeConstraint{

	public ClassIsAbstract(TypeDeclaration typeToCheck) {
		super(typeToCheck, null);
	}

	@Override
	public boolean check() {
		int classModifiers = typeToCheck.getModifiers();
		
		return Modifier.isAbstract(classModifiers);
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class is not abstract";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class is abstract";
		return error;
	}

}
