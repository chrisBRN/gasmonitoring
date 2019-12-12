package training.gasmonitoring;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class S3Client {

    private final static Logger logger = LogManager.getLogger();



    static void fetchJsonFileFromBucket(String bucket_name, String key_name, Regions regions){

        logger.info("Downloading " + key_name + " from S3 bucket " + bucket_name + " ...");

        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(regions).build();

        try {

            S3Object s3Object = amazonS3.getObject(bucket_name, key_name);

            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(key_name));

            byte[] read_buf = new byte[1024];
            int read_len = 0;

            while ((read_len = s3ObjectInputStream.read(read_buf)) > 0) {
                fileOutputStream.write(read_buf, 0, read_len);
            }

            s3ObjectInputStream.close();
            fileOutputStream.close();

        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            System.exit(1);

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }

        logger.info("Success");
    }

}
