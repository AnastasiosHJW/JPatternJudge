package constraints.MethodConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Type;

public class MethodReturnsCollectionOfType extends MethodConstraint{

	public MethodReturnsCollectionOfType(MethodDeclaration methodToCheck, String targetName) {
		super(methodToCheck, targetName);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		Type returnType = methodToCheck.getReturnType2();
		
		if (returnType instanceof ArrayType)
		{
			ArrayType arrayReturn = (ArrayType) returnType;
			if (compareTypeNames(arrayReturn.getElementType()))
			{
				satisfied = true;
			}
		}
		else if (returnType instanceof ParameterizedType)
		{
			ParameterizedType parameterReturn = (ParameterizedType) returnType;
			List<Type> parameters = parameterReturn.typeArguments();
			for (Type type: parameters)
			{
				if (compareTypeNames(type))
				{
					satisfied = true;
				}
			}			
		}		
		return satisfied;
	}

}
