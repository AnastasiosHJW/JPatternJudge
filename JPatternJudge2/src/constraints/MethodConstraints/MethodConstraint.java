package constraints.MethodConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import constraints.Constraint;

public abstract class MethodConstraint extends Constraint{
	
	protected MethodDeclaration methodToCheck;
	
	public MethodConstraint(MethodDeclaration methodToCheck, String targetName)
	{
		this.methodToCheck = methodToCheck;
		targetTypeName = targetName;
		
	}

}
