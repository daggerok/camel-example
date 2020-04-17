package daggerok;

import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;

@Log4j2
public class MainApp {
  public static void main(String... args) throws Exception {
    var routeBuilder = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        errorHandler(deadLetterChannel("mock:error"));
        from("direct:a")
            .filter(header("foo").isEqualTo("bar"))
            .to("direct:b");
        from("direct:testIn").to("direct:testOut");
        // from("stream:in?promptMessage=Enter something: ")
        //     .transform(simple("${body.toUpperCase()}"))
        //     .to("stream:out")
        //     ;
      }
    };
    var listener = new MainListenerSupport() {
      @Override
      public void configure(CamelContext context) {
        var pause = 1234;
        Try.run(() -> Thread.sleep(pause))
           .andThenTry(aVoid -> {
             var template = context.createProducerTemplate();

             Try.run(() -> Thread.sleep(pause));
             log.info("sending...");
             template.sendBody("direct:test", "ololo trololo");

             Try.run(() -> Thread.sleep(pause));
             log.info("getting...");
             var body = template.requestBody("direct:test", "ololo trololo");

             Try.run(() -> Thread.sleep(pause));
             log.info(body);
           })
           .andThen(() -> context.getShutdownStrategy()
                                 .setShutdownNowOnTimeout(true))
           .andFinally(() -> System.exit(0));
      }
    };
    // var myProcessor = new Processor() {
    //   public void process(Exchange exchange) {
    //     log.info("Called with exchange: " + exchange);
    //   }
    // };

    var main = new Main();
    main.addRouteBuilder(routeBuilder);
    main.addMainListener(listener);
    main.setDuration(4567);
    main.run(args);
  }
}
