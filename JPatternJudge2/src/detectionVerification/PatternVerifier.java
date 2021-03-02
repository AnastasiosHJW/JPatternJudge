package detectionVerification;

import designPatterns.DesignPattern;
import designPatterns.DesignPatternRegistry;

public class PatternVerifier {
	
	private String message;

	public void verifyPatterns(DesignPatternStorage patternStorage)
	{
		message = "";
		for (String patternName:DesignPatternRegistry.getAllPatternNames())
		{
			for (int id:patternStorage.getAllIDsOfPattern(patternName))
			{
				DesignPattern pattern = patternStorage.getDesignPattern(patternName,id);
				if (pattern.checkRoles())
				{
					pattern.populateConstraints();
					pattern.verifyPattern();	
				}

				message += patternName + " " + id + "\n " + pattern.getPatternErrorMessage() +"\n";
			}
		}
	}
	
	public String getMessage()
	{
		return message;
	}
}
