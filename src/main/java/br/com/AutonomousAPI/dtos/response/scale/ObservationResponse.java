package br.com.AutonomousAPI.dtos.response.scale;

public class ObservationResponse {
    private Long id;
    private String observation;

    public ObservationResponse() {

    }

    public ObservationResponse(Long id, String observation) {
        this.id = id;
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
