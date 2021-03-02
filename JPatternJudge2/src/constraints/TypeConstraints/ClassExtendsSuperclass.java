package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassExtendsSuperclass extends TypeConstraint{

	public ClassExtendsSuperclass(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		Type superclass = typeToCheck.getSuperclassType();
		if (superclass != null)
		{
			satisfied = compareTypeNames(superclass);
		}
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not extend the superclass " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class extends the superclass " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
