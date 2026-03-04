/**
 * REST controller for warranty claim APIs.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.web;

import com.scania.warranty.dto.ClaimCreationRequest;
import com.scania.warranty.service.ClaimCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for claim creation operations.
 */
@RestController
@RequestMapping("/api/claims")
public class ClaimCreationController {
    
    private final ClaimCreationService claimCreationService;
    
    @Autowired
    public ClaimCreationController(ClaimCreationService claimCreationService) {
        this.claimCreationService = claimCreationService;
    }
    
    @PostMapping("/create-from-request")
    public ResponseEntity<Void> createClaim(@RequestBody ClaimCreationRequest request) {
        claimCreationService.createClaim(request.kuerzel(), request.claimNr(), request.pakz(), request.rechNr(), request.rechDatum());
        return ResponseEntity.ok().build();
    }
}