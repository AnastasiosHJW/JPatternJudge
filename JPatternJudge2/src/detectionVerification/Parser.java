package detectionVerification;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser {
	private ASTParser parser; 
	
	public Parser()
	{
		parser = ASTParser.newParser(AST.JLS13);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
	}
	
	public CompilationUnit parse(ICompilationUnit source)
	{
		parser.setSource(source);
		return (CompilationUnit) parser.createAST(null);
	}

}
