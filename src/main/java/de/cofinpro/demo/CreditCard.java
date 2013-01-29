/**
 * 
 */
package de.cofinpro.demo;

/**
 * @author gtudan
 *
 */
public class CreditCard {

	private CreditCardType cardType;
	private String cardNumber;
	private int validUntilMonth;
	private int validUntilYear;

	
	/**
	 * @return the cardType
	 */
	public CreditCardType getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(CreditCardType cardType) {
		this.cardType = cardType;
	}
	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * @return the validUntilMonth
	 */
	public int getValidUntilMonth() {
		return validUntilMonth;
	}
	/**
	 * @param validUntilMonth the validUntilMonth to set
	 */
	public void setValidUntilMonth(int validUntilMonth) {
		this.validUntilMonth = validUntilMonth;
	}
	/**
	 * @return the validUntilYear
	 */
	public int getValidUntilYear() {
		return validUntilYear;
	}
	/**
	 * @param validUntilYear the validUntilYear to set
	 */
	public void setValidUntilYear(int validUntilYear) {
		this.validUntilYear = validUntilYear;
	}
}
