package com.LibraryCT.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "rerun:target/rerun/txt"
        },
        features = "src/test/resources/features",
        glue = "com/LibraryCT/step_definitions",
        //"net.serenity_bdd.samples",
        dryRun = false,
        tags = "@G24-180"
)
public class CukesRunner {
}
