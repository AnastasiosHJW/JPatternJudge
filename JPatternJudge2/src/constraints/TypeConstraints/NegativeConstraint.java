package constraints.TypeConstraints;

public class NegativeConstraint extends RoleConstraint{
	
	private RoleConstraint constraint;
	
	public NegativeConstraint(RoleConstraint constraint) {
		super(null);
		this.constraint = constraint;
	}

	@Override
	public boolean check() {
		return !constraint.check();
	}
	
	@Override
	public String getTypeName()
	{
		return constraint.getTypeName();
	} 

	@Override
	public String getErrorMessage() {
		String error = constraint.getNegativeErrorMessage();
		return error;
	}

	@Override
	public String getNegativeErrorMessage() {
		String error = constraint.getErrorMessage();
		return error;
	}

}
