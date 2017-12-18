import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

import java.io.*;

public class S3 {
    AmazonS3 client;
    private static String bucketname;

    public S3() {
        bucketname = "bucket.sandbox.getmore.mx";
        client = AmazonS3ClientBuilder
                .standard().withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    S3(String bucketname, String keyname) {
        this.bucketname = bucketname;
    }

    public void uploadFile(String filename) {
        try {
            filename += ".txt";
            File file = new File(getClass().getClassLoader().getResource(filename).toURI());
            if (!file.exists()) throw new FileNotFoundException();

            client.putObject(bucketname, filename, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(String filename) {
        String content = "";

        try {
            filename += ".txt";
            S3Object object = client.getObject(bucketname, filename);
            InputStream objectData = object.getObjectContent();

            content = stringifyInputStream(objectData);
            objectData.close();

            File file = new File(getClass().getClassLoader().getResource(filename).toURI());
            if (!file.exists()) throw new FileNotFoundException();

            client.putObject(bucketname, filename, file);
        } catch (Exception e) {
            content = null;
            e.printStackTrace();
        } finally {
            return content;
        }
    }

    public void writeFile(String filename, String content) {
        try {
            filename += ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

            writer.append(' ');
            writer.append(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String stringifyInputStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String content = "";

        try {
            boolean goOn = true;

            while (goOn) {
                String line = reader.readLine();

                if (line == null) {
                    goOn = false;
                } else {
                    content += line + "\n";
                }
            }
        } catch (Exception e) {
            content = null;
            e.printStackTrace();
        } finally {
            return content;
        }
    }
}
