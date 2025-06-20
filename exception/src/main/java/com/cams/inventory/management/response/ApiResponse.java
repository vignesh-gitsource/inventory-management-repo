package com.cams.inventory.management.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * API Response.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T, S> {

  /**
   * Boolean flag indicating success or error of functionality.
   */
  boolean success;

  /**
   * Success/Error message.
   */
  String message;

  /**
   * Contains error's.
   */
  List<T> errors;

  /**
   * Data to send back as response.
   */
  S data;
}

