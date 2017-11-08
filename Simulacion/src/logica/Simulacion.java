package logica;

import java.util.ArrayList;

public class Simulacion {

    Cola cola1;
    Cola cola2;

    int tiempoSimulacion; //horas
    int numeroCorridas;

    int posicionNumeroAleatorio;

    int numeroMayorCola1;
    int numeroMayorCola2;

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

        this.tiempoSimulacion = tiempoSimulacion * 60;
        this.numeroCorridas = numeroCorridas;
        this.posicionNumeroAleatorio = 0;

        this.numeroMayorCola1 = 0;
        this.numeroMayorCola2 = 0;

        this.mediaPoisson = mediaPoisson;
        this.mediaExponencial = mediaExponencial;
        this.rangoAUniforme = rangoAUniforme;
        this.rangoBUniforme = rangoBUniforme;

        this.metodos = new Metodos();
        this.numerosAleatorios = this.metodos.numerosAleatorios(x, a, c, m);
        this.listaPoisson = metodos.listaPoisson(this.mediaPoisson);

    }

    //metodo para obtener el numero de clientes que llegan a la cola 1 siguiendo la distribuacion de poisson
    public int numeroClientesEnCola1() {

        int numeroClientesCola1 = this.metodos.variableAleatoriaPoisson(this.listaPoisson, numerosAleatorios.get(this.posicionNumeroAleatorio));
        this.posicionNumeroAleatorio++;

        return numeroClientesCola1;
    }

    //Metodo para asignarle a cada cliente un numero aleatorio con el tiempo de servicio que tendra siguiendo la distribucion exponencial
    public void agregaClientesEnCola1() {

        Double tiempoEnColaCliente = 0.0;

        tiempoEnColaCliente = this.metodos.variableAleatoriaExponencial(this.mediaExponencial, this.numerosAleatorios.get(this.posicionNumeroAleatorio));

        Cliente a = new Cliente(tiempoEnColaCliente, tiempoEnColaCliente, 0);

        this.cola1.clientesEnCola.add(a);

        this.posicionNumeroAleatorio++;

    }

    public void aumentaTiempoEnSistema(int posicionCola1, int posicionCola2) {

        for (int i = posicionCola1; i < this.cola1.clientesEnCola.size(); i++) {
            this.cola1.clientesEnCola.get(i).tiempoEnElSistema += 0.01;
        }

        for (int i = posicionCola2; i < this.cola2.clientesEnCola.size(); i++) {
            this.cola2.clientesEnCola.get(i).tiempoEnElSistema += 0.01;
        }
    }

    //metodo que simula la operacion del sistema con colas que se atienden simultaneamente
    public void simular() {

        int clientesPorAgregar = numeroClientesEnCola1();

        int cola1Size = clientesPorAgregar;
        int cola2Size = 0;

        int clientesAtendidosCola1 = 0;
        int clientesAtendidosCola2 = 0;

        //agregamos el primer cliente a la cola, en el tiempo 0
        agregaClientesEnCola1();
        clientesPorAgregar--;

        int clientesEnCola1 = this.cola1.clientesEnCola.size();
        int clientesEnCola2 = 0;

        int aux1 = clientesEnCola1;
        int aux2 = 0;

        //se corre la simulacion en el tiempo que establecio el usuario
        for (double i = 0; i <= this.tiempoSimulacion; i += 0.01) {

            //Se agrega el numero de clientes que llegaran a la cola 1 cada hora
            if ((metodos.redondear(i, 2) % 60) == 0.00) {
                clientesPorAgregar += numeroClientesEnCola1();
            }

            //Tenemos una razon de llegada de clientes de 1 cliente cada 3 minutos
            //cada 3 minutos añadimos 1 cliente a la cola
            if ((metodos.redondear(i, 2) % 3) == 0.00) {

                //validamos que podamos agregar clientes segun el numero aleatorio obtenido
                if (clientesPorAgregar > 0) {
                    agregaClientesEnCola1();
                    clientesEnCola1++;
                    clientesPorAgregar--;
                }
            }

            //validamos que hayan clientes en cola esperando, si no hay clientes en cola, se hace nada
            if (clientesEnCola1 > 0) {

                //validamos que el cliente tenga tiempo de atencion todavia
                if (this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola > 0) {

                    //si tiene tiempo de atencion, se le reduce hasta que llegue a cero
                    this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola -= 0.01;

                    aumentaTiempoEnSistema(clientesAtendidosCola1, clientesAtendidosCola2);

                } else {

                    //si el tiempo de atencion del cliente llega a cero
                    if (this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola <= 0) {

                        //se le asigna un nuevo tiempo de atencion siguiendo la distribucion uniforme
                        double nuevoTiempoCliente = metodos.variableAleatoriaUniforme(this.rangoAUniforme, this.rangoBUniforme, numerosAleatorios.get(posicionNumeroAleatorio));

                        //se le da el nuevo tiempo
                        this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoEnCola = nuevoTiempoCliente;

                        //se suma el tiempo total en el sistema del cliente
                        this.cola1.clientesEnCola.get(clientesAtendidosCola1).tiempoTotalAtencion += nuevoTiempoCliente;

                        //se pasa el cliente a la siguiente cola
                        this.cola2.clientesEnCola.add(this.cola1.clientesEnCola.get(clientesAtendidosCola1));

                        //se aumenta el numero de clientes atendidos
                        clientesAtendidosCola1++;

                        //disminuimos el numero de clientes en la cola
                        clientesEnCola1--;

                        //aumentamos el numero de clientes en la cola 2
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
                        
                        aumentaTiempoEnSistema(clientesAtendidosCola1, clientesAtendidosCola2);

                    } else {

                        //si el cliente ya fue atendido
                        if (this.cola2.clientesEnCola.get(clientesAtendidosCola2).tiempoEnCola <= 0) {

                            //se aumenta el contador de clientes atendidos
                            clientesAtendidosCola2++;

                            //disminuimos el numero de clientes en la cola 2
                            clientesEnCola2--;

                            this.cola2.numeroClientesEnCola = clientesAtendidosCola2;

                        }

                    }
                }

            } else {

            }

            /*
            //Mostramos el numero de clientes en cola y atendidos de ambas colas cada vez que un cliente llega, pasa a otra cola o se va
            if (aux1 != clientesEnCola1 || aux2 != clientesEnCola2) {
                System.out.println("Clientes en cola y atendidos: cola 1 y 2: " + +clientesEnCola1 + " " + clientesAtendidosCola1 + " " + clientesEnCola2 + " " + clientesAtendidosCola2);
                aux1 = clientesEnCola1;
                aux2 = clientesEnCola2;
            }
             */
            //Guardamos los numeros de clientes en las colas cuando son mayores a los anteriores
            if (clientesEnCola1 > numeroMayorCola1) {
                numeroMayorCola1 = clientesEnCola1;
            }

            if (clientesEnCola2 > numeroMayorCola2) {
                numeroMayorCola2 = clientesEnCola2;
            }

        }

    }

    //metodo para calcular el tiempo promedio que el usuario es atendido
    public double promedioTiempoEnAtencion() {

        double promedioTiempoEnAtencion = 0;

        for (int i = 0; i < cola2.clientesEnCola.size(); i++) {
            promedioTiempoEnAtencion += cola2.clientesEnCola.get(i).tiempoTotalAtencion;
        }
        return metodos.redondear(promedioTiempoEnAtencion /= cola2.numeroClientesEnCola, 2);

    }

    //metodo para realizar los calculos solicitados por el problema
    public double promedioTiempoEnSistema() {

        double promedioTiempoEnSistema = 0;

        for (int i = 0; i < cola2.clientesEnCola.size(); i++) {
            promedioTiempoEnSistema += cola2.clientesEnCola.get(i).tiempoEnElSistema;
        }
        return metodos.redondear(promedioTiempoEnSistema /= cola2.numeroClientesEnCola, 2);

    }

    public void limpiar() {

        cola1.clientesEnCola.clear();
        cola2.clientesEnCola.clear();

        cola1.numeroClientesEnCola = 0;
        cola2.numeroClientesEnCola = 0;

        numeroMayorCola1 = 0;
        numeroMayorCola2 = 0;

    }

    public void correrSimulacion() {

        double promedioTiempoEnAtencion = 0;
        double promedioTiempoEnSistema = 0;
        double numeroCola1 = 0;
        double numeroCola2 = 0;
        double iguales = 0;

        for (int i = 1; i < numeroCorridas + 1; i++) {

            System.out.println("\nCorrida " + i);
            simular();

            System.out.println("Promedio de tiempo en Atencion: " + promedioTiempoEnAtencion() + " Minutos");
            promedioTiempoEnAtencion += promedioTiempoEnAtencion();

            System.out.println("Promedio de tiempo en el sistema: " + promedioTiempoEnSistema() + " Minutos");
            promedioTiempoEnSistema += promedioTiempoEnSistema();

            System.out.println("Numero Maximo de clientes esperando en cola 1: " + numeroMayorCola1 + " clientes");

            System.out.println("Numero Maximo de clientes esperando en cola 2: " + numeroMayorCola2 + " clientes");

            if (numeroMayorCola1 > numeroMayorCola2) {
                numeroCola1++;
            } else {
                if (numeroMayorCola2 > numeroMayorCola1) {
                    numeroCola2++;
                } else {
                    if (numeroMayorCola1 == numeroMayorCola2) {
                        iguales++;
                    }
                }
            }

            limpiar();
        }

        promedioTiempoEnSistema = metodos.redondear(promedioTiempoEnSistema / numeroCorridas, 2);
        promedioTiempoEnAtencion = metodos.redondear(promedioTiempoEnAtencion / numeroCorridas, 2);

        numeroCola1 = metodos.redondear((numeroCola1 / numeroCorridas) * 100, 2);
        numeroCola2 = metodos.redondear((numeroCola2 / numeroCorridas) * 100, 2);
        iguales = metodos.redondear((iguales / numeroCorridas) * 100, 2);

        System.out.println("\nConclusiones Generales");
        System.out.println("Promedio general de tiempo de los clientes siendo atendidos: " + promedioTiempoEnAtencion + " Minutos"
                + "\nPromedio general de tiempo de los clientes en el sistema: " + promedioTiempoEnSistema + " Minutos"
                + "\nProbabilidad de tener mas clientes esperando en cola 1: " + numeroCola1 + "%"
                + "\nProbabilidad de tener mas clientes esperando en cola 2: " + numeroCola2 + "%"
                + "\nProbabilidad de tener igual numero de clientes en ambas colas: " + iguales + "%");
        
        if (numeroCola1 > numeroCola2){
            System.out.println("\nHay un " + numeroCola1 + "% de probabilidad de que la cola 1 sea mas larga que la cola 2.");
        }else{
            System.out.println("\nHay un " + numeroCola2 + "% de probabilidad de que la cola 2 sea mas larga que la cola 1.");
        }
    }

}
