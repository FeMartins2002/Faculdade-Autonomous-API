package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerCostDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerRankingDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.MonthlyScaleDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.ScaleStatusCountDTO;
import br.com.AutonomousAPI.repositories.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ScaleStatusCountDTO> findScalesByYear(int year) {
        return scaleRepository.getScaleStatistics(year);
    }

    public List<FreelancerRankingDTO> findTopFreelancersScalesByYear(int year) {
        Pageable pageable = PageRequest.of(0, 5);

        return scaleRepository.getTopFreelancerScales(year, pageable);
    }

    public List<FreelancerCostDTO> findTopFreelancersPaymentByYear(int year) {
        Pageable pageable = PageRequest.of(0, 5);

        return scaleRepository.getTopFreelancerPayments(year, pageable);
    }

    public List<MonthlyScaleDTO> findMonthlyScalesByYear(int year) {
        List<Object[]> result = scaleRepository.getMonthlyScales(year);
        List<MonthlyScaleDTO> response = new ArrayList<>();

        for (Object[] row : result) {
            int monthNumber = ((Number) row[0]).intValue();
            int yearNumber = ((Number) row[1]).intValue();

            Long total = ((Number) row[2]).longValue();
            String monthName = Month.of(monthNumber).getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));

            monthName = monthName.replace(".", "");
            String label = monthName;
            response.add(new MonthlyScaleDTO(label, total));
        }

        return response;
    }
}
