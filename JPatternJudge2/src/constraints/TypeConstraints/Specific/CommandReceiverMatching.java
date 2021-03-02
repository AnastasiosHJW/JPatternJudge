package constraints.TypeConstraints.Specific;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.ClassHasFieldOfType;
import constraints.TypeConstraints.ClassHasFieldOrVariableOfType;

public class CommandReceiverMatching extends MatchingConstraint{

	public CommandReceiverMatching(TypeDeclaration typeToCheck, ArrayList<TypeDeclaration> targetTypes) {
		super(typeToCheck, targetTypes);
	}
	
	@Override
	public boolean check() {
		boolean satisfied = false;
		for (TypeDeclaration receiver:matchingTypes)
		{
			ClassHasFieldOrVariableOfType constraint = new ClassHasFieldOrVariableOfType(typeToCheck,receiver);
			if (constraint.check())
			{
				satisfied = true;
			}
		}
		return satisfied;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This Command does not have a Receiver field\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		//should never be called
		String error = ": This Command has a Receiver field\n";
		return error;
	}

}
