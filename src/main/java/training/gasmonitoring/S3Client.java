package training.gasmonitoring;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class S3Client {

    static List<Location> fetchJsonAsListFromBucket(String bucket_name, String key_name, Regions regions) throws Exception {
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(regions).build();
        S3Object s3Object = amazonS3.getObject(bucket_name, key_name);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(s3ObjectInputStream, new TypeReference<List<Location>>(){});
    }
}