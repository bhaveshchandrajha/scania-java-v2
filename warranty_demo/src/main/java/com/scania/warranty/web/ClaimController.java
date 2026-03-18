/**
 * REST controller for warranty claim APIs.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.web;

import com.scania.warranty.domain.ClaimSearchCriteria;
import com.scania.warranty.domain.SortDirection;
import com.scania.warranty.dto.*;
import com.scania.warranty.service.ClaimSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimSearchService claimSearchService;

    public ClaimController(ClaimSearchService claimSearchService) {
        this.claimSearchService = claimSearchService;
    }

    /** GET search for screen.html and simple clients; companyCode defaults to 001 */
    @GetMapping("/search")
    public ResponseEntity<List<ClaimListItemDto>> searchClaimsGet(
            @RequestParam(defaultValue = "001") String companyCode) {
        ClaimSearchCriteria criteria = new ClaimSearchCriteria(
            companyCode, null, null, null, null, null, null,
            0, false, false, null, true, com.scania.warranty.domain.SortDirection.ASCENDING);
        List<ClaimListItemDto> results = claimSearchService.searchClaims(criteria);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ClaimListItemDto>> searchClaims(@RequestBody ClaimSearchRequestDto request) {
        String company = request.companyCode() != null ? request.companyCode() : "001";
        ClaimSearchCriteria criteria = new ClaimSearchCriteria( // @rpg-trace: n422
            company,
            request.status(),
            request.statusCompareSign(),
            request.filterBranch(),
            request.filterCustomer(),
            request.filterSde(),
            request.filterArt(),
            request.filterAgeDaysResolved(),
            request.filterOpenOnlyResolved(),
            request.filterMinimumOnlyResolved(),
            request.searchString(),
            Boolean.TRUE.equals(request.sortByClaimNr()),
            request.sortDirection()
        );
        List<ClaimListItemDto> results = claimSearchService.searchClaims(criteria); // @rpg-trace: n436
        return ResponseEntity.ok(results);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteClaim(@RequestBody ClaimDeleteRequestDto request) {
        claimSearchService.deleteClaim(request.companyCode(), request.claimNr()); // @rpg-trace: n587
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-status")
    public ResponseEntity<Boolean> updateStatus(@RequestBody ClaimStatusUpdateDto request) {
        boolean updated = claimSearchService.updateClaimStatus( // @rpg-trace: n653
            request.companyCode(), request.claimNr(), 2, request.newStatus()
        );
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/book-minimum")
    public ResponseEntity<Boolean> bookMinimumClaim(@RequestBody MinimumClaimBookingDto request) {
        boolean booked = claimSearchService.bookMinimumClaim(request.companyCode(), request.claimNr()); // @rpg-trace: n1663
        return ResponseEntity.ok(booked);
    }

    @PostMapping("/release-request")
    public ResponseEntity<Void> createReleaseRequest(@RequestParam String companyCode,
                                                      @RequestParam String invoiceNr,
                                                      @RequestParam String invoiceDate) {
        claimSearchService.createClaimReleaseRequest(companyCode, invoiceNr, invoiceDate); // @rpg-trace: n1706
        return ResponseEntity.ok().build();
    }
}