/**
 * REST controller for warranty claim APIs.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.web;

import com.scania.warranty.dto.ClaimCreationRequest;
import com.scania.warranty.dto.ClaimCreationResponse;
import com.scania.warranty.service.ClaimCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
public class ClaimCreationController {
    
    private final ClaimCreationService claimCreationService;

    public ClaimCreationController(ClaimCreationService claimCreationService) {
        this.claimCreationService = claimCreationService;
    }

    @PostMapping("/create-from-gps")
    public ResponseEntity<ClaimCreationResponse> createClaimFromGps(@RequestBody ClaimCreationRequest request) {
        try {
            claimCreationService.createClaim(
                request.kuerzel(),
                request.claimNr(),
                request.pakz(),
                request.rechNr(),
                request.rechDatum()
            ); // @rpg-trace: n1779
            
            return ResponseEntity.ok(new ClaimCreationResponse(true, "Claim created successfully")); // @rpg-trace: n1836
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new ClaimCreationResponse(false, "Error creating claim: " + e.getMessage())); // @rpg-trace: n1836
        }
    }
}