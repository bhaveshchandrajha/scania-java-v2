/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.dto;

public record ClaimCreationRequest(
    String kuerzel,
    String claimNr,
    String pakz,
    String rechNr,
    String rechDatum
) {}