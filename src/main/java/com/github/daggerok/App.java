package com.github.daggerok;

import com.github.daggerok.app.AppRouteBuilder;
import com.github.daggerok.infrastructure.ShutdownHook;
import io.vavr.control.Try;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;

/**
 * A Camel Application
 */
public class App {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {

        Main main = new Main();
        main.addRouteBuilder(new AppRouteBuilder());
        main.addMainListener(new ShutdownHook());
        main.setDuration(30);
        main.run(args);
    }
}

