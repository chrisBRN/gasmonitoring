package training.gasmonitoring;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String args[]) {

        String bucket_name = "eventprocessing-locationss3bucket-7mbrb9iiisk4";
        String key_name = "locations.json";
        Regions regions = Regions.EU_WEST_2;

        S3Client.fetchJsonFileFromBucket(bucket_name, key_name, regions);


    }


}

