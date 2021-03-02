package constraints.MethodConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

public class MethodHasParameterOfType extends MethodConstraint{

	public MethodHasParameterOfType(MethodDeclaration methodToCheck, String targetName) {
		super(methodToCheck, targetName);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		List<SingleVariableDeclaration> paramList = methodToCheck.parameters();
		for (SingleVariableDeclaration parameter:paramList)
		{
			if (compareTypeNames(parameter.getType()))
			{
				satisfied = true;
			}
		}
		
		return satisfied;
	}

}
