package detectionVerification;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;

import designPatterns.DesignPattern;
import designPatterns.DesignPatternRegistry;

public class PatternDetector {
	
	private Parser parser;
	private CompilationUnitTypeVisitor visitor;
	private IProject currentProject;
	
	public PatternDetector()
	{
		parser = new Parser();
	
	}
	
	public DesignPatternStorage detectPatterns(IProject project)
	{
		DesignPatternStorage patternStorage = new DesignPatternStorage();
		
		visitor = new CompilationUnitTypeVisitor();
		currentProject = project;
		
		ArrayList<IFile> fileList = new ArrayList<IFile>();
		if (currentProject.isOpen())
		{
			HashSet<CompilationUnit> CUset = new HashSet<CompilationUnit>();
			
			processContainer(currentProject, fileList);	
			for (IFile file:fileList)
			{
				//turn the files into compilation units if possible (returns null otherwise)
				ICompilationUnit cu = null;

				IJavaElement element = JavaCore.create(file); 

				if (element instanceof ICompilationUnit) {
				   cu = (ICompilationUnit)element;
				  
				}
				//parse the ICompilationUnits into AST CompilationUnits
				if (cu != null)
				{
					//System.out.println("Parsing " + cu.getElementName());
					CUset.add(parser.parse(cu));
				}
				
			}
			
			//accept the CompilationUnitVisitor to get the TypeDeclarations
			for (CompilationUnit cu:CUset)
			{
				
				cu.accept(visitor);
			}
			
			//create patterns and put the roles in them
			ArrayList<Role> roles = visitor.getRoles();
			for (Role role:roles)
			{
				String rolePattern = role.getPattern();
				int id = role.getPatternID();
				
				if (DesignPatternRegistry.getAllPatternNames().contains(rolePattern))
				{
					DesignPattern pattern = patternStorage.getDesignPattern(rolePattern, id);
					
					if (pattern == null)
					{
						pattern = DesignPatternRegistry.getPattern(rolePattern).createPattern();
						patternStorage.addDesignPattern(pattern,id);
					}
							
					pattern.addRole(role.getRoleName(), role.getTypeDeclaration());		
				}
				
			}
		
		}
		
		return patternStorage;
	}
	
	
	private void processContainer(IContainer container, ArrayList<IFile> fileList)
	{
		
		IResource[] projectMembers;
			try {
				projectMembers = container.members();
				for (IResource member:projectMembers)
				{
					if (member instanceof IContainer)
					{
						processContainer((IContainer) member, fileList);
					}
					else if (member instanceof IFile)
					{
						fileList.add((IFile) member);
					}
				}
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
	}
	

}
