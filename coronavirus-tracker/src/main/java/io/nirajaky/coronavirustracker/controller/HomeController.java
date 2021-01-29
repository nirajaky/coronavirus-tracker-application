package io.nirajaky.coronavirustracker.controller;

import io.nirajaky.coronavirustracker.models.LocationStats;
import io.nirajaky.coronavirustracker.services.CoronaVirusDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CoronaVirusDataServices coronaVirusDataServices;
    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats = coronaVirusDataServices.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalReportedNewCases = allStats.stream().mapToInt(num -> num.getDiffFromPrevDay()).sum();
        System.out.println(totalReportedCases);
        System.out.println(totalReportedNewCases);

        DecimalFormat formatter = new DecimalFormat("###,###,###");

        model.addAttribute("title", "Coronavirus Tracker App");
        model.addAttribute("locationStats", coronaVirusDataServices.getAllStats());
        model.addAttribute("totalReportedCases", formatter.format(totalReportedCases));
        model.addAttribute("totalReportedNewCases", formatter.format(totalReportedNewCases));
        return "home";
    }
}
