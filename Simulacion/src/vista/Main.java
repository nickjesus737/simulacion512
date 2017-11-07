package vista;

import logica.Metodos;
import logica.Simulacion;

public class Main {

    public static void main(String arg[]) {

        Simulacion simulacion = new Simulacion(4, 5, 5, 127, 11, 100003, 20, 2, 1, 2);
        
        simulacion.corridas();
        
    }

}