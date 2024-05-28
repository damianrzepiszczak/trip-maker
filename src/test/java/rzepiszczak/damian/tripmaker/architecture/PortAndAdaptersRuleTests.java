package rzepiszczak.damian.tripmaker.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "rzepiszczak.damian.tripmaker")
class PortAndAdaptersRuleTests {

    @Test
    void domainModelDoesNotDependOnOutside() {
        noClasses()
                .that()
                .resideInAPackage("rzepiszczak.damian.tripmaker.trip.application.model..")
                .should()
                .dependOnClassesThat()
                .resideOutsideOfPackages("rzepiszczak.damian.tripmaker.trip.application.model..",
                        "java..",
                        "lombok..",
                        "rzepiszczak.damian.tripmaker.common..",
                        "org.springframework..")
                .check(new ClassFileImporter()
                        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                        .importPackages("rzepiszczak.damian.tripmaker.trip.."));
    }
}
