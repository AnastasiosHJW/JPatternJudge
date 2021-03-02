package constraints.TypeConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassHasFieldCollectionOfType extends TypeConstraint{

	public ClassHasFieldCollectionOfType(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}
	
	@Override
	public boolean check() {
		FieldDeclaration[] classFields = typeToCheck.getFields();
		boolean satisfied = false;
		
		for (FieldDeclaration field:classFields)
		{
			if (field.getType() instanceof ArrayType)
			{
				ArrayType fieldType = (ArrayType) field.getType();
				if (compareTypeNames(fieldType.getElementType()))
				{
					satisfied = true;
				}
			}
			else if (field.getType() instanceof ParameterizedType)
			{
				ParameterizedType fieldType = (ParameterizedType) field.getType();
				List<Type> parameters = fieldType.typeArguments();
				for (Type type: parameters)
				{
					if (compareTypeNames(type))
					{
						satisfied = true;
					}
				}			
			}
		}
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a field that is a collection of " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class has a field that is a collection of " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
