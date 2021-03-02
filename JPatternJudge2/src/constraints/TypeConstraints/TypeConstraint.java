package constraints.TypeConstraints;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public abstract class TypeConstraint extends RoleConstraint{


	protected TypeDeclaration targetType;
	
	public TypeConstraint(TypeDeclaration typeToCheck, TypeDeclaration targetType)
	{
		super(typeToCheck);
		this.targetType = targetType;
		if (targetType!=null)
		{
			this.targetTypeName = targetType.getName().getIdentifier();
		}
		
	}
	
	public abstract String getErrorMessage();
	public abstract String getNegativeErrorMessage();
	

}
