package org.kkmvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编解码工具类
 */
public final class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 将url编码
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException();
        }
        return target;
    }

    /**
     * 将URL解码
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode url failure", e);
            throw new RuntimeException();
        }
        return target;
    }
}
