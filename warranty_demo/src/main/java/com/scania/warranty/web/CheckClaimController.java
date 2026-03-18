/**
 * REST controller for warranty claim APIs.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.web;

import com.scania.warranty.domain.CheckClaimRequest;
import com.scania.warranty.dto.CheckClaimResultDto;
import com.scania.warranty.service.CheckClaimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
public class CheckClaimController {

    private final CheckClaimService checkClaimService;

    public CheckClaimController(CheckClaimService checkClaimService) {
        this.checkClaimService = checkClaimService;
    }

    @PostMapping("/check")
    public ResponseEntity<CheckClaimResultDto> checkClaim(@RequestBody CheckClaimRequest request) {
        CheckClaimResultDto result = checkClaimService.checkClaim(request); // @rpg-trace: n1919
        if (result.valid()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.unprocessableEntity().body(result);
        }
    }
}