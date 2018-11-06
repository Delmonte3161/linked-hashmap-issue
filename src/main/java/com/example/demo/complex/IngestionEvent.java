package com.example.demo.complex;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ds3161
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IngestionEvent extends ResourceSupport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2550496966080741261L;

	@JsonIgnore
	private String ingestionId;
	private String sourceId;
	private String tenantId;
	private String foreignId;
	private String targetDomain;
	private Map<String, Object> relationships;
	private Map<String, Object> payload;
	private String domainLink;
	private Integer retryCount = 0;
	private String status = "Pending";
	private String statusReason;
	private Integer timeToLive;
	private Boolean updateOnly = false;
}
