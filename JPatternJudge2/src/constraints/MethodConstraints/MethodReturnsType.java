package constraints.MethodConstraints;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class MethodReturnsType extends MethodConstraint{
	
	public MethodReturnsType(MethodDeclaration methodToCheck, String targetName) {
		super(methodToCheck, targetName);
	}

	@Override
	public boolean check() {
		Type returnType = methodToCheck.getReturnType2();
		
		return compareTypeNames(returnType);
	}

}
