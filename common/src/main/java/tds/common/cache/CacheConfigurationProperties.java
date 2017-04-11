package tds.common.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This properties class holds configuration values for our caches.
 */
@ConfigurationProperties(prefix = "tds.cache")
public class CacheConfigurationProperties {
    private int expireTimeShort = 20;
    private int expireTimeMedium = 600;
    private int expireTimeLong = 7200;

    /**
     * @return Cache expiration after write time in seconds for the short-term cache.
     */
    public int getExpireTimeShort() {
        return expireTimeShort;
    }

    /**
     * @param expireTimeShort Cache expiration after write time in seconds for the short-term cache.
     */
    public void setExpireTimeShort(final int expireTimeShort) {
        this.expireTimeShort = expireTimeShort;
    }

    /**
     * @return Cache expiration after write time in seconds for the medium-term cache.
     */
    public int getExpireTimeMedium() {
        return expireTimeMedium;
    }

    /**
     * @param expireTimeMedium Cache expiration after write time in seconds for the medium-term cache.
     */
    public void setExpireTimeMedium(final int expireTimeMedium) {
        this.expireTimeMedium = expireTimeMedium;
    }

    /**
     * @return Cache expiration after write time in seconds for the long-term cache.
     */
    public int getExpireTimeLong() {
        return expireTimeLong;
    }

    /**
     * @param expireTimeLong Cache expiration after write time in seconds for the long-term cache.
     */
    public void setExpireTimeLong(final int expireTimeLong) {
        this.expireTimeLong = expireTimeLong;
    }
}
