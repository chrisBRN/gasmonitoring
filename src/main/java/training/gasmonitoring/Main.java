package training.gasmonitoring;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.Topic;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.apache.commons.codec.binary.Base64;

import java.util.List;

public class Main {

    public static void main(String args[]) throws Exception {

        String bucket_name = "eventprocessing-locationss3bucket-7mbrb9iiisk4";
        String key_name = "locations.json";
        Regions regions = Regions.EU_WEST_2;
        String topic = "arn:aws:sns:eu-west-2:099421490492:EventProcessing-snsTopicSensorDataPart1-QVDE0JIXZS1V";

        List<Location> locations = S3Client.fetchJsonAsListFromBucket(bucket_name, key_name, regions);


        AmazonSNS sns = AmazonSNSClientBuilder.standard().withRegion(regions).build();
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(regions).build();
        String myQueueUrl = sqs.createQueue(new CreateQueueRequest("queueName")).getQueueUrl();

        Topics.subscribeQueue(sns, sqs, topic, myQueueUrl);

        List<Message> messages = sqs.receiveMessage(new ReceiveMessageRequest(myQueueUrl)
            .withMaxNumberOfMessages(10)
            .withWaitTimeSeconds(3)).getMessages();

        System.out.println(messages.get(0));

        sqs.deleteQueue(myQueueUrl);

    }

    // TODO Message Bean
    // Random Quene name
    //
}

