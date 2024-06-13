package com.rainbowletter.server.common.dto;

import java.util.Date;
import lombok.Builder;

@Builder
public record TokenInfo(String subject, String claimKey, String claimValue, Date expiration) {

}
