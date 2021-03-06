package logica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Metodos {

    public Metodos() {
    }
    
    //generador de numeros aleatorios con el metodo congruencial mixto
    public ArrayList numerosAleatorios(int x, int a, int c, int m) {

        ArrayList<Double> numerosAleatorios = new ArrayList<>();

        double X = x;
        double aux;
        double pro;

        aux = a * X + c;
        X = aux % m;
        pro = X;

        numerosAleatorios.add(X / m);

        do {
            aux = a * X + c;
            X = aux % m;
            numerosAleatorios.add(X / m);
        } while (pro != X);

        return numerosAleatorios;
    }

    //generador de variables aleatorias con el metodo de poisson
    public int variableAleatoriaPoisson(ArrayList lista, double numeroAleatorio) {

        double rango1 = 0;
        double rango2 = 0;

        int variableAleatoria = 0;

        for (int i = 0; i < lista.size(); i++) {

            rango2 = Double.valueOf(lista.get(i).toString());

            if (numeroAleatorio >= rango1 && numeroAleatorio < rango2) {
                variableAleatoria = i;
                return variableAleatoria;
            }

            rango1 = Double.valueOf(lista.get(i).toString());
        }

        return variableAleatoria;
    }

    //generador de variables aleatorias con el metodo exponencial
    public double variableAleatoriaExponencial(double media, double numeroAleatorio) {

        double lambda = 1 / media;

        double variableAleatoria = 0;

        if (numeroAleatorio == 0) {
            variableAleatoria = 0;
        } else {
            variableAleatoria = (-(1 / lambda) * Math.log(numeroAleatorio));
        }
        
        return redondear(variableAleatoria, 2);

    }

    //generador de variables aleatorias uniformemente distribuidas
    public double variableAleatoriaUniforme(double rangoA, double rangoB, double numeroAleatorio) {

        double variableAleatoria = rangoA + (rangoB - rangoA) * numeroAleatorio;

        return redondear(variableAleatoria, 2);

    }

    //metodo para calcular el factorial de un numero
    public float factorial(float x) {

        float factorial = 1;

        while (x != 0) {
            factorial *= x;
            x--;
        }

        return factorial;
    }

    //metodo para generar la lista de numeros que siguen una distribucion de poisson
    public ArrayList listaPoisson(double lambda) {

        ArrayList<Double> listaPoisson = new ArrayList<>();

        double x = lambda + 2 * lambda;
        double poisson = 0;

        poisson = (Math.pow(Math.E, -lambda) * Math.pow(lambda, 0)) / factorial(0);

        listaPoisson.add(poisson);

        for (int i = 1; i <= x; i++) {
            poisson += (Math.pow(Math.E, -lambda) * Math.pow(lambda, i)) / factorial(i);

            if (listaPoisson.get(i - 1) != poisson) {
                listaPoisson.add(poisson);
            } else {
                break;
            }

        }

        return listaPoisson;
    }
    
    //metodo para redondear numeros double a dos decimales
    public double redondear (double decimal, int escala){
        
        BigDecimal bd = new BigDecimal(decimal).setScale(escala, RoundingMode.HALF_UP);

        return Double.parseDouble(bd.toString());
    }
}
