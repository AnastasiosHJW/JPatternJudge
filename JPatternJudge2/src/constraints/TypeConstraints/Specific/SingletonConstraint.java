package constraints.TypeConstraints.Specific;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.MethodConstraints.*;
import constraints.TypeConstraints.TypeConstraint;

public class SingletonConstraint extends TypeConstraint{
	
	private boolean privateConstructors;
	private boolean methodSatisfied;
	private boolean fieldExists;

	public SingletonConstraint(TypeDeclaration typeToCheck) {
		super(typeToCheck, typeToCheck);
	}

	@Override
	public boolean check() {
		privateConstructors = true;
		MethodDeclaration[] methods = typeToCheck.getMethods();
		for (MethodDeclaration method:methods)
		{
			if (method.isConstructor())
			{
				if (!Modifier.isPrivate(method.getModifiers()))
				{
					privateConstructors = false;
				}
			}
		}
		
		FieldDeclaration[] fields = typeToCheck.getFields();
		FieldDeclaration singletonField = null;
		fieldExists = false;
		for (FieldDeclaration field:fields)
		{
			if (compareTypeNames(field.getType()) && Modifier.isStatic(field.getModifiers()))
			{
				singletonField = field;
				fieldExists = true;
			}
		}
		
		methodSatisfied = false;
		if (fieldExists)
		{
			for (MethodDeclaration method:methods)
			{
				if (!method.isConstructor() && Modifier.isStatic(method.getModifiers()))
				{
					MethodInstantiatesObjectOfType methodConstraint1 = new MethodInstantiatesObjectOfType(method, targetTypeName);
					MethodReturnsType methodConstraint2 = new MethodReturnsType(method,targetTypeName);
					methodSatisfied = methodConstraint1.check() && methodConstraint2.check();
					if (methodSatisfied && privateConstructors)
					{
						return true;
					}
				}
			}
		}
		
		return false;
		
		
	}

	@Override
	public String getErrorMessage() {	
		String error = ": All Singleton constructors should be private. Singleton should have a static field of itself. Singleton should have a static method that instantiates and returns the Singleton instance.";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String error = ": This class is a singleton";
		return error;
	}
}
