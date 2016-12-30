package com.zyj.filepreferences.lib.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${zyj} on 2016/9/2.
 */
public class CacheUtil {

    /**
     * 把key 转成md5作为磁盘缓存的名字
     * @param key
     * @return
     */
    private static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getKey(String key ){
       return hashKeyForDisk( key );
    }
}
