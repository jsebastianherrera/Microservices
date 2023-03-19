package javeriana.ms.rest.models;


public class Trip {
  public String id;
  public String origen;
  public String destino;

  public Trip() {
  }

  public Trip(String id, String origen, String destino) {
    this.id = id;
    this.origen = origen;
    this.destino = destino;
  }

}
