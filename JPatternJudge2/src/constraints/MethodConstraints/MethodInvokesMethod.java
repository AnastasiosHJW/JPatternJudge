package constraints.MethodConstraints;

import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class MethodInvokesMethod extends MethodConstraint{
	
	public MethodInvokesMethod(MethodDeclaration methodToCheck, String targetName) {
		super(methodToCheck, targetName);
	}
	
	@Override
	public boolean check() {
		//boolean satisfied = false;
		
		List<Statement> methodStatements = methodToCheck.getBody().statements();
		for (Statement statement:methodStatements)
		{
			if (indirectInvocationStatement(statement))
			{
				return true;
			}
		}
		return false;
	}

	private boolean indirectInvocationExpression(Expression exp)
	{
		boolean satisfied = false;
		if (exp == null)
		{
			return false;
		}
		if (exp instanceof ArrayAccess)
		{
			ArrayAccess aaExp = (ArrayAccess) exp;
			boolean arrayInvocation = indirectInvocationExpression(aaExp.getArray());
			boolean indexInvocation = indirectInvocationExpression(aaExp.getIndex());
			satisfied = arrayInvocation || indexInvocation;
		}
		else if(exp instanceof ArrayCreation)
		{
			ArrayCreation acExp = (ArrayCreation) exp;
			List<Expression> dimExp = acExp.dimensions();
			for (Expression aExp:dimExp)
			{
				if (indirectInvocationExpression(aExp))
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
				if (indirectInvocationExpression(aExp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if(exp instanceof Assignment)
		{
			Assignment assExp = (Assignment) exp;
			boolean left = indirectInvocationExpression(assExp.getLeftHandSide());
			boolean right = indirectInvocationExpression(assExp.getRightHandSide());
			satisfied = left || right;
		}
		else if(exp instanceof CastExpression)
		{
			CastExpression castExp = (CastExpression) exp;
			satisfied = indirectInvocationExpression(castExp.getExpression());
		}
		else if(exp instanceof ClassInstanceCreation)
		{
			ClassInstanceCreation cicExp = (ClassInstanceCreation) exp;
			if (indirectInvocationExpression(cicExp.getExpression()))
			{
				satisfied = true;
			}
			else
			{
				List<Expression> argExpList = cicExp.arguments();
				for (Expression argExp:argExpList)
				{
					if (indirectInvocationExpression(argExp))
					{
						satisfied = true;
						break;
					}
				}
			}
		}
		else if(exp instanceof ConditionalExpression)
		{
			ConditionalExpression condExp = (ConditionalExpression) exp;
			boolean conditionalCheck = indirectInvocationExpression(condExp.getExpression());
			boolean elseCheck = indirectInvocationExpression(condExp.getElseExpression());
			boolean thenCheck = indirectInvocationExpression(condExp.getThenExpression());
			satisfied = conditionalCheck || elseCheck || thenCheck;
		}
		else if(exp instanceof FieldAccess)
		{
			FieldAccess fExp = (FieldAccess) exp;
			satisfied = indirectInvocationExpression(fExp.getExpression());
		}
		else if (exp instanceof InfixExpression)
		{
			InfixExpression infixExp = (InfixExpression) exp;
			boolean left = indirectInvocationExpression(infixExp.getLeftOperand());
			boolean right = indirectInvocationExpression(infixExp.getRightOperand());
			satisfied = left || right;
		}
		else if (exp instanceof InstanceofExpression)
		{
			InstanceofExpression instExp = (InstanceofExpression) exp;
			satisfied = indirectInvocationExpression(instExp.getLeftOperand());
		}
		else if (exp instanceof LambdaExpression)
		{
			LambdaExpression lExp = (LambdaExpression) exp;
			ASTNode bodyNode = lExp.getBody();
			if (bodyNode instanceof Expression)
			{
				Expression bodyExp = (Expression) bodyNode;
				satisfied = indirectInvocationExpression(bodyExp);
			}
			else if (bodyNode instanceof Block)
			{
				Block bodyBlock = (Block) bodyNode;
				List<Statement> blockStatements = bodyBlock.statements();
				for (Statement statement:blockStatements)
				{
					if (indirectInvocationStatement(statement))
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
			if (methodExp.getName().getIdentifier().equals(targetTypeName))
			{
				satisfied = true;
			}
			else {
				boolean expCheck = indirectInvocationExpression(methodExp.getExpression());
				boolean argCheck = false;
				List<Expression> args = methodExp.arguments();
				for (Expression argExp:args)
				{
					if (indirectInvocationExpression(argExp))
					{
						argCheck = true;
						break;
					}
				}
				satisfied = expCheck || argCheck;
			}
		}
		else if (exp instanceof MethodReference)
		{
			MethodReference mrExp = (MethodReference) exp;
			if (mrExp instanceof ExpressionMethodReference)
			{
				ExpressionMethodReference emrExp = (ExpressionMethodReference) mrExp;
				satisfied = indirectInvocationExpression(emrExp.getExpression());
			}
		}
		else if (exp instanceof ParenthesizedExpression)
		{
			ParenthesizedExpression parExp = (ParenthesizedExpression) exp;
			satisfied = indirectInvocationExpression(parExp.getExpression());
		}
		else if (exp instanceof PostfixExpression)
		{
			PostfixExpression postfixExp = (PostfixExpression) exp;
			satisfied = indirectInvocationExpression(postfixExp.getOperand());
		}
		else if (exp instanceof PrefixExpression)
		{
			PrefixExpression prefixExp = (PrefixExpression) exp;
			satisfied = indirectInvocationExpression(prefixExp.getOperand());
		}
		else if (exp instanceof SuperMethodInvocation)
		{
			SuperMethodInvocation smiExp = (SuperMethodInvocation) exp;
			List<Expression> argExpList = smiExp.arguments();
			for (Expression argExp:argExpList)
			{
				if (indirectInvocationExpression(argExp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (exp instanceof SwitchExpression)
		{
			SwitchExpression sExp = (SwitchExpression) exp;
			if (indirectInvocationExpression(sExp.getExpression()))
			{
				satisfied = true;
			}
			else
			{
				List<Statement> statementList = sExp.statements();
				for (Statement statement:statementList)
				{
					if (indirectInvocationStatement(statement))
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
				if (indirectInvocationExpression(fragment.getInitializer()))
				{
					satisfied = true;
					break;
				}

			}
		}
			
			
			
			
			
		return satisfied;
	}
	
	private boolean indirectInvocationStatement(Statement statement)
	{
		boolean satisfied = false;
		if (statement == null)
		{
			return false;
		}
		if (statement instanceof AssertStatement)
		{
			AssertStatement assState = (AssertStatement) statement;
			boolean exp = indirectInvocationExpression(assState.getExpression());
			boolean message = indirectInvocationExpression(assState.getMessage());
			satisfied = exp || message;
		}
		else if (statement instanceof Block)
		{
			Block block = (Block) statement;
			List<Statement> blockStatements = block.statements();
			for (Statement blockStatement:blockStatements)
			{
				if (indirectInvocationStatement(blockStatement))
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
				if (indirectInvocationExpression(argExp))
				{
					satisfied =true;
					break;
				}
			}
		}
		else if (statement instanceof DoStatement)
		{
			DoStatement doStatement = (DoStatement) statement;
			boolean statementCheck = indirectInvocationStatement(doStatement.getBody());
			boolean expressionCheck = indirectInvocationExpression(doStatement.getExpression());
			satisfied = statementCheck || expressionCheck;
		}
		else if (statement instanceof EnhancedForStatement)
		{
			EnhancedForStatement efor = (EnhancedForStatement) statement;
			boolean body = indirectInvocationStatement(efor.getBody());
			boolean exp = indirectInvocationExpression(efor.getExpression());
			boolean var = indirectInvocationExpression(efor.getParameter().getInitializer());
			satisfied = body || exp || var;
		}
		else if (statement instanceof ExpressionStatement)
		{
			ExpressionStatement exp = (ExpressionStatement) statement;
			satisfied = indirectInvocationExpression(exp.getExpression());
		}
		else if (statement instanceof ForStatement)
		{
			ForStatement forStatement = (ForStatement) statement;
			boolean bodyCheck = indirectInvocationStatement(forStatement.getBody());
			boolean expCheck = indirectInvocationExpression(forStatement.getExpression());
			boolean initCheck = false;
			List<Expression> initializers = forStatement.initializers();
			for (Expression exp:initializers)
			{
				if (indirectInvocationExpression(exp))
				{
					initCheck = true;
					break;
				}
			}
			boolean updateCheck = false;
			List<Expression> updaters = forStatement.updaters();
			for (Expression exp:updaters)
			{
				if (indirectInvocationExpression(exp))
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
			boolean ifCheck = indirectInvocationExpression(ifStatement.getExpression());
			boolean thenCheck = indirectInvocationStatement(ifStatement.getThenStatement());
			boolean elseCheck = indirectInvocationStatement(ifStatement.getElseStatement());
			satisfied = ifCheck || thenCheck || elseCheck;
		}
		else if (statement instanceof LabeledStatement)
		{
			LabeledStatement label = (LabeledStatement) statement;
			satisfied = indirectInvocationStatement(label.getBody());
		}
		else if (statement instanceof ReturnStatement)
		{
			ReturnStatement rtn = (ReturnStatement) statement;
			satisfied = indirectInvocationExpression(rtn.getExpression());
		}
		else if (statement instanceof SuperConstructorInvocation)	
		{
			SuperConstructorInvocation sci = (SuperConstructorInvocation) statement;
			boolean expCheck = indirectInvocationExpression(sci.getExpression());
			boolean argCheck = false;
			List<Expression> argList = sci.arguments();
			for (Expression exp:argList)
			{
				if (indirectInvocationExpression(exp))
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
				if (indirectInvocationExpression(exp))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof SwitchStatement)
		{
			SwitchStatement ss = (SwitchStatement) statement;
			boolean expCheck = indirectInvocationExpression(ss.getExpression());
			boolean stateCheck = false;
			List<Statement> stateList = ss.statements();
			for (Statement state:stateList)
			{
				if (indirectInvocationStatement(state))
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
			boolean expCheck = indirectInvocationExpression(sync.getExpression());
			boolean bodyCheck = indirectInvocationStatement(sync.getBody());
			satisfied = expCheck || bodyCheck;
		}
		else if (statement instanceof ThrowStatement)
		{
			ThrowStatement throwState = (ThrowStatement) statement;
			satisfied = indirectInvocationExpression(throwState.getExpression());
		}
		else if (statement instanceof TryStatement)
		{
			TryStatement tryState = (TryStatement) statement;
			boolean bodyCheck = indirectInvocationStatement(tryState.getBody());
			boolean finallyCheck = indirectInvocationStatement(tryState.getFinally());
			boolean resourceCheck = false;
			List<Expression> resources = tryState.resources();
			for (Expression exp:resources)
			{
				if (indirectInvocationExpression(exp))
				{
					resourceCheck = true;
					break;
				}
			}
			boolean clauseCheck = false;
			List<CatchClause> clauses = tryState.catchClauses();
			for (CatchClause clause:clauses)
			{
				boolean clauseBodyCheck = indirectInvocationStatement(clause.getBody());
				boolean clauseExpCheck = indirectInvocationExpression(clause.getException().getInitializer());
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
				if (indirectInvocationExpression(fragment.getInitializer()))
				{
					satisfied = true;
					break;
				}
			}
		}
		else if (statement instanceof WhileStatement)
		{
			WhileStatement whileState = (WhileStatement) statement;
			boolean bodyCheck = indirectInvocationStatement(whileState.getBody());
			boolean expCheck = indirectInvocationExpression(whileState.getExpression());
			satisfied = bodyCheck || expCheck;
		}
		else if (statement instanceof YieldStatement)
		{
			YieldStatement yieldState = (YieldStatement) statement;
			satisfied = indirectInvocationExpression(yieldState.getExpression());
		}
		
		return satisfied;
	}


}
