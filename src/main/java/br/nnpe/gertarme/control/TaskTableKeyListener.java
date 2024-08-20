package br.nnpe.gertarme.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import br.nnpe.gertarme.model.TaskBean;
import br.nnpe.gertarme.model.TaskTableModel;
import br.nnpe.gertarme.view.TaskWindow;

public class TaskTableKeyListener extends KeyAdapter {
	private JTable table;
	private TaskTableModel model;
	private TaskWindow taskWindow;

	public TaskTableKeyListener(JTable table, TaskWindow taskWindow) {
		super();
		this.table = table;
		model = (TaskTableModel) table.getModel();
		this.taskWindow = taskWindow;
	}

	public void keyPressed(KeyEvent e) {
		int keyCoode = e.getKeyCode();

		if (keyCoode == KeyEvent.VK_F3) {
			String valor = JOptionPane.showInputDialog("Entre com o texto");
			taskWindow.procurar(valor);
		}

		if (KeyEvent.VK_INSERT == keyCoode) {
			model.inserirLinha(new TaskBean());
			table.setRowSelectionInterval(table.getRowCount() - 1, table
					.getRowCount() - 1);
			table.setColumnSelectionInterval(1, 1);
		}

		if (KeyEvent.VK_DELETE == keyCoode) {
			int[] selected = table.getSelectedRows();

			for (int i = selected.length - 1; i >= 0; i--)
				model.removerLinha(selected[i]);
		}

		if (e.isControlDown() && (keyCoode == KeyEvent.VK_F)) {
			taskWindow.moverSelPendentes();
		}

		if (e.isControlDown() && (keyCoode == KeyEvent.VK_P)) {
			taskWindow.moverSelConcluidas();
		}

		if (e.isControlDown() && (keyCoode == KeyEvent.VK_S)) {
			taskWindow.salvar();
		}
	}
}
