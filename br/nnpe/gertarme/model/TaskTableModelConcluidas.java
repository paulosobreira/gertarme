package br.nnpe.gertarme.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.JTable;

public class TaskTableModelConcluidas extends TaskTableModel {
	private static final long serialVersionUID = -6570846720547742912L;

	public TaskTableModelConcluidas(ArrayList data) {
		super(data);
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

							return new Long(t0.getHoraCriacao())
									.compareTo(new Long(t1.getHoraCriacao()));
						}
					});
					fireTableDataChanged();

					break;

				case 1:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return new Long(t0.getHoraConclusao())
									.compareTo(new Long(t1.getHoraConclusao()));
						}
					});
					fireTableDataChanged();

					break;

				case 2:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return t0.getDescricao().compareTo(
									t1.getDescricao());
						}
					});
					fireTableDataChanged();

					break;

				case 3:
					Collections.sort(data, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							TaskBean t0 = (TaskBean) arg0;
							TaskBean t1 = (TaskBean) arg1;

							return t0.getTipoAtividade().compareTo(
									t1.getTipoAtividade());
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
			return "Inicio";
		case 1:
			return "Fim";
		case 2:
			return "Descrição";
		case 3:
			return "Tipo Ativiade";
		default:
			return "";
		}
	}

	public int getColumnCount() {
		return 4;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		TaskBean taskBean = (TaskBean) data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return TimeUtil
					.gregorianCalendarToString(taskBean.getHoraCriacao());
		case 1:
			return TimeUtil.gregorianCalendarToString(taskBean
					.getHoraConclusao());

		case 2:
			return taskBean.getDescricao();

		case 3:
			return taskBean.getTipoAtividade();

		default:
			return null;
		}
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return GregorianCalendar.class;
		case 1:
			return GregorianCalendar.class;
		case 2:
			return String.class;
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
				taskBean
						.setHoraCriacao(TimeUtil.string2GregorianCalendar(data));
			}

			break;
		case 1:

			String data2 = (String) aValue;

			if (data2.length() >= 16) {
				taskBean.setHoraConclusao(TimeUtil
						.string2GregorianCalendar(data2));
			}

			break;

		case 2:
			taskBean.setDescricao((String) aValue);

			break;
		case 3:
			taskBean.setTipoAtividade((String) aValue);

			break;

		default:
			break;
		}
	}

	public void inserirLinha(Object bean) {
		TaskBean taskBean = (TaskBean) bean;
		taskBean.setHoraConclusao(System.currentTimeMillis());
		super.inserirLinha(bean);

	}
}
