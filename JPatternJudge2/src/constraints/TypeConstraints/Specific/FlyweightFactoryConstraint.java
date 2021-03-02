package constraints.TypeConstraints.Specific;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.MethodInstantiatesObjectOfType;
import constraints.MethodConstraints.MethodReturnsType;
import constraints.TypeConstraints.TypeConstraint;

public class FlyweightFactoryConstraint extends TypeConstraint{

	public FlyweightFactoryConstraint(TypeDeclaration typeToCheck, TypeDeclaration targetType) {
		super(typeToCheck, targetType);
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNegativeErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean check() {
		
		boolean fieldSatisfied = false;
		for (FieldDeclaration field:typeToCheck.getFields())
		{
			if(compareTypeNames(field.getType()) && Modifier.isStatic(field.getModifiers()))
			{
				fieldSatisfied = true;
			}
		}
		
		boolean factoryMethod = false;
		for (MethodDeclaration method:typeToCheck.getMethods())
		{
			MethodInstantiatesObjectOfType constraint1 = new MethodInstantiatesObjectOfType(method,targetType.getName().getIdentifier());
			MethodReturnsType constraint2 = new MethodReturnsType(method, targetType.getName().getIdentifier());
			
			if (constraint1.check() && constraint2.check() && Modifier.isStatic(method.getModifiers()))
			{
				factoryMethod = true;
			}
		
		}
		
		
		return fieldSatisfied && factoryMethod;
	}

}
