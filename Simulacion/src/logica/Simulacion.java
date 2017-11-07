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

    //metodo para obtener el numero de clientes que llegan a la cola 1 siguiendo la distribuacion de poisson y 
    //asignarle a cada cliente un numero aleatorio con el tiempo de servicio que tendra siguiendo la distribucion exponencial
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

    //metodo que simula la operacion del sistema con colas que se atienden simultaneamente
    public void simular() {

        int clientesEnCola1 = this.cola1.clientesEnCola.size();
        int clientesEnCola2 = 0;

        int aux1 = clientesEnCola1;

        int clientesAtendidosCola1 = 0;
        int clientesAtendidosCola2 = 0;

        int cola1Size = this.cola1.clientesEnCola.size();
        int cola2Size = 0;

        //se corre la simulacion en el tiempo que establecio el usuario
        for (double i = 0; i <= this.tiempoSimulacion; i += 0.01) {

            //validamos que todos los clientes de la cola hayan sido atendidos, si es asi, creamos nuevos clientes
            if (clientesAtendidosCola1 == cola1Size) {

                asignaClientesEnCola1();
                clientesEnCola1 = clientesEnCola1 + this.cola1.clientesEnCola.size() - cola1Size;
                cola1Size = this.cola1.clientesEnCola.size();

            } else {

                //validamos que el cliente tenga tiempo de atencion todavia
                if (this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola > 0) {

                    //si tiene tiempo de atencion, se le reduce hasta que llegue a cero
                    this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola -= 0.01;

                } else {

                    //si el tiempo de atencion del cliente llega a cero
                    if (this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola <= 0) {

                        //se le asigna un nuevo tiempo de atencion siguiendo la distribucion uniforme
                        double nuevoTiempoCliente = metodos.variableAleatoriaUniforme(this.rangoAUniforme, this.rangoBUniforme, numerosAleatorios.get(posicionNumeroAleatorio));

                        //se le da el nuevo tiempo
                        this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola = nuevoTiempoCliente;

                        //se suma el tiempo total en el sistema del cliente
                        this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoTotalEnCola += nuevoTiempoCliente;

                        //se pasa el cliente a la siguiente cola
                        this.cola2.clientesEnCola.add(this.cola1.clientesEnCola.get(clientesAtendidosCola1));

                        //se aumenta el numero de clientes atendidos
                        clientesAtendidosCola1++;

                        clientesEnCola1--;

                        clientesEnCola2++;

                        this.cola1.numeroClientesEnCola = clientesAtendidosCola1;

                        this.posicionNumeroAleatorio++;

                    } else {
                    }
                }
            }

            //se valida que almenos se haya atendido un cliente en la cola 1
            if (clientesAtendidosCola1 > 0) {

                cola2Size = cola2.clientesEnCola.size();

                if (clientesAtendidosCola2 >= cola2Size) {

                } else {

                    //si el cliente tiene tiempo en cola disponible se le reduce
                    if (this.cola2.clientesEnCola.get(clientesAtendidosCola2).tiempoEnCola > 0) {

                        this.cola2.clientesEnCola.get(clientesAtendidosCola2).tiempoEnCola -= 0.01;

                    } else {

                        //si el cliente ya fue atendido
                        if (this.cola2.clientesEnCola.get(clientesAtendidosCola2).tiempoEnCola <= 0) {

                            //se aumenta el contador de clientes atendidos
                            clientesAtendidosCola2++;

                            clientesEnCola2--;

                            this.cola2.numeroClientesEnCola = clientesAtendidosCola2;

                        }

                    }
                }

            } else {

            }

            if (aux1 != clientesEnCola1) {
                System.out.println("Clientes en cola 1 y 2: " + clientesEnCola1 + " " + clientesEnCola2);
                aux1 = clientesEnCola1;
            }

        }
    }

    //metodo para realizar los calculos solicitados por el problema
    public void calculos() {

        double promedioTiempoEnSistema = 0;

        for (int i = 0; i < cola2.clientesEnCola.size(); i++) {

            promedioTiempoEnSistema += cola2.clientesEnCola.get(i).tiempoTotalEnCola;

        }

        promedioTiempoEnSistema = metodos.redondear(promedioTiempoEnSistema /= cola2.numeroClientesEnCola);

        System.out.println(promedioTiempoEnSistema + " " + cola1.numeroClientesEnCola + " " + cola2.numeroClientesEnCola);

    }
}
