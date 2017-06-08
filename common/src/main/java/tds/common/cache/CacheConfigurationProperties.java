package tds.common.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static java.util.concurrent.TimeUnit.DAYS;

/**
 * This properties class holds configuration values for our caches.
 */
@ConfigurationProperties(prefix = "tds.cache")
public class CacheConfigurationProperties {
    private long expireTimeShort = 20;
    private long expireTimeMedium = 600;
    private long expireTimeLong = 7200;
    private long expireTimeDefault = DAYS.toSeconds(1);

    /**
     * @return Cache expiration after write time in seconds for the short-term cache.
     */
    public long getExpireTimeShort() {
        return expireTimeShort;
    }

    /**
     * @param expireTimeShort Cache expiration after write time in seconds for the short-term cache.
     */
    public void setExpireTimeShort(final long expireTimeShort) {
        this.expireTimeShort = expireTimeShort;
    }

    /**
     * @return Cache expiration after write time in seconds for the medium-term cache.
     */
    public long getExpireTimeMedium() {
        return expireTimeMedium;
    }

    /**
     * @param expireTimeMedium Cache expiration after write time in seconds for the medium-term cache.
     */
    public void setExpireTimeMedium(final long expireTimeMedium) {
        this.expireTimeMedium = expireTimeMedium;
    }

    /**
     * @return Cache expiration after write time in seconds for the long-term cache.
     */
    public long getExpireTimeLong() {
        return expireTimeLong;
    }

    /**
     * @param expireTimeLong Cache expiration after write time in seconds for the long-term cache.
     */
    public void setExpireTimeLong(final long expireTimeLong) {
        this.expireTimeLong = expireTimeLong;
    }

    /**
     * @return Default expiration after write time in seconds for an unspecified cache.
     */
    public long getExpireTimeDefault() {
        return expireTimeDefault;
    }

    /**
     * @param expireTimeDefault Default expiration after write time in seconds for an unspecified cache.
     */
    public void setExpireTimeDefault(final long expireTimeDefault) {
        this.expireTimeDefault = expireTimeDefault;
    }
}
