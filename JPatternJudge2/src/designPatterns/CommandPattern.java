package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;
import constraints.TypeConstraints.Specific.CommandReceiverMatching;

public class CommandPattern extends DesignPattern{
	
	public CommandPattern()
	{
		patternName = "Command";
		
		roleNames.add("Command");
		roleNames.add("Concrete Command");
		roleNames.add("Invoker");
		roleNames.add("Receiver");
		
	}

	@Override
	public void populateConstraints() {
		TypeDeclaration command = roles.get("Command").get(0);
		
		constraintList.add(new TypeIsInterfaceOrAbstractClass(command));
		
		for (TypeDeclaration invoker:roles.get("Invoker"))
		{
			constraintList.add(new ClassHasFieldOrVariableOfType(invoker, command));
			//for (TypeDeclaration receiver:roles.get("Receiver"))
			//{
				//constraintList.add(new NegativeConstraint(new ClassHasFieldOfType(invoker, receiver)));
				//constraintList.add(new NegativeConstraint(new ClassHasVariableOfType(invoker, receiver)));
			//}
		}
		for (TypeDeclaration concreteCommand:roles.get("Concrete Command"))
		{
			constraintList.add(new TypeInheritsFrom(concreteCommand, command));
			constraintList.add(new CommandReceiverMatching(concreteCommand, roles.get("Receiver")));
		}

	}

	@Override
	public CommandPattern createPattern() {
		return new CommandPattern();
	}

}
