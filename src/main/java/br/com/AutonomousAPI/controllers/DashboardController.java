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

    @GetMapping("/monthly-scales")
    public List<MonthlyScaleDTO> findMonthlyScalesByYear(@RequestParam int year) {
        return dashboardService.findMonthlyScalesByYear(year);
    }

    @GetMapping("/scales")
    public List<ScaleStatusCountDTO> findScalesByYear(@RequestParam int year) {
        return dashboardService.findScalesByYear(year);
    }

    @GetMapping("/top-freelancers-scales")
    public List<FreelancerRankingDTO> findTopFreelancersScalesByYear(@RequestParam int year) {
        return dashboardService.findTopFreelancersScalesByYear(year);
    }

    @GetMapping("/top-freelancers-payments")
    public List<FreelancerCostDTO> findTopFreelancersPaymentByYear(@RequestParam int year) {
        return dashboardService.findTopFreelancersPaymentByYear(year);
    }
}
