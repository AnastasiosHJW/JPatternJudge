package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;
import constraints.TypeConstraints.Specific.TemplateMethodConstraint;

public class TemplateMethodPattern extends DesignPattern{

	public TemplateMethodPattern()
	{
		patternName = "Template Method";
		
		roleNames.add("Template");
		roleNames.add("Concrete Class");
	}
	
	@Override
	public void populateConstraints() {
		TypeDeclaration template = roles.get("Template").get(0);
		
		constraintList.add(new ClassIsAbstract(template));
		constraintList.add(new TemplateMethodConstraint(template));
		
		for (TypeDeclaration concrete:roles.get("Concrete Class"))
		{
			constraintList.add(new ClassExtendsSuperclass(concrete,template));
		}
		
	}

	@Override
	public TemplateMethodPattern createPattern() {
		return new TemplateMethodPattern();
	}

}
