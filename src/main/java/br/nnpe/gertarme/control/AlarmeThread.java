package br.nnpe.gertarme.control;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import br.nnpe.gertarme.model.TaskBean;
import br.nnpe.gertarme.view.AlarmeWindow;
import br.nnpe.gertarme.view.MainWindow;
import br.nnpe.gertarme.view.TaskWindow;

public class AlarmeThread extends Thread {
	private MainWindow mainWindow;
	private TaskWindow taskWindow;
	private Toolkit toolkit;
	private boolean alarmar = true;
	private TaskBean taskBean;
	private TimerThread timerThread;

	public AlarmeThread(MainWindow mainWindow, TaskWindow taskWindow,
			TaskBean taskBean, TimerThread timerThread) {
		super();
		this.mainWindow = mainWindow;
		this.taskWindow = taskWindow;
		this.taskBean = taskBean;
		this.toolkit = mainWindow.getToolkit();
		this.timerThread = timerThread;
		setPriority(Thread.MIN_PRIORITY);
		start();
		mainWindow.setVisible(true);
		mainWindow.setAlwaysOnTop(true);
		mainWindow.setTitle("Nova tarefa");
		dispararPopUp();
	}

	public AlarmeThread() {
		this.toolkit = Toolkit.getDefaultToolkit();
	}

	public static void main(String[] args) {
		AlarmeThread alarmeThread = new AlarmeThread();
		alarmeThread.start();
		alarmeThread.dispararPopUp();
	}

	private void dispararPopUp() {
		new AlarmeWindow(this);
	}

	public void run() {
		while (alarmar) {
			try {
				toolkit.beep();
				toolkit.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK, !toolkit
						.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK));
				Thread.sleep((int) (250 + (Math.random() * 400)));
			} catch (Exception e) {
			}
		}

		mainWindow.setTitle(MainWindow.TITULO);
		mainWindow.setAlwaysOnTop(false);
	}

	public boolean isAlarmar() {
		return alarmar;
	}

	public void setAlarmar(boolean alarmar) {
		this.alarmar = alarmar;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public TaskBean getTaskBean() {
		return taskBean;
	}

	public TaskWindow getTaskWindow() {
		return taskWindow;
	}

	public TimerThread getTimerThread() {
		return timerThread;
	}

}
