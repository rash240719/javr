import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class S3 {
    private AmazonS3 client;
    private String bucketname;

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
            File file = new File(getClass().getClassLoader().getResource(filename).toURI());
            if (!file.exists()) throw new FileNotFoundException();

            client.putObject(bucketname, filename, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InputStream downloadFile(String filename) {
        InputStream inputStream = null;

        try {
            inputStream = client.getObject(bucketname, filename).getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
            inputStream = null;
        } finally {
            return inputStream;
        }
    }

    public String downloadFileAsString(String filename) {
        return stringifyInputStream(downloadFile(filename));
    }

    public void writeFile(String filename, InputStream content) {
        File file = new File(filename);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            if (content != null) {
                if (!file.exists()) {
                    file.createNewFile();
                }

                byte[] contentInBytes = IOUtils.toByteArray(content);
                fileOutputStream.write(contentInBytes);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadAndWriteFile(String filename) {
        try {
            if (!filename.isEmpty()) {
                writeFile(filename, downloadFile(filename));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFileFromString(String filename, String content) {
        try {
            InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8.name()));
            writeFile(filename, inputStream);
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

    public AmazonS3 getClient() {
        return client;
    }

    public void setClient(AmazonS3 client) {
        this.client = client;
    }

    public String getBucketname() {
        return bucketname;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname;
    }
}
