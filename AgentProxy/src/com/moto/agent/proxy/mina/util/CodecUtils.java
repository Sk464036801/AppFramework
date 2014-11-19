package com.moto.agent.proxy.mina.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Eric on 10/23/14.
 */
public class CodecUtils {

    private static final String ISO88591 = "ISO-8859-1";

    private static final String GBK = "GBK";

    private static final String UNICODE = "ISO-10646-UCS-2";

    private static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO88591);

    private static final Charset CHARSET_GBK = Charset.forName(GBK);

    private static final Charset CHARSET_UNICODE = Charset.forName(UNICODE);

    public final static byte MSG_FMT_ASC = 0;

    public final static byte MSG_FMT_DOWN = 3;

    public final static byte MSG_FMT_BIN = 4;

    public final static byte MSG_FMT_UCS = 8;

    public final static byte MSG_FMT_GB = 15;

    public static byte[] genEncPassword(String username, String password,
                                        String date) {

        if (username == null)
            username = "";
        if (password == null)
            password = "";

        ByteBuffer buffer = ByteBuffer.allocate(username.length() + 9
                + password.length() + 10);
        buffer.put(username.getBytes());
        for (int i = 0; i < 9; i++) {
            buffer.put((byte) 0);
        }
        buffer.put(password.getBytes());
        buffer.put((new String(date).getBytes()));
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            return messageDigest.digest(buffer.array());
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static CharsetDecoder getISO88591Decoder() {
        return (CHARSET_ISO_8859_1.newDecoder());
    }

    public static CharsetDecoder getGBKDecoder() {
        return (CHARSET_GBK.newDecoder());
    }

    public static CharsetDecoder getUnicodeDecoder() {
        return (CHARSET_UNICODE.newDecoder());
    }

    public static CharsetDecoder getCharsetDecoder(byte fmt) {
        switch (fmt) {
            case MSG_FMT_ASC:
                return getISO88591Decoder();
            case MSG_FMT_GB:
                return getGBKDecoder();
            case MSG_FMT_UCS:
                return getUnicodeDecoder();
            default:
                return getISO88591Decoder();
        }
    }

    public static CharsetEncoder getISO88591Encoder() {
        return (CHARSET_ISO_8859_1.newEncoder());
    }

    public static CharsetEncoder getGBKEncoder() {
        return (CHARSET_GBK.newEncoder());
    }

    public static CharsetEncoder getUnicodeEncoder() {
        return (CHARSET_UNICODE.newEncoder());
    }

    public static CharsetEncoder getCharsetEncoder(byte fmt) {
        switch (fmt) {
            case MSG_FMT_ASC:
                return getISO88591Encoder();
            case MSG_FMT_GB:
                return getGBKEncoder();
            case MSG_FMT_UCS:
                return getUnicodeEncoder();
            default:
                return getISO88591Encoder();
        }
    }

    public static String getCharsetName(CharsetDecoder decoder) {
        if (decoder.charset() == CHARSET_ISO_8859_1)
            return ISO88591;
        else if (decoder.charset() == CHARSET_GBK)
            return GBK;
        else if (decoder.charset() == CHARSET_UNICODE)
            return UNICODE;
        else
            return ISO88591;
    }
}
