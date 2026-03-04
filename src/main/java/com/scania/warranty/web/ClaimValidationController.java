/**
 * REST controller for warranty claim APIs.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.web;

import com.scania.warranty.domain.ClaimValidationResult;
import com.scania.warranty.dto.ClaimValidationRequest;
import com.scania.warranty.dto.ClaimValidationResponse;
import com.scania.warranty.service.ClaimValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for warranty claim validation operations.
 */
@RestController
@RequestMapping("/api/claims/validation")
public class ClaimValidationController {

    private final ClaimValidationService claimValidationService;

    public ClaimValidationController(ClaimValidationService claimValidationService) {
        this.claimValidationService = claimValidationService;
    }

    @PostMapping
    public ResponseEntity<ClaimValidationResponse> validateClaim(@RequestBody ClaimValidationRequest request) {
        ClaimValidationResult result = claimValidationService.validateClaim(
            request.kuerzel(),
            request.claimNumber(),
            request.errorNumber(),
            request.sequenceNumber(),
            request.customerDamageCause(),
            request.campaignNumber(),
            request.customerMainCause(),
            request.customerSubCause(),
            request.workshopDamageCause(),
            request.workshopMainCause(),
            request.workshopSubCause(),
            request.damageCausingPartNumber()
        );

        ClaimValidationResponse response = new ClaimValidationResponse(
            result.isValid(),
            result.getErrorMessages(),
            result.getFormattedErrors()
        );

        return ResponseEntity.ok(response);
    }
}