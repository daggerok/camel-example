package daggerok;

/**
 * A Camel Application
 */
public class Main {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        var main = new org.apache.camel.main.Main();
        main.addRouteBuilder(new MyRouteBuilder());
        main.addMainListener(new MyShutdownHook());
        main.setDuration(30);
        main.run(args);
    }
}

