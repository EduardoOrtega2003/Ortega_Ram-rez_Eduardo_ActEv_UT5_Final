import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Notas {
    private JFrame frame;
    private JTextField titleField, searchField;
    private JTextArea contentArea;
    private JList<String> noteList;
    private DefaultListModel<String> listModel;
    private java.util.List<String> contentList;
    private String userEmail;

    public Notas(String userEmail) {
        this.userEmail = userEmail;
        frame = new JFrame("Creador de Notas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(152, 62, 255));

        listModel = new DefaultListModel<>();
        contentList = new ArrayList<>();
        noteList = new JList<>(listModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane listScrollPane = new JScrollPane(noteList);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(173, 216, 230));

        JPanel titlePanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        titleField = new JTextField();
        titleField.setFont(new Font("Arial", Font.BOLD, 14));
        titleField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        titleField.setPreferredSize(new Dimension(30, 10));
        
        contentArea = new JTextArea(5, 20);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentArea.setPreferredSize(new Dimension(30, 30));
        JScrollPane scrollPane = new JScrollPane(contentArea);

        titlePanel.add(new JLabel("Título:"), BorderLayout.WEST);
        titlePanel.add(titleField, BorderLayout.CENTER);
        contentPanel.add(new JLabel("Contenido:"), BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        inputPanel.add(titlePanel);
        inputPanel.add(contentPanel);

        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(152, 62, 255)); 
        JButton saveButton = new JButton("Guardar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");
        JButton clearButton = new JButton("Limpiar");
        JButton searchButton = new JButton("Buscar");
        searchField = new JTextField(10);
        
        saveButton.setBackground(new Color(60, 179, 113)); // Verde
        editButton.setBackground(new Color(255, 165, 0)); // Naranja
        deleteButton.setBackground(new Color(220, 20, 60)); // Rojo
        clearButton.setBackground(new Color(255, 215, 0)); // Amarillo
        searchButton.setBackground(new Color(30, 144, 255)); // Azul oscuro

        saveButton.setForeground(Color.WHITE);
        editButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
        clearButton.setForeground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveNote());
        editButton.addActionListener(e -> editNote());
        deleteButton.addActionListener(e -> deleteNote());
        clearButton.addActionListener(e -> clearFields());
        searchButton.addActionListener(e -> searchNotes());

        noteList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && noteList.getSelectedIndex() != -1) {
                    int index = noteList.getSelectedIndex();
                    titleField.setText(listModel.get(index));
                    contentArea.setText(contentList.get(index));
                }
            }
        });

        loadNotes();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void saveNote() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        if (!title.isEmpty() && !content.isEmpty()) {
            listModel.addElement(title);
            contentList.add(content);
            saveNotesToFile();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(frame, "El título y contenido no pueden estar vacíos.");
        }
    }

    private void editNote() {
        int index = noteList.getSelectedIndex();
        if (index != -1) {
            listModel.set(index, titleField.getText());
            contentList.set(index, contentArea.getText());
            saveNotesToFile();
        }
    }

    private void deleteNote() {
        int index = noteList.getSelectedIndex();
        if (index != -1) {
            listModel.remove(index);
            contentList.remove(index);
            saveNotesToFile();
        }
    }

    private void clearFields() {
        titleField.setText("");
        contentArea.setText("");
    }

    private void searchNotes() {
        String query = searchField.getText().trim().toLowerCase();
        if (!query.isEmpty()) {
            for (int i = 0; i < listModel.size(); i++) {
                if (listModel.get(i).toLowerCase().contains(query)) {
                    noteList.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void loadNotes() {
        File notesFile = new File("usuarios/" + userEmail + "/notas.txt");
        if (notesFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(notesFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    listModel.addElement(line);
                    contentList.add(""); // Placeholder for content
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveNotesToFile() {
        try {
            File userFolder = new File("usuarios/" + userEmail);
            if (!userFolder.exists()) userFolder.mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios/" + userEmail + "/notas.txt"));
            for (int i = 0; i < listModel.size(); i++) {
                writer.write(listModel.get(i) + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }
}
