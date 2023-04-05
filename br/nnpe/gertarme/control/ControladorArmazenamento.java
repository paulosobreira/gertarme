package br.nnpe.gertarme.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import br.nnpe.gertarme.model.ExampleFileFilter;
import br.nnpe.gertarme.model.TaskBeanArmazenamento;
import br.nnpe.gertarme.view.MainWindow;

public class ControladorArmazenamento {

	public boolean gravarArquivoComo(TaskBeanArmazenamento taskBeanArmazenamento)
			throws IOException {
		File file = null;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ExampleFileFilter exampleFileFilter = new ExampleFileFilter("tm");
		fileChooser.setFileFilter(exampleFileFilter);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.CANCEL_OPTION) {
			return false;
		}

		file = fileChooser.getSelectedFile();
		file = new File(file.getCanonicalFile() + ".tm");
		taskBeanArmazenamento.setNomeArquivo(file.getCanonicalPath());
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
		oos.writeObject(taskBeanArmazenamento);
		oos.flush();
		fileOutputStream.close();
		salvarUltimoAberto(file);
		return true;
	}

	public void gravarArquivo(TaskBeanArmazenamento taskBeanArmazenamento)
			throws IOException {
		File file = null;

		if (taskBeanArmazenamento.getNomeArquivo() == null) {
			return;
		} else {
			file = new File(taskBeanArmazenamento.getNomeArquivo());
		}

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
		oos.writeObject(taskBeanArmazenamento);
		oos.flush();
		fileOutputStream.close();
		salvarUltimoAberto(file);
	}

	private Object carregarArquivo(File arqImportar) throws IOException,
			ClassNotFoundException {
		FileInputStream inputStream = new FileInputStream(arqImportar);
		ObjectInputStream ois = new ObjectInputStream(inputStream);

		return ois.readObject();
	}

	public TaskBeanArmazenamento carregarTarefa() throws IOException,
			ClassNotFoundException {
		File file = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ExampleFileFilter exampleFileFilter = new ExampleFileFilter("tm");
		fileChooser.setFileFilter(exampleFileFilter);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.CANCEL_OPTION) {
			return new TaskBeanArmazenamento();
		}

		file = fileChooser.getSelectedFile();
		salvarUltimoAberto(file);
		return (TaskBeanArmazenamento) carregarArquivo(file);
	}

	public TaskBeanArmazenamento carregarUltimaTarefa() throws IOException,
			ClassNotFoundException {
		Properties properties = new Properties();

		properties.load(new FileInputStream("getarme.properties"));
		String lastFile = properties.getProperty("ultima_tarefa");
		if ("".equals(lastFile) || lastFile == null) {
			return null;
		}
		File file = new File(lastFile);
		if (file == null) {
			return null;
		}
		return (TaskBeanArmazenamento) carregarArquivo(file);
	}

	private void salvarUltimoAberto(File file) throws IOException {
		Properties properties = new Properties();

		properties.load(new FileInputStream("getarme.properties"));
		properties.setProperty("ultima_tarefa", file.getCanonicalPath());
		properties.store(new FileOutputStream("getarme.properties"),
				MainWindow.TITULO);
	}
}
