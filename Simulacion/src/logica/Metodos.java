package logica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Metodos {

    public Metodos() {
    }

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

    public double variableAleatoriaExponencial(double media, double numeroAleatorio) {

        double lambda = 1 / media;

        double variableAleatoria = 0;

        if (numeroAleatorio == 0) {
            variableAleatoria = 0;
        } else {
            variableAleatoria = (-(1 / lambda) * Math.log(numeroAleatorio));
        }

        //para redondear a dos decimales 
        BigDecimal bd = new BigDecimal(variableAleatoria).setScale(2, RoundingMode.HALF_UP);

        variableAleatoria = Double.parseDouble(bd.toString());

        return variableAleatoria;

    }

    public double variableAleatoriaUniforme(double rangoA, double rangoB, double numeroAleatorio) {

        double variableAleatoria = rangoA + (rangoB - rangoA) * numeroAleatorio;

        //para redondear a dos decimales 
        BigDecimal bd = new BigDecimal(variableAleatoria).setScale(2, RoundingMode.HALF_UP);

        variableAleatoria = Double.parseDouble(bd.toString());

        return variableAleatoria;

    }

    public float factorial(float x) {

        float factorial = 1;

        while (x != 0) {
            factorial *= x;
            x--;
        }

        return factorial;
    }

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
}
