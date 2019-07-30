package com.spike.utils;

import org.springframework.util.DigestUtils;

/**
 * Author: tangji
 * Date: 2019 07 28 20:31
 * Description: <...>
 */
public class MD5Util {

    //用于混淆MD5的盐
    private static final String salt = "mfamkfkdmgfd%$$#@*&&jkfdfk";
    public static String getMd5(long spikeId) {
        String base = spikeId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
