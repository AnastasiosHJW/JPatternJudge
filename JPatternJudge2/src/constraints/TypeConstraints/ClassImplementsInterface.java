package constraints.TypeConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassImplementsInterface extends TypeConstraint{

	public ClassImplementsInterface(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public boolean check() {
		List<Type> interfaceList = typeToCheck.superInterfaceTypes();
		boolean satisfied = false;
		for (Type interfaceType: interfaceList)
		{
			if(compareTypeNames(interfaceType))
			{
				satisfied = true;
			}
		}
		
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not implement the interface " + targetType.getName().getIdentifier() + "\n";
		return error;
	}
	
	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class does implements the interface " + targetType.getName().getIdentifier() + "\n";
		return error;
	}

}
