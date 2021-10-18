package edu.brown.cs.student.main.client;

import java.net.URI;
import java.net.http.HttpRequest;

/**
 * This class generates the HttpRequests that are then used to make requests from the ApiClient.
 */
public class ClientRequestGenerator {


  /**
   * Similar to the introductory GET request, but restricted to api key holders only. Try calling it without the API
   * Key configured and see what happens!
   *
   * @return an HttpRequest object for accessing the secured resource.
   */
  public static HttpRequest getSecuredRequest(String filepath) {
    ClientAuth clientAuth = new ClientAuth();
    String apiKey = clientAuth.getApiKey();
    String[] apiKeyArray = apiKey.split(" ");
    String user = apiKeyArray[0];
    String password = apiKeyArray[1];
    String reqUri = filepath + user + "&key=" + password;
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(reqUri))
              .build();
    return request;
  }

  /**
   * Similar to the secured GET request, but is a POST request instead.
   *
   * @param param - the body of the POST request. This should be your name, passed in from the REPL.
   * @return an HttpRequest object for accessing and posting to the secured resource.
   */
  public static HttpRequest getSecuredPostRequest(String param) {
    String reqUri = "https://runwayapi.herokuapp.com/integration";

    // The body of this POST request must include your auth code as a json: {"auth": "<cs-login>"}.

    //make a json of auth: cs-login
  //  JSONObject jsonObj = new JSONObject();

    String apiKey = ClientAuth.getApiKey();
    ////Loaded Recommender with k students.

    // TODO build and return a new POST HttpRequest with an apikey.txt key header, and the param in the body.
    // Hint: the POST param should be: HttpRequest.BodyPublishers.ofString("{\"name\":\"" + param + "\"}")
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(reqUri))
            .header("x-api-key", apiKey)
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"" + param + "\"}"))
            .build();
    return request;
  }

}
