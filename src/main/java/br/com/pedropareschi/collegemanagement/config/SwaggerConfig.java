package br.com.pedropareschi.collegemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private final ApiResponseEntry m201 = simpleMessage(201, "Created");
    private final ApiResponseEntry m204put = simpleMessage(204, "Updated");
    private final ApiResponseEntry m204del = simpleMessage(204, "Deleted");
    private final ApiResponseEntry m403 = simpleMessage(403, "Unauthorized");
    private final ApiResponseEntry m404 = simpleMessage(404, "Not found");
    private final ApiResponseEntry m422 = simpleMessage(422, "Validation Error");
    private final ApiResponseEntry m500 = simpleMessage(500, "Unexpected error");

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("collegemanagement")
                .packagesToScan("br.com.pedropareschi.collegemanagement.resources")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(globalResponseMessages())
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("College management API")
                .description("Experimental API simulating a college management project")
                .version("1.0")
                .termsOfService("https://github.com/PedroPareschi/CollegeManagement/blob/main/LICENSE")
                .contact(new Contact().name("Pedro Pareschi").url("https://github.com/PedroPareschi").email("pedropareschi15@gmail.com"))
                .license(new License().name("MIT").url("https://github.com/PedroPareschi/CollegeManagement/blob/main/LICENSE")));
    }

    private OpenApiCustomizer globalResponseMessages() {
        return openApi -> {
            if (openApi.getPaths() == null) {
                return;
            }
            for (PathItem pathItem : openApi.getPaths().values()) {
                addResponses(pathItem.getGet(), Arrays.asList(m403, m404, m500));
                addResponses(pathItem.getPost(), Arrays.asList(m201, m403, m422, m500));
                addResponses(pathItem.getPut(), Arrays.asList(m204put, m403, m404, m422, m500));
                addResponses(pathItem.getDelete(), Arrays.asList(m204del, m403, m404, m500));
            }
        };
    }

    private void addResponses(Operation operation, List<ApiResponseEntry> messages) {
        if (operation == null) {
            return;
        }
        if (operation.getResponses() == null) {
            operation.setResponses(new ApiResponses());
        }
        for (ApiResponseEntry message : messages) {
            operation.getResponses().addApiResponse(message.code(), new ApiResponse().description(message.message()));
        }
    }

    private ApiResponseEntry simpleMessage(int code, String msg) {
        return new ApiResponseEntry(String.valueOf(code), msg);
    }

    private record ApiResponseEntry(String code, String message) {
    }
}
