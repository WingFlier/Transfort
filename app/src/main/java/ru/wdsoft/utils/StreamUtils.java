package ru.wdsoft.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class StreamUtils {

    private static final String LOG_PREFIX = "StreamUtils -- ";

    public static String decodeStream(InputStream stream) {

        try {

            if (stream != null) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();

                return sb.toString();
            }

        } catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

    private static byte[] streamToByteArray(InputStream stream) throws IOException {

        if (stream == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while ((n = stream.read(buf)) >= 0) {
            baos.write(buf, 0, n);

        }
        stream.close();
        baos.flush();
        baos.close();

        return baos.toByteArray();
    }

}
