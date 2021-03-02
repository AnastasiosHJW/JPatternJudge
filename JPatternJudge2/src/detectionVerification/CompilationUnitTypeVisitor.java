package detectionVerification;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CompilationUnitTypeVisitor extends ASTVisitor{

	private ArrayList<Role> roles = new ArrayList<Role>();
	private int annotationsFound;
	
	@Override
	public boolean visit(CompilationUnit cu)
	{
		//System.out.println("visiting");
		List<AbstractTypeDeclaration> cuAbstractTypes = cu.types();
		ArrayList<TypeDeclaration> cuTypes = new ArrayList<TypeDeclaration>();
		for (AbstractTypeDeclaration ATD: cuAbstractTypes)
		{
			if (ATD instanceof TypeDeclaration)
				{
					cuTypes.add((TypeDeclaration) ATD);
				}
		}
		
		for (TypeDeclaration node: cuTypes)
		{
			//types.add(node);
			List<IExtendedModifier> modList = node.modifiers();
			//System.out.println("Number of modifiers: " + modList.size());
			for (IExtendedModifier mod:modList)
			{
				if (mod.isAnnotation())
				{
					annotationsFound++;
					//System.out.println("Modifier is Annotation");
					Annotation annotation = (Annotation) mod;
					
					if (annotation.isNormalAnnotation())
					{
						NormalAnnotation nAnnotation = (NormalAnnotation) annotation;

						List<MemberValuePair> annotationValues = nAnnotation.values();
						
						if (annotationValues.size() == 3)
						{
							if (annotationValues.get(0).getName().getIdentifier().equals("pattern")
									&& annotationValues.get(1).getName().getIdentifier().equals("patternID")
									&& annotationValues.get(2).getName().getIdentifier().equals("role"))
							{
								
								Role role = new Role(nAnnotation, node);
								roles.add(role);
								
							}
						}
					}
					
					
				}
			}
		}
		
		
		return false;
	}
	
	public ArrayList<Role> getRoles()
	{
		return roles;
	}
	
	public int getAnnotationsFound()
	{
		return annotationsFound;
	}
	
}
