/**
 * REST controller for warranty claim APIs.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.web;

import com.scania.warranty.dto.ClaimListItemDto;
import com.scania.warranty.service.ClaimSubfileService;
import com.scania.warranty.domain.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for claim subfile operations (optional, for web UI).
 */
@RestController
@RequestMapping("/api/claims/subfile")
public class ClaimSubfileController {

    private final ClaimSubfileService claimSubfileService;

    @Autowired
    public ClaimSubfileController(ClaimSubfileService claimSubfileService) {
        this.claimSubfileService = claimSubfileService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Claim>> listClaims(
            @RequestParam String pakz,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false, defaultValue = "0") Integer filterAgeDays,
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String filterOpen,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) String statusFilter,
            // @origin HS1210 L856-856 (SETOFF)
            @RequestParam(required = false) String zeichen) {
        LocalDate currentDate = LocalDate.now();
        List<Claim> claims = claimSubfileService.buildClaimSubfile(pakz, ascending, filterAgeDays != null ? filterAgeDays : 0, filterType, filterOpen, searchString, statusFilter, zeichen, currentDate);
        return ResponseEntity.ok(claims);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClaim(
            @RequestParam String pakz,
            @RequestParam String rechNr,
            @RequestParam String rechDatum,
            @RequestParam String auftragsNr,
            @RequestParam String wete,
            @RequestParam String bereich,
            // @origin HS1210 L1709-1709 (EXSR)
            @RequestParam String claimNr) {
        try {
            claimSubfileService.createClaimFromInvoice(pakz, rechNr, rechDatum, auftragsNr, wete, bereich, claimNr);
            return ResponseEntity.ok("Claim created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteClaim(
            @RequestParam String pakz,
            @RequestParam String rechNr,
            @RequestParam String rechDatum,
            @RequestParam String auftragsNr,
            @RequestParam String wete,
            @RequestParam String bereich,
            // @origin HS1210 L1077-1077 (SETOFF)
            @RequestParam String claimNr) {
        claimSubfileService.deleteClaimAndRelatedData(pakz, rechNr, rechDatum, auftragsNr, wete, bereich, claimNr);
        return ResponseEntity.ok("Claim deleted successfully");
    }

    @PostMapping("/request-warranty-release")
    public ResponseEntity<String> requestWarrantyRelease(
            @RequestParam String pakz,
            @RequestParam String rechNr,
            @RequestParam String rechDatum,
            @RequestParam(required = false) String fgnr,
            @RequestParam(required = false) String repDat,
            // @origin HS1210 L2710-2729 (IF)
            @RequestParam String auftragsNr) {
        claimSubfileService.requestWarrantyRelease(pakz, rechNr, rechDatum, fgnr, repDat, auftragsNr);
        return ResponseEntity.ok("Warranty release requested");
    }
}