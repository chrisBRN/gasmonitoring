package training.gasmonitoring;
import com.amazonaws.regions.Regions;

public class Main {



    public static void main(String args[]) {



        String bucket_name = "eventprocessing-locationss3bucket-7mbrb9iiisk4";
        String key_name = "locations.json";
        Regions regions = Regions.EU_WEST_2;

        S3Client.fetchJsonFileFromBucket(bucket_name, key_name, regions);


    }
}

