/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.CheckClaimRequest;
import com.scania.warranty.domain.Hsgpspf;
import com.scania.warranty.dto.CheckClaimResultDto;
import com.scania.warranty.repository.HsgpspfRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckClaimService {

    private final HsgpspfRepository hsgpspfRepository;
    private final ClaimValueService claimValueService;
    private final ClaimErrorUpdateService claimErrorUpdateService;

    public CheckClaimService(HsgpspfRepository hsgpspfRepository,
                             ClaimValueService claimValueService,
                             ClaimErrorUpdateService claimErrorUpdateService) {
        this.hsgpspfRepository = hsgpspfRepository;
        this.claimValueService = claimValueService;
        this.claimErrorUpdateService = claimErrorUpdateService;
    }

    @Transactional
    public CheckClaimResultDto checkClaim(CheckClaimRequest request) {
        List<String> errors = new ArrayList<>(); // @rpg-trace: n1922
        int counter = 0; // @rpg-trace: n1922
        boolean error = false; // @rpg-trace: n1922

        String g73360Current = request.g73360(); // local mutable copy for potential update

        // Check: cause is campaign ('3') but no campaign number entered
        if ("3".equals(trimToEmpty(request.g73360())) && isBlank(request.g73280())) { // @rpg-trace: n1924
            counter += 1; // @rpg-trace: n1926
            errors.add("Ursache Kampagne aber keine Kampagnen-Nr. eingetragen."); // @rpg-trace: n1927
            g73360Current = ""; // @rpg-trace: n1928
            claimErrorUpdateService.clearDamageCodeCustomer(request.g73000(), request.g73050(), request.g73060()); // @rpg-trace: n1929
        }

        // Check: damage code customer is blank
        if (isBlank(g73360Current)) { // @rpg-trace: n1932
            counter += 1; // @rpg-trace: n1933
            errors.add("Schadenscodierung Kunde ungültig."); // @rpg-trace: n1934
        }

        // Check: damage code customer is '3' but sub-codes are not blank
        if ("3".equals(trimToEmpty(g73360Current)) && (!isBlank(request.g73370()) || !isBlank(request.g73380()))) { // @rpg-trace: n1937
            counter += 1; // @rpg-trace: n1939
            errors.add("Schadenscodierung Kunde ungültig."); // @rpg-trace: n1940
        }

        // Check: damage code workshop is blank
        if (isBlank(request.g73390())) { // @rpg-trace: n1943
            counter += 1; // @rpg-trace: n1944
            errors.add("Schadenscodierung Werkstatt ungültig."); // @rpg-trace: n1945
        }

        // Check: damage code workshop is '97' but sub-codes are not blank
        if ("97".equals(trimToEmpty(request.g73390())) && (!isBlank(request.g73400()) || !isBlank(request.g73410()))) { // @rpg-trace: n1948
            counter += 1; // @rpg-trace: n1950
            errors.add("Schadenscodierung Werkstatt ungültig."); // @rpg-trace: n1951
        }

        // Check: no damage-causing part number specified
        if (isBlank(request.g73070())) { // @rpg-trace: n1954
            counter += 1; // @rpg-trace: n1955
            errors.add("Es ist keine schadensverursachende Teilenummer angegeben."); // @rpg-trace: n1956
        }

        // Read HSGPSPF records matching composite key and check for SMA special costs without coding
        List<Hsgpspf> specialCostRecords = hsgpspfRepository.findByCompositeKey(
                request.g73000(), request.g73050(), request.g73060()); // @rpg-trace: n1958

        for (Hsgpspf record : specialCostRecords) { // @rpg-trace: n1959
            if (!error) { // @rpg-trace: n1960
                if ("SMA".equals(trimToEmpty(record.getGps040())) // @rpg-trace: n1963
                        && (isBlank(record.getGps220()) || isBlank(record.getGps240()))) { // @rpg-trace: n1963
                    error = true; // @rpg-trace: n1964
                }
            }
        }

        if (error) { // @rpg-trace: n1967
            counter += 1; // @rpg-trace: n1969
            errors.add("Es sind Sonderkosten ohne Codierung vorhanden."); // @rpg-trace: n1970
        }

        // Check: claim value is 0 - cannot be sent
        BigDecimal claimValue = claimValueService.getClaimValues(
                "Requested", request.g73000(), request.g73050(), request.g73060(), request.g73065()); // @rpg-trace: n1972
        if (claimValue != null && claimValue.compareTo(BigDecimal.ZERO) == 0) { // @rpg-trace: n1973
            counter += 1; // @rpg-trace: n1974
            errors.add("Antrag mit Wert 0 kann nicht versendet werden."); // @rpg-trace: n1975
        }

        // If errors exist, return invalid result
        if (counter > 0 && !errors.isEmpty()) { // @rpg-trace: n1978
            return new CheckClaimResultDto(false, errors, g73360Current); // @rpg-trace: n1979
        }

        return new CheckClaimResultDto(true, List.of(), g73360Current); // @rpg-trace: n1981
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}