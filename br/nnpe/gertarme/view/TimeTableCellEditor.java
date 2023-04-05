package br.nnpe.gertarme.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import br.nnpe.gertarme.control.TimeMaskKeyListener;
import br.nnpe.gertarme.model.TaskBean;
import br.nnpe.gertarme.model.TaskTableModelConcluidas;
import br.nnpe.gertarme.model.TimeUtil;

public class TimeTableCellEditor extends AbstractCellEditor implements
		TableCellEditor {
	private static final long serialVersionUID = 1L;
	private ArrayList data;
	private JTextField textField;

	public TimeTableCellEditor(ArrayList data) {
		this.textField = new JTextField();
		this.data = data;
		this.textField.addKeyListener(new TimeMaskKeyListener(textField));
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		TaskBean taskBean = (TaskBean) data.get(row);
		if (column == 0) {
			if (table.getModel() instanceof TaskTableModelConcluidas) {
				textField.setText(TimeUtil.gregorianCalendarToString(taskBean
						.getHoraCriacao()));
			} else {
				textField.setText(TimeUtil.gregorianCalendarToString(taskBean
						.getHoraTarefa()));

			}
		}
		if (column == 1 && table.getModel() instanceof TaskTableModelConcluidas) {
			textField.setText(TimeUtil.gregorianCalendarToString(taskBean
					.getHoraConclusao()));
		}
		return textField;
	}

	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent == null)
			return false;
		return true;
	}

	public Object getCellEditorValue() {
		return textField.getText();
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return super.shouldSelectCell(anEvent);
	}

}
