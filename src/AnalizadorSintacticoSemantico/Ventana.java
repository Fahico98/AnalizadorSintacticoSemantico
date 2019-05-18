
package AnalizadorSintacticoSemantico;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.regex.Matcher;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Ventana extends JFrame implements ActionListener{
    
    private String rutaActual = System.getProperty("user.dir").replaceAll(Matcher.quoteReplacement("\\"), "/");
    private File archivoSeleccionado = new File("cualquierCosa.txt");
    
    private Font letraGrande = new Font("Calibri", Font.BOLD, 18);
    private Font letraMediana = new Font("Calibri", Font.BOLD, 14);
    private Border borde = BorderFactory.createLineBorder(Color.BLACK);
    private Color color = new Color(210, 210, 210);
    
    private JPanel panelPrincipal = new JPanel(new BorderLayout(0, 30));
    private JPanel panelSuperior = new JPanel(new BorderLayout(20, 0));
    private JPanel panelTextos = new JPanel(new GridLayout(1, 2, 20, 0));
    private JPanel panelInferior = new JPanel(new GridLayout(1, 2, 20, 0));
    
    private JLabel etiqueta = new JLabel("Seleccione archivo:");
    
    private JTextField rutaSeleccionada = new JTextField("(ninguno)");
    
    private JButton botonExaminar = new JButton("Examinar...");
    private JButton botonAnalizar = new JButton("Analizar texto de entrada.");
    private JButton botonLimpiar = new JButton("Limpiar campos para nuevo análisis.");
    
    private JTextArea textoEntrada = new JTextArea("Texto de entrada...!");
    private JTextArea textoSalida = new JTextArea("Texto de salida...!");
    
    private JScrollPane scrollEntrada = new JScrollPane(
            textoEntrada, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    
    private JScrollPane scrollSalida = new JScrollPane(
            textoSalida,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    
    public Ventana(String titulo){
        
        super(titulo);
        
        setSize(1000, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        etiqueta.setFont(letraGrande);
        etiqueta.setForeground(Color.BLACK);
        
        rutaSeleccionada.setBackground(Color.WHITE);
        rutaSeleccionada.setEditable(false);
        rutaSeleccionada.setFont(letraMediana);
        rutaSeleccionada.setForeground(Color.BLACK);
        rutaSeleccionada.setBackground(color);
        rutaSeleccionada.setBorder(
                BorderFactory.createCompoundBorder(
                        borde,
                        BorderFactory.createEmptyBorder(0, 5, 0, 5)
                )
        );
        
        textoEntrada.setEditable(false);
        textoEntrada.setFont(letraMediana);
        textoEntrada.setForeground(Color.BLACK);
        textoEntrada.setBackground(color);
        textoEntrada.setBorder(
                BorderFactory.createCompoundBorder(
                        borde,
                        BorderFactory.createEmptyBorder(10, 5, 20, 5)
                )
        );
        
        textoSalida.setEditable(false);
        textoSalida.setFont(letraMediana);
        textoSalida.setForeground(Color.BLACK);
        textoSalida.setBackground(color);
        textoSalida.setBorder(
                BorderFactory.createCompoundBorder(
                        borde,BorderFactory.createEmptyBorder(10, 5, 20, 5)
                )
        );
        
        botonExaminar.setFont(letraMediana);
        botonExaminar.setForeground(Color.BLACK);
        botonExaminar.addActionListener(this);
        
        botonAnalizar.setEnabled(true);
        botonAnalizar.setFont(letraMediana);
        botonAnalizar.setForeground(Color.BLACK);
        botonAnalizar.setEnabled(false);
        botonAnalizar.addActionListener(this);
        
        botonLimpiar.setEnabled(true);
        botonLimpiar.setFont(letraMediana);
        botonLimpiar.setForeground(Color.BLACK);
        botonLimpiar.setEnabled(false);
        botonLimpiar.addActionListener(this);
        
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.add(etiqueta, BorderLayout.WEST);
        panelSuperior.add(rutaSeleccionada, BorderLayout.CENTER);
        panelSuperior.add(botonExaminar, BorderLayout.EAST);
        
        panelTextos.setBackground(Color.WHITE);
        panelTextos.add(scrollEntrada);
        panelTextos.add(scrollSalida);
        
        panelInferior.setBackground(Color.WHITE);
        panelInferior.add(botonAnalizar);
        panelInferior.add(botonLimpiar);
        
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelTextos, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
    }
    
    public void actionPerformed(ActionEvent evento){
        //___________________________________________________________________________________________Acciones del Boton Examinar!
        if(evento.getSource() == botonExaminar){
            JFileChooser seleccionador = new JFileChooser(this.rutaActual);
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                    "Archivos de texto (*.txt)",
                    "txt",
                    "text"
            );
            seleccionador.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            seleccionador.setFileFilter(filtro);
            int resultado = seleccionador.showOpenDialog(null);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                try {
                    archivoSeleccionado = seleccionador.getSelectedFile();
                    BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado));
                    String linea = br.readLine();
                    textoEntrada.setText("");
                    while(linea != null){
                        textoEntrada.append(linea + "\n");
                        linea = br.readLine();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
                botonAnalizar.setEnabled(true);
                botonLimpiar.setEnabled(true);
                rutaActual = archivoSeleccionado.getPath().replaceAll(Matcher.quoteReplacement("\\"), "/");
                //rutaSeleccionada.setForeground(Color.green);
                rutaSeleccionada.setText(rutaActual);
            }
        //___________________________________________________________________________________________Acciones del Boton Analizar!
        }else if(evento.getSource() == botonAnalizar){
            String[] rutaDeArchivo = {rutaActual};
            AnalizadorSintactico.main(rutaDeArchivo);
        //___________________________________________________________________________________________Acciones del Boton Limpiar!
        }else if(evento.getSource() == botonLimpiar){
            int respuesta =JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea quitar el resultado del ultimo análisis?",
                    "¿Limpiar espacios?",
                    JOptionPane.YES_NO_OPTION
            );
            if(respuesta == JOptionPane.YES_OPTION){
                rutaActual = System.getProperty("user.dir").replaceAll(Matcher.quoteReplacement("\\"), "/");
                textoEntrada.setText("Texto de entrada...!");
                textoSalida.setText("Texto de salida...!");
                rutaSeleccionada.setText("(ninguno)");
                botonAnalizar.setEnabled(false);
                botonLimpiar.setEnabled(false);
            }
            
        }
    }
}
