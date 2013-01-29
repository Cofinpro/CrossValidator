/**
 * 
 */
package de.cofinpro.crossvalidator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.routines.CodeValidator;

import de.cofinpro.demo.CreditCard;
import de.cofinpro.demo.CreditCardType;

/**
 * @author gtudan
 * 
 */
@ManagedBean(name = "creditCardValidator")
public class CreditCardValidator extends AbstractCrossValidator<CreditCard> {

	private Logger log = Logger.getLogger(CreditCardValidator.class.getName());

	@Override
	public boolean executeValidation(CreditCard creditCard) {
		log.fine("Starting credit card validation");

		boolean isValid = true;
		isValid = validateCardNumber(creditCard.getCardNumber()) && isValid;

		// Only check the type if the number was valid, since the type depends
		// on it
		if (isValid) {
			isValid = validateCardType(creditCard.getCardNumber(),
					creditCard.getCardType())
					&& isValid;
		}

		isValid = validateExpirationDate(creditCard.getValidUntilMonth(),
				creditCard.getValidUntilYear()) && isValid;
		return isValid;
	}

	private boolean validateCardNumber(String cardNumber) {
		if (cardNumber == null || cardNumber.isEmpty()) {
			return false;
		}
		if (new org.apache.commons.validator.routines.CreditCardValidator()
				.isValid(cardNumber)) {
			return true;
		} else {
			addMessage(new FacesMessage("Die Kartennummer ist ungültig."));
			return false;
		}
	}

	private boolean validateCardType(String cardNumber, CreditCardType cardType) {
		CodeValidator apacheCardValidator;
		switch (cardType) {
		case VISA:
			apacheCardValidator = org.apache.commons.validator.routines.CreditCardValidator.VISA_VALIDATOR;
			break;
		case AMERICAN_EXPRESS:
			apacheCardValidator = org.apache.commons.validator.routines.CreditCardValidator.AMEX_VALIDATOR;
			break;
		case MASTERCARD:
			apacheCardValidator = org.apache.commons.validator.routines.CreditCardValidator.MASTERCARD_VALIDATOR;
			break;
		default:
			throw new ValidatorException(new FacesMessage(
					"Kartentyp wird nicht akzeptiert."));
		}

		org.apache.commons.validator.routines.CreditCardValidator validator = new org.apache.commons.validator.routines.CreditCardValidator(
				new CodeValidator[] { apacheCardValidator });

		if (validator.isValid(cardNumber)) {
			return true;
		} else {
			addMessage(new FacesMessage(
					"Der Kartentyp passt nicht zur Kartennummer"));
			return false;
		}
	}

	private boolean validateExpirationDate(int month, int year) {
		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
			addMessage(new FacesMessage("Die Karte ist abgelaufen."));

			return false;
		} else {
			return true;
		}
	}

}
