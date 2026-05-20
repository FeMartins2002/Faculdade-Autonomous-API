package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerCostDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerRankingDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.MonthlyScaleDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.ScaleStatusCountDTO;
import br.com.AutonomousAPI.repositories.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {
    @Autowired
    private ScaleRepository scaleRepository;

    public List<ScaleStatusCountDTO> getScaleStatistics() {
        return scaleRepository.getScaleStatistics();
    }

    public List<FreelancerRankingDTO> getTopFreelancersScales(int limit) {
        return scaleRepository.getTopFreelancerScales(PageRequest.of(0, limit));
    }

    public List<FreelancerCostDTO> getTopFreelancerPayments(int limit) {
        return scaleRepository.getTopFreelancerPayments(PageRequest.of(0, limit));
    }

    public List<MonthlyScaleDTO> getMonthlyScales() {
        List<Object[]> result = scaleRepository.getMonthlyScales();
        List<MonthlyScaleDTO> response = new ArrayList<>();

        for (Object[] row : result) {
            int monthNumber = ((Number) row[0]).intValue();
            int year = ((Number) row[1]).intValue();
            Long total = ((Number) row[2]).longValue();

            String monthName = Month.of(monthNumber)
                    .getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));

            monthName = monthName.replace(".", "");
            String label = monthName + "/" + year;
            response.add(new MonthlyScaleDTO(label, total));
        }

        return response;
    }
}
