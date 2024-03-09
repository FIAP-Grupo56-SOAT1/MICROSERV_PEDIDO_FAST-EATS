package br.com.fiap.fasteats.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AwsSQSConfig {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${spring.cloud.aws.region.static}")
    private String region;
    @Value("${spring.cloud.aws.sqs.endpoint}")
    private String endpoint;

    //@Value("${spring.cloud.aws.credentials.session-token}")
    //private String sessionToken;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        AWSCredentials credentials = new DefaultAWSCredentialsProviderChain().getCredentials();
        String accessKey = credentials.getAWSAccessKeyId();
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        //String sessionToken = System.getenv("AWS_SESSION_TOKEN");
        String region = System.getenv("AWS_REGION");
        System.out.println("region: " + region);
        System.out.println("accessKey: " + accessKey);
        System.out.println("secretKey: " + secretKey);
        //System.out.println("sessionToken: " + sessionToken);
        return SqsAsyncClient
                .builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
    }
}
