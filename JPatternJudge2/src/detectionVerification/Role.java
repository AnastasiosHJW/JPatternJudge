package detectionVerification;

import java.util.List;

import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Role {
	
	private NormalAnnotation annotation;
	
	private String pattern;
	private int patternID;
	private String roleName;
	
	private TypeDeclaration roleTD;

	public Role(NormalAnnotation annotation, TypeDeclaration roleTD)
	{
		this.annotation = annotation;
		this.roleTD = roleTD;
		getAnnotationInformation();
	}
	
	private void getAnnotationInformation()
	{
		List<MemberValuePair> annotationValues = annotation.values();
		pattern = ((StringLiteral) annotationValues.get(0).getValue()).getLiteralValue();
		patternID = Integer.parseInt(((NumberLiteral) annotationValues.get(1).getValue()).getToken());
		roleName = ((StringLiteral) annotationValues.get(2).getValue()).getLiteralValue();
	}
	
	
	
	public String getPattern() {
		return pattern;
	}

	public int getPatternID() {
		return patternID;
	}

	public String getRoleName() {
		return roleName;
	}
	
	public TypeDeclaration getTypeDeclaration()
	{
		return roleTD;
	}
}
