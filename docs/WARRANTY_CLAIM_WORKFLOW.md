# Warranty Claim Processing – End-to-End Workflow

## 1. Objective

This document describes the validation workflow for creating and transmitting a warranty claim based on an existing workorder.

## 2. Business Preconditions

A claim can only be created if all of the following conditions are fulfilled:

| Condition | Description |
|-----------|-------------|
| Valid Workorder | A workorder exists in the system |
| Workorder Structure | Invoice Number (AHK010), Invoice Date (AHK020), Order Number (AHK040), Split (AHK070), Area (AHK050), Type (AHK060) must match |
| Repair Date Rule | The workorder creation date (AHK620) must not be older than 19 days |
| Duplicate Prevention | No claim already exists for this workorder |

Failure to meet any of these conditions blocks claim creation.

## 3. Process Overview

### Phase 1 – Claim Creation

- User selects F6 – CREATE in HS1210
- User enters workorder data (Invoice Number, Order Number, Split, Area, Type)
- System validates:
  - Workorder existence
  - Date rule (≤ 19 days)
  - No duplicate claim
- System creates claim header with the next available claim number
- Initial claim status: **00 – OPEN**

### Phase 2 – Failure Assignment (Mandatory Step)

- A claim cannot be transmitted without at least one failure.
- From HS1210, user opens the claim using Option 2 – EDIT
- In HS1212, user selects F6 – CREATE to create a failure
- (Maximum of 9 failures allowed per claim)
- Mandatory failure fields must be completed in HS1212W6, including:
  - Description
  - Failed Part
  - Demand Code
  - (and other required fields)
- User confirms entry and returns to overview screen HS1212C1/C2

### Phase 3 – Transmission

- User selects Option 10 – SEND
- System validates:
  - At least one failure exists
  - Claim is technically complete and eligible for transmission
- System calls factory interface WP_SC01
- If transmission is successful (RC = 0):
  - Claim status changes to **10 – SENT**
- If transmission fails:
  - Status remains unchanged
  - An error message is displayed

## 4. Process Summary

Claim lifecycle: **OPEN** → Failure Added → **SEND** → **SENT**
