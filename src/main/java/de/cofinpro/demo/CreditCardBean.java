/**
 * 
 */
package de.cofinpro.demo;

import javax.faces.bean.ManagedBean;

/**
 * @author gtudan
 *
 */
@ManagedBean(name="creditCardBean")
public class CreditCardBean {

	private CreditCard creditCard = new CreditCard();

	/**
	 * @return the creditCard
	 */
	public CreditCard getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	public CreditCardType[] getAcceptedCardTypes() {
		return CreditCardType.values();
	}
}
