package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassHasFieldOfType extends TypeConstraint{

	public ClassHasFieldOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		FieldDeclaration[] classFields = typeToCheck.getFields();
		for (FieldDeclaration field:classFields)
		{
			if(compareTypeNames(field.getType()))
			{
				satisfied = true;
			}
		}
		
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a field of type " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class has a field of type " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
