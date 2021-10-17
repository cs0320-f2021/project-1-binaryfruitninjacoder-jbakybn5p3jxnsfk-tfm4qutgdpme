package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

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
  REPL repl = new REPL(new HashMap<String, Consumer<String>>());

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

  private void run() throws SQLException, IOException, ClassNotFoundException {


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




// ______________________________________ Siddarth's notes
//    Map<String, Function<String, String>> commandMap = new HashMap<String, Function<String, String>>(){{
//       put("similar", SimilarHandler::handle);
//    }
//    };

//_________________________________Siddarth's notes with edits
    //    Map<String, Function<String, String>> commandMap = new HashMap<String, Function<String, String>>(){{
//      Function<String, String> similar = put("similar",
//              SimilarHandler::handle);

//    ________________________________________ Error : SimilarHandler should return a method(not sure why)
//    Map<String, Consumer<String>> commandMap = new HashMap<String, Consumer<String>>();
//    commandMap.put("similar", SimilarHandler.handle("similar"));

    //___________________________________________ error: requires a character item
    //todo: pass method as value in hashmap
    Stack<Character> s = new Stack<>();
    Map<String, Consumer<String>> commandMap = new HashMap<String, Consumer<String>>();
      commandMap.put("similar", SimilarHandler.handle("similar") -> s.push('('));
//    commandMap.put("classify", ClassifyHandler.handle("similar"));
//    commandMap.put("command", CommandHandler.handle("similar"));



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
