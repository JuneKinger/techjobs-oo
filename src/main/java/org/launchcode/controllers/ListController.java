package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.JobField;
import org.launchcode.models.JobFieldType;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    // first time it creates a new (empty) jobData object

    private JobData jobData = JobData.getInstance();

    @RequestMapping(value = "")
    public String list(Model model) {

        // fields have 0="EMPLOYER", 1="lOCATION" etc from JobFieldtype (enum type)
        // .values() gets enum fields from JobFieldType
        JobFieldType[] fields = JobFieldType.values();

        // send to the "list" view
        model.addAttribute("fields", fields);

        return "list";
    }

    // from list.html:
    // eg. http://localhost:8080/list/values?column=EMPLOYER
    @RequestMapping(value = "values")
    public String listColumnValues(Model model, @RequestParam JobFieldType column) {

        // if all is chosen, goes to list/all handler below - value="all" below - to display all Jobs
        if (column.equals(JobFieldType.ALL)) {
            return "redirect:/list/all";
        }

        // ArrayList<? means that it is an arrayList of exactly ONE type that extends JobField
        // So you can be sure, that when you call get method, you'll get something that is of type
        // JobField only since you don't know upfront what ArrayList will be
        ArrayList<? extends JobField> items;
        // get all jobs for column chosen
        switch(column) {
            case EMPLOYER:
                items = jobData.getEmployers().findAll();
                break;
            case LOCATION:
                items = jobData.getLocations().findAll();
                break;
            case CORE_COMPETENCY:
                items = jobData.getCoreCompetencies().findAll();
                break;
            case POSITION_TYPE:
            default:
                items = jobData.getPositionTypes().findAll();
        }

        // getNane() gets String name= as above
        model.addAttribute("title", "All " + column.getName() + " Values");
        model.addAttribute("column", column);
        model.addAttribute("items", items);

        return "list-column";
    }

    // find by column and value:
    // http://localhost:8080/list/jobs?column=EMPLOYER&name=WWT
    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model,
            @RequestParam JobFieldType column, @RequestParam String name) {

        ArrayList<Job> jobs = jobData.findByColumnAndValue(column, name);

        // Jobs With Employer: WWT
        model.addAttribute("title", "Jobs with " + column.getName() + ": " + name);
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }

    @RequestMapping(value = "all")
    public String listAllJobs(Model model) {

        ArrayList<Job> jobs = jobData.findAll();

        model.addAttribute("title", "All Jobs");
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}
