package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class AdapterPattern extends DesignPattern{

	public AdapterPattern()
	{
		patternName = "Adapter";
		
		roleNames.add("Adapter");
		roleNames.add("Adaptee");
		roleNames.add("Adapter Client");
		roleNames.add("Adapter Interface");
		
	}
	
	@Override
	public void populateConstraints() {
		TypeDeclaration adapter = roles.get("Adapter").get(0);
		TypeDeclaration adaptee = roles.get("Adaptee").get(0);
		TypeDeclaration adapterClient = roles.get("Adapter Client").get(0);
		TypeDeclaration adapterInterface = roles.get("Adapter Interface").get(0);
		
		constraintList.add(new TypeIsInterface(adapterInterface));
		
		constraintList.add(new ClassHasFieldOfType(adapterClient,adapterInterface));
		constraintList.add(new NegativeConstraint(new ClassHasFieldOfType(adapterClient, adaptee)));
		constraintList.add(new NegativeConstraint(new ClassHasVariableOfType(adapterClient, adaptee)));
			
		constraintList.add(new ClassImplementsInterface(adapter, adapterInterface));
		constraintList.add(new ClassHasFieldOfType(adapter,adaptee));
		constraintList.add(new ClassInvokesMethodOfClass(adapter,adaptee));
		
	}
	
	public AdapterPattern createPattern()
	{
		return new AdapterPattern();
	}
}
