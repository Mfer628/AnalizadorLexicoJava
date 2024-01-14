package Clases;

/*

public class Automata {
    enum  {
        InicialQ0,
        Identificador,
        OperadorMayor,
        OperadorMenor,
        Final,
        ComentMultiline,
        Error
    }

    private State estadoActual;
    private int identificador = 0;
    private int operadorRela = 0;


    public Automata() {
        estadoActual = State.InicialQ0;
    }

    public State getEstadoActual() {
        return estadoActual;
    }

    public int getNumeroIdentificadores() {
        return identificador;
    }
    public int getOperadorRelacional(){
        return operadorRela;
    }

    public void LogicaAutomata(char caracter) {
        switch (estadoActual) {
            case InicialQ0:
                if (esLetra(caracter) || esDigito(caracter)|| caracter == '_') {
                    estadoActual = State.Identificador;
                    //Estoy Manejando los espacios y los enter
                }else if(caracter == ' '|| caracter == '\n'){
                    estadoActual = State.InicialQ0;
                    System.out.println("Encontre un espacio");
                }else if (caracter == '<'){
                    estadoActual = State.OperadorMayor;
                    operadorRela++;
                }else if (caracter == '>'){
                    estadoActual = State.OperadorMenor;
                }else if (caracter == '/'){
                    estadoActual = State.ComentMultiline;
                }
                else{
                    estadoActual = State.Error;
                    System.out.println("Me fui al estado de error");
                }
                break;
            case Identificador:
                if (esLetra(caracter) || esDigito(caracter) || caracter == '_')  {
                    //
                    System.out.println("Encontre un identificador");
                    estadoActual = State.Identificador;

                }else if(caracter == '\n' ){
                    estadoActual = State.InicialQ0;
                    System.out.println("Encontre un espacio ");
                }
                else if (esEspecial(caracter)) {
                    estadoActual = State.Error;
                    System.out.println("Hay un espacio y hay un caracter especial");
                }else if (caracter == ' ' || caracter == '<' || caracter == '>') {
                    if (caracter == '<') {
                        System.out.println("Encontre Operador Mayor");
                        estadoActual = State.OperadorMayor;
                        operadorRela++;
                    } else if (caracter == '>') {
                        estadoActual = State.OperadorMenor;
                        operadorRela++;
                    }else {
                        estadoActual = State.Error;
                        System.out.println("Me fui al estado de error");
                    }
                }


                    estadoActual = State.Final;
                    System.out.println("Fin de Identificador");
                    identificador++; // Incrementa el contador de identificadores


                break;
            case OperadorMayor:
                if (caracter == ' '){
                    estadoActual =State.InicialQ0;
                }
                estadoActual = State.Final;
                System.out.println("Final operador Mayor");
                break;
            case OperadorMenor:

            default:
                break;
        }

        System.out.println("El automata esta en el estado " + estadoActual.toString());

    }

    private boolean esLetra(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean esDigito(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean esEspecial(char c) {
        return c == '@' || c == '#' || c == '$'||c=='.';
    }






}

 */
