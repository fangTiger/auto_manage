package com.xl.basic.kafuka;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;


public  class MessageScheme implements Scheme {

    public static final String STRING_SCHEME_KEY = "str";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    public static String deserializeString(ByteBuffer string) {
        if (string.hasArray()) {
            int base = string.arrayOffset();
            return new String(string.array(), base + string.position(), string.remaining());
        } else {
            return new String(Utils.toByteArray(string), UTF8_CHARSET);
        }
    }

    @Override
    public List<Object> deserialize(ByteBuffer byteBuffer) {

        return new Values(deserializeString(byteBuffer));
       // return null;
    }

    public Fields getOutputFields() {
        return new Fields(STRING_SCHEME_KEY);
    }
}





