package constraints.MethodConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodReference;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.YieldStatement;

public class MethodInstantiatesObjectOfType extends MethodConstraint{

	public MethodInstantiatesObjectOfType(MethodDeclaration methodToCheck, String targetName) {
		super(methodToCheck, targetName);
	}

	@Override
	public boolean check() {
		boolean satisfied = false;
		
		List<Statement> statements = methodToCheck.getBody().statements();
		for (Statement statement:statements)
		{
			satisfied = indirectCreationStatement(statement);
		}
		return satisfied;
	}
	

	private boolean indirectCreationExpression(Expression exp)
	{
		boolean satisfied = false;
		if (exp == null)
		{
			return false;
		}
		if (exp instanceof ArrayAccess)
		{
			ArrayAccess aaExp = (ArrayAccess) exp;
			boolean arrayInvocation = indirectCreationExpression(aaExp.getArray());
			boolean indexInvocation = indirectCreationExpression(aaExp.getIndex());
			satisfied = arrayInvocation || indexInvocation;
		}
		else if(exp instanceof ArrayCreation)
		{
			ArrayCreation acExp = (ArrayCreation) exp;
			List<Expression> dimExp = acExp.dimensions();
			for (Expression aExp:dimExp)
			{
				if (indirectCreationExpression(aExp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if(exp instanceof ArrayInitializer)
		{
			ArrayInitializer aiExp = (ArrayInitializer) exp;
			List<Expression> initExp = aiExp.expressions();
			for (Expression aExp:initExp)
			{
				if (indirectCreationExpression(aExp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if(exp instanceof Assignment)
		{
			Assignment assExp = (Assignment) exp;
			boolean left = indirectCreationExpression(assExp.getLeftHandSide());
			boolean right = indirectCreationExpression(assExp.getRightHandSide());
			satisfied = left || right;
		}
		else if(exp instanceof CastExpression)
		{
			CastExpression castExp = (CastExpression) exp;
			satisfied = indirectCreationExpression(castExp.getExpression());
		}
		else if(exp instanceof ClassInstanceCreation)
		{
			ClassInstanceCreation cicExp = (ClassInstanceCreation) exp;
			if (compareTypeNames(cicExp.getType()))
			{
				satisfied = true;
			}
			else {
				if (indirectCreationExpression(cicExp.getExpression()))
				{
					satisfied = true;
				}
				else
				{
					List<Expression> argExpList = cicExp.arguments();
					for (Expression argExp:argExpList)
					{
						if (indirectCreationExpression(argExp))
						{
							satisfied = true;
							break;
						}
					}
				}
			}
		}
		else if(exp instanceof ConditionalExpression)
		{
			ConditionalExpression condExp = (ConditionalExpression) exp;
			boolean conditionalCheck = indirectCreationExpression(condExp.getExpression());
			boolean elseCheck = indirectCreationExpression(condExp.getElseExpression());
			boolean thenCheck = indirectCreationExpression(condExp.getThenExpression());
			satisfied = conditionalCheck || elseCheck || thenCheck;
		}
		else if(exp instanceof FieldAccess)
		{
			FieldAccess fExp = (FieldAccess) exp;
			satisfied = indirectCreationExpression(fExp.getExpression());
		}
		else if (exp instanceof InfixExpression)
		{
			InfixExpression infixExp = (InfixExpression) exp;
			boolean left = indirectCreationExpression(infixExp.getLeftOperand());
			boolean right = indirectCreationExpression(infixExp.getRightOperand());
			satisfied = left || right;
		}
		else if (exp instanceof InstanceofExpression)
		{
			InstanceofExpression instExp = (InstanceofExpression) exp;
			satisfied = indirectCreationExpression(instExp.getLeftOperand());
		}
		else if (exp instanceof LambdaExpression)
		{
			LambdaExpression lExp = (LambdaExpression) exp;
			ASTNode bodyNode = lExp.getBody();
			if (bodyNode instanceof Expression)
			{
				Expression bodyExp = (Expression) bodyNode;
				satisfied = indirectCreationExpression(bodyExp);
			}
			else if (bodyNode instanceof Block)
			{
				Block bodyBlock = (Block) bodyNode;
				List<Statement> blockStatements = bodyBlock.statements();
				for (Statement statement:blockStatements)
				{
					if (indirectCreationStatement(statement))
					{
						satisfied = true;
						break;
					}

				}
			}
		}
		else if (exp instanceof MethodInvocation)
		{
			MethodInvocation methodExp = (MethodInvocation) exp;
			boolean expCheck = indirectCreationExpression(methodExp.getExpression());
			boolean argCheck = false;
			List<Expression> args = methodExp.arguments();
			for (Expression argExp:args)
			{
				if (indirectCreationExpression(argExp))
				{
					argCheck = true;
					break;
				}
			}
			satisfied = expCheck || argCheck;
		
		}
		else if (exp instanceof MethodReference)
		{
			MethodReference mrExp = (MethodReference) exp;
			if (mrExp instanceof ExpressionMethodReference)
			{
				ExpressionMethodReference emrExp = (ExpressionMethodReference) mrExp;
				satisfied = indirectCreationExpression(emrExp.getExpression());
			}
		}
		else if (exp instanceof ParenthesizedExpression)
		{
			ParenthesizedExpression parExp = (ParenthesizedExpression) exp;
			satisfied = indirectCreationExpression(parExp.getExpression());
		}
		else if (exp instanceof PostfixExpression)
		{
			PostfixExpression postfixExp = (PostfixExpression) exp;
			satisfied = indirectCreationExpression(postfixExp.getOperand());
		}
		else if (exp instanceof PrefixExpression)
		{
			PrefixExpression prefixExp = (PrefixExpression) exp;
			satisfied = indirectCreationExpression(prefixExp.getOperand());
		}
		else if (exp instanceof SuperMethodInvocation)
		{
			SuperMethodInvocation smiExp = (SuperMethodInvocation) exp;
			List<Expression> argExpList = smiExp.arguments();
			for (Expression argExp:argExpList)
			{
				if (indirectCreationExpression(argExp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (exp instanceof SwitchExpression)
		{
			SwitchExpression sExp = (SwitchExpression) exp;
			if (indirectCreationExpression(sExp.getExpression()))
			{
				satisfied = true;
			}
			else
			{
				List<Statement> statementList = sExp.statements();
				for (Statement statement:statementList)
				{
					if (indirectCreationStatement(statement))
					{
						satisfied = true;
						break;
					}
				}
			}
		}
		else if (exp instanceof VariableDeclarationExpression)
		{
			VariableDeclarationExpression varExp = (VariableDeclarationExpression) exp;
			List<VariableDeclarationFragment> fragments = varExp.fragments();
			for (VariableDeclarationFragment fragment:fragments)
			{
				if (indirectCreationExpression(fragment.getInitializer()))
				{
					satisfied = true;
					break;
				}

			}
		}
			
			
			
			
			
		return satisfied;
	}
	
	private boolean indirectCreationStatement(Statement statement)
	{
		boolean satisfied = false;
		if (statement == null)
		{
			return false;
		}
		if (statement instanceof AssertStatement)
		{
			AssertStatement assState = (AssertStatement) statement;
			boolean exp = indirectCreationExpression(assState.getExpression());
			boolean message = indirectCreationExpression(assState.getMessage());
			satisfied = exp || message;
		}
		else if (statement instanceof Block)
		{
			Block block = (Block) statement;
			List<Statement> blockStatements = block.statements();
			for (Statement blockStatement:blockStatements)
			{
				if (indirectCreationStatement(blockStatement))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof ConstructorInvocation)
		{
			ConstructorInvocation constr = (ConstructorInvocation) statement;
			List<Expression> argExpList = constr.arguments();
			for (Expression argExp:argExpList)
			{
				if (indirectCreationExpression(argExp))
				{
					satisfied =true;
					break;
				}
			}
		}
		else if (statement instanceof DoStatement)
		{
			DoStatement doStatement = (DoStatement) statement;
			boolean statementCheck = indirectCreationStatement(doStatement.getBody());
			boolean expressionCheck = indirectCreationExpression(doStatement.getExpression());
			satisfied = statementCheck || expressionCheck;
		}
		else if (statement instanceof EnhancedForStatement)
		{
			EnhancedForStatement efor = (EnhancedForStatement) statement;
			boolean body = indirectCreationStatement(efor.getBody());
			boolean exp = indirectCreationExpression(efor.getExpression());
			boolean var = indirectCreationExpression(efor.getParameter().getInitializer());
			satisfied = body || exp || var;
		}
		else if (statement instanceof ExpressionStatement)
		{
			ExpressionStatement exp = (ExpressionStatement) statement;
			satisfied = indirectCreationExpression(exp.getExpression());
		}
		else if (statement instanceof ForStatement)
		{
			ForStatement forStatement = (ForStatement) statement;
			boolean bodyCheck = indirectCreationStatement(forStatement.getBody());
			boolean expCheck = indirectCreationExpression(forStatement.getExpression());
			boolean initCheck = false;
			List<Expression> initializers = forStatement.initializers();
			for (Expression exp:initializers)
			{
				if (indirectCreationExpression(exp))
				{
					initCheck = true;
					break;
				}
			}
			boolean updateCheck = false;
			List<Expression> updaters = forStatement.updaters();
			for (Expression exp:updaters)
			{
				if (indirectCreationExpression(exp))
				{
					updateCheck = true;
					break;
				}
			}
			satisfied = bodyCheck || expCheck || initCheck || updateCheck;
		}
		else if (statement instanceof IfStatement)
		{
			IfStatement ifStatement = (IfStatement) statement;
			boolean ifCheck = indirectCreationExpression(ifStatement.getExpression());
			boolean thenCheck = indirectCreationStatement(ifStatement.getThenStatement());
			boolean elseCheck = indirectCreationStatement(ifStatement.getElseStatement());
			satisfied = ifCheck || thenCheck || elseCheck;
		}
		else if (statement instanceof LabeledStatement)
		{
			LabeledStatement label = (LabeledStatement) statement;
			satisfied = indirectCreationStatement(label.getBody());
		}
		else if (statement instanceof ReturnStatement)
		{
			ReturnStatement rtn = (ReturnStatement) statement;
			satisfied = indirectCreationExpression(rtn.getExpression());
		}
		else if (statement instanceof SuperConstructorInvocation)	
		{
			SuperConstructorInvocation sci = (SuperConstructorInvocation) statement;
			boolean expCheck = indirectCreationExpression(sci.getExpression());
			boolean argCheck = false;
			List<Expression> argList = sci.arguments();
			for (Expression exp:argList)
			{
				if (indirectCreationExpression(exp))
				{
					argCheck = true;
					break;
				}
			}
			satisfied = expCheck || argCheck;
		}
		else if (statement instanceof SwitchCase)
		{
			SwitchCase sc = (SwitchCase) statement;
			List<Expression> expList = sc.expressions();
			for (Expression exp:expList)
			{
				if (indirectCreationExpression(exp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof SwitchStatement)
		{
			SwitchStatement ss = (SwitchStatement) statement;
			boolean expCheck = indirectCreationExpression(ss.getExpression());
			boolean stateCheck = false;
			List<Statement> stateList = ss.statements();
			for (Statement state:stateList)
			{
				if (indirectCreationStatement(state))
				{
					stateCheck = true;
					break;
				}
			}
			satisfied = expCheck || stateCheck;
		}
		else if (statement instanceof SynchronizedStatement)
		{
			SynchronizedStatement sync = (SynchronizedStatement) statement;
			boolean expCheck = indirectCreationExpression(sync.getExpression());
			boolean bodyCheck = indirectCreationStatement(sync.getBody());
			satisfied = expCheck || bodyCheck;
		}
		else if (statement instanceof ThrowStatement)
		{
			ThrowStatement throwState = (ThrowStatement) statement;
			satisfied = indirectCreationExpression(throwState.getExpression());
		}
		else if (statement instanceof TryStatement)
		{
			TryStatement tryState = (TryStatement) statement;
			boolean bodyCheck = indirectCreationStatement(tryState.getBody());
			boolean finallyCheck = indirectCreationStatement(tryState.getFinally());
			boolean resourceCheck = false;
			List<Expression> resources = tryState.resources();
			for (Expression exp:resources)
			{
				if (indirectCreationExpression(exp))
				{
					resourceCheck = true;
					break;
				}
			}
			boolean clauseCheck = false;
			List<CatchClause> clauses = tryState.catchClauses();
			for (CatchClause clause:clauses)
			{
				boolean clauseBodyCheck = indirectCreationStatement(clause.getBody());
				boolean clauseExpCheck = indirectCreationExpression(clause.getException().getInitializer());
				if (clauseBodyCheck || clauseExpCheck)
				{
					clauseCheck = true;
					break;
				}
			}
			satisfied = bodyCheck || finallyCheck || resourceCheck || clauseCheck;
		}
		else if (statement instanceof VariableDeclarationStatement)
		{
			VariableDeclarationStatement vds = (VariableDeclarationStatement) statement;
			List<VariableDeclarationFragment> fragments = vds.fragments();
			for (VariableDeclarationFragment fragment:fragments)
			{
				if (indirectCreationExpression(fragment.getInitializer()))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof WhileStatement)
		{
			WhileStatement whileState = (WhileStatement) statement;
			boolean bodyCheck = indirectCreationStatement(whileState.getBody());
			boolean expCheck = indirectCreationExpression(whileState.getExpression());
			satisfied = bodyCheck || expCheck;
		}
		else if (statement instanceof YieldStatement)
		{
			YieldStatement yieldState = (YieldStatement) statement;
			satisfied = indirectCreationExpression(yieldState.getExpression());
		}
		return satisfied;
	}


}
