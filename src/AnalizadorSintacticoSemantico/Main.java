
package AnalizadorSintacticoSemantico;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

public class Main {
    
    public final static int GENERAR = 1;
    public final static int EJECUTAR = 2;
    
    public static void main(String[] args) {
        main();
        Ventana ventana = new Ventana("Analizador Sintáctico y Semántico (Por Jerson Jimenez)");
    }
    
    public static void main(){
        String archivoJFlexRuta = "", archivoCupRuta = "";
        String dirPrincipal = System.getProperty("user.dir").replaceAll(Matcher.quoteReplacement("\\"), "/");
        dirPrincipal += "/src/AnalizadorSintacticoSemantico";
        archivoJFlexRuta = dirPrincipal + "/AnalizadorLexico.jflex";
        archivoCupRuta = dirPrincipal + "/AnalizadorSintactico.cup";
        File pruebaJFlex = new File(dirPrincipal + "AnalizadorLexico.java");
        File pruebaCup = new File(dirPrincipal + "AnalizadorSintactico.java");
        File pruebaSym = new File(dirPrincipal + "sym.java");
        //if(!(pruebaJFlex.exists() && pruebaCup.exists() && pruebaSym.exists())){
            // Generacion del archivo .jflex...
            try{
                File archivoJFlex = new File(archivoJFlexRuta);
                if(archivoJFlex.exists() && archivoJFlex.isFile()){
                    jflex.Main.generate(archivoJFlex);
                    System.out.println("\n*** Archivo AnalizadorLexico.java generado con exito ***\n");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            // Generacion del archivo .cup...
            try {
                String[] archivoCup = {"-parser", "AnalizadorSintactico", archivoCupRuta};
                java_cup.Main.main(archivoCup);
                System.out.println("\n*** Archivo AnalizadorSintactico.java generado con exito ***");
                System.out.println("\n*** Archivo sym.java generado con exito ***\n");
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Se transladan los archivos AnalizadorSintactico.java y sym.java al directorio src...
            boolean archivoCUPMovido = moverArchivo("AnalizadorSintactico.java");
            boolean archivoSymMovido = moverArchivo("sym.java");
        //}
    }
    
    public static boolean moverArchivo(String nombreArchivo) {
        boolean salida = false;
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            Path rutaActualRelativa = Paths.get("");
            String nuevoDirectorio =
                    rutaActualRelativa.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "AnalizadorSintacticoSemantico" + File.separator + archivo.getName();
            File archivoAnterior = new File(nuevoDirectorio);
            archivoAnterior.delete();
            if (archivo.renameTo(new File(nuevoDirectorio))) {
                salida = true;
            }
        }
        return salida;
    }
}
