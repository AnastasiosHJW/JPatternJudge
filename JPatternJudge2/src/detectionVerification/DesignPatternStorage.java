package detectionVerification;

import java.util.HashMap;
import java.util.HashSet;

import designPatterns.DesignPattern;
import designPatterns.DesignPatternRegistry;

public class DesignPatternStorage {
	
	private HashMap<String,HashMap<Integer,DesignPattern>> patternStorage;
	
	public DesignPatternStorage()
	{
		patternStorage = new HashMap<String,HashMap<Integer,DesignPattern>>();
		for (String patternName:DesignPatternRegistry.getAllPatternNames())
		{
			patternStorage.put(patternName, new HashMap<Integer,DesignPattern>());
		}
	}
	
	public HashSet<Integer> getAllIDsOfPattern(String patternName)
	{
		HashSet<Integer> patternIDs = new HashSet<Integer>();
		for (int id:patternStorage.get(patternName).keySet())
		{
			patternIDs.add(id);
		}
		return patternIDs;
	}
	
	public DesignPattern getDesignPattern(String name, int id)
	{
		if (patternStorage.get(name).keySet().contains(id))
		{
			return patternStorage.get(name).get(id);
		}
		return null;
	}
	
	public void addDesignPattern(DesignPattern pattern, int id)
	{
		if (!patternStorage.get(pattern.getPatternName()).keySet().contains(id))
		{
			patternStorage.get(pattern.getPatternName()).put(id, pattern);
		}
		
	}

}
