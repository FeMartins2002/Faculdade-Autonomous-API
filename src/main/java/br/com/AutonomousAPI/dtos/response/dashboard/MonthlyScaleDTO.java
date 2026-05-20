package br.com.AutonomousAPI.dtos.response.dashboard;

public class MonthlyScaleDTO {
    private String label;
    private Long totalScales;

    public MonthlyScaleDTO() {

    }

    public MonthlyScaleDTO(String label, Long totalScales) {
        this.label = label;
        this.totalScales = totalScales;
    }

    public String getlabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getTotalScales() {
        return totalScales;
    }

    public void setTotalScales(Long totalScales) {
        this.totalScales = totalScales;
    }

    @Override
    public String toString() {
        return "MonthlyScaleDTO{" +
                "label='" + label + '\'' +
                ", totalScales=" + totalScales +
                '}';
    }
}
