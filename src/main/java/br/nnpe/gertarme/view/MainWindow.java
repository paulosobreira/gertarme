package br.nnpe.gertarme.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import br.nnpe.gertarme.control.ControladorArmazenamento;
import br.nnpe.gertarme.control.TimerThread;
import br.nnpe.gertarme.model.TaskBeanArmazenamento;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String TITULO = "GERTARME - Gerenciador de Tarefas com Alarme 1.5";

	private static boolean tray = true;

	private JDesktopPane desktopPane;
	private JMenuBar menuBar;
	private ControladorArmazenamento armazenamento = new ControladorArmazenamento();

	public MainWindow() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		setarItensMenu();
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.lightGray);
		setTitle(TITULO);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(desktopPane, BorderLayout.CENTER);

		TimerThread timerThread = new TimerThread(this);
		timerThread.start();
		doSystemTray();
		carregarUltima();
	}

	private void carregarUltima() {
		TaskWindow taskWindow;

		try {
			TaskBeanArmazenamento taskBeanArmazenamento = armazenamento
					.carregarUltimaTarefa();
			if (taskBeanArmazenamento == null) {
				return;
			}

			taskWindow = new TaskWindow(taskBeanArmazenamento);
			desktopPane.add(taskWindow);
			taskWindow.show();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void doSystemTray() {
		Image imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/"+"Gear.gif"))).getImage();
		PopupMenu menu = new PopupMenu();
		MenuItem restore = new MenuItem("Restaurar");
		restore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!tray) {
					return;
				}
				MainWindow.this.setVisible(true);
			}

		});
		menu.add(restore);
		MenuItem sair = new MenuItem("Sair");
		sair.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!tray) {
					return;
				}
				System.exit(0);

			}

		});
		menu.add(sair);
		try {
			TrayIcon trayIcon = new TrayIcon(imageIcon, TITULO, menu);
			trayIcon.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (!tray) {
							return;
						}
						MainWindow.this.setVisible(true);
					}
				}

			});
			SystemTray systemTray = SystemTray.getSystemTray();
			systemTray.add(trayIcon);
		} catch (Exception e1) {
			e1.printStackTrace();
			tray = false;
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}
		addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {
				MainWindow.this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
				MainWindow.this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
				MainWindow.this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}

		});

	}

	private void setarItensMenu() {
		JMenu menu1 = new JMenu("Arquivo");
		JMenu menu2 = new JMenu("Sobre");
		JMenuItem criar = new JMenuItem("Criar");
		criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomeTarefas = JOptionPane.showInputDialog(
						"Entre com o nome Agrupamento de Tarefas");

				TaskWindow taskWindow = new TaskWindow(nomeTarefas);
				desktopPane.add(taskWindow);
				taskWindow.show();
			}
		});
		menu1.add(criar);

		JMenuItem abrir = new JMenuItem("Abrir");
		abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskWindow taskWindow;

				try {
					taskWindow = new TaskWindow(armazenamento.carregarTarefa());
					desktopPane.add(taskWindow);
					taskWindow.show();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		menu1.add(abrir);

		JMenuItem sobre = new JMenuItem("Autor");
		sobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "Feito por Paulo Sobreira \n"
						+ "sowbreira@gmail.com \n"
						+ "https://sowbreira-26fe1.firebaseapp.com/ \n"
						+ "Janeiro de 2006 ";
				JOptionPane.showMessageDialog(MainWindow.this, msg, "Sobre",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu2.add(sobre);
		menuBar.add(menu1);
		menuBar.add(menu2);
	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
}
