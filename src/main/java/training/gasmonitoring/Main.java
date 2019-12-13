package training.gasmonitoring;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

public class Main {

    public static void main(String args[]) throws Exception {

        String bucket_name = "eventprocessing-locationss3bucket-7mbrb9iiisk4";
        String key_name = "locations.json";
        Regions regions = Regions.EU_WEST_2;
        String topic = "arn:aws:sns:eu-west-2:099421490492:EventProcessing-snsTopicSensorDataPart1-QVDE0JIXZS1V";

        List<LocationBean> locations = S3Client.fetchJsonAsListFromBucket(bucket_name, key_name, regions);
        List<Message> messages = S3Client.fetchMessageFromQueue(regions, topic);


        //System.out.println(messages.get(0));



    }

    // TODO Message Bean

    //
}

