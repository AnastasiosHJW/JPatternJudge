package designPatterns;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.RoleConstraint;

public abstract class DesignPattern {

	protected int id;

	protected String patternName;
	
	protected ArrayList<String> roleNames = new ArrayList<String>();
	protected HashMap<String,ArrayList<TypeDeclaration>> roles = new HashMap<String,ArrayList<TypeDeclaration>>();
	
	protected ArrayList<RoleConstraint> constraintList = new ArrayList<RoleConstraint>();
	
	protected String patternErrorMessage = "";
	
	public void addRole(String roleName, TypeDeclaration typeDec)
	{
		if (roleNames.contains(roleName))
		{
			if(!roles.keySet().contains(roleName))
			{
				roles.put(roleName, new ArrayList<TypeDeclaration>());
			}
			roles.get(roleName).add(typeDec);
		}
		else
		{
			patternErrorMessage += "Unknown role: " + roleName + " at class " + typeDec.getName().getIdentifier() + "\n";
		}
	}
	
	
	public boolean checkRoles()
	{
		boolean satisfied = true;
		for (String roleName:roleNames)
		{
			if (!roles.keySet().contains(roleName))
			{
				satisfied = false;
				patternErrorMessage += "Missing Role: " + roleName + "\n";
			}
		}
		return satisfied;
	}
	
	public boolean verifyPattern()
	{
		boolean satisfied = true;
		for (RoleConstraint c:constraintList)
		{
			if (!c.check())
			{
				satisfied = false;
				patternErrorMessage += c.getTypeName() + c.getErrorMessage();
			}
		}
		if (satisfied)
		{
			patternErrorMessage = "Pattern implemented correctly";
		}
		return satisfied;
	}
	
	public String getPatternErrorMessage()
	{
		return patternErrorMessage;
	}
	
	public String getPatternName()
	{
		return patternName;
	}
	
	public abstract void populateConstraints();
	
	public abstract DesignPattern createPattern();
}
