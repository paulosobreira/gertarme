package br.nnpe.gertarme.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import br.nnpe.gertarme.control.ControladorArmazenamento;
import br.nnpe.gertarme.control.TaskTableKeyListener;
import br.nnpe.gertarme.model.TaskBean;
import br.nnpe.gertarme.model.TaskBeanArmazenamento;
import br.nnpe.gertarme.model.TaskTableModel;
import br.nnpe.gertarme.model.TaskTableModelConcluidas;

public class TaskWindow extends JInternalFrame {
	private static final long serialVersionUID = 2203424149937354491L;
	private JTabbedPane tabbedPane;
	private JTable pendenciasTable;
	private JTable concluidosTable;
	private TaskBeanArmazenamento taskBeanArmazenamento = new TaskBeanArmazenamento();
	private ControladorArmazenamento armazenamento = new ControladorArmazenamento();
	private JTextArea textArea;
	public static final String[] tipoAtividade = { "Desenvolvimento",
			"Correções", "Análise", "Reunião", "Estudo", "Requisitos",
			"Pesquisa", "Particular", "Consultoria", "Externo", "Teste",
			"Migração", "Nenhuma das citadas" };

	public TaskWindow(String nomeTarefas) {
		taskBeanArmazenamento.setNomeTarefas(nomeTarefas);
		setarComponentes();
	}

	public TaskWindow(TaskBeanArmazenamento taskBeanArmazenamento) {
		this.taskBeanArmazenamento = taskBeanArmazenamento;
		setarComponentes();
	}

