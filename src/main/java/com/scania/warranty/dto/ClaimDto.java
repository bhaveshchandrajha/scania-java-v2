/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim detail.
 */
public record ClaimDto(
        String pakz,
        String rechNr,
        String rechDatum,
        String auftragsNr,
        String wete,
        String claimNr,
        String chassisNr,
        String kennzeichen,
        Integer zulDatum,
        Integer repDatum,
        Integer kmStand,
        Integer produktTyp,
        String anhang,
        String auslaender,
        String kdNr,
        String kdName,
        String claimNrSde,
        Integer statusCodeSde,
        Integer anzFehler,
        String bereich,
        String aufNr
) {
}