package Clases;

import java.util.Scanner;

public class Prueba {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Objeto
        Prueba objPrueba = new Prueba();

        System.out.println("Introduce Cadena");
        String palabraEjem=in.nextLine();
        //objPrueba.ValidandoPalabra(palabraEjem);
        objPrueba.ValidandoPalabraCaracteresEspeciales(palabraEjem);




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
        }else if (contadorPuntos > 1) {
            System.out.println("Tiene mas de un punto ");
        }else{
            System.out.println("No tiene puntos");
        }
    }
    public  boolean ValidandoPalabraCaracteresEspeciales (String Palabra){
        char[] arregloPalabra = Palabra.toCharArray();
        boolean bandera = false;
        for (char ca : arregloPalabra){
            if (ca== '#') {
               bandera=true;
            }
        }
        if (bandera){
            System.out.println("El identificador tiene un caracter especial");
        }else{
            System.out.println("Todo bien");
        }
    return bandera;
    }

}
