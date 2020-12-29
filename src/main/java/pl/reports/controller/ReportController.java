package pl.reports.controller;


import org.springframework.web.bind.annotation.*;
import pl.reports.dto.PutReportRequest;

@RestController
@RequestMapping("/reports")
public class ReportController {


    @GetMapping
    public String getAllReportsData(){
        return "GET all reports";
    }

    @GetMapping("/{reportId}")
    public String getAllReportsData(@PathVariable("reportId") int reportId){
        return "GET report with id: " + reportId;
    }

    @DeleteMapping("/{reportId}")
    public String deleteSingleReport(@PathVariable("reportId") int reportId){
        return "DELETE report with id: " + reportId;
    }

    @DeleteMapping
    public String deleteAllReports(){
        return "DELETE all reports";
    }

    @PutMapping("/{reportId}")
    public String creationOrUpdateOfReport(@PathVariable("reportId") int reportId,
                                           @RequestBody PutReportRequest putReport){

        return "PUT report creation or update with id: " + reportId + " with data " + putReport.toString();
    }





}
