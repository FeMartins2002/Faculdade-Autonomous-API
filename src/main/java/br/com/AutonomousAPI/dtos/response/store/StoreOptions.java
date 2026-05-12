package br.com.AutonomousAPI.dtos.response.store;

public class StoreOptions {
    private Long id;
    private String name;

    public StoreOptions() {

    }

    public StoreOptions(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
