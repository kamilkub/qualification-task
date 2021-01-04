package pl.reports.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.reports.dao.Report;
import pl.reports.dto.PutReportRequest;
import pl.reports.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {


    private ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAllReportsData(){
        return reportService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public Report getOneReportData(@PathVariable("reportId") long reportId){
        return reportService.getReportById(reportId);
    }

    @DeleteMapping("/{reportId}")
    public void deleteSingleReport(@PathVariable("reportId") long reportId){
        reportService.deleteReportById(reportId);
    }

    @DeleteMapping
    public void deleteAllReports(){
        reportService.deleteAllReports();
    }

    @PutMapping("/{reportId}")
    public ResponseEntity creationOrUpdateOfReport(@PathVariable("reportId") long reportId,
                                           @RequestBody PutReportRequest putReport){
        reportService.createOrUpdateReport(reportId, putReport);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}