	private void setarComponentes() {
		tabbedPane = new JTabbedPane();

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(tabbedPane, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		container.add(southPanel, BorderLayout.SOUTH);

		JButton inserir = new JButton("Inserir (Insert)");
		JButton remover = new JButton("Remover (Delete)");

		JButton concluir = new JButton("Concluida (Ctrl+f)");
		JButton pendente = new JButton("Pendente (Ctrl+p)");
		JButton salvar = new JButton("Salvar (Ctrl+s)");
		JButton salvarComo = new JButton("Salvar Como");
		JButton alterarNome = new JButton("Alterar Nome");
		southPanel.add(inserir);
		southPanel.add(remover);
		southPanel.add(concluir);
		southPanel.add(pendente);
		southPanel.add(salvar);
		southPanel.add(salvarComo);
		southPanel.add(alterarNome);
		inserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskTableModel pendTaskTableModel = (TaskTableModel) pendenciasTable
						.getModel();
				pendTaskTableModel.inserirLinha(new TaskBean());
			}
		});
		remover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selected = pendenciasTable.getSelectedRows();

				for (int i = selected.length - 1; i >= 0; i--) {
					TaskTableModel tableModel = (TaskTableModel) pendenciasTable
							.getModel();
					tableModel.removerLinha(selected[i]);
				}
				selected = concluidosTable.getSelectedRows();

				for (int i = selected.length - 1; i >= 0; i--) {
					TaskTableModel tableModel = (TaskTableModel) concluidosTable
							.getModel();
					tableModel.removerLinha(selected[i]);
				}
			}
		});

		concluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moverSelPendentes();
			}
		});
		pendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moverSelConcluidas();
			}
		});
		salvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		salvarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					salvarComo();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			}
		});
		alterarNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskBeanArmazenamento
						.setNomeTarefas(JOptionPane.showInputDialog(
								"Entre com o nome Agrupamento de Tarefas"));
				setTitle(taskBeanArmazenamento.getNomeTarefas());
			}
		});
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		prepararJTables();
		textArea = new JTextArea(taskBeanArmazenamento.getNotas());

		JScrollPane pane = new JScrollPane(textArea);
		tabbedPane.addTab("Notas", pane);
		textArea.setCaretPosition(taskBeanArmazenamento.getNotas().length());
		setTitle(taskBeanArmazenamento.getNomeTarefas());
		setSize(1280, 720);
	}

	public void salvarComo() throws IOException {
		taskBeanArmazenamento.setNomeTarefas(
				JOptionPane.showInputDialog(this, "Identificador"));
		taskBeanArmazenamento.setNotas(textArea.getText());
		if (armazenamento.gravarArquivoComo(taskBeanArmazenamento))
			setTitle(taskBeanArmazenamento.getNomeTarefas());
	}

	public void salvar() {
		try {
			taskBeanArmazenamento.setNotas(textArea.getText());
			armazenamento.gravarArquivo(taskBeanArmazenamento);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void moverSelPendentes() {
		int[] selected = pendenciasTable.getSelectedRows();
		TaskTableModel pendTaskTableModel = (TaskTableModel) pendenciasTable
				.getModel();
		TaskTableModel concTaskTableModel = (TaskTableModel) concluidosTable
				.getModel();

		for (int i = 0; i < selected.length; i++) {
			concTaskTableModel.inserirLinha(
					taskBeanArmazenamento.getPendenciasData().get(selected[i]));
		}

		for (int i = selected.length - 1; i >= 0; i--)
			pendTaskTableModel.removerLinha(selected[i]);
		concluidosTable.requestFocus();
	}

	public void moverSelConcluidas() {
		int[] selected = concluidosTable.getSelectedRows();
		TaskTableModel pendTaskTableModel = (TaskTableModel) pendenciasTable
				.getModel();
		TaskTableModel concTaskTableModel = (TaskTableModel) concluidosTable
				.getModel();

		for (int i = 0; i < selected.length; i++) {
			pendTaskTableModel.inserirLinha(
					taskBeanArmazenamento.getConcluidosData().get(selected[i]));
		}

		for (int i = selected.length - 1; i >= 0; i--)
			concTaskTableModel.removerLinha(selected[i]);
	}

	private void prepararJTables() {
		pendenciasTable = new JTable();
		concluidosTable = new JTable();
		new ExcelAdapter(pendenciasTable);
		new ExcelAdapter(concluidosTable);
		ToolTipManager.sharedInstance().unregisterComponent(pendenciasTable);
		ToolTipManager.sharedInstance()
				.unregisterComponent(pendenciasTable.getTableHeader());
		ToolTipManager.sharedInstance().unregisterComponent(concluidosTable);
		ToolTipManager.sharedInstance()
				.unregisterComponent(concluidosTable.getTableHeader());

		tabbedPane.addTab("Pendecias", new JScrollPane(pendenciasTable));
		tabbedPane.addTab("Concluidos", new JScrollPane(concluidosTable));

		TaskTableModel pendenciasTaskTableModel = new TaskTableModel(
				taskBeanArmazenamento.getPendenciasData());
		pendenciasTable.setModel(pendenciasTaskTableModel);
		pendenciasTable.getColumn("Hora Alarme").setMinWidth(120);
		pendenciasTable.getColumn("Descrição").setMinWidth(400);
		pendenciasTable.getColumn("Alarme").setMinWidth(50);
		pendenciasTable.setDefaultEditor(GregorianCalendar.class,
				new TimeTableCellEditor(
						taskBeanArmazenamento.getPendenciasData()));
		pendenciasTaskTableModel.addMouseListener(pendenciasTable);

		TaskTableKeyListener pendenciasKeyListener = new TaskTableKeyListener(
				pendenciasTable, this);
		tabbedPane.addKeyListener(pendenciasKeyListener);
		pendenciasTable.addKeyListener(pendenciasKeyListener);

		TaskTableModelConcluidas concluidosTaskTableModel = new TaskTableModelConcluidas(
				taskBeanArmazenamento.getConcluidosData());
		concluidosTable.setModel(concluidosTaskTableModel);
		concluidosTable.getColumnModel().getColumn(0)
				.setCellEditor(new TimeTableCellEditor(
						taskBeanArmazenamento.getConcluidosData()));
		concluidosTable.getColumnModel().getColumn(1)
				.setCellEditor(new TimeTableCellEditor(
						taskBeanArmazenamento.getConcluidosData()));
		concluidosTaskTableModel.addMouseListener(concluidosTable);
		TaskTableKeyListener concluidosKeyListener = new TaskTableKeyListener(
				concluidosTable, this);
		concluidosTable.addKeyListener(concluidosKeyListener);
		concluidosTable.getColumn("Descrição").setMinWidth(400);
		gerarComboAtividades(pendenciasTable.getColumnModel().getColumn(3));
		gerarComboAtividades(concluidosTable.getColumnModel().getColumn(3));
	}

	private void gerarComboAtividades(TableColumn column) {
		JComboBox comboBox = new JComboBox();
		for (int i = 0; i < tipoAtividade.length; i++) {
			comboBox.addItem(tipoAtividade[i]);
		}
		column.setCellEditor(new DefaultCellEditor(comboBox));

		// Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Clique para escolher Tipo de Atividade");
		column.setCellRenderer(renderer);

	}

	public TaskBeanArmazenamento getTaskBeanArmazenamento() {
		return taskBeanArmazenamento;
	}

	public void setTaskBeanArmazenamento(
			TaskBeanArmazenamento taskBeanArmazenamento) {
		this.taskBeanArmazenamento = taskBeanArmazenamento;
	}

	public JTable getConcluidosTable() {
		return concluidosTable;
	}

	public JTable getPendenciasTable() {
		return pendenciasTable;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void procurar(String valor) {
		int cont = tabbedPane.getComponentCount();
		for (int i = 0; i < cont; i++) {
			Component component = tabbedPane.getComponentAt(i);
			if (component instanceof JTable) {
				procurarNaJTable(component, valor);
			}

		}

	}

	private void procurarNaJTable(Component component, String valor) {
		JTable table = (JTable) component;
		TableModel model = table.getModel();
		model.getColumnCount();
	}
}
