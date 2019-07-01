package com.github.daggerok.app;

import io.vavr.control.Try;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class AppRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
        from("file:src/data?noop=true")
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .log("UK message")
                    .to("file:target/messages/uk")
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others");

        // from("smb://maksim.kostromin@i.sigmaukraine.com/store/Training-and-development/Seminars/Architectural-meetup?password=secret")
        //     .to("file://target/test-reports");
    }

    // protected RouteBuilder createRouteBuilder() throws Exception {
    //     return new RouteBuilder() {
    //         public void configure() throws Exception {
    //             // we use a delay of 60 minutes (eg. once pr. hour) we poll the server long delay = 60 * 60 * 1000L;
    //             // from the given server we poll (= download) all the files
    //             // from the public/reports folder and store this as files
    //             // in a local directory. Camel will use the filenames from the server
    //             from("smb://foo@myserver.example.com/public/reports?password=secret&delay=" + delay)
    //                 .to("file://target/test-reports");
    //             from("smb://foo@myserver.example.com/sharename?password=secret&amp;delay=60000")
    //                 .to("file://target/test-reports");
    //         }
    //     };
    // }
}
