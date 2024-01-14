package GUI;

import Clases.Automata2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnalizadorLexi extends JFrame {
    private JTextArea textArea;

    public AnalizadorLexi() {
        // Configuración de la ventana
        setTitle("Analizador Lexico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Creación de Componentes
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton openButton = new JButton("Seleccionar el Archivo");
        JButton analyzeButton = new JButton("Analizar Código");

        // Configuración del tamaño del botón
        Dimension buttonSize = new Dimension(180, 30);
        openButton.setPreferredSize(buttonSize);
        analyzeButton.setPreferredSize(buttonSize);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(AnalizadorLexi.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        String fileContent = readFile(filePath);
                        textArea.setText(fileContent);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AnalizadorLexi.this, "Error al leer el archivo", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Automata2 objAutomata = new Automata2();
                objAutomata.logicaAutomata(textArea);
                objAutomata.ValidandoLlaves();
                objAutomata.ValidandoParentesis();

                System.out.println("Analizando Codigo ");

                JOptionPane.showMessageDialog(AnalizadorLexi.this ,
                        "Las palabras reservadas: "+objAutomata.getContadorPalabrasReserv()+
                                "\nLos identificadores: " + objAutomata.getContadorIdentificadores() +
                                "\nLos operadores relacionales: " + objAutomata.getContadorOperadorRelacional()+
                                "\nLos operadores Logicos: " + objAutomata.getContadorOperadorLogico()+
                                "\nLos operadores aritmeticos: " + objAutomata.getContadorOperadoresAritmeticos()+
                                "\nAsignacion: " + objAutomata.getContadorAsignacion() +
                                "\nNumeros enteros: "+ objAutomata.getContadorNumberEntero()+
                                "\nNumeros decimales: "+ objAutomata.getContadorNumberDecimal()+
                                "\nCadena de caracteres: " + objAutomata.getContadorCaracteres()+
                                "\nComentario de una linea: " + objAutomata.getContadorcomentUnaLinea()+
                                "\nComentario Multilinea "+ objAutomata.getContadorComentMultiline()+
                                "\n\nParentesis: " + (objAutomata.getContadorParentesisA() + objAutomata.getContadorParentesisC()) +
                                "\nLlaves: "+ (objAutomata.getContadorLlaveA() + objAutomata.getContadorLlaveC())+
                                "\nContador Error:  " + objAutomata.getContadorError());

            }
        });
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(analyzeButton);
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
