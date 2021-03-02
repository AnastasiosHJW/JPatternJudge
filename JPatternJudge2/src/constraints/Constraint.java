package constraints;

import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;

public abstract class Constraint {
	
	protected String targetTypeName;
	
	public abstract boolean check();
	
	//compares the name of the target type with the name of the actual type
	protected boolean compareTypeNames(Type t)
	{
		if (t.isSimpleType()) {
			SimpleType simpleType = (SimpleType) t;
			if (simpleType.getName().getFullyQualifiedName().equals(targetTypeName))
			{
				return true;
			}
		}
		else if (t.isQualifiedType()) {
			QualifiedType qType = (QualifiedType) t;
			if (qType.getName().getFullyQualifiedName().equals(targetTypeName))
			{
				return true;
			}
		}
		return false;
	}

}
