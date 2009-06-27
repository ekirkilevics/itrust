package edu.ncsu.csc.itrust.risk.factors;

/**
 * Abstract class for risk factors, the hasRiskFactor delegates to the protected version. Caches the result in
 * case it gets checked more than once.
 * 
 * @author Andy
 * 
 */
abstract public class PatientRiskFactor {
	private Boolean hasRisk = null;

	abstract public String getDescription();

	abstract protected boolean hasFactor();

	public boolean hasRiskFactor() {
		if (hasRisk == null)
			hasRisk = hasFactor();
		return hasRisk;
	}
}
