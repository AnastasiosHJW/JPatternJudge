package constraints.MethodConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class MethodHasVariableOfType extends MethodConstraint{

	public MethodHasVariableOfType(MethodDeclaration methodToCheck, String targetName) {
		super(methodToCheck, targetName);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		List<Statement> statements = methodToCheck.getBody().statements();
		for (Statement statement:statements)
		{
			satisfied = indirectVariableStatement(statement);
		}
		
		return satisfied;
		
	}
	
	private boolean indirectVariableStatement(Statement statement)
	{
		boolean satisfied = false;
		if (statement == null)
		{
			return false;
		}
		else if (statement instanceof Block)
		{
			Block block = (Block) statement;
			List<Statement> blockStatements = block.statements();
			for (Statement blockStatement:blockStatements)
			{
				if (indirectVariableStatement(blockStatement))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof DoStatement)
		{
			DoStatement doStatement = (DoStatement) statement;
			satisfied = indirectVariableStatement(doStatement.getBody());
		}
		else if (statement instanceof EnhancedForStatement)
		{
			EnhancedForStatement efor = (EnhancedForStatement) statement;
			satisfied = indirectVariableStatement(efor.getBody());
		}
		else if (statement instanceof ForStatement)
		{
			ForStatement forStatement = (ForStatement) statement;
			satisfied = indirectVariableStatement(forStatement.getBody());
		}
		else if (statement instanceof IfStatement)
		{
			IfStatement ifStatement = (IfStatement) statement;
			boolean thenCheck = indirectVariableStatement(ifStatement.getThenStatement());
			boolean elseCheck = indirectVariableStatement(ifStatement.getElseStatement());
			satisfied = thenCheck || elseCheck;
		}
		else if (statement instanceof LabeledStatement)
		{
			LabeledStatement label = (LabeledStatement) statement;
			satisfied = indirectVariableStatement(label.getBody());
		}
		else if (statement instanceof SwitchStatement)
		{
			SwitchStatement ss = (SwitchStatement) statement;
			boolean stateCheck = false;
			List<Statement> stateList = ss.statements();
			for (Statement state:stateList)
			{
				if (indirectVariableStatement(state))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof SynchronizedStatement)
		{
			SynchronizedStatement sync = (SynchronizedStatement) statement;
			satisfied = indirectVariableStatement(sync.getBody());
		}
		else if (statement instanceof TryStatement)
		{
			TryStatement tryState = (TryStatement) statement;
			boolean bodyCheck = indirectVariableStatement(tryState.getBody());
			boolean finallyCheck = indirectVariableStatement(tryState.getFinally());
			boolean clauseCheck = false;
			List<CatchClause> clauses = tryState.catchClauses();
			for (CatchClause clause:clauses)
			{
				boolean clauseBodyCheck = indirectVariableStatement(clause.getBody());
				if (clauseBodyCheck)
				{
					clauseCheck = true;
					break;
				}
			}
			satisfied = bodyCheck || finallyCheck || clauseCheck;
		}
		else if (statement instanceof VariableDeclarationStatement)
		{
			VariableDeclarationStatement vds = (VariableDeclarationStatement) statement;
			if (compareTypeNames(vds.getType()))
			{	
				satisfied = true;
			}
		}
		else if (statement instanceof WhileStatement)
		{
			WhileStatement whileState = (WhileStatement) statement;
			satisfied = indirectVariableStatement(whileState.getBody());
		}	
		
		return satisfied;
	}

}
