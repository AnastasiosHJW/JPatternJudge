package designPatterns;

import java.util.HashMap;
import java.util.HashSet;

public class DesignPatternRegistry {
	private static HashMap<String, DesignPattern> patterns = new HashMap<String, DesignPattern>();
	
	static {
		patterns.put("Abstract Factory", new AbstractFactoryPattern());
		patterns.put("Adapter", new AdapterPattern());
		patterns.put("Bridge", new BridgePattern());
		patterns.put("Builder", new BuilderPattern());
		patterns.put("Chain Of Responsibility", new ChainOfResponsibilityPattern());
		patterns.put("Command", new CommandPattern());
		patterns.put("Composite", new CompositePattern());
		patterns.put("Decorator", new DecoratorPattern());
		patterns.put("Flyweight", new FlyweightPattern());
		patterns.put("Mediator", new MediatorPattern());
		patterns.put("Memento", new MementoPattern());
		patterns.put("Observer", new ObserverPattern());
		patterns.put("Proxy", new ProxyPattern());
		patterns.put("Singleton", new SingletonPattern());
		patterns.put("State", new StatePattern());	
		patterns.put("Strategy", new StrategyPattern());
		patterns.put("Template Method", new TemplateMethodPattern());
		patterns.put("Visitor", new VisitorPattern());

	}
	
	public static DesignPattern getPattern(String patternName)
	{
		return patterns.get(patternName).createPattern();
	}
	
	public static HashSet<String> getAllPatternNames()
	{
		HashSet<String> patternNames = new HashSet<String>();
		for (String name:patterns.keySet())
		{
			patternNames.add(name);
		}
		return patternNames;
	}

}
