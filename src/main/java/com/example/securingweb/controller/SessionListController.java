package com.example.securingweb.controller;

import com.example.securingweb.domain.Session;
import com.example.securingweb.file.XlsWriter;
import com.example.securingweb.repo.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class SessionListController {
    @Autowired
    private SessionRepo sessionRepo;

    @Value("${download.path}")
    private String downloadPath;

    @GetMapping("/sessionList")
    public String sessionList(@RequestParam Date startDateStr, @RequestParam Date endDateStr, Map<String, Object> model) {
        Iterable<Session> sessions;
        sessions = sessionRepo.findAll();

        ArrayList<Session> sessionArrayList = new ArrayList<>();
        sessions.forEach(s -> {
            if (s.getDate().after(startDateStr) && s.getDate().before(endDateStr)) {
                sessionArrayList.add(s);
            }
        });

        model.put("sessions", sessionArrayList);
        File file = new File(downloadPath,"data.xls");
        try {

            if (file.exists()) {
                file.delete();
                file.createNewFile();
                XlsWriter.writeToXLS(file, sessionArrayList);
            } else {
                file.createNewFile();
                XlsWriter.writeToXLS(file, sessionArrayList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.put("path", file.getName());

        return "sessionList";
    }

    /**
     * For right convert from HTML Date to Spring Date
     **/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
