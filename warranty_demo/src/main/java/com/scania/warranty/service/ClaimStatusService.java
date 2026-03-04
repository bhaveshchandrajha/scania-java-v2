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
        // @origin HS1210 L2838-2838 (CHAIN)
        Optional<Claim> claimOpt = claimRepository.findByClaimNumber(companyCode, claimNumber);

        // @origin HS1210 L2818-2831 (IF)
        if (claimOpt.isEmpty()) {
            throw new IllegalArgumentException("Claim not found");
        }

        Claim claim = claimOpt.get();

        // @origin HS1210 L2832-2868 (IF)
        if (claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(2)) == 0) {
            claim.setStatusCodeSde(3);
            // @origin HS1210 L2877-2877 (WRITE)
            claimRepository.save(claim);
        }
    }

    public void deleteClaimAndErrors(String companyCode, String claimNumber) {
        // @origin HS1210 L2874-2874 (CHAIN)
        Optional<Claim> claimOpt = claimRepository.findByClaimNumber(companyCode, claimNumber);

        // @origin HS1210 L2837-2845 (IF)
        if (claimOpt.isEmpty()) {
            throw new IllegalArgumentException("Claim not found");
        }

        Claim claim = claimOpt.get();
        claim.setStatusCodeSde(99);
        claimRepository.save(claim);

        // @origin HS1210 L2815-2815 (CHAIN)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(companyCode, claimNumber);
        // @origin HS1210 L2817-2870 (DOW)
        for (ClaimError error : errors) {
            claimErrorRepository.delete(error);
        }
    }

    public void postMinimumClaim(String companyCode, String claimNumber) {
        Optional<Claim> claimOpt = claimRepository.findByClaimNumber(companyCode, claimNumber);

        // @origin HS1210 L2839-2844 (IF)
        if (claimOpt.isEmpty()) {
            throw new IllegalArgumentException("Claim not found");
        }

        Claim claim = claimOpt.get();

        // @origin HS1210 L2840-2843 (IF)
        if (claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(5)) == 0) {
            claim.setStatusCodeSde(20);
            claimRepository.save(claim);
        }
    }
}