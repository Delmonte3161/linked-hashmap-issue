package com.example.demo.complex;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

/**
 * @author ds3161
 *
 */
@Data
@RedisHash("something-wicked-this-way-comes")
public class IngestionEventModel {
	@Id
	private String id;

	@Indexed
	private String sourceId;

	@Indexed
	private String tenantId;

	@Indexed
	private String targetDomain;

	@Indexed
	private String foreignId;

	@Indexed
	private String domainLink;

	@Indexed
	private Integer retryCount = 0;

	@Indexed
	private String status = "Pending";

	private String statusReason = "Event queued for processing";

	// The following fields are just for search indexes
	@Indexed
	private String sourceIdTenantId;

	@Indexed
	private String sourceIdTenantIdTargetDomain;

	@Indexed
	private String sourceIdTenantIdTargetDomainForeignId;

	private Map<String, Object> relationships;

	private Map<String, Object> payload;

	@TimeToLive
	private Integer timeToLive;

	/**
	 * Creates secondary key values for indexing
	 */
	public void populateKeys() {
		this.sourceIdTenantId = getKey(this.sourceId, this.tenantId);
		this.sourceIdTenantIdTargetDomain = getKey(this.sourceId, this.tenantId,
				this.targetDomain);
		this.sourceIdTenantIdTargetDomainForeignId = getKey(this.sourceId, this.tenantId,
				this.targetDomain, this.foreignId);
	}

	/**
	 * @param sourceId
	 * @param tenantId
	 * @return String
	 */
	public static String getKey(String sourceId, String tenantId) {
		return sourceId + "|" + tenantId;
	}

	/**
	 * @param sourceId
	 * @param tenantId
	 * @param targetDomain
	 * @return String
	 */
	public static String getKey(String sourceId, String tenantId, String targetDomain) {
		return sourceId + "|" + tenantId + "|" + targetDomain;
	}

	/**
	 * @param sourceId
	 * @param tenantId
	 * @param targetDomain
	 * @param foreignId
	 * @return String
	 */
	public static String getKey(String sourceId, String tenantId, String targetDomain,
			String foreignId) {
		return sourceId + "|" + tenantId + "|" + targetDomain + "|" + foreignId;
	}
}
