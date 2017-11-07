package vista;

import logica.Simulacion;

public class Main {

    public static void main(String arg[]) {

        Simulacion simulacion = new Simulacion(1, 2, 5, 127, 11, 100003, 20, 2, 1, 2);
        
        simulacion.asignaClientesEnCola1();
        simulacion.simular();
        simulacion.calculos();
        
    }

}