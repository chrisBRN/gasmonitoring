package training.gasmonitoring;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

public class S3Client {

    public static List<LocationBean> fetchJsonAsListFromBucket(String bucket_name, String key_name, Regions regions) throws Exception {
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(regions).build();
        S3Object s3Object = amazonS3.getObject(bucket_name, key_name);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(s3ObjectInputStream, new TypeReference<List<LocationBean>>(){});
    }

    public static List<Message> fetchMessageFromQueue(Regions regions, String topicARN){

        AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard().withRegion(regions).build();
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(regions).build();
        String queueName    = UUID.randomUUID().toString();
        String myQueueUrl   = amazonSQS.createQueue(new CreateQueueRequest(queueName)).getQueueUrl();

        Topics.subscribeQueue(amazonSNS, amazonSQS, topicARN, myQueueUrl);

        // TODO catch 0 messages out of array bounds
        List<Message> messages = amazonSQS
                                    .receiveMessage(new ReceiveMessageRequest(myQueueUrl)
                                    .withMaxNumberOfMessages(10)
                                    .withWaitTimeSeconds(3)).getMessages();

        amazonSQS.deleteQueue(myQueueUrl);

        return messages;
    }
}