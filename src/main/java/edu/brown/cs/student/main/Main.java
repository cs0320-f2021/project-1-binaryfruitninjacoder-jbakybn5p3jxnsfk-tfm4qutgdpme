package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {


  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  // stars array: [[starID: String, starName: String, X: String, Y: String, Z: String]]
  private ArrayList<String[]> stars;
  // counter for total number of stars
  private int counter;
  // hm1: {starName: coordinate}
  private HashMap<String, Double[]> hm1;
  // hm3: {starName: starID}
  private HashMap<String, String> hm3;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // initialize a StarDistance object
    StarDistance distCalculator = new StarDistance();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      // read the INPUT by line
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] args = input.split(" ");

          // hm2: {dist difference: starID[]}, a list of starIDs in case there are two stars at equal
          // distance to the one given
          HashMap<Double, ArrayList<String>> hm2;
          // if the args have 2 values in total, the first one is "stars"
          if (args.length == 2 && args[0].equals("stars")) {
            // refresh stars array, counter, hashmap1 and hashmap3 so they are refreshed
            stars = new ArrayList<>();
            counter = 0;
            hm1 = new HashMap<>();
            hm3 = new HashMap<>();
            // try read in CSV filepath
            try (BufferedReader fr = new BufferedReader(new FileReader(args[1]))) {
              String line;
              int lineNum = 0;
              while ((line = fr.readLine()) != null) {
                String[] values = line.split(",");
                try {
                  // lineNum = 0 is the header, check if header is in the correct format
                  if (lineNum == 0 && values[0].equals("StarID") &&
                      values[1].equals("ProperName") &&
                      values[2].equals("X") && values[3].equals("Y") && values[4].equals("Z")) {
                    lineNum++;
                  } else if (lineNum > 0) {
                    // split into format like [starID, starName, X, Y, Z]
                    Double[] coordinate =
                        distCalculator.formCoordinate(values[2], values[3], values[4]);
                    // hm1 => {starName: [X,Y,Z]}
                    // hm3 => {starName: starID}
                    hm1.put(values[1], coordinate);
                    hm3.put(values[1], values[0]);
                    stars.add(values);
                    counter++;
                  }
                } catch (Exception e) {
                  System.out.println(
                      "ERROR: CSV format is incorrect, please check header and row information");
                }
              }
              System.out.println("Read " + this.counter + " stars from " + args[1]);
            } catch (Exception e) {
              System.out.println("ERROR: The csv file could not be read, please check file path!");
            }
          } // whether in the format of naive_neighbors k x y z
          else if (args.length == 5 && args[0].equals("naive_neighbors") &&
              !args[2].contains("\"")) {
            try {
              //  create hashmap2 --> {distance: starIDs}
              hm2 = new HashMap<>();
              int k = Integer.parseInt(args[1]);
              Double[] coordinate = distCalculator.formCoordinate(args[2], args[3], args[4]);
              for (String[] star : stars) { // e.g. stars => ["1234", "awesomeStar", "21.00", "2398.23", "284.23"]
                Double[] starCoordinate = distCalculator.formCoordinate(star[2], star[3], star[4]);
                double distDiff = distCalculator.euclideanDistance(starCoordinate, coordinate);
                // fill the hashmap 2
                if (!hm2.containsKey(distDiff)) {
                  hm2.put(distDiff, new ArrayList<>());
                }
                hm2.get(distDiff).add(star[0]);
              }
              // then sort the hashmap2 by key value, ascending
              Map<Double, ArrayList<String>> sortedhm2 = new TreeMap<>(hm2);
              // create res array to put all starIDs (after sorting by distance in ascending order) inside
              ArrayList<String> res = new ArrayList<>();
              for (Double dist : sortedhm2.keySet()) {
                if (sortedhm2.get(dist).size() == 1) {
                  res.add(sortedhm2.get(dist).get(0));
                } else {
                  // shuffles the equidistance stars in place to guarantee randomness, then add all to res array
                  Collections.shuffle(sortedhm2.get(dist));
                  res.addAll(sortedhm2.get(dist));
                }
              }
              // print the first K starIDs
              for (int i = 0; i < k; i++) {
                System.out.println(res.get(i));
              }
            } catch (Exception e) {
              System.out.println(
                  "ERROR: We couldn't process your input, please check if you follow the format: naive_neighbors k x y z");
            }
          }
          // case where INPUT is in the format of naive_neighbors k "name"
          else if (args.length >= 3 && args[0].equals("naive_neighbors") &&
              args[2].contains("\"")) {
            try {
              //  create hashmap2 --> {distance: starIDs}
              hm2 = new HashMap<>();
              int k = Integer.parseInt(args[1]);
              String starName;
              // case where there's no empty space in the starName -> e.g. "Sol"
              if (args.length == 3) {
                starName = args[2].replace("\"", "");
              } else { // case there's empty space, thus args is longer than 3 and the third element has "\"" -> e.g. "Lonely Star"
                // extract name in form of list
                ArrayList<String> starNameWithQuoteLst =
                    new ArrayList<>(Arrays.asList(args).subList(2, args.length));

                // join name by delimiter " " and get rid of "\""
                String starNameWithQuote = String.join(" ", starNameWithQuoteLst);
                starName = starNameWithQuote.replace("\"", "");

              }
              // case where the starName is not in the dataset
              if (!this.hm1.containsKey(starName)) {
                System.out.println("ERROR: The star couldn't be found");
              } else {
                Double[] starCoordinate = this.hm1.get(starName);
                for (String[] star : stars) { // stars => ["1234", "awesomeStar", "21.00", "2398.23", "284.23"]
                  Double[] coordinate = distCalculator.formCoordinate(star[2], star[3], star[4]);
                  double distDiff = distCalculator.euclideanDistance(starCoordinate, coordinate);
                  if (!hm2.containsKey(distDiff)) {
                    hm2.put(distDiff, new ArrayList<>());
                  }
                  hm2.get(distDiff).add(star[0]);
                }
                // then sort the hashmap by key value (difference in dist), ascending
                Map<Double, ArrayList<String>> sortedhm2 = new TreeMap<>(hm2);
                ArrayList<String> res = new ArrayList<>();
                for (Double dist : sortedhm2.keySet()) {
                  if (sortedhm2.get(dist).size() == 1) {
                    res.add(sortedhm2.get(dist).get(0));
                  } else {
                    // shuffles the equidistance stars in place to guarantee randomness
                    Collections.shuffle(sortedhm2.get(dist));
                    res.addAll(sortedhm2.get(dist));
                  }
                }
                // remove the star itself from the returned results
                String thisStarID = hm3.get(starName);
                res.remove(thisStarID);
                if (res.isEmpty()) {
                  continue;
                }
                // if the k is smaller than total number of stars, pick top k
                else if (k <= this.counter) {
                  for (int i = 0; i < k; i++) {
                    System.out.println(res.get(i));
                  }
                  // otherwise, return all
                } else {
                  for (String starID : res) {
                    System.out.println(starID);
                  }
                }
              }
            } catch (Exception e) {
              System.out.println(
                  "ERROR: We couldn't process your input, please make sure the format follows: naive_neighbors k \"star name\"");
            }
          } else {
            System.out.println(
                "ERROR: Sorry, invalid input! Please make sure yor file path is correct, and you have entered the naive_neighbors method in the right format!");
          }
        } catch (Exception e) {
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
