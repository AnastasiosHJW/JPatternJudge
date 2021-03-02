package constraints.TypeConstraints.Specific;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.ClassHasFieldOfType;
import constraints.TypeConstraints.ClassHasMethodWithReturnType;
import constraints.TypeConstraints.ClassInstantiatesObjectOfType;
import constraints.TypeConstraints.TypeConstraint;

public class BuilderProductMatchingConstraint extends MatchingConstraint{

	public BuilderProductMatchingConstraint(TypeDeclaration builder, ArrayList<TypeDeclaration> products) {
		super(builder, products);
	}

	@Override
	public boolean check() {
		boolean fieldCheck = false;
		boolean instanceCheck = false;
		boolean returnCheck = false;
		
		for (TypeDeclaration product:matchingTypes)
		{
			ClassHasFieldOfType constraint1 = new ClassHasFieldOfType(typeToCheck, product);
			ClassInstantiatesObjectOfType constraint2 = new ClassInstantiatesObjectOfType(typeToCheck, product);
			ClassHasMethodWithReturnType constraint3 = new ClassHasMethodWithReturnType(typeToCheck, product);
			
			fieldCheck = constraint1.check();
			instanceCheck = constraint2.check();
			returnCheck = constraint3.check();
			if (fieldCheck && instanceCheck && returnCheck)
			{
				//matchingTypes.remove(product);
				match = product;
				return true;
			}
			else
			{
				fieldCheck = false;
				instanceCheck = false;
				returnCheck = false;
			}
		}
		return false;
	}

	@Override
	public String getErrorMessage() {
		String error = ": This Builder does not produce a Product\n";
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		// Should never be called
		return "";
	}

}
