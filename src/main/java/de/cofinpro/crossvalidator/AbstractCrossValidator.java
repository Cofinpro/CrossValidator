package de.cofinpro.crossvalidator;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;



@RequestScoped
public abstract class AbstractCrossValidator<E> implements Validator {

	private Map<String, Object> parameterMap = new HashMap<String, Object>();
	private Class<E> clazz;
	private List<FacesMessage> facesMessages;

	// A dummy property, for which messages will be displayed
	protected String validate = "";

	public String getValidate() {
		return validate;
	}

	@SuppressWarnings("unchecked")
	public AbstractCrossValidator() {
		clazz = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		facesMessages = new LinkedList<FacesMessage>();
	}



	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		List<UIComponent> children = new LinkedList<UIComponent>(component
				.getParent().getChildren());
		// remove inputhidden
		children.remove(component);
		fillParameterMap(children);
		E instanceToValidate = createInstance();
		executeValidation(instanceToValidate);
		if (!facesMessages.isEmpty()) {
			throw new ValidatorException(facesMessages);
		}
	}

	private void fillParameterMap(List<UIComponent> components) {
		for (UIComponent child : components) {
			if (child instanceof UIInput) {
				UIInput input = (UIInput) child;
				if (!parameterMap
						.containsKey(getPropertyNameByUIComponent(input))) {
					String propertyName = getPropertyNameByUIComponent(child);
					parameterMap.put(propertyName, input.getValue());
				}
			}
			// Recurse the elements children
			if (child.getChildCount() > 0) {
				fillParameterMap(child.getChildren());
			}
		}
	}

	private String getPropertyNameByUIComponent(UIComponent component) {
		String expressionString = component.getValueExpression("value")
				.getExpressionString();
		return expressionString.substring(
				expressionString.lastIndexOf('.') + 1,
				expressionString.length() - 1);
	}

	private E createInstance() {

		E instance = null;
		try {
			instance = clazz.newInstance();
			for (String propertyName : parameterMap.keySet()) {
				Method m = findMethodFor(propertyName, clazz);
				if (m != null) {
					m.invoke(instance, parameterMap.get(propertyName));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	private Method findMethodFor(String propertyName, Class<?> clazz) {
		for (Method m : clazz.getMethods()) {
			if (m.getName().equalsIgnoreCase("set" + propertyName)) {
				return m;
			}
		}
		return null;
	}

	public abstract boolean executeValidation(E instanceToValidate);

	// TODO add convenience methods

	public void addMessage(FacesMessage message) {
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		facesMessages.add(message);
	}

}
