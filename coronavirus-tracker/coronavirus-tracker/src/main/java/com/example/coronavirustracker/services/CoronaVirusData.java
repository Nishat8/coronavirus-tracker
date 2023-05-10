package com.example.coronavirustracker.services;

import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronaVirusData {
    private static String virus_data_url="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    public <CSVRecord> void fetchData() throws IOException, InterruptedException {

        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(virus_data_url)).build();
        HttpResponse<String> httpResponse= client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());

//        Reader in = new FileReader("path/to/file.csv");
        StringReader csvReader=new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = (Iterable<CSVRecord>) CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {
            String id = record.get("ID");
            String customerNo = record.get("CustomerNo");
            String name = record.get("Name");
        }
    }
}
