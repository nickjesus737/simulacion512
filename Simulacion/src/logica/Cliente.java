package logica;

public class Cliente {

    double tiempoEnCola;
    double tiempoTotalAtencion;
    double tiempoEnElSistema;
    
    public Cliente(double tiempoEnCola, double tiempoTotalEnCola, double tiempoEnElSistema) {
        this.tiempoEnCola = tiempoEnCola;
        this.tiempoTotalAtencion = tiempoTotalEnCola;
        this.tiempoEnElSistema = tiempoEnElSistema;
    }

    public Cliente() {
        this.tiempoEnCola = 0;
        this.tiempoTotalAtencion = 0;
        this.tiempoEnElSistema = 0;
    }

}
