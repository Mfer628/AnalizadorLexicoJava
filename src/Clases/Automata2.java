package Clases;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Automata2 extends Component {
    enum State {
        InicialQ0,
        Identificador,
        OperadorRelacional,
        Final,
        OperadorLogico,
        OperadorLogicoAnd,
        OperadorLogicoOr,
        OperadorAritmetico,
        OperadorAsignacion,
        esNumero,
        CadenaCaracteres,
        ComentUnaLinea,

        PotencialComentarioMultilinea,
        ComentMultiline ,
        PotencialComentarioUnaLinea,
        PotencialCierreComentarioMultilinea,
        ParentesisA,
        OperadorNot,
        ParentesisC,
        LlaveA,
        LlaveC,
        Error
    }
    public Automata2() {

        estadoActual = State.InicialQ0;
        palabraActual = new StringBuilder();
    }

    //Contadores de Tokens
    private State estadoActual;

    private StringBuilder palabraActual;
    private ArrayList<String> identificadoresValidos = new ArrayList<>();

//Contadores
    int contadorPalabrasReserv=0;
    int contadorOperadoresAritmeticos=0;
    int contadorOperadorRelacional=0;
    int contadorOperadorLogico=0;
    int contadorIdentificadores=0;
    int contadorAsignacion = 0;
    int contadorNumberEntero = 0;
    int contadorNumberDecimal = 0;
    int contadorCaracteres = 0;
    int contadorcomentUnaLinea=0;
    int contadorParentesisA=0;
    int contadorParentesisC=0;

    int contadorError=0;
    int contadorLlaveA=0;
    int contadorLlaveC= 0;
    int contadorComentMultiline=0;


    public void logicaAutomata(JTextArea txtArea) {


            //Palabras Reservadas
        String[] reservWords = {"if", "else", "switch", "case", "default", "for", "while", "break", "int", "String", "double", "char", "print"};

        String texto = txtArea.getText();
        //Variable para almacenar la palabra actual



        //Verificamos cada caracter en el texto
        for (int i = 0; i < texto.length(); i++) {
            //Analizamos caracter por caracter
            char caracter = (i < texto.length() - 1) ? texto.charAt(i) : ' ';

            switch (estadoActual) {
                case InicialQ0:
                    if (esLetra(caracter) || caracter == '_') {
                        palabraActual.append(caracter);
                        estadoActual = State.Identificador;
                    } else if (caracter == '>' || caracter == '<') {
                       estadoActual = State.OperadorRelacional;
                    } else if (caracter == '&') {
                        estadoActual = State.OperadorLogicoAnd;
                    } else if (caracter == '|') {
                        estadoActual = State.OperadorLogicoOr;
                    }else if (caracter == '!'){
                        estadoActual = State.OperadorNot;
                    }else if (caracter == '=') {
                        estadoActual = State.OperadorAsignacion;
                    }else if (caracter == '"'){
                        palabraActual.append(caracter);
                        estadoActual = State.CadenaCaracteres;
                    }else if (caracter == '/'){
                        estadoActual = State.PotencialComentarioUnaLinea;
                    }else if (esOperadorAritmetico(caracter)){
                        estadoActual = State.OperadorAritmetico;
                    } else if (caracter == '(') {
                        // Parentesis de apertura
                        estadoActual = State.ParentesisA;
                    }else if (caracter == ')'){
                        estadoActual = State.ParentesisC;
                    }else if (esDigito(caracter)){
                        palabraActual.append(caracter);
                        estadoActual = State.esNumero;
                    }else if (caracter == '{'){
                        estadoActual = State.LlaveA;
                    }else if (caracter == '}'){
                        estadoActual = State.LlaveC;
                    }

                    break;

//Identificador
                case Identificador:
                    palabraActual.append(caracter);
                    if (esLimitePalabra(caracter) || i == texto.length() - 1) {
                        String palabra = palabraActual.toString().trim();
                        if (esPalabraReservada(palabra, reservWords)) {
                            // Manejar palabras reservadas de manera específica
                            contadorPalabrasReserv++;
                            System.out.println("Palabra reservada encontrada: " + palabra);
                        } else if (!palabra.isEmpty()) {
                            if (!ValidandoPalabraCaracteresEspeciales(palabra)) {
                                // Verificación adicional: el identificador no debe comenzar con ciertos caracteres

                                contadorIdentificadores++;
                                identificadoresValidos.add(palabra);
                                System.out.println("Identificador encontrado: " + palabra);
                            } else {
                                System.out.println("Error: Identificador no válido encontrado (comienza con carácter especial): " + palabra);
                                estadoActual = State.Error;
                            }


                        }

                        palabraActual.setLength(0);
                        estadoActual = State.InicialQ0;
                    }
                    break;
//Operador Relacional

                case OperadorRelacional:
                if (caracter == '=') {
                    // Operador '>'
                    System.out.println("Operador relacional con Igual encontrado: " + palabraActual.toString());
                    contadorOperadorRelacional++;
                } else if (caracter == '.' || caracter == ','  ){
                    System.out.println("Hay algo raro");
                    contadorError++;
                }
                else {
                    // Solo el operador '>'
                    System.out.println("Operador Mayor encontrado: " + palabraActual.toString());
                    contadorOperadorRelacional++;
                    i--;  // Retroceder para procesar el siguiente caracter
                }
                    estadoActual = State.InicialQ0;
                    palabraActual.setLength(0);
                   break;
//operador logico

                case OperadorLogico:
                    // Lógica para operadores lógicos
                    if (caracter == '&') {
                        // Posible inicio de "&&"
                        estadoActual = State.OperadorLogicoAnd;
                    } else if (caracter == '|') {
                        // Posible inicio de "||"
                        estadoActual = State.OperadorLogicoOr;
                    } else if (caracter == '!') {
                        // Operador "!"
                        System.out.println("Operador Lógico NOT encontrado: " + palabraActual.toString());
                        // Puedes incrementar el contador si lo deseas
                        contadorOperadorLogico++;
                        estadoActual = State.InicialQ0;
                        palabraActual.setLength(0);
                    } else {
                        // Carácter no válido después del inicio de un operador lógico
                        System.out.println("Error: Carácter no válido después de operador lógico: " + caracter);
                        estadoActual = State.Error;
                    }
                    break;
//operadores logicos
                case OperadorLogicoAnd:
                case OperadorLogicoOr:
                    if (caracter == (estadoActual == State.OperadorLogicoAnd ? '&' : '|')) {
                        // Operador "&&" o "||"
                        System.out.println("Operador Lógico " + (estadoActual == State.OperadorLogicoAnd ? "AND" : "OR") + " encontrado: " + palabraActual.toString());
                        // Puedes incrementar el contador si lo deseas
                        contadorOperadorLogico++;
                        estadoActual = State.InicialQ0;
                        palabraActual.setLength(0);
                    } else {
                        // Carácter no válido después del inicio de "&&" o "||"
                        System.out.println("Error: Carácter no válido después de " + (estadoActual == State.OperadorLogicoAnd ? "&" : "|") + ": " + caracter);
                        estadoActual = State.Error;
                    }
                    break;
// operador Aritmetico
                case OperadorAritmetico:

                        System.out.println("Operador Aritmético encontrado: " + caracter);
                        contadorOperadoresAritmeticos++;
                        estadoActual = State.InicialQ0;

                    break;
//operador de asignacion
                case OperadorAsignacion:
                    if (caracter == '=') {
                        // Operador de igualdad '==' encontrado
                        System.out.println("Operador de Igualdad encontrado: " + palabraActual.toString());
                        contadorOperadorRelacional++;
                        estadoActual = State.InicialQ0;
                        palabraActual.setLength(0);
                    } else {
                        // Solo el operador '='
                        System.out.println("Operador de Asignación encontrado: " + palabraActual.toString());
                        contadorAsignacion++;
                        // Puedes manejar la lógica para el operador de asignación si es necesario
                        estadoActual = State.InicialQ0;
                        palabraActual.setLength(0);
                        i--;  // Retroceder para procesar el siguiente caracter

                    }
                    break;

//Cadena de Caracteres
                case CadenaCaracteres:
                    palabraActual.append(caracter);
                    if (caracter == '"') {
                        // Cierre de comillas encontrado, terminar cadena de caracteres
                        System.out.println("Cadena de caracteres encontrada: " + palabraActual.toString());
                        palabraActual.setLength(0);
                        estadoActual = State.InicialQ0;
                        contadorCaracteres++;
                    } else if (i == texto.length() - 1) {
                        // Fin del texto y aún no se ha cerrado la cadena de caracteres
                        System.out.println("Error: Fin del texto antes del cierre de comillas para la cadena de caracteres.");
                        estadoActual = State.Error;
                    }
                    break;
//Comentario de una linea
                case PotencialComentarioUnaLinea:
                    if (caracter == '/') {
                        // Potencial comentario de una línea
                        palabraActual.append(caracter);
                        estadoActual = State.ComentUnaLinea;

                    }else if (caracter == '*'){
                        palabraActual.append(caracter);
                        estadoActual = State.ComentMultiline;
                    }
                    else {
                        // Potencial operador aritmético
                        System.out.println("Tengo una barra o un asterisco, puede ser un comentario de una línea o un operador aritmético.");
                        estadoActual = State.OperadorAritmetico;
                        palabraActual.setLength(0);
                        i--;  // Retroceder para procesar el siguiente caracter
                    }

                    break;
//Comentario de una linea
                case ComentUnaLinea:
                    if ( caracter == '\n' || i == texto.length() - 1) {
                        // Fin de comentario de una línea o fin del texto
                        System.out.println("Comentario de una línea encontrado: " + palabraActual.toString());
                        contadorcomentUnaLinea++;
                        palabraActual.setLength(0);
                        estadoActual = State.InicialQ0;

                    }else {
                        palabraActual.append(caracter);
                    }
                    break;
//Parentesis de Apertura
                case ParentesisA:
                    System.out.println("Encontre Parentesis de Apertura");
                    contadorParentesisA++;
                    estadoActual = State.InicialQ0;
                    break;

//parentesis de Cierre
                case ParentesisC:
                    // Acciones específicas para el estado de paréntesis de apertura
                    System.out.println("Encontrado paréntesis de Cierre.");
                    contadorParentesisC++;
                    estadoActual = State.InicialQ0;
                    break;


//Operador Not !
                case OperadorNot:
                    if (caracter == '='){
                        System.out.println("Es un operdor relacional");
                        contadorOperadorRelacional++;
                    }else {
                        System.out.println("Es un operador logico Not");
                        contadorOperadorLogico++;
                    }
                    estadoActual = State.InicialQ0;
                    palabraActual.setLength(0);
                    break;
//NUmeros enteros
                case esNumero:
                    System.out.println("Encontre numero ");
                    palabraActual.append(caracter);
                    if (esLimitePalabra(caracter) || i == texto.length() - 1) {
                        String palabraEjem = palabraActual.toString().trim();
                        System.out.println(palabraEjem);

                        // Llama al método ValidandoPalabra al final del caso
                        ValidandoPalabra(palabraEjem);

                        // Restablece el StringBuilder para el próximo número
                        palabraActual.setLength(0);
                        estadoActual = State.InicialQ0;
                    }
                    break;

//Llave de Apertura
                case LlaveA:
                // Acciones específicas para el estado de llave de apertura
                System.out.println("Encontrada llave de apertura.");
                contadorLlaveA++;  // Incrementa el contador de llaves de apertura
                estadoActual = State.InicialQ0;
                break;
//llave de Cierre
                case LlaveC:
                    System.out.println("Encontrada llave de cierre.");
                    contadorLlaveC++;  // Incrementando el contador de llaves de cierre
                    estadoActual = State.InicialQ0;
                    break;

                case PotencialComentarioMultilinea:
                    if (caracter == '*') {
                        // Potencial inicio de comentario multilinea
                        estadoActual = State.ComentMultiline;
                    } else {
                        // No es un comentario multilinea, puede ser un operador aritmético
                        System.out.println("Tengo una barra y un caracter que no es asterisco. Puede ser un comentario multilinea o un operador aritmético.");
                        estadoActual = State.OperadorAritmetico;
                        palabraActual.setLength(0);
                        i--;  // Retroceder para procesar el siguiente caracter
                    }
                    break;

                case ComentMultiline:
                    palabraActual.append(caracter);
                    if (caracter == '*') {
                        // Posible cierre de comentario multilinea
                        estadoActual = State.PotencialCierreComentarioMultilinea;
                    } else if (i == texto.length() - 1) {
                        // Fin del texto y aún no se ha cerrado el comentario multilinea
                        System.out.println("Error: Fin del texto antes del cierre de comentario multilinea.");
                        contadorError++;
                        estadoActual = State.Error;
                    }
                    break;

                case PotencialCierreComentarioMultilinea:
                    if (caracter == '/') {
                        // Cierre de comentario multilinea encontrado
                        System.out.println("Comentario multilinea encontrado: " + palabraActual.toString());
                        contadorComentMultiline++;
                        palabraActual.setLength(0);
                        estadoActual = State.InicialQ0;
                    } else {
                        // No era el cierre, continúa en el estado de comentario multilinea
                        palabraActual.append('*').append(caracter);
                        estadoActual = State.ComentMultiline;
                    }
                    break;

//Error
                case Error:
                    System.out.println("Estado de Error");
                    break;




            }

        }

    }


    private boolean esLimitePalabra(char caracter){
        // Aquí puedes agregar tus propias condiciones para determinar los límites de las palabras
        // Por ejemplo, podrías considerar como límites los espacios, comas, puntos, etc.
        return caracter == ' ' || caracter == ',' || caracter == '\n' || caracter == '\t';
    }

    private boolean esLetra(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    private boolean esDigito(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean esPalabraReservada(String palabra, String[] reservadas) {
        for (String reservada : reservadas) {
            if (palabra.equalsIgnoreCase(reservada)) {
                return true;
            }
        }
        return false;
    }

    private boolean esOperadorAritmetico(char caracter){
        // Aquí puedes agregar tus propias condiciones para determinar los límites de las palabras
        // Por ejemplo, podrías considerar como límites los espacios, comas, puntos, etc.
        return caracter == '+' || caracter == '-' || caracter == '/' || caracter == '%'|| caracter == '*';
    }


    public  void ValidandoPalabra (String Palabra){
        char[] arregloPalabra = Palabra.toCharArray();

        int contadorPuntos = 0;
        for (char ca : arregloPalabra){
            if (ca == '.') {
                contadorPuntos++;
                if (contadorPuntos > 1){
                    break; //Si encuntro mas de un punto, no necesito seguir buscando
                }
            }
        }
        if (contadorPuntos == 1){
            System.out.println("La cadena tiene puntito");
            contadorNumberDecimal++;
        }else if (contadorPuntos > 1) {
            System.out.println("Tiene mas de un punto ");
            contadorError++;
        }else{
            System.out.println("No tiene puntos");
            contadorNumberEntero++;

        }
    }

    public void ValidandoLlaves() {
        if (contadorLlaveA == contadorLlaveC) {
            System.out.println("Las llaves de apertura y cierre coinciden.");
        } else {
            System.out.println("Error: Las llaves de apertura y cierre no coinciden.");

            contadorError++;
        }
    }

    public void ValidandoParentesis() {
        if (contadorParentesisA == contadorParentesisC) {
            System.out.println("Los parentesis concuerdan");
        } else {
            System.out.println("Error: el parentesis  de apertura y cierre no coinciden.");

            contadorError++;
        }
    }
    public  boolean ValidandoPalabraCaracteresEspeciales (String Palabra) {
        char[] arregloPalabra = Palabra.toCharArray();
        boolean bandera = false;
        for (char ca : arregloPalabra) {
            if (ca == '#') {
                bandera = true;
            }
        }
        if (bandera) {
            System.out.println("El identificador tiene un caracter especial");
            contadorError++;
        } else {
            System.out.println("Todo bien");
        }
        return bandera;
    }

    //Getters que me ayudan a mostrar el contador en JFrame
    public int getContadorOperadoresAritmeticos() {
        return contadorOperadoresAritmeticos;
    }


    public int getContadorOperadorRelacional() {
        return contadorOperadorRelacional;
    }

    public int getContadorOperadorLogico() {
        return contadorOperadorLogico;
    }


    public int getContadorIdentificadores() {
        return contadorIdentificadores;
    }


    public State getEstadoActual() {
        return estadoActual;
    }

    public int getContadorPalabrasReserv() {
        return contadorPalabrasReserv;
    }
    public int getContadorAsignacion() {
        return contadorAsignacion;
    }

    public int getContadorNumberEntero() {
        return contadorNumberEntero;
    }

    public int getContadorNumberDecimal() {
        return contadorNumberDecimal;
    }

    public int getContadorCaracteres() {
        return contadorCaracteres;
    }

    public int getContadorcomentUnaLinea() {
        return contadorcomentUnaLinea;
    }


    public int getContadorLlaveA() {
        return contadorLlaveA;
    }

    public int getContadorLlaveC() {
        return contadorLlaveC;
    }

    public int getContadorParentesisA() {
        return contadorParentesisA;
    }

    public int getContadorParentesisC() {
        return contadorParentesisC;
    }

    public int getContadorComentMultiline() {
        return contadorComentMultiline;
    }

    public int getContadorError() {
        return contadorError;
    }
    public ArrayList<String> getIdentificadoresValidos() {
        return identificadoresValidos;
    }
}
