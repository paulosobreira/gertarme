package br.nnpe.gertarme.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class TaskTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected ArrayList data = new ArrayList();

	public TaskTableModel(ArrayList data) {
		this.data = data;
	}

	public void addMouseListener(final JTable table) {
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				int tableColumn = table.columnAtPoint(event.getPoint());
				int modelColumn = table.convertColumnIndexToModel(tableColumn);

				switch (modelColumn) {
				case 0:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return new Long(t0.getHoraTarefa())
									.compareTo(new Long(t1.getHoraTarefa()));
						}
					});
					fireTableDataChanged();

					break;

				case 1:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return t0.getDescricao()
									.compareTo(t1.getDescricao());
						}
					});
					fireTableDataChanged();

					break;

				case 2:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return new Boolean(t0.isAtivada())
									.compareTo(new Boolean(t1.isAtivada()));
						}
					});
					fireTableDataChanged();

					break;
				case 3:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return t0.getTipoAtividade()
									.compareTo(t1.getTipoAtividade());
						}
					});
					fireTableDataChanged();

					break;

				default:
					break;
				}
			}
		});
	}

	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Hora Alarme";

		case 1:
			return "Descrição";

		case 2:
			return "Alarme";

		case 3:
			return "Tipo Atividade";
		default:
			return "";
		}
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return 4;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		TaskBean taskBean = (TaskBean) data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return TimeUtil.gregorianCalendarToString(taskBean.getHoraTarefa());

		case 1:
			return taskBean.getDescricao();

		case 2:
			return new Boolean(taskBean.isAtivada());
		case 3:
			return taskBean.getTipoAtividade();

		default:
			return null;
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return GregorianCalendar.class;

		case 1:
			return String.class;

		case 2:
			return Boolean.class;

		case 3:
			return String.class;
		default:
			return null;
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		TaskBean taskBean = (TaskBean) data.get(rowIndex);

		switch (columnIndex) {
		case 0:

			String data = (String) aValue;

			if (data.length() >= 16) {
				taskBean.setHoraTarefa(TimeUtil.string2GregorianCalendar(data));
			}

			break;

		case 1:
			taskBean.setDescricao((String) aValue);

			break;

		case 2:
			taskBean.setAtivada(((Boolean) aValue).booleanValue());

		case 3:
			if (aValue instanceof String)
				taskBean.setTipoAtividade((String) aValue);
		default:
			break;
		}
	}

	public void inserirLinha(Object bean) {
		int row = data.size();
		data.add(bean);
		fireTableRowsInserted(row, row);
	}

	public void removerLinha(int row) {
		data.remove(row);
		fireTableRowsDeleted(row, row);
	}
}
