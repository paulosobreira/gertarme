package br.nnpe.gertarme.control;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import br.nnpe.gertarme.model.TaskBean;
import br.nnpe.gertarme.view.MainWindow;
import br.nnpe.gertarme.view.TaskWindow;

public class TimerThread extends Thread {
	private JDesktopPane desktopPane;
	private MainWindow mainWindow;
	private Set taskAlarmSet;

	public TimerThread(MainWindow mainWindow) {
		super();
		this.mainWindow = mainWindow;
		desktopPane = mainWindow.getDesktopPane();
		taskAlarmSet = new HashSet();
	}

	public Set getTaskAlarmSet() {
		return taskAlarmSet;
	}

	public void setTaskAlarmSet(Set taskAlarmSet) {
		this.taskAlarmSet = taskAlarmSet;
	}

	public void run() {
		super.run();

		while (true) {
			GregorianCalendar gregCal = new GregorianCalendar();
			int ano = gregCal.get(Calendar.YEAR);
			int mes = gregCal.get(Calendar.MONTH) + 1;
			int dia = gregCal.get(Calendar.DAY_OF_MONTH);
			int hora = gregCal.get(Calendar.HOUR_OF_DAY);
			int minuto = gregCal.get(Calendar.MINUTE);
			JInternalFrame[] frames = desktopPane.getAllFrames();

			for (int i = 0; i < frames.length; i++) {
				if (frames[i] instanceof TaskWindow) {
					TaskWindow taskWindow = (TaskWindow) frames[i];
					List pendetes = taskWindow.getTaskBeanArmazenamento()
							.getPendenciasData();

					for (int j = 0; j < pendetes.size(); j++) {
						TaskBean taskBean = (TaskBean) pendetes.get(j);
						gregCal.setTimeInMillis(taskBean.getHoraTarefa());

						int ano2 = gregCal.get(Calendar.YEAR);
						int mes2 = gregCal.get(Calendar.MONTH) + 1;
						int dia2 = gregCal.get(Calendar.DAY_OF_MONTH);
						int hora2 = gregCal.get(Calendar.HOUR_OF_DAY);
						int minuto2 = gregCal.get(Calendar.MINUTE);

						if ((ano == ano2) && (mes == mes2) && (dia == dia2)
								&& (hora == hora2) && (minuto == minuto2)
								&& taskBean.isAtivada()) {
							if (!taskAlarmSet.contains(taskBean)) {
								new AlarmeThread(mainWindow, taskWindow,
										taskBean, this);
								taskAlarmSet.add(taskBean);
							}

						}
					}
				}
			}

			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
