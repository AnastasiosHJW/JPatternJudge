package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.Constraint;

public abstract class RoleConstraint extends Constraint{
	
	protected TypeDeclaration typeToCheck;
	
	public RoleConstraint(TypeDeclaration typeToCheck)
	{
		this.typeToCheck = typeToCheck;
	}
	
	
	public abstract String getErrorMessage();
	public abstract String getNegativeErrorMessage();
	
	public String getTypeName()
	{
		return typeToCheck.getName().getIdentifier();
	}

}
