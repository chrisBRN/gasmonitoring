package training.gasmonitoring;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class S3Client {

    private final static Logger logger = LogManager.getLogger();

    static List<Location> fetchJsonFileFromBucket(String bucket_name, String key_name, Regions regions) {

        final String generalLog = "Downloading " + key_name + " from S3 bucket " + bucket_name;

        logger.info(generalLog + " ...");
        List<Location> locations = new ArrayList<>();
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(regions).build();

        try {
            S3Object s3Object = amazonS3.getObject(bucket_name, key_name);
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
            ObjectMapper objectMapper = new ObjectMapper();

            locations = objectMapper.readValue(s3ObjectInputStream, new TypeReference<List<Location>>(){});

        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            logger.info(generalLog + " FAILURE");

        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.info(generalLog + " FAILURE");
        }

        if (locations == null) {
            logger.info(generalLog + " FAILURE");
        } else {
            logger.info(generalLog + " SUCCESS");
        }

        return locations;
    }
}