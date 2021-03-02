package designPatterns;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import constraints.TypeConstraints.*;

public class ProxyPattern extends DesignPattern{
	
	public ProxyPattern()
	{
		patternName = "Proxy";
		
		roleNames.add("Proxy");
		roleNames.add("Proxy Subject");
		roleNames.add("Proxy Real Subject");
		
	}

	@Override
	public void populateConstraints() {
		TypeDeclaration proxySubject = roles.get("Proxy Subject").get(0);	
		TypeDeclaration proxyRealSubject = roles.get("Proxy Real Subject").get(0);
		TypeDeclaration proxy = roles.get("Proxy").get(0);
		
		constraintList.add(new TypeIsInterface(proxySubject));

		constraintList.add(new ClassImplementsInterface(proxyRealSubject, proxySubject));
		
		constraintList.add(new ClassImplementsInterface(proxy, proxySubject));
		constraintList.add(new ClassHasFieldOfType(proxy, proxyRealSubject));
		constraintList.add(new ClassInvokesMethodOfClass(proxy, proxyRealSubject));

	}

	@Override
	public ProxyPattern createPattern() {
		return new ProxyPattern();
	}

}
