package br.com.fiap.fasteats.config;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AwsSQSConfig {
    //@Value("${spring.cloud.aws.credentials.access-key}")
    private final String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
   // @Value("${spring.cloud.aws.credentials.secret-key}")
    private final String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
    //@Value("${spring.cloud.aws.region.static}")

    private final String sessionToken = System.getenv("AWS_SESSION_TOKEN");
    private String region = System.getenv("AWS_REGION");
    @Value("${spring.cloud.aws.sqs.endpoint}")
    private String endpoint;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        System.out.println("accessKey: " + accessKey);
        System.out.println("secretKey: " + secretKey);
        System.out.println("sessionToken: " + sessionToken);
        return SqsAsyncClient
                .builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsSessionCredentials.create((accessKey, secretKey, sessionToken))))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
    }
}
