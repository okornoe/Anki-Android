package com.ichi2.anki.lint.rules;

import org.junit.Test;

import static com.android.tools.lint.checks.infrastructure.TestFile.JavaTestFile.create;
import static com.android.tools.lint.checks.infrastructure.TestLintTask.lint;
import static org.junit.Assert.assertTrue;

public class DirectDateInstantiationTest {
    private final String stubDate = "                         \n" +
            "package java.util;                               \n" +
            "                                                 \n" +
            "public class Date {                              \n" +
            "                                                 \n" +
            "    public Date() {                              \n" +
            "                                                 \n" +
            "    }                                            \n" +
            "}                                                \n";

    private final String javaFileToBeTested = "               \n" +
            "package com.ichi2.anki.lint.rules;               \n" +
            "                                                 \n" +
            "import java.util.Date;                           \n" +
            "                                                 \n" +
            "public class TestJavaClass {                     \n" +
            "                                                 \n" +
            "    public static void main(String[] args) {     \n" +
            "        Date d = new Date();                     \n" +
            "    }                                            \n" +
            "}                                                \n";


    @Test
    public void showsErrorsForInvalidUsage() {
        lint().
                allowMissingSdk().
                allowCompilationErrors()
                .files(create(stubDate), create(javaFileToBeTested))
                .issues(DirectDateInstantiation.ISSUE)
                .run()
                .expectErrorCount(1)
                .check(output -> {
                    assertTrue(output.contains(DirectDateInstantiation.ID));
                    assertTrue(output.contains(DirectDateInstantiation.DESCRIPTION));
                });
    }
}
