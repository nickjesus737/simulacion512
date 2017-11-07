package logica;

public class Cliente {

    int id;
    double tiempoEnCola;
    double tiempoTotalEnCola;
    
    public Cliente(Integer id, double tiempoEnCola, double tiempoTotalEnCola) {
        this.id = id;
        this.tiempoEnCola = tiempoEnCola;
        this.tiempoTotalEnCola = tiempoTotalEnCola;
    }

    public Cliente() {
        this.id = 0;
        this.tiempoEnCola = 0;
        this.tiempoTotalEnCola = 0;
    }

}
