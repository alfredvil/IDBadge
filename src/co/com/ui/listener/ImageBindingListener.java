package co.com.ui.listener;

import java.lang.reflect.Method;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import co.com.model.BadgeImageModel;
import co.com.model.BadgeTemplateModel;

public class ImageBindingListener implements DocumentListener {
	private BadgeImageModel dataModel;
	private String fieldName;

	public ImageBindingListener(BadgeImageModel dataModel, String fieldName) {
		this.dataModel = dataModel;
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
				Method method = dataModel.getClass().getDeclaredMethod("set" + fieldName, Integer.class);
				method.invoke(dataModel, intValue);
			}
		} catch (Exception e1) {
		}
	}
}
