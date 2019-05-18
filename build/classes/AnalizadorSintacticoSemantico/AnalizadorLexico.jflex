    


package AnalizadorSintacticoSemantico;

import java.util.ArrayList;
import java.io.IOException;
import java_cup.runtime.*;
import java.io.Reader;

%%

%class AnalizadorLexico
%line
%column
%cup
%function next_token
%type java_cup.runtime.Symbol
%public
%unicode

%{
    public static boolean comillasAbiertas = false;

    ArrayList<ArrayList<String>> conjuntos = new ArrayList();

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
    
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }

    private void error() throws IOException{
        throw new IOException("(Fahico) Illegal text at line = "+yyline+", column = "+yycolumn+", text = '"+yytext()+"'");
    }
%}

SALTO = \r|\n|\r\n

ESPACIO = {SALTO} | [ \t\f]

NOMBRE_DE_CONJUNTO = [A-Z]+

ELEMENTO_DE_CONJUNTO = [0-9a-zA-Z]+

%%






<YYINITIAL> {
   
    "&" {return symbol(sym.UNION);}

    "$" {return symbol(sym.INTERSECCION);}

    "/" {return symbol(sym.DIFERENCIA);}

    "^c" {return symbol(sym.COMPLEMENTO);}

    "*" {return symbol(sym.PRODUCTO_CRUZ);}

    "=" {return symbol(sym.ASIGNACION);}

    "," {return symbol(sym.COMA);}

    "(" {return symbol(sym.PARENT_IZQ);}

    ")" {return symbol(sym.PARENT_DER);}

    "{" {return symbol(sym.LLAVE_IZQ);}

    "}" {return symbol(sym.LLAVE_DER);}

    "\"" {  if(comillasAbiertas){
                comillasAbiertas = false;
            }else{
                comillasAbiertas = true;
            }
            return symbol(sym.COMILLAS);
    }
    
    "DEFINICION" {return symbol(sym.DEFINICION, new String(yytext()));}

    "UNIVERSO" {return symbol(sym.UNIVERSO, new String(yytext()));}
    
    "OPERACION" {return symbol(sym.OPERACION, new String(yytext()));}
   
    {NOMBRE_DE_CONJUNTO} {return symbol(sym.NOMBRE_DE_CONJUNTO, new String(yytext()));}

    {ELEMENTO_DE_CONJUNTO} {return symbol(sym.ELEMENTO_DE_CONJUNTO, new String(yytext()));}

    {ESPACIO} {/* ignora el espacio */}
}

[^] {throw new Error("Pilas! caracter ilegal <" + yytext() + ">");}