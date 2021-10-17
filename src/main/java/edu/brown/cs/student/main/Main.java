package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.student.main.Handlers.ClassifyHandler;
import edu.brown.cs.student.main.Handlers.MathBot;
import edu.brown.cs.student.main.Handlers.RecommendHandler;
import edu.brown.cs.student.main.Handlers.SimilarHandler;
import edu.brown.cs.student.main.Handlers.StarHandler;
import edu.brown.cs.student.main.Handlers.UserHandler;
import edu.brown.cs.student.main.ORM.Database;
import edu.brown.cs.student.main.ORM.User;
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
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
//    Repl repl = new Repl();
//    repl.run(?);
//    for (String str : args) {
//      System.out.println(str);
//    }
    new Main(args).run();

  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }


  private static boolean isDouble(String str) {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      System.out.println("not an integer");
      return false;
    }
  }

  private static boolean isPath(String str) {
    File f = new File(str);
    // Check if the specified file
    // Exists or not
    if (f.exists()) {
      return true;
    } else {
      return false;
    }
  }


  private void run() throws SQLException, ClassNotFoundException {
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
    //StarDistance distCalculator = new StarDistance();
    Map<String, Function<String, String>> commandMap =
        new HashMap<String, Function<String, String>>() {
          {
            put("similar", SimilarHandler::handle);
            put("classify", ClassifyHandler::handle);
            put("recsys_rec", RecommendHandler::handleUser);
            put("recsys_gen_groups", RecommendHandler::handleGroup);
            //put("recsys_load responses", LoadHandler::handle);
          }
        };

    REPL repl = new REPL(commandMap);
    repl.activate(new InputStreamReader(System.in));

    //running the parsing method
    // starhandler.stars("data/stars/stardata.csv");
//
//    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//      String input;
//      while ((input = br.readLine()) != null) {
//        try {
//          input = input.trim();
//          // Don't split the string into two if the string consists of two words if the string is
//          //bounded between a pair of quotation marks
//          String[] arguments = input.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//
//          // Progress test: to see what the arguments are
//          //          StringBuffer sb = new StringBuffer();
//          //          for(int i = 0; i < arguments.length; i++) {
//          //            System.out.println(arguments[i]);
//          //          }
//
//          //for the lab, conducting the add method
//          if (arguments[0].equals("add") && isDouble(arguments[1]) &&
//              isDouble(arguments[2])) {
//            System.out.println(mathbot.add(Double.parseDouble(arguments[1]),
//                Double.parseDouble(arguments[2])));
//            //for the lab, conducting the subtract method
//          } else if (arguments[0].equals("subtract") && isDouble(arguments[1]) &&
//              isDouble(arguments[2])) {
//            System.out.println(mathbot.subtract(Double.parseDouble(arguments[1]),
//                Double.parseDouble(arguments[2])));
//            //when "stars" is called we will parse the given CSV
//          } else if (arguments.length == 2 && arguments[0].equals("stars") && isPath(arguments[1])) {
//            starhandler.stars(arguments[1]);
//            //when "naive_neighbors" is called with a coordinate,
//            //we will call the naive neighbours method
//          } else if (arguments[0].equals("naive_neighbors") && isInteger(arguments[1]) &&
//              isDouble(arguments[2]) && isDouble(arguments[3]) && isDouble(arguments[4])) {
//            starhandler.naive_neighbors(Integer.parseInt(arguments[1]), Double.parseDouble(arguments[2]),
//                    Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]), -1);
//            //when "naive_neighbors" is called with a star name we will call the naive neighbours
//            //by name method
//          } else if (arguments[0].equals("naive_neighbors") && isInteger(arguments[1])) {
//            starhandler.naive_neighborsByName(Integer.parseInt(arguments[1]),
//                (arguments[2].replace("\"", "")));
//          }
////          else if (arguments.length == 2 && arguments[0].equals("database") && isPath(arguments[1])) {
////            userHandler.user(arguments[1])//connection can be a static object ...
////          }
//          else if (arguments.length == 1 && arguments[0].equals("insertMandy")) {
//            User Mandy = new User(1, 130, "34b", "6'7", 20, "hourglass", "libra");
//            database.insert(Mandy);
//          }
//          //when an incorrect command is typed
//          else {
//            System.out.println("ERROR: incorrect command");
//          }
//        }
//        catch (Exception e) {
//           e.printStackTrace();
//          System.out.println("ERROR: We couldn't process your input");
//        }
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      System.out.println("ERROR: Invalid input for REPL");
//    }
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
