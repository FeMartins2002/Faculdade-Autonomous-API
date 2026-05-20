package br.com.AutonomousAPI.controllers;

import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerCostDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerRankingDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.MonthlyScaleDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.ScaleStatusCountDTO;
import br.com.AutonomousAPI.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autonomous/statistics")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/scales")
    public List<ScaleStatusCountDTO> getScaleStatistics() {
        return dashboardService.getScaleStatistics();
    }

    @GetMapping("/top-freelancers-scales")
    public List<FreelancerRankingDTO> getTopFreelancersScales(@RequestParam(defaultValue = "5") int limit) {
        return dashboardService.getTopFreelancersScales(limit);
    }

    @GetMapping("/top-freelancers-payments")
    public List<FreelancerCostDTO> getTopFreelancerPayments(@RequestParam(defaultValue = "5") int limit) {
        return dashboardService.getTopFreelancerPayments(limit);
    }

    @GetMapping("/monthly-scales")
    public List<MonthlyScaleDTO> getMonthlyScales() {
        return dashboardService.getMonthlyScales();
    }
}
