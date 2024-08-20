package br.nnpe.gertarme.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyVetoException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import br.nnpe.gertarme.control.AlarmeThread;
import br.nnpe.gertarme.model.TaskBean;
import br.nnpe.gertarme.model.TaskTableModel;
import br.nnpe.gertarme.model.TimeUtil;

public class AlarmeWindow extends JInternalFrame {
	private AlarmeThread alarmeThread;
	private TaskWindow taskWindow;
	private TaskBean taskBean;

	public AlarmeWindow(AlarmeThread alarmeThread) {
		this.alarmeThread = alarmeThread;
		this.taskWindow = alarmeThread.getTaskWindow();
		this.taskBean = alarmeThread.getTaskBean();
		this.setTitle("Alarme - "
				+ TimeUtil.gregorianCalendarToString(alarmeThread.getTaskBean()
						.getHoraTarefa()));
		setClosable(true);
		setIconifiable(false);
		setMaximizable(false);
		setResizable(false);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(new JLabel(UIManager
				.getIcon("OptionPane.informationIcon")), BorderLayout.WEST);
		container.add(new JLabel(taskBean.getDescricao()), BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		container.add(southPanel, BorderLayout.SOUTH);

		JButton concluido = new JButton("Concluido");
		concluido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluido();
			}
		});

		JButton adiar5 = new JButton("Adiar 5 min");
		adiar5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adiar(5);
			}
		});

		JButton adiar15 = new JButton("Adiar 15 min");
		adiar15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adiar(15);
			}
		});
		
		JButton adiar30 = new JButton("Adiar 30 min");
		adiar30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adiar(30);
			}
		});
		
		JButton desativar = new JButton("Desativar");
		desativar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desativar();
			}
		});
		southPanel.add(concluido);
		southPanel.add(adiar5);
		southPanel.add(adiar15);
		southPanel.add(adiar30);
		southPanel.add(desativar);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				fechar();
			}
		});
		alarmeThread.getMainWindow().getDesktopPane().add(this);

		setVisible(true);
		setLocation(
				(alarmeThread.getMainWindow().getWidth() - this.getWidth()) / 3,
				(alarmeThread.getMainWindow().getHeight() - this.getHeight()) / 3);
		setSize(600, 130);
	}

	protected void fechar() {
		alarmeThread.setAlarmar(false);
	}

	protected void desativar() {
		taskBean.setAtivada(false);
		((TaskTableModel) taskWindow.getPendenciasTable().getModel())
				.fireTableDataChanged();
		alarmeThread.getTimerThread().getTaskAlarmSet().remove(taskBean);
		try {
			this.setClosed(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	protected void adiar(int value) {
		GregorianCalendar gregCal = new GregorianCalendar();
		gregCal.set(Calendar.MINUTE, gregCal.get(Calendar.MINUTE) + value);
		taskBean.setHoraTarefa(gregCal.getTimeInMillis());
		((TaskTableModel) taskWindow.getPendenciasTable().getModel())
				.fireTableDataChanged();
		alarmeThread.getTimerThread().getTaskAlarmSet().remove(taskBean);
		try {
			this.setClosed(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	protected void concluido() {
		taskBean.setAtivada(false);
		((TaskTableModel) taskWindow.getConcluidosTable().getModel())
				.inserirLinha(taskBean);
		List pendetes = taskWindow.getTaskBeanArmazenamento()
				.getPendenciasData();
		int linha = 0;
		for (int j = 0; j < pendetes.size(); j++) {
			TaskBean taskBeanLoop = (TaskBean) pendetes.get(j);
			if (taskBean == taskBeanLoop) {
				linha = j;
			}
		}
		((TaskTableModel) taskWindow.getPendenciasTable().getModel())
				.removerLinha(linha);
		alarmeThread.getTimerThread().getTaskAlarmSet().remove(taskBean);
		try {
			this.setClosed(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
