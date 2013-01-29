/**
 * 
 */
package de.cofinpro.demo;

/**
 * @author gtudan
 *
 */
public enum CreditCardType {

	VISA("VISA"),
	MASTERCARD("MasterCard"),
	AMERICAN_EXPRESS("American Express");
	
	private String string;


	/**
	 * @param string
	 */
	private CreditCardType(String string) {
		this.string = string;
	}


	@Override
	public String toString() {
		return string;
	}

}
