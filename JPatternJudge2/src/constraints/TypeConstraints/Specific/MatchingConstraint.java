package constraints.TypeConstraints.Specific;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.RoleConstraint;

public abstract class MatchingConstraint extends RoleConstraint{
	
	ArrayList<TypeDeclaration> matchingTypes;
	TypeDeclaration match;
	
	public MatchingConstraint(TypeDeclaration typeToCheck, ArrayList<TypeDeclaration> targetTypes) {
		super(typeToCheck);
		match = null;
		matchingTypes = targetTypes;
	}
	
	public TypeDeclaration getMatch()
	{
		return match;
	}

	
}
