import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);
            aValue = aValue.toLowerCase();

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();
       // value = value.toLowerCase();
       // column = column.toLowerCase();
        /* String searchField = getUserSelection("Search by:", columnChoices);
        * System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();
                searchTerm = searchTerm.toLowerCase();
                *
                *   if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm String searchTerm = in.nextLine();));
                }
                *
                */
        HashMap<String, String> moons = new HashMap<>();




        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);
            aValue = aValue.toLowerCase();


            if (aValue.contains(value)) {
                jobs.add(row);




            }
            else{
                moons.put("nothing", "no value");


            }


        }
        if(jobs.size() < 1){
            jobs.add(moons);
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();
        // value = value.toLowerCase();
        /* String searchField = getUserSelection("Search by:", columnChoices);
        * System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();
                searchTerm = searchTerm.toLowerCase();
                *
                *   if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
                * */

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();


        //0ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
       /* HashMap<String, String> noValue = new HashMap<>();

        noValue.put("none","something");

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(value);


            if (aValue.contains(value)) {
                jobs.add(row);
            }
            else{
                System.out.println(aValue);
                jobs.add(noValue);

            }

        }

        return jobs;
    } */

        // noValue.put("none":"something");

        HashMap<String, String> moons = new HashMap<>();



        for (HashMap<String, String> row : allJobs) {
            for (String i : row.keySet()) {
                // accessing the hashmap / dictionary at top level
                String rowVal = row.get(i);
                // grabs the value of the dictionary key
                rowVal = rowVal.toLowerCase();

                if(rowVal.contains(value)) {
                    // if dictionary contains the input from mainclass add it
                    jobs.add(row);
                    break;
                } else {

                    moons.put("none", "something");



                }
            }

        }
               /* if(rowVal.toLowerCase().contains(value.toLowerCase())) {
                    jobs.add(row);
                    break;
                } else {
                    jobs.add(moons);
                    break;
                }
            }

        } */

        //used this if statement as a means of addign moons otherwise the code would contain things I dont want such
        // such as no value when values are clealy appering
        if(jobs.size() < 1){
            jobs.add(moons);
        }
        return jobs;
    }




    /**
     *for (HashMap<String, String> inSomeJob : someJobs) {
     *             System.out.println("\n" );
     *             System.out.println("*************");
     *             for (Map.Entry<String, String> student : inSomeJob.entrySet()) {
     *                 System.out.println(student.getKey() + " - " + student.getValue());
     */

        // TODO - implement this method


    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    // Ronnie removed tolowercase to see if searches appear
                    //System.out.println(record.get(headerLabel));
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
