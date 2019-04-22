import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Running class for the project. It will produce a json object with my email, name, final test score average, and the female computer science student's ids.
 */
public class TopBloc {

    public static void main(String args[]) {
    ArrayList<Student> students = Utils.generateStudentList();

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        ArrayList<String> ids= new ArrayList<>();
        int total = 0;

        for(Student s : students) {
            total += s.getScore();
        }

        //calculate mean
        int mean = total / students.size();

        json.put("id", "nsebenste@gmail.com");
        json.put("name", "Noah Sebenste");
        json.put("average", mean);

        for(Student s : students) {
            if(s.getGender().equals("F") && s.getMajor().equals("computer science")) {
                ids.add("" + s.getId());
            }
        }

        //sort female ids
        Collections.sort(ids);

        //add female computer science student ids to the json object in the form of an array
        array.add(ids);
        json.put("studentids", array);

        //send a post request to the server
        StringEntity entity = new StringEntity(json.toString(),
                ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://3.86.140.38:5000/challenge");
        request.setEntity(entity);

        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //print out the error and response code
        System.out.println(json);
        System.out.println(response.getStatusLine().getStatusCode());
    }
}
