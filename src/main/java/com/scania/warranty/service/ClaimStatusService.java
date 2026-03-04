/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimError;
import com.scania.warranty.repository.ClaimErrorRepository;
import com.scania.warranty.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service for claim status updates.
 */
@Service
@Transactional
public class ClaimStatusService {

    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;

    @Autowired
    public ClaimStatusService(ClaimRepository claimRepository, ClaimErrorRepository claimErrorRepository) {
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
    }

    public void updateClaimStatus(String companyCode, String claimNumber, int newStatus) {
        // @origin HS1210 L941-941 (CHAIN)
        Optional<Claim> claimOpt = claimRepository.findByClaimNumber(companyCode, claimNumber);

        // @origin HS1210 L830-833 (IF)
        if (claimOpt.isEmpty()) {
            // @origin HS1210 L895-895 (EXSR)
            throw new IllegalArgumentException("Claim not found");
        }

        Claim claim = claimOpt.get();

        // @origin HS1210 L841-844 (IF)
        if (claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(2) == 0) {
            // @origin HS1210 L887-887 (EVAL)
            claim.setStatusCodeSde(3);
            // @origin HS1210 L860-860 (WRITE)
            claimRepository.save(claim);
        }
    }

    public void deleteClaimAndErrors(String companyCode, String claimNumber) {
        // @origin HS1210 L1027-1027 (CHAIN)
        Optional<Claim> claimOpt = claimRepository.findByClaimNumber(companyCode, claimNumber);

        // @origin HS1210 L845-848 (IF)
        if (claimOpt.isEmpty()) {
            // @origin HS1210 L926-926 (EXSR)
            throw new IllegalArgumentException("Claim not found");
        }

        Claim claim = claimOpt.get();
        // @origin HS1210 L890-890 (EVAL)
        claim.setStatusCodeSde(99);
        // @origin HS1210 L861-861 (WRITE)
        claimRepository.save(claim);

        // @origin HS1210 L1035-1035 (CHAIN)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(companyCode, claimNumber);
        // @origin HS1210 L884-1012 (DOW)
        for (ClaimError error : errors) {
            claimErrorRepository.delete(error);
        }
    }

    public void postMinimumClaim(String companyCode, String claimNumber) {
        // @origin HS1210 L1100-1100 (CHAIN)
        Optional<Claim> claimOpt = claimRepository.findByClaimNumber(companyCode, claimNumber);

        // @origin HS1210 L864-883 (IF)
        if (claimOpt.isEmpty()) {
            // @origin HS1210 L934-934 (EXSR)
            throw new IllegalArgumentException("Claim not found");
        }

        Claim claim = claimOpt.get();

        // @origin HS1210 L886-892 (IF)
        if (claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(5) == 0) {
            // @origin HS1210 L898-898 (EVAL)
            claim.setStatusCodeSde(20);
            // @origin HS1210 L989-989 (WRITE)
            claimRepository.save(claim);
        }
    }
}