package constraints.TypeConstraints.Specific;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodInvokesMethod;
import constraints.TypeConstraints.TypeConstraint;

public class TemplateMethodConstraint extends TypeConstraint{
	
	public TemplateMethodConstraint(TypeDeclaration typeToCheck) {
		super(typeToCheck, null);
	}
	
	@Override
	public boolean check() {
		
		MethodDeclaration[] methods = typeToCheck.getMethods();
		ArrayList<MethodDeclaration> abstractMethods = new ArrayList<MethodDeclaration>();
		ArrayList<MethodDeclaration> concreteMethods = new ArrayList<MethodDeclaration>();
		
		for (MethodDeclaration method:methods)
		{
			int methodModifiers = method.getModifiers();
			if (Modifier.isAbstract(methodModifiers))
			{
				abstractMethods.add(method);
			}
			else
			{
				concreteMethods.add(method);
			}
		}
		
		for (MethodDeclaration templateMethod:concreteMethods)
		{
			for (MethodDeclaration abstractMethod:abstractMethods)
			{
				MethodInvokesMethod constraint = new MethodInvokesMethod(templateMethod,abstractMethod.getName().getIdentifier());
				if (constraint.check())
				{
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This class does not have a template method\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		//should never be called
		String error = ": This class has a template method\n";
		return error;
	}
}
