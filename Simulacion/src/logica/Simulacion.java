package logica;

import java.util.ArrayList;

public class Simulacion {
    
    Cola cola1;
    Cola cola2;

    int tiempoSimulacion; //horas
    int hora; //minutos
    int numeroCorridas;

    int posicionNumeroAleatorio;
    double tiempoTotalCola1;
    double tiempoTotalCola2;

    double mediaPoisson;
    double mediaExponencial;
    double rangoAUniforme;
    double rangoBUniforme;

    Metodos metodos;
    ArrayList<Double> numerosAleatorios;
    ArrayList<Double> listaPoisson;
    
    public Simulacion(Integer tiempoSimulacion, Integer numeroCorridas, Integer x, Integer a, Integer c, Integer m, double mediaPoisson, double mediaExponencial, double rangoAUniforme, double rangoBUniforme) {
        this.cola1 = new Cola();
        this.cola2 = new Cola();

        this.hora = 60;
        this.tiempoSimulacion = tiempoSimulacion * 60;
        this.numeroCorridas = numeroCorridas;

        this.posicionNumeroAleatorio = 0;
        this.tiempoTotalCola1 = 0;
        this.tiempoTotalCola2 = 0;

        this.mediaPoisson = mediaPoisson;
        this.mediaExponencial = mediaExponencial;
        this.rangoAUniforme = rangoAUniforme;
        this.rangoBUniforme = rangoBUniforme;

        this.metodos = new Metodos();
        this.numerosAleatorios = this.metodos.numerosAleatorios(x, a, c, m);
        this.listaPoisson = metodos.listaPoisson(this.mediaPoisson);

    }
    
    public void asignaClientesEnCola1() {

        Integer numeroClientesCola1 = this.metodos.variableAleatoriaPoisson(this.listaPoisson, numerosAleatorios.get(this.posicionNumeroAleatorio));

        System.out.println(numeroClientesCola1);

        Double tiempoEnColaCliente = 0.0;

        this.posicionNumeroAleatorio++;

        for (int i = 0; i < numeroClientesCola1; i++) {

            tiempoEnColaCliente = this.metodos.variableAleatoriaExponencial(this.mediaExponencial, this.numerosAleatorios.get(this.posicionNumeroAleatorio));

            Cliente a = new Cliente(i, tiempoEnColaCliente, tiempoEnColaCliente);

            this.cola1.clientesEnCola.add(a);

            this.posicionNumeroAleatorio++;

        }
    }
    
    public void simular() {

        int cliente1 = 0;
        int cliente2 = 0;

        int cola1Size = this.cola1.clientesEnCola.size();
        int cola2Size = 0;
        
        for (double i = 0; i <= this.tiempoSimulacion; i += 0.01) {

            if (cliente1 == cola1Size) {

                asignaClientesEnCola1();
                cola1Size = this.cola1.clientesEnCola.size();

            } else {
                
                if (this.cola1.clientesEnCola.get(cliente1).tiempoEnCola > 0) {

                    this.cola1.clientesEnCola.get(cliente1).tiempoEnCola -= 0.01;

                } else {

                    if (this.cola1.clientesEnCola.get(cliente1).tiempoEnCola <= 0) {

                        double nuevoTiempoCliente = metodos.variableAleatoriaUniforme(this.rangoAUniforme, this.rangoBUniforme, numerosAleatorios.get(posicionNumeroAleatorio));

                        this.cola1.clientesEnCola.get(cliente1).tiempoEnCola = nuevoTiempoCliente;
                        
                        this.cola1.clientesEnCola.get(cliente1).tiempoTotalEnCola += nuevoTiempoCliente;
                        
                        this.cola2.clientesEnCola.add(this.cola1.clientesEnCola.get(cliente1));

                        cliente1++;

                        this.cola1.numeroClientesEnCola = cliente1;

                        this.posicionNumeroAleatorio++;

                    } else {
                    }
                }
            }

            if (cliente1 > 0) {
                
                cola2Size = cola2.clientesEnCola.size();
                
                if (cliente2 >= cola2Size) {

                } else {
                    
                    
                    if (this.cola2.clientesEnCola.get(cliente2).tiempoEnCola > 0) {

                        this.cola2.clientesEnCola.get(cliente2).tiempoEnCola -= 0.01;

                    } else {

                        if (this.cola2.clientesEnCola.get(cliente2).tiempoEnCola <= 0) {

                            cliente2++;
                            
                            this.cola2.numeroClientesEnCola = cliente2;

                        }

                    }
                }

            } else {

            }

        }
    }

    public void calculos() {

        double promedioTiempoEnSistema = 0;

        for (int i = 0; i < cola2.clientesEnCola.size(); i++) {

            promedioTiempoEnSistema += cola2.clientesEnCola.get(i).tiempoTotalEnCola;

        }
        
        promedioTiempoEnSistema /= cola2.numeroClientesEnCola;

        System.out.println(promedioTiempoEnSistema + " " + cola1.numeroClientesEnCola + " " + cola2.numeroClientesEnCola);

    }
}
