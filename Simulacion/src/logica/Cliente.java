package logica;

public class Cliente {

    double tiempoEnCola;
    double tiempoTotalEnCola;
    
    public Cliente(double tiempoEnCola, double tiempoTotalEnCola) {
        this.tiempoEnCola = tiempoEnCola;
        this.tiempoTotalEnCola = tiempoTotalEnCola;
    }

    public Cliente() {
        this.tiempoEnCola = 0;
        this.tiempoTotalEnCola = 0;
    }

}
