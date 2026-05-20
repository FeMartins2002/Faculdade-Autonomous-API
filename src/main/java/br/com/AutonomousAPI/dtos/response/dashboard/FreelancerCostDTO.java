package br.com.AutonomousAPI.dtos.response.dashboard;

public class FreelancerCostDTO {
    private String freelancerName;
    private double totalValue;

    public FreelancerCostDTO() {

    }

    public FreelancerCostDTO(String freelancerName, double totalValue) {
        this.freelancerName = freelancerName;
        this.totalValue = totalValue;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "FreelancerCostDTO{" +
                "freelancerName='" + freelancerName + '\'' +
                ", totalValue=" + totalValue +
                '}';
    }
}
