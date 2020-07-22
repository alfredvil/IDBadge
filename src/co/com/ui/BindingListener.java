package co.com.ui;

import java.lang.reflect.Method;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BindingListener implements DocumentListener {
	private BadgeTemplateModel model;
	private String fieldName;

	public BindingListener(BadgeTemplateModel model, String fieldName) {
		this.model = model;
		String firstChar = String.valueOf(fieldName.charAt(0));
		if (firstChar.equals(firstChar.toLowerCase())) {
			fieldName = firstChar.toUpperCase() + fieldName.substring(1, fieldName.length());
		}

		this.fieldName = fieldName;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		dataUpdated(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		dataUpdated(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		dataUpdated(e);
	}

	private void dataUpdated(DocumentEvent e) {
		try {
			String stringValue = e.getDocument().getText(e.getDocument().getStartPosition().getOffset(),
					e.getDocument().getEndPosition().getOffset() - 1);
			stringValue = stringValue.trim();
			if (!stringValue.isEmpty()) {
				int intValue = Integer.parseInt(stringValue);
				Method method = model.getClass().getDeclaredMethod("set" + fieldName, Integer.class);
				method.invoke(model, intValue);
			}
		} catch (Exception e1) {
		}
	}
}
