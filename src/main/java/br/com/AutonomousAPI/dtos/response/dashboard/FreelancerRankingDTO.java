package br.com.AutonomousAPI.dtos.response.dashboard;

public class FreelancerRankingDTO {
    private String freelancerName;
    private Long totalScales;

    public FreelancerRankingDTO() {

    }

    public FreelancerRankingDTO(String freelancerName, Long totalScales) {
        this.freelancerName = freelancerName;
        this.totalScales = totalScales;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public Long getTotalScales() {
        return totalScales;
    }

    public void setTotalScales(Long totalScales) {
        this.totalScales = totalScales;
    }

    @Override
    public String toString() {
        return "FreelancerRankingDTO{" +
                "freelancerName='" + freelancerName + '\'' +
                ", totalScales=" + totalScales +
                '}';
    }
}
