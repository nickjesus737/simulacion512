package vista;

import logica.Simulacion;

public class Main {

    public static void main(String arg[]) {

        /*datos a ingresar (Integer tiempoSimulacion (horas), Integer numeroCorridas, 
        Integer x, Integer a, Integer c, Integer m, para los numeros aleatorios
        double mediaPoisson, double mediaExponencial, double rangoAUniforme, double rangoBUniforme)
        */
        Simulacion simulacion = new Simulacion(8, 10, 5, 127, 11, 100003, 20, 2, 1, 2);
        
        simulacion.correrSimulacion();
        
    }

}