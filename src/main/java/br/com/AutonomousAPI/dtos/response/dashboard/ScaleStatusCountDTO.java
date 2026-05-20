package br.com.AutonomousAPI.dtos.response.dashboard;

import br.com.AutonomousAPI.enums.ScaleStatus;

public class ScaleStatusCountDTO {
    private ScaleStatus status;
    private Long total;

    public ScaleStatusCountDTO() {

    }

    public ScaleStatusCountDTO(ScaleStatus status, Long total) {
        this.status = status;
        this.total = total;
    }

    public ScaleStatus getStatus() {
        return status;
    }

    public void setStatus(ScaleStatus status) {
        this.status = status;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ScaleStatusCountDTO{" +
                "status=" + status +
                ", total=" + total +
                '}';
    }
}
